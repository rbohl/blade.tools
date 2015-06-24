package com.liferay.test;

import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portlet.wiki.util.WikiUtil;

public class TestUtil {

	public void wikiUtilGetEntries() {
		WikiUtil.getEntries(new HitsImpl());
	}
}
