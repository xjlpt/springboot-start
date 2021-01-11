package org.com.cn.project.util;

import org.com.cn.project.global.Page;

public class ConstantUtils {

    public static final String[] TEMPLATE_EXPORT_COLUMN = {"", "userName", "userPass", "nickname"};
    public static final String[] TEMPLATE_IMPORT_COLUMN = {"userName", "userPass", "nickname"};

    public static final String[] TEMPLATE_IMPORT_TITLE = {"用户名", "用户密码", "昵称"};

    public static final String[] TEMPLATE_IMPORT_INPUT_EXPORTDATA_COLUMN = {"stringCode","stringCodeStatus","distributionCounty","remark","intputDate"};
    public static final String[] TEMPLATE_IMPORT_INPUT_EXPORTDATA_TITLE = {"串码", "串码状态","分配县市", "串码类型","入库时间"};

    public static final String[] TEMPLATE_IMPORT_OUTPUT_EXPORTDATA_COLUMN = {"stringCode","stringCodeStatus"};
    public static final String[] TEMPLATE_IMPORT_OUTPUT_EXPORTDATA_TITLE = {"串码", "串码状态"};

    public static final String[] TEMPLATE_EXPORTDATA_COLUMN = {"","stringCode","stringCodeStatus","distributionCounty","remark","intputDate","outputDate"};


    public static Page getPage(int limit, Page page) {
        Integer pag = page.getPage() * limit - limit;
        Integer row = page.getPage() * limit;
        page.setRows(row);
        page.setPage(pag);
        return page;
    }
}
