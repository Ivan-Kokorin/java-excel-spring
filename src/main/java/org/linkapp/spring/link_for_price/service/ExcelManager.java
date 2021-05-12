package org.linkapp.spring.link_for_price.service;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

public class ExcelManager {
    HSSFWorkbook wb;

    ExcelManager(File excelFile) {
        wb = readWorkbook(excelFile);
    }

    public static HSSFWorkbook readWorkbook(File filename) {
        try {
            POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filename));
            HSSFWorkbook wb = new HSSFWorkbook(fs);
            return wb;
        } catch (Exception e) {
            return null;
        }
    }
    public void writeExcel(){
        writeWorkbook(wb, "newTable.xls");
    }

    public static void writeWorkbook(HSSFWorkbook wb, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            wb.write(fileOut);
            fileOut.close();
        } catch (Exception e) {
            //Обработка ошибки
        }
    }

    public String getNameProduct(int sheetIndex, int rowIndex, int cellIndex) {
        HSSFCell cell = wb.getSheetAt(sheetIndex).getRow(rowIndex).getCell(cellIndex);
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() != CellType.STRING) {
            return "другой тип";
        }
        String nameProduct = cell.getRichStringCellValue().getString();
        return nameProduct;
    }

    public void setLinkProduct(int sheetIndex, int rowIndex, int cellIndex, String nameProduct, String urlProduct) {
        HSSFSheet sheet = wb.getSheetAt(sheetIndex);
        HSSFRow row = sheet.getRow(rowIndex);
        HSSFCell cell = row.getCell(cellIndex);
        nameProduct = nameProduct.replace('"', '\'');
        String formula = "HYPERLINK(\"https://albasale.ru/products/" + urlProduct + "\",\"" + nameProduct + "\")";
        cell.setCellFormula(formula);
    }

    public int checkSizeColumn() {
        int maxColumn = 0;
        Iterator<Cell> cellIterator = wb.getSheetAt(0).getRow(0).cellIterator();
        while (cellIterator.hasNext()) {
            cellIterator.next();
            maxColumn++;
        }
        return maxColumn;
    }

    public int findColumn() { //поиск колонки с наименованием товаров
        int i = 0;
        int maxColumn = checkSizeColumn();
        System.out.println("Ширина таблицы: " + maxColumn);
        System.out.println("Поиск нужной колонки");
        while (true) {
            for (int j = 0; j < maxColumn; j++) {
                String nameCell = getNameProduct(0, i, j);
                if (nameCell == null) {
                    continue;
                }
                if (nameCell.equalsIgnoreCase("Наименование")) {
                    return j;
                }
            }
            i++;
        }
    }

    public int checkSizeRow() {
        int maxRow = 0;
        Iterator<Row> rowIterator = wb.getSheetAt(0).rowIterator();
        while (rowIterator.hasNext()) {
            rowIterator.next();
            maxRow++;
        }
        return maxRow;
    }
}