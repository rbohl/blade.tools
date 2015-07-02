package blade.migrate.provider;

import blade.migrate.api.FileMigrator;
import blade.migrate.api.Migration;
import blade.migrate.api.Problem;
import blade.migrate.api.ProgressMonitor;
import blade.migrate.api.ProjectMigrator;
import blade.migrate.api.Reporter;

import java.io.File;
import java.io.IOException;

import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

@Component(immediate = true)
public class ProjectMigrationService implements Migration {

	@Activate
	public void activate(BundleContext context) {
		this.context = context;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY
	)
	public void setProgressMonitor(ProgressMonitor progressMonitor) {
		this.progressMonitor = progressMonitor;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeFileMigrator"

	)
	public void addFileMigrator(ServiceReference<FileMigrator> fileMigrator) {
		fileMigrators.add(fileMigrator);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeProjectMigrator"
	)
	public void addProjectMigrator(ProjectMigrator projectMigrator) {
		projectMigrators.add(projectMigrator);
	}

	@Override
	public List<Problem> findProblems(File projectDir) {
		this.progressMonitor.beginTask("Migrating directory " + projectDir, -1);
		this.progressMonitor.done();

		final List<Problem> problems = new ArrayList<>();

		for (ProjectMigrator projectMigrator : projectMigrators) {
			try {
				List<Problem> migrationProblems = projectMigrator.analyze(projectDir);

				problems.addAll(migrationProblems);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.progressMonitor.beginTask("Analyzing files", -1);

		walkFiles(projectDir, problems);

		this.progressMonitor.done();

		return problems;
	}

	public void removeFileMigrator(
		ServiceReference<FileMigrator> fileMigrator) {
		fileMigrators.remove(fileMigrator);
	}

	public void removeProjectMigrator(ProjectMigrator projectMigrator) {
		projectMigrators.remove(projectMigrator);
	}

	@Override
	public void reportProblems(File projectDir, int format) {
		List<Problem> problems = findProblems(projectDir);

		ServiceReference<Reporter> sr = this.context.getServiceReference(Reporter.class);
		Reporter reporter = this.context.getService(sr);

		if (problems.size() != 0) {
			reporter.beginReporting(format);

			for (Problem problem : problems) {
				reporter.report(problem);
			}

			reporter.endReporting();
		}
	}

	private void walkFiles(final File dir, final List<Problem> problems) {
		final FileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(
					Path path, BasicFileAttributes attrs)
				throws IOException {
				File file = path.toFile();

				if (file.isFile())
				{
					String fileName = file.toPath().getFileName().toString();
					String extension = fileName.substring(
						fileName.lastIndexOf('.')+1);

					for ( ServiceReference<FileMigrator> fm : fileMigrators ) {
						Object fileExt = fm.getProperty("file.extension");

						if ( extension.equals(fileExt) ) {
							FileMigrator fmigrator = context.getService(fm);

							progressMonitor.setTaskName("Analyzing file " + fileName);

							List<Problem> fileProblems = fmigrator.analyzeFile(
								file);

							if ( fileProblems != null &&
								fileProblems.size() > 0) {

								problems.addAll(fileProblems);
							}

							context.ungetService(fm);
						}
					}
				}

				return super.visitFile(path, attrs);
			}
		};

		try {
			Files.walkFileTree(dir.toPath(), visitor);
		} catch (IOException e) {

			// TODO properly log exception

			e.printStackTrace();
		}
	}

	private BundleContext context;
	private Set<ServiceReference<FileMigrator>> fileMigrators = new HashSet<>();
	private ProgressMonitor progressMonitor;
	private Set<ProjectMigrator> projectMigrators = new HashSet<>();

}