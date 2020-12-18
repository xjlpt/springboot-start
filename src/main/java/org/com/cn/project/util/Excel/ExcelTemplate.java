package org.com.cn.project.util.Excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ExcelTemplate {
    /**
     * <p>下载文件并删除源文件</p>
     *
     * @param filePath     源文件地址
     * @param
     * @param response
     * @throws IOException
     */
    public static void downloadWithDelete(String filePath, String fileName, HttpServletResponse response) {
        File file = new File(filePath);
        OutputStream out = null;
        BufferedInputStream br = null;
        try {
            out = response.getOutputStream();
            br = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            // 非常重要
//            response.setContentType("application/x-msdownload");
            // 采用中文文件名需要在此处转码
            fileName = new String(fileName.getBytes("GB2312"), "ISO_8859_1");
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 下载后删除文件
        if (file.exists() && file.isFile()) {
            file.delete();
        }
    }

    /**
     * <p>下载文件</p>
     *
     * @param filePath     源文件地址
     * @param downloadName 下载后改名，如不需要改名置为空
     * @param response
     * @throws IOException
     */
    public static void download(String filePath, String downloadName, HttpServletResponse response) {
        File file = new File(filePath);
        OutputStream out = null;
        BufferedInputStream br = null;
        try {
            out = response.getOutputStream();
            br = new BufferedInputStream(new FileInputStream(file));
            byte[] buf = new byte[1024];
            int len = 0;
            response.reset();
            // 非常重要
            response.setContentType("application/x-msdownload");
            if (StringUtils.isNotBlank(downloadName)) {
                // 采用中文文件名需要在此处转码
                String fileName = new String(downloadName.getBytes("GB2312"), "ISO_8859_1");
                response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            }
            while ((len = br.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @描述 根据指定的excel模板生成文档（.xlsx）
     * @param data               需要导出的数据
     * @param clazz
     * @param columnArr          模板表头对应的字段名数组,如果第一列为自然序号，数组0位置请留空
     * @param firstColumnIsIndex 第一列是否为序号，true-是，false-否
     * @param sheetName          需要将第一个sheet修改的名字（不修改可为null）
     * @param templateFilePath   模板文件
     * @param targetFilePath     目标文件
     * @throws Exception
     */
    public static boolean generateExcel(List<?> data, Class<?> clazz, String[] columnArr, boolean firstColumnIsIndex,
                                        String sheetName, String templateFilePath, String targetFilePath) {
        // 创建Excel文件的输入流对象
        FileInputStream fis = null;
        // 创建Excel文件输出流对象
        FileOutputStream fos = null;
        try {
            fis = new FileInputStream(templateFilePath);
            // 根据模板创建excel工作簿
            XSSFWorkbook workBook = new XSSFWorkbook(fis);
            fos = new FileOutputStream(targetFilePath);

            // 获取创建的工作簿第一页
            XSSFSheet sheet = workBook.getSheetAt(0);
            if (StringUtils.isNotBlank(sheetName)) {
                // 给指定的sheet命名
                workBook.setSheetName(0, sheetName);
            }

            // 获取当前sheet最后一行数据对应的行索引
            int currentLastRowIndex = sheet.getLastRowNum();

            if (null == data) {
                data = new ArrayList();
            }

            for (int i = 0; i < data.size(); i++) {
                int curIndex = currentLastRowIndex + (i + 1);

                XSSFRow newRow = sheet.createRow(curIndex);
                // 开始创建并设置该行每一单元格的信息，该行单元格的索引从 0 开始
                int cellIndex = 0;

                Object obj = data.get(i);

                // 获取表格样式，边框、自动换行、垂直居中
                XSSFCellStyle cellStyle = getBaseCellStyle(workBook, false, (short)0);

                for (int j = 0; j < columnArr.length; j++) {
                    String column = columnArr[j];

                    // 判断第一列是不是为序号
                    if (j == 0 && firstColumnIsIndex) {
                        XSSFCell newNameCell = newRow.createCell(cellIndex++, CellType.forInt(1));//CELL_TYPE_STRING
                        newNameCell.setCellStyle(cellStyle);
                        newNameCell.setCellValue(i + 1);
                    } else {
                        Field field = clazz.getDeclaredField(column);
                        field.setAccessible(true);
                        Object fieldValue = field.get(obj);

                        boolean flag = false;
                        if("adjustNumStr".equals(field.getName())) {
                            Field tempField = clazz.getDeclaredField("adjustNum");
                            tempField.setAccessible(true);
                            Object tempValue = tempField.get(obj);
                            if(null != tempValue) {
                                try {
                                    if((int)tempValue > 1) {
                                        flag = true;
                                        cellStyle = getBaseCellStyle(workBook, true, (short) 10);
                                    }
                                } catch (Exception e) {
                                }
                            }
                        }

                        // 创建一个单元格，设置其内的数据格式为字符串，并填充内容，其余单元格类同
                            XSSFCell newNameCell = newRow.createCell(cellIndex++, CellType.forInt(1));
                        newNameCell.setCellStyle(cellStyle);
                        if (fieldValue == null || fieldValue == "") {
                            newNameCell.setCellValue("");
                        }else {
                            String type = field.getType().toString();
                            if(type.endsWith("Date")) {
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                Date dateValue = (Date) fieldValue;
                                String str1 = sdf2.format(dateValue);
                                if(str1.endsWith("00:00:00")) {
                                    newNameCell.setCellValue(sdf.format(dateValue));
                                }else {
                                    newNameCell.setCellValue(str1);
                                }

                            }else {
                                newNameCell.setCellValue(fieldValue.toString());
                            }


                        }

                        //单元格格式设置回去
                        if("adjustNumStr".equals(field.getName()) && flag) {
                            cellStyle = getBaseCellStyle(workBook, false,  (short) 10);
                        }
                    }
                }
            }
            workBook.write(fos);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭流
            try {
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 基础样式
     * @param workbook
     * @param setFontColor 是否需要设置字体颜色
     * @param index 字体字号
     * @return
     */
    private static XSSFCellStyle getBaseCellStyle(XSSFWorkbook workbook, boolean setFontColor, short index) {
        XSSFCellStyle style = workbook.createCellStyle();
        // 下边框
        style.setBorderBottom(BorderStyle.THIN);
        // 左边框
        style.setBorderLeft(BorderStyle.THIN);
        // 上边框
        style.setBorderTop(BorderStyle.THIN);
        // 右边框
        style.setBorderRight(BorderStyle.THIN);
        // 水平居中
        style.setAlignment(HorizontalAlignment.CENTER);
        // 上下居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置自动换行
        style.setWrapText(true);

        if(setFontColor) {
            Font font = workbook.createFont();
            font.setColor(index);
            style.setFont(font);
        }
        return style;
    }

}
