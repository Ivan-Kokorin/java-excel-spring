package org.linkapp.spring.link_for_price.service;

import org.linkapp.spring.link_for_price.service.ExcelManager;

import java.io.File;

public class InfoTable {
    private static InfoTable infoTable;
    private ExcelManager excelManager;
    private int column;
    private int sizeRowTable;

    private InfoTable(File selectedFile) {
        excelManager = new ExcelManager(selectedFile);
        column = excelManager.findColumn();
        sizeRowTable = excelManager.checkSizeRow();
        System.out.println("Всего строк: " + sizeRowTable);
    }

    public static InfoTable getInfoTable(File selectedFile) {
        if (infoTable == null) {
            infoTable = new InfoTable(selectedFile);
            return infoTable;
        } else {
            return infoTable;
        }
    }

    public ExcelManager getExcelManager() {
        return excelManager;
    }

    public int getColumn() {
        return column;
    }

    public int getSizeRowTable() {
        return sizeRowTable;
    }
}
