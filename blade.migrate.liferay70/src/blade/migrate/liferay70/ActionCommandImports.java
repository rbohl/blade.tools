package blade.migrate.liferay70;

import blade.migrate.api.FileMigrator;
import blade.migrate.core.JavaFileChecker;
import blade.migrate.core.JavaFileMigrator;
import blade.migrate.core.SearchResult;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;

@Component(
	property = {
		"file.extensions=java",
		"problem.summary=The classes from package com.liferay.util.bridges.mvc in util-bridges.jar were moved to a new package com.liferay.portal.kernel.portlet.bridges.mvc in portal-service.jar.",
		"problem.tickets=LPS-50156",
		"problem.title=Moved MVCPortlet, ActionCommand and ActionCommandCache from util-bridges.jar to portal-service.jar",
		"problem.url=https://github.com/liferay/liferay-portal/blob/master/readme/7.0/BREAKING_CHANGES.markdown#changed-the-assetrenderer-and-indexer-apis-to-include-the-portletrequest-and-portletresponse-parameters"
	},
	service = FileMigrator.class
)
public class ActionCommandImports extends JavaFileMigrator {

	private final static String[] imports = new String[] {
			"com.liferay.util.bridges.mvc.ActionCommand",
			"com.liferay.util.bridges.mvc.BaseActionCommand",
			"com.liferay.util.bridges.mvc.MVCPortlet"
	};

	@Override
	protected List<SearchResult> searchJavaFile(File file, JavaFileChecker javaFileChecker) {
		final List<SearchResult> searchResults = new ArrayList<>();

		for (String importName : imports) {
			final SearchResult importResult = javaFileChecker.findImport(importName);

			if (importResult != null) {
				searchResults.add(importResult);
			}
		}

		return searchResults;
	}
}
