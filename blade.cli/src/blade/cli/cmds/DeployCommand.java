package blade.cli.cmds;

import blade.cli.DeployOptions;
import blade.cli.blade;
import blade.cli.util.FileWatcher;

import java.io.File;
import java.util.Collections;
import java.util.Formatter;
import java.util.List;

import aQute.bnd.osgi.Jar;
import aQute.remote.util.JMXBundleDeployer;

public class DeployCommand {

	final private blade blade;
	final private DeployOptions options;
	private JMXBundleDeployer _bundleDeployer;

	public DeployCommand(blade blade, DeployOptions options) throws Exception {
		this.blade = blade;
		this.options = options;

		List<String> args = options._();

		if (args.size() == 0) {

			// Default command

			printHelp();
		}
		else {
			execute();
		}
	}

	private void addError(String prefix, String msg) {
		blade.addErrors(prefix, Collections.singleton(msg));
	}

	private void deploy(JMXBundleDeployer bundleDeployer, String bsn,
			File bundleFile) throws Exception {

		final long bundleId = bundleDeployer.deploy(bsn, bundleFile);
		blade.out().println("Installed or updated bundle " + bundleId);

		if (bundleId <= 0) {
			addError(
				"Deploy", "Unable to deploy bundle to framework " + bundleId);
		}
	}

	private void execute() throws Exception {
		int numOfFiles = options._().size();

		for (int i = 0; i < numOfFiles; i++) {
			String bundlePath = options._().get(i);

			File bundleFile = new File(bundlePath);

			if (!bundleFile.exists() && !bundleFile.isAbsolute()) {
				bundleFile = new File(blade.getBase(), bundlePath);
			}

			if (bundleFile.exists()) {
				String bsn = null;

				try (Jar jar = new Jar(bundleFile)) {
					bsn = jar.getBsn();
				}

				if (bsn == null) {
					addError("Deploy", "Unable to determine bsn for file " +
						bundleFile.getAbsolutePath());
				}
				else {
					final JMXBundleDeployer bundleDeployer = getBundleDeployer();

					if (bundleDeployer != null) {
						deploy(bundleDeployer, bsn, bundleFile);

						if (options.watch()) {
							watch(bundleDeployer, bsn, bundleFile);
						}
					}
				}
			}
			else {
				addError("Deploy", "Unable to find specified bundle file " +
						bundleFile.getAbsolutePath());
			}
		}
	}

	private JMXBundleDeployer getBundleDeployer() {
		if (_bundleDeployer == null) {
			int port = options.port();

			JMXBundleDeployer bundleDeployer = null;

			try {
				if (port > 0) {
					bundleDeployer = new JMXBundleDeployer(port);
				}
				else {
					bundleDeployer = new JMXBundleDeployer();
				}
			}
			catch (IllegalArgumentException e) {
				addError("Deploy", "Unable to connect to Liferay's OSGi framework");
			}

			_bundleDeployer = bundleDeployer;
		}

		return _bundleDeployer;
	}

	private void printHelp() throws Exception {
		Formatter f = new Formatter();
		options._command().help(f, this);
		blade.out().println(f);
		f.close();
	}

	private void watch(final JMXBundleDeployer bundleDeployer, final String bsn,
			final File bundleFile) throws Exception {

		final boolean[] deploy = new boolean[1];

		new Thread() {
			@Override
			public void run() {
				synchronized (bundleFile) {
					while (true) {
						try {
							bundleFile.wait();
						} catch (InterruptedException e) {
						}

						while (deploy[0]) {
							deploy[0] = false;

							try {
								bundleFile.wait(300);
							} catch (InterruptedException e) {
							}
						}

						deploy[0] = false;

						try {
							long bundleId = bundleDeployer.deploy(
								bsn, bundleFile);
							blade.out().println("Installed or updated bundle " + bundleId);
						} catch (Exception e) {
						}
					}
				}
			}
		}.start();

		new FileWatcher(blade.getBase().toPath(), bundleFile.getAbsoluteFile()
				.toPath(), true, new Runnable() {
			@Override
			public void run() {
				synchronized (blade) {
					deploy[0] = true;
					bundleFile.notify();
				}
			}
		});
	}

}