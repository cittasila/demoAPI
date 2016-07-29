package com.langying.toolbox.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenbo on 2015/10/20.
 */
public class ReadExcelUtil {

   
   @SuppressWarnings("deprecation")
   public static String[][] read2003ExcelStandard(InputStream fileInStream, Integer excelColumns) throws IOException {
       List<String[]> result = new ArrayList<String[]>();
       int rowSize = excelColumns;


       HSSFWorkbook hwb = new HSSFWorkbook(fileInStream);
       hwb.getSheetAt(0).getColumnBreaks();
       HSSFCell cell = null;

       for (int sheetIndex = 0; sheetIndex < 1; sheetIndex++) {
           HSSFSheet sheet = hwb.getSheetAt(sheetIndex);
             int rowCount= sheet.getLastRowNum();
           for (int rowIndex = 0; rowIndex < sheet.getLastRowNum()+1; rowIndex++) {
               HSSFRow row = sheet.getRow(rowIndex);
               cell = row.getCell(0);
               if(cell==null||cell.toString().length()==0){
                   rowCount=rowCount-1;
               }
           }

           for (int rowIndex = 0; rowIndex < rowCount+1; rowIndex++) {
               HSSFRow row = sheet.getRow(rowIndex);
               if (row == null) {
                   continue;
               }

               String[] values = new String[rowSize];
               Arrays.fill(values, "");
               boolean hasValue = false;

               for (short columnIndex = 0; columnIndex < row.getLastCellNum()&&columnIndex<rowSize; columnIndex++) {
                   String value = "";
                   cell = row.getCell(columnIndex);
                   if(cell!=null){
                       switch (cell.getCellType()) {
                           case HSSFCell.CELL_TYPE_STRING:
                               value = cell.getStringCellValue();
                               break;
                           case HSSFCell.CELL_TYPE_NUMERIC:
                               if (!org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                   NumberFormat nf = new DecimalFormat("#.#########");
                                   value = nf.format(cell.getNumericCellValue());
                               }
                               break;
                           case HSSFCell.CELL_TYPE_FORMULA:
                               if (!cell.getStringCellValue().equals("")) {
                                   value = cell.getStringCellValue();
                               } else {
                                   value = cell.getNumericCellValue() + "";
                               }
                               break;
                           case HSSFCell.CELL_TYPE_BOOLEAN:
                               value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                               break;
                           case HSSFCell.CELL_TYPE_BLANK:
                               value = "";
                               break;
                           case HSSFCell.CELL_TYPE_ERROR:
                               value = "";
                               break;
                           default:
                               value = cell.toString();
                       }
                   }
                   values[columnIndex] = rightTrim(value);
                   hasValue = true;
               }
               if (hasValue) {
                   result.add(values);
               }
           }
       }
       String[][] returnArray = new String[result.size()][rowSize];
       for (int m = 0; m < returnArray.length; m++) {
           returnArray[m] = (String[]) result.get(m);
       }

       return returnArray;
   }


   public static String[][] read2007ExcelStandard(InputStream fileInStream, Integer excelColumns) throws IOException {

       List<String[]> result = new ArrayList<String[]>();
       int rowSize = excelColumns;
       XSSFCell cell = null;
       XSSFWorkbook xwb = new XSSFWorkbook(fileInStream);
       xwb.getSheetAt(0).getColumnBreaks();

       for (int sheetIndex = 0; sheetIndex < 1; sheetIndex++) {
           XSSFSheet sheet = xwb.getSheetAt(sheetIndex);
           for (int rowIndex = 0; rowIndex < sheet.getLastRowNum()+1; rowIndex++) {
               XSSFRow row = sheet.getRow(rowIndex);
               if (row == null) {
                   continue;
               }
               String[] values = new String[rowSize];
               Arrays.fill(values, "");
               boolean hasValue = false;
               for (short columnIndex = 0; columnIndex < row.getLastCellNum()&&columnIndex<rowSize; columnIndex++) {
                   String value = "";
                   cell = row.getCell(columnIndex);

                   if (cell != null) {
                       switch (cell.getCellType()) {
                           case XSSFCell.CELL_TYPE_STRING:
                               value = cell.getStringCellValue();
                               break;
                           case XSSFCell.CELL_TYPE_NUMERIC:
                               if (!org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                   NumberFormat nf = new DecimalFormat("#.#########");
                                   value = nf.format(cell.getNumericCellValue());
                               }
                               break;
                           case XSSFCell.CELL_TYPE_FORMULA:
                               if (!cell.getStringCellValue().equals("")) {
                                   value = cell.getStringCellValue();
                               } else {
                                   value = cell.getNumericCellValue() + "";
                               }
                               break;
                           case XSSFCell.CELL_TYPE_BLANK:
                               break;
                           case XSSFCell.CELL_TYPE_ERROR:
                               value = "";
                               break;
                           case XSSFCell.CELL_TYPE_BOOLEAN:
                               value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                               break;
                           default:
                               value = "";
                               break;
                       }
                   }
                   values[columnIndex] = rightTrim(value);
                   hasValue = true;
               }
               if (hasValue) {
                   result.add(values);
               }
           }
       }
       String[][] returnArray = new String[result.size()][rowSize];
       for (int i = 0; i < returnArray.length; i++) {
           returnArray[i] = (String[]) result.get(i);
       }
       return returnArray;
   }

   /**
    * @param value
    * @return
    */
   public static String rightTrim(String value) {
       if (value == null) {
           return "";
       }
       int length = value.length();
       for (int i = length - 1; i >= 0; i--) {
           if (value.charAt(i) != 0x20) {
               break;
           }
           length--;
       }
       return value.substring(0, length);
   }

   
   @SuppressWarnings("deprecation")
   public static String[][] read2003ExcelStandardCopy(InputStream fileInStream, String fileName, Integer excelColumns) throws IOException {
       List<String[]> result = new ArrayList<String[]>();
       int rowSize = excelColumns;

       HSSFWorkbook hwb = new HSSFWorkbook(fileInStream);
       hwb.getSheetAt(0).getColumnBreaks();
       HSSFCell cell = null;
       for (int sheetIndex = 0; sheetIndex < 1; sheetIndex++) {
           HSSFSheet sheet = hwb.getSheetAt(sheetIndex);

           for (int rowIndex = 1; rowIndex < sheet.getLastRowNum()-2; rowIndex++) {
               HSSFRow row = sheet.getRow(rowIndex);
               if (row == null) {
                   continue;
               }

               String[] values = new String[rowSize];
               Arrays.fill(values, "");
               boolean hasValue = false;

               for (short columnIndex = 0; columnIndex < row.getLastCellNum(); columnIndex++) {
                   String value = "";
                   cell = row.getCell(columnIndex);
                   if(cell!=null){
                       switch (cell.getCellType()) {
                           case XSSFCell.CELL_TYPE_STRING:
                               value = cell.getStringCellValue();
                               break;
                           case XSSFCell.CELL_TYPE_NUMERIC:
                               if (!org.apache.poi.ss.usermodel.DateUtil.isCellDateFormatted(cell)) {
                                   NumberFormat nf = new DecimalFormat("#.#########");
                                   value = nf.format(cell.getNumericCellValue());
                               }
                               break;
                           case XSSFCell.CELL_TYPE_FORMULA:
                               if (!cell.getStringCellValue().equals("")) {
                                   value = cell.getStringCellValue();
                               } else {
                                   value = cell.getNumericCellValue() + "";
                               }
                               break;
                           case XSSFCell.CELL_TYPE_BOOLEAN:
                               value = (cell.getBooleanCellValue() == true ? "Y" : "N");
                               break;
                           case XSSFCell.CELL_TYPE_BLANK:
                               value = "";
                               break;
                           case XSSFCell.CELL_TYPE_ERROR:
                               value = "";
                               break;
                           default:
                               value = cell.toString();
                       }
                   }
                   values[columnIndex] = rightTrim(value);
                   hasValue = true;
               }
               if (hasValue) {
                   result.add(values);
               }
           }
       }
       String[][] returnArray = new String[result.size()][rowSize];
       for (int m = 0; m < returnArray.length; m++) {
           returnArray[m] = (String[]) result.get(m);
       }

       return returnArray;
   }
}
