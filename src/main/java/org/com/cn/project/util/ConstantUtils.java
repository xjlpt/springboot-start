package org.com.cn.project.util;

import org.com.cn.project.global.Page;

public class ConstantUtils {

    public static final String[] TEMPLATE_EXPORT_COLUMN = {"", "userName", "userPass", "nickname"};
    public static final String[] TEMPLATE_IMPORT_COLUMN = {"userName", "userPass", "nickname"};

    public static final String[] TEMPLATE_IMPORT_TITLE = {"用户名", "用户密码", "昵称"};

    public static Page getPage(int limit, Page page) {
        Integer pag = page.getPage() * limit - limit;
        Integer row = page.getPage() * limit;
        page.setRows(row);
        page.setPage(pag);
        return page;
    }
}
