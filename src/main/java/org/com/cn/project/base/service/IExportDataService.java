package org.com.cn.project.base.service;

import org.com.cn.project.base.enty.exportData;
import org.com.cn.project.global.Page;

import java.util.List;

public interface IExportDataService {

    List<exportData> getExportDataList(Page page);

    int getExportDataCount(Page page);

    List<exportData> getExportDataParams(exportData data);

}
