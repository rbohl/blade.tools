package com.liferay.test;

import com.liferay.portal.kernel.search.HitsImpl;
import com.liferay.portlet.wiki.util.WikiUtil;
import com.liferay.portal.util.PortalUtil;

public class TestUtil {

    public void wikiUtilGetEntries() {
        WikiUtil.getEntries(new HitsImpl());
    }

    public void portalUtilGetClassNamePortletId() {
        PortalUtil.getClassNamePortletId("");
    }

}
