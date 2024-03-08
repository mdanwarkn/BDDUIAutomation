package utilities;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExcelUtil {

    public static synchronized List<String> getColumnNamesInExcel(String fileSource , String sheet) throws IOException, InvalidFormatException {
        XSSFWorkbook workbook = new XSSFWorkbook(new File(fileSource));
        Row row = workbook.getSheet(sheet).getRow(0);
        List<String> colummnNames = new ArrayList<>();
        for(int i = 0 ; i < row.getLastCellNum() ; i++){
            colummnNames.add(row.getCell(i).getStringCellValue());
        }
        return colummnNames;
    }

    public static synchronized void writeDataToExcelFile(Map<String,String> outputRow , String fileSource , String sheetName) throws IOException, InvalidFormatException {
        FileInputStream inputStream = new FileInputStream(fileSource);
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet =  workbook.getSheet(sheetName);
        List<String> columnNamesInExcel =  getColumnNamesInExcel(fileSource , sheetName);
        int lastRowNum = sheet.getLastRowNum();
        System.out.println("Last Row : "+lastRowNum);
        Row row = sheet.createRow(lastRowNum+1);
        for(int  i = 0 ; i<columnNamesInExcel.size() ; i++){
            row.createCell(i).setCellValue(outputRow.get(columnNamesInExcel.get(i)));
        }
        OutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(fileSource);
            workbook.write(fileOut);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            workbook.close();
            fileOut.flush();
            fileOut.close();
        }
    }
}
