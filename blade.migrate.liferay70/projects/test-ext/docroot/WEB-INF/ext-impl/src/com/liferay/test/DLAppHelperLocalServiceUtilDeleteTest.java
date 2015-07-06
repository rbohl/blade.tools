package com.liferay.test;

import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalServiceUtil;

public class DLAppHelperLocalServiceUtilDeleteTest {

    public void deleteFileEntry() {
        DLAppHelperLocalServiceUtil.deleteFileEntry(null);
    }

    public void deleteFolder() {
        DLAppHelperLocalServiceUtil.deleteFolder(null);
    }

}
