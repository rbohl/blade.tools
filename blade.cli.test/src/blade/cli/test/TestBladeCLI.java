package blade.cli.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import aQute.lib.io.IO;
import blade.cli.blade;

@SuppressWarnings("restriction")
public class TestBladeCLI {

	@Before
	public void setup() {
		File testdir = IO.getFile("generated/test");
		if(testdir.exists()) {
			IO.delete(testdir);
			assertFalse(testdir.exists());
		}
	}

	@Test
	public void createMavenPortletProject() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-d",
			"generated/test",
			"foo",
			"portlet"
		};

		new blade().run(args);

		assertTrue(IO.getFile("generated/test/foo").exists());

		assertTrue(IO.getFile("generated/test/foo/pom.xml").exists());

		File portletFile = IO.getFile("generated/test/foo/src/main/java/foo/Foo.java");

		assertTrue(portletFile.exists());

		String portletFileContent = new String(IO.read(portletFile));

		contains(portletFileContent, "^package foo;.*");

		contains(portletFileContent, ".*javax.portlet.display-name=foo.*");

		contains(portletFileContent, ".*^public class Foo.*");

		contains(portletFileContent, ".*printWriter.print\\(\\\"Foo Portlet.*");

		File pomFile = IO.getFile("generated/test/foo/pom.xml");

		assertTrue(pomFile.exists());

		String pomFileContent = new String(IO.read(pomFile));

		contains(pomFileContent, ".*<groupId>foo</groupId>.*");

		contains(pomFileContent, ".*<artifactId>foo</artifactId>.*");

		contains(pomFileContent, ".*<name>Foo</name>.*");

		contains(pomFileContent, ".*<Private-Package>foo</Private-Package>.*");

		File projectFile = IO.getFile("generated/test/foo/.project");

		assertTrue(projectFile.exists());

		String projectFileContent = new String(IO.read(projectFile));

		contains(projectFileContent, ".*<name>foo</name>.*");

		contains(projectFileContent, ".*<nature>org.eclipse.m2e.core.maven2Nature</nature>.*");
	}

	@Test
	public void createMavenServicePreAction() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-d",
			"generated/test",
			"servicepreaction",
			"service",
			"com.liferay.portal.kernel.events.LifecycleAction",
		};

		new blade().run(args);

		assertTrue(IO.getFile("generated/test/servicepreaction").exists());

		assertTrue(IO.getFile("generated/test/servicepreaction/pom.xml").exists());

		File serviceFile = IO.getFile("generated/test/servicepreaction/src/main/java/servicepreaction/Servicepreaction.java");

		assertTrue(serviceFile.exists());

		String serviceFileContent = new String(IO.read(serviceFile));

		contains(serviceFileContent, "^package servicepreaction;.*");

		contains(serviceFileContent, ".*^import com.liferay.portal.kernel.events.LifecycleAction;$.*");

		contains(serviceFileContent, ".*service = LifecycleAction.class.*");

		contains(serviceFileContent, ".*^public class Servicepreaction implements LifecycleAction \\{.*");
	}

	@Test
	public void createMavenServicePreActionClassname() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-d",
			"generated/test",
			"-c",
			"LoginPreAction",
			"loginpre",
			"service",
			"com.liferay.portal.kernel.events.LifecycleAction",
		};

		new blade().run(args);

		File serviceFile = IO.getFile("generated/test/loginpre/src/main/java/loginpre/LoginPreAction.java");

		assertTrue(serviceFile.exists());

		String serviceFileContent = new String(IO.read(serviceFile));

		contains(serviceFileContent, ".*^public class LoginPreAction implements LifecycleAction \\{.*");
	}

	@Test
	public void createMavenPackagePath() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-d",
			"generated/test",
			"-c",
			"PackagePathTest",
			"blade.package.path.test",
			"service",
			"com.liferay.portal.kernel.events.LifecycleAction",
		};

		new blade().run(args);

		File serviceFile = IO.getFile("generated/test/blade.package.path.test/src/main/java/blade/package/path/test/PackagePathTest.java");

		assertTrue(serviceFile.exists());

		String serviceFileContent = new String(IO.read(serviceFile));

		contains(serviceFileContent, "^package blade.package.path.test;.*");

		contains(serviceFileContent, ".*^public class PackagePathTest implements LifecycleAction \\{.*");

		File pomFile = IO.getFile("generated/test/blade.package.path.test/pom.xml");

		String pomFileContent = new String(IO.read(pomFile));

		contains(pomFileContent, ".*<Private-Package>blade.package.path.test</Private-Package>.*");
	}

	@Test
	public void createGradlePortletProject() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-b",
			"gradle",
			"-d",
			"generated/test",
			"-c",
			"Foo",
			"gradle.test",
			"portlet",
		};

		new blade().run(args);

		assertTrue(IO.getFile("generated/test/gradle.test/build.gradle").exists());

		File portletFile = IO.getFile("generated/test/gradle.test/src/main/java/gradle/test/Foo.java");

		assertTrue(portletFile.exists());

		String portletFileContent = new String(IO.read(portletFile));

		contains(portletFileContent, "^package gradle.test;.*");

		contains(portletFileContent, ".*javax.portlet.display-name=Gradle.test.*");

		contains(portletFileContent, ".*^public class Foo .*");

		contains(portletFileContent, ".*printWriter.print\\(\\\"Gradle.test Portlet.*");

		File bndFile = IO.getFile("generated/test/gradle.test/bnd.bnd");

		assertTrue(bndFile.exists());

		String bndFileContent = new String(IO.read(bndFile));

		contains(bndFileContent, ".*^Private-Package: gradle.test$.*");

		File projectFile = IO.getFile("generated/test/gradle.test/.project");

		assertTrue(projectFile.exists());

		String projectFileContent = new String(IO.read(projectFile));

		contains(projectFileContent, ".*<name>gradle.test</name>.*");

		contains(projectFileContent, ".*<nature>org.springsource.ide.eclipse.gradle.core.nature</nature>.*");
	}

	@Test
	public void createGradleServicePreAction() throws Exception {
		String[] args = new String[] {
			"-t",
			"create",
			"-b",
			"gradle",
			"-d",
			"generated/test",
			"servicepreaction",
			"service",
			"com.liferay.portal.kernel.events.LifecycleAction",
		};

		new blade().run(args);

		File buildFile = IO.getFile("generated/test/servicepreaction/build.gradle");

		assertTrue(buildFile.exists());

		String buildFileContent = new String(IO.read(buildFile));

		contains(buildFileContent, ".*compile 'com.liferay.portal:portal-service:7.0.0-SNAPSHOT'.*");

		File serviceFile = IO.getFile("generated/test/servicepreaction/src/main/java/servicepreaction/Servicepreaction.java");

		assertTrue(serviceFile.exists());

		String serviceFileContent = new String(IO.read(serviceFile));

		contains(serviceFileContent, "^package servicepreaction;.*");

		contains(serviceFileContent, ".*^import com.liferay.portal.kernel.events.LifecycleAction;$.*");

		contains(serviceFileContent, ".*service = LifecycleAction.class.*");

		contains(serviceFileContent, ".*^public class Servicepreaction implements LifecycleAction \\{.*");

		File bndFile = IO.getFile("generated/test/servicepreaction/bnd.bnd");

		assertTrue(bndFile.exists());

		String bndFileContent = new String(IO.read(bndFile));

		contains(bndFileContent, ".*com.liferay.portal.service;version=\"7.0.0\".*");
	}

	private void contains(String content, String pattern) {
		assertTrue(Pattern.compile(pattern, Pattern.MULTILINE | Pattern.DOTALL).matcher(content).matches());
	}
}
