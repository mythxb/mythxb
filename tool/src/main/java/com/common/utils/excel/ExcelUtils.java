package com.common.utils.excel;


import com.common.utils.Constants;
import com.common.utils.DateUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;


public class ExcelUtils {


    private final static Logger log = LoggerFactory.getLogger(ExcelUtils.class);

    private final static String EXCEL2003 = "xls";
    private final static String EXCEL2007 = "xlsx";


    public static <T> void writeExcel(HttpServletResponse response, String excelName, List<T> dataList, Class<T> cls){
        String time = DateUtils.getNowDateTimeStr("yyyy-MM-dd");
        Field[] fields = cls.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(fields)
                .filter(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null && annotation.col() > 0) {
                        field.setAccessible(true);
                        return true;
                    }
                    return false;
                }).sorted(Comparator.comparing(field -> {
                    int col = 0;
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    if (annotation != null) {
                        col = annotation.col();
                    }
                    return col;
                })).collect(Collectors.toList());

        Workbook wb ;

        if (dataList.size()>10000){
            wb =  new SXSSFWorkbook();
        }else {
            wb = new XSSFWorkbook();
        }
        Sheet sheet = wb.createSheet("Sheet1");
        CellStyle cellDataStyle = wb.createCellStyle();
        AtomicInteger ai = new AtomicInteger();
        {
            Row row = sheet.createRow(ai.getAndIncrement());
            AtomicInteger aj = new AtomicInteger();
            //写入头部
            fieldList.forEach(field -> {
                ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                String columnName = "";
                if (annotation != null) {
                    columnName = annotation.value();
                }
                Cell cell = row.createCell(aj.getAndIncrement());

                CellStyle cellStyle = wb.createCellStyle();
                cellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

                Font font = wb.createFont();
                cellStyle.setFont(font);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(columnName);
            });
        }
        if (CollectionUtils.isNotEmpty(dataList)) {
            dataList.forEach(t -> {
                Row row1 = sheet.createRow(ai.getAndIncrement());
                AtomicInteger aj = new AtomicInteger();
                fieldList.forEach(field -> {
                    ExcelColumn annotation = field.getAnnotation(ExcelColumn.class);
                    int contentType = 0;
                    if (annotation != null) {
                        contentType = annotation.contentType();
                    }
                    Class<?> type = field.getType();
                    Object value = "";
                    try {
                        value = field.get(t);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Cell cell = row1.createCell(aj.getAndIncrement());





                    if (value != null) {
                        if (type == Date.class) {
                            cell.setCellValue(value.toString());
                        } else if (1 == contentType){
                            cellDataStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,#0"));
                            cell.setCellStyle(cellDataStyle);
                            try {
                                cell.setCellValue(Integer.parseInt(value.toString()));
                            }catch (NumberFormatException e){
                                cell.setCellValue(new Double(value.toString()).intValue());
                            }
                        }else if (2 == contentType){
                            cellDataStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#0.00"));
                            cell.setCellStyle(cellDataStyle);
                            cell.setCellValue(Double.parseDouble(value.toString()));
                        }else{
                            cell.setCellValue(value.toString());
                        }

                    }
                });
            });
        }
        //冻结窗格
        wb.getSheet("Sheet1").createFreezePane(0, 1, 0, 1);
        //浏览器下载excel
        buildExcelDocument(excelName,wb,response);
        //生成excel文件
        //buildExcelFile(excelName,time,wb);
    }



    /**
     * 浏览器下载excel
     * @param fileName
     * @param wb
     * @param response
     */

    private static  void  buildExcelDocument(String fileName, Workbook wb, HttpServletResponse response){
        try {
            response.setContentType("application/binary;charset=UTF-8");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(fileName, "UTF-8"));
            response.flushBuffer();
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成excel文件
     * @param excelName 生成excel名
     * @param wb
     */
    private static  void   buildExcelFile(String excelName,String time, Workbook wb){


        String folder = Constants.FILE_LOCATION+time;
        File secondFolder = new File(folder);
        if(!secondFolder.exists()){
            secondFolder.mkdirs();
        }
        String fileName = folder+"/"+excelName;
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            wb.write(new FileOutputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
