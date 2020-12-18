package org.com.cn.project.util.Excel;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExcelUtils {

    /**
     * 导入数据
     * @param <T>
     * @param excelFile 文件流
     * @param rowheads 模板表头
     * @param rowheadColumns 表头对应的属性
     * @param clazz
     * @param requiredInfo 必填参数
     * @param errorMsg 错误信息
     * @param firstIsIndex 第一列是否为虚拟序号
     * @return
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> readExcel(MultipartFile excelFile, String[] rowheads, String[] rowheadColumns, Class<?> clazz, Map<String, String> requiredInfo, Map<String, String> errorMsg, boolean firstIsIndex) throws IOException, InstantiationException, IllegalAccessException {
        if(null == requiredInfo) {
            requiredInfo = new HashMap<String, String>();
        }
        if(null == errorMsg) {
            errorMsg = new HashMap<String, String>();
        }

        Workbook wookbook = null;
        Sheet sheet = null;
        if (excelFile.getOriginalFilename().endsWith(".xls")) {
            // 2003版本的excel，用.xls结尾
            wookbook = new HSSFWorkbook(excelFile.getInputStream());// 得到工作簿
        } else if (excelFile.getOriginalFilename().endsWith(".xlsx")) {
            // 2007版本的excel，用.xlsx结尾
            wookbook = new XSSFWorkbook(excelFile.getInputStream());// 得到工作簿
        }

        if (wookbook == null) {
            errorMsg.put("msg", "文件内容为空");
            return null;
        }
        sheet = wookbook.getSheetAt(0);
        if (sheet == null) {
            errorMsg.put("msg", "文件内容为空");
            return null;
        }
        return getData(sheet, rowheads, rowheadColumns, clazz, requiredInfo, errorMsg, firstIsIndex);
    }

    /**
     * 获取sheet数据
     * @param <T>
     * @param sheet
     * @param rowheads 模板表头
     * @param rowheadColumns 表头对应的属性
     * @param clazz
     * @param requiredInfo 必填参数
     * @param errorMsg 错误信息
     * @param firstIsIndex 第一列是否为虚拟序号
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    private static <T> List<T> getData(Sheet sheet, String[] rowheads, String[] rowheadColumns, Class<?> clazz, Map<String, String> requiredInfo, Map<String, String> errorMsg, boolean firstIsIndex) throws InstantiationException, IllegalAccessException {
        List<T> result = new ArrayList<T>();
        // 获得表头
        Row rowHead = sheet.getRow(0);
        if (rowHead == null) {
            errorMsg.put("msg", "文件内容为空");
            return null;
        }
        if (rowHead.getPhysicalNumberOfCells() != rowheads.length) {
            errorMsg.put("msg", "请选择正确的数据模板！");
            return null;
        }
        for(int i = 0; i < rowheads.length; i++) {
            String str1 = rowHead.getCell(i).getStringCellValue().toString();;
            String str2 = rowheads[i];
            if(!str1.equals(str2)) {
                errorMsg.put("msg", "请选择正确的数据模板！");
                return null;
            }
        }

        //Class<?> clazz = BudgetYishangModel.class;

        //获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();
        for(int i = 1 ; i <= totalRowNum ; i++){
            T obj = (T) clazz.newInstance();
            //获得第i行对象
            Row row = sheet.getRow(i);

            int index = 0;
            //如果第一列为序号则从第二列（第二次循环）取值
            if(firstIsIndex) {
                index = 1;
            }

            for(int j=index ;j < rowheads.length;j++){
                Cell cell = row.getCell((short)j);
                String value = getValue(cell);
                if(StringUtils.isBlank(value)) {
                    if(StringUtils.isNotBlank(requiredInfo.get(j+""))) {
                        errorMsg.put("msg", "sheet："+sheet.getSheetName()+" 第"+(i+1)+"行第"+(j+1)+"列：" + requiredInfo.get(j+""));
                        return null;
                    }
                }
                String column = rowheadColumns[j];
                try {
                    Field field = clazz.getDeclaredField(column);
                    setFieldValue(obj, field, value);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (SecurityException e) {
                    e.printStackTrace();
                }
            }
            result.add(obj);
        }
        //return "success";
        return result;
    }

    /**
     * 利用反射原理给对象的对应属性赋值
     * @param obj 要赋值的对象
     * @param field 属性
     * @param value 值
     */
    private static void setFieldValue(Object obj, Field field, String value) {
        field.setAccessible(true);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //System.out.println(field.getType().toString());
        String type = field.getType().toString();
        try {
            if(type.endsWith("String")) {
                field.set(obj, value);
            }else if(type.endsWith("Integer") || type.endsWith("int")) {
                if(StringUtils.isBlank(value)) {
                    value = "0";
                }
                field.set(obj, Integer.parseInt(value));
            }else if(type.endsWith("Double") || type.endsWith("double")) {
                if(StringUtils.isBlank(value)) {
                    value = "0";
                }
                field.set(obj, Double.parseDouble(value));
            }else if(type.endsWith("Boolean") || type.endsWith("boolean")) {
                field.set(obj, Boolean.parseBoolean(value));
            }else if(type.endsWith("Date")) {
                if(value.length() < 19) {
                    field.set(obj, sdf.parse(value));
                }else {
                    field.set(obj, sdf2.parse(value));
                }
            }else {

            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将excel内容转换成对应的值
     * @param cell
     * @return
     */
    private static String getValue(Cell cell) {
        String value = null;
        if(null == cell) {
            return "";
        }
        CellType cellType = cell.getCellType();
        switch(cellType) {
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    value = sdf.format(date);
                }else {
                    value = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
                break;
            case STRING:
                value = cell.getStringCellValue();
                break;
            case FORMULA:
                if(DateUtil.isCellDateFormatted(cell)){
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = DateUtil.getJavaDate(cell.getNumericCellValue());
                    value = sdf.format(date);
                }else {
                    value = new DecimalFormat("#.##").format(cell.getNumericCellValue());
                }
                break;
            case BLANK:
                value = "";
                break;
            default:
                System.out.println("未知类型：" + cellType);
                value = "";
                break;
        }
        return value;
    }


}
