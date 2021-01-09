package org.com.cn.project.base.dao;

import org.com.cn.project.base.enty.exportData;
import org.com.cn.project.global.Page;

import java.util.List;

public interface ExportDataMapper {

    List<exportData> getExportDataList(Page page);

    int getExportDataCount(Page page);

    List<exportData> getExportDataParams(exportData data);

}
