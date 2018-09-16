package cn.org.continent.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Design By Scrooged
 * @version 1.0
 * @description 导入导出Excel的工具类
 * @date 2018/9/12 14:33
 */
public class ExcelUtil {

    private static Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

    public static boolean isExcel2003(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xls)$");
    }

    public static boolean isExcel2007(String filePath)
    {
        return filePath.matches("^.+\\.(?i)(xlsx)$");
    }

    /**
     * 导出Excel
     * @param filename 文件名 如：D:\用户信息.xls/.xlsx
     * @param titles 标题
     * @param list 读入的信息
     * @param sheetname 表名
     */
    private static void exportExcel(String filename, String[] titles, List<Object> list, String sheetname){
        //第一步，创建一个webBook，对应一个Excel文件
        Workbook wb = null;
        if(isExcel2007(filename)){
            wb = new XSSFWorkbook();
        }else{
            wb = new HSSFWorkbook();
        }
        //第二步，在webBook中添加一个sheet，对应Excel文件中的sheet
        Sheet sheet = wb.createSheet(sheetname);
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
        Row row = sheet.createRow(0);
        Cell cell = null;
        for (int i = 0; i < titles.length; i++) {
            sheet.setColumnWidth(i, 600*6);
            /*sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);*/
            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
        }
        //第四步，创建表格数据
        createTable(list, sheet, row, cell);
        //第五步，将文件存到指定位置
        readFile(wb, filename);
    }

    /**
     * 创建表格数据
     * @param list 读的数据
     * @param sheet Excel表名
     * @param row 行
     * @param cell 单元格
     */
    private static void createTable(List<Object> list, Sheet sheet, Row row, Cell cell){
        Field[] fields = null;
        int i = 1;
        for (Object obj:list) {
            fields = obj.getClass().getDeclaredFields();
            row = sheet.createRow(i);
            int j = 0;
            for (Field field:fields) {
                field.setAccessible(true);
                Object va = null;
                try {
                    va = field.get(obj);
                } catch (IllegalAccessException e) {
                    logger.error(e.getMessage());
                    e.printStackTrace();
                }
                if(null == va){
                    va = "";
                }
                cell = row.createCell(j);
                cell.setCellValue(va.toString());
                j++;
            }
            i++;
        }
    }

    /**
     * 将文件存到指定位置
     * @param workbook
     * @param filename 文件名 如：D:\用户信息.xls/.xlsx
     */
    private static void readFile(Workbook workbook, String filename){
        try {
            BufferedOutputStream bout = new BufferedOutputStream(new FileOutputStream(createFile(filename)));
            workbook.write(bout);
            bout.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 创建文件，如果当前目录下存在则在文件名后面加（(1),(2),(3),...），不存在则直接创建
     * @param fileName 文件名 如：D:\用户信息.xls/.xlsx
     * @return 创建的文件
     */
    private static File createFile(String fileName){
        //源文件
        File sourceFile = new File(fileName);
        //文件的完整名，如user.xls
        String filename = sourceFile.getName();
        //文件名，如user
        String name = filename.substring(0, filename.indexOf("."));
        //文件的后缀，如.xls
        String suffix = filename.substring(filename.lastIndexOf("."));
        //目标文件
        File descFile = new File(fileName.substring(0, fileName.length() - filename.length()) + File.separator + filename);
        int i = 1;
        //若文件存在重命名
        while (descFile.exists()){
            String newFilename = name + "(" + i + ")" + suffix;
            String parentPath = descFile.getParent();
            descFile = new File(parentPath + File.separator + newFilename);
        }
        return descFile;
    }

    /**
     * 导出Excel的模板
     * @param filename 文件名 如：D:\用户信息.xls/.xlsx
     * @param titles 标题
     * @param sheetname 表名
     * @param comboBox 下拉框的值
     */
    public static void exportExcelTemplate(String filename, String[] titles, String sheetname, Map<String, String[]> comboBox){
        //第一步，创建一个webBook，对应一个Excel文件
        Workbook wb = null;
        if(isExcel2007(filename)){
            wb = new XSSFWorkbook();
        }else{
            wb = new HSSFWorkbook();
        }
        //第二步，在webBook中添加一个sheet，对应Excel文件中的sheet
        Sheet sheet = wb.createSheet(sheetname);
        //第三步，在sheet中添加表头第0行，注意老版本poi对Excel的行数列数有限制short
        Row row = sheet.createRow(0);
        Cell cell = null;
        for (int i = 0; i < titles.length; i++) {
            sheet.setColumnWidth(i, 600*6);

            if("身份证号".equals(titles[i])){
                CellStyle cellStyle = wb.createCellStyle();
                DataFormat format = wb.createDataFormat();
                cellStyle.setDataFormat(format.getFormat("@"));
                sheet.setDefaultColumnStyle(i, cellStyle);//设置第i列为文本格式
            }

            cell = row.createCell(i);
            cell.setCellValue(titles[i]);
            if(comboBox.containsKey(titles[i])){
                ExcelUtil.serComboBox(sheet, comboBox.get(titles[i]), i);
            }
        }

        //第四步，将文件存到指定位置
        readFile(wb, filename);
    }

    /**
     * 导入时进行版本判断
     * @param filename 文件名 如：D:\用户信息.xls/.xlsx
     * @return 相应版本的Excel
     */
    private static Workbook getWorkbook(String filename){
        File file = new File(filename);
        Workbook wb = null;
        try {
            if(ExcelUtil.isExcel2007(file.getPath())){
                wb = new XSSFWorkbook(new FileInputStream(file));
            }else if(ExcelUtil.isExcel2003(file.getPath())){
                wb = new HSSFWorkbook();
            }else {
                throw new IllegalArgumentException("请导入Excel文件！");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wb;
    }

    /**
     * 导入Excel
     * @param filename 文件名 如：D:\用户信息.xls/.xlsx
     * @param object 类名
     * @return 导入的数据
     */
    public static List<Object> importExcel(String filename, Object object){
        Workbook wb = getWorkbook(filename);
        List<Object> objects = new ArrayList<>();
        //默认获得第一张
        Sheet sheet = wb.getSheetAt(0);
        //Sheet sheet = wb.getSheetAt("***");
        Field[] fields = object.getClass().getDeclaredFields();
        try {
            for (int i = 1; i <= sheet.getLastRowNum() ; i++) {
                //获得索引为i行，以0开始
                Row row = sheet.getRow(i);
                object = objects.getClass().newInstance();
                for (int j = 0; j < row.getLastCellNum(); j++) {
                    fields[j].setAccessible(true);
                    String cellValue = readCellValue(row.getCell(j));
                    if(fields[j].getType() == Integer.class || fields[j].getType() == int.class){
                        fields[j].set(object, Optional.ofNullable(Integer.valueOf(cellValue)).orElse(null));
                    }else {
                        fields[j].set(object, cellValue);
                    }
                }
                objects.add(object);
            }
            wb.close();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return objects;
    }

    /**
     * 读取单元格的值
     * @param cell 当前列
     * @return 单元格的值
     */
    private static String readCellValue(Cell cell){
        String cellValue = null;
        if(null != cell){
            if(cell.getCellType() == CellType.STRING){
                cellValue = cell.getStringCellValue();
            }else if(cell.getCellType() == CellType.BOOLEAN){
                cellValue = String.valueOf(cell.getBooleanCellValue());
            }else if(cell.getCellType() == CellType.FORMULA){
                cellValue = cell.getCellFormula();
            }else if(cell.getCellType() == CellType.NUMERIC){
                if(DateUtil.isCellDateFormatted(cell)){
                    cellValue = DateFormatUtils.format(cell.getDateCellValue(), "yyyy-MM-dd");
                }else{
                    DecimalFormat decimalFormat = new DecimalFormat("#");
                    cellValue = decimalFormat.format(cell.getNumericCellValue());
                }
            }
        }
        return cellValue;
    }

    /**
     * 下载模板时设置下拉框的值
     * @param sheet 表名
     * @param comboBoxContent 下拉框的值
     * @param col 列的值
     */
    private static void serComboBox(Sheet sheet, String[] comboBoxContent, int col){
        DataValidationHelper helper = sheet.getDataValidationHelper();

        //CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);设置行列范围
        CellRangeAddressList addressList = new CellRangeAddressList(1, 500, col, col);

        //设置下拉框数据
        DataValidationConstraint constraint = helper.createExplicitListConstraint(comboBoxContent);
        DataValidation dataValidation = helper.createValidation(constraint, addressList);

        //处理Excel兼容性问题
        if(dataValidation instanceof XSSFDataValidation){
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        }else{
            dataValidation.setSuppressDropDownArrow(false);
        }
        sheet.addValidationData(dataValidation);
    }
}
