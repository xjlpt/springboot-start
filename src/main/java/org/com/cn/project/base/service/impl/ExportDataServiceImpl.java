package org.com.cn.project.base.service.impl;

import org.com.cn.project.base.dao.ExportDataMapper;
import org.com.cn.project.base.enty.exportData;
import org.com.cn.project.base.service.IExportDataService;
import org.com.cn.project.global.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
@Transactional
public class ExportDataServiceImpl implements IExportDataService {
    @Resource
    private ExportDataMapper exportDataMapper;
    @Override
    public List<exportData> getExportDataList(Page page) {
        return exportDataMapper.getExportDataList(page);
    }

    @Override
    public int getExportDataCount(Page page) {
        return exportDataMapper.getExportDataCount(page);
    }

    @Override
    public List<exportData> getExportDataParams(exportData data) {
        return exportDataMapper.getExportDataParams(data);
    }
}
