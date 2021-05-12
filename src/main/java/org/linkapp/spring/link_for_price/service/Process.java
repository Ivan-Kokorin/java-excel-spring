package org.linkapp.spring.link_for_price.service;

import org.linkapp.spring.link_for_price.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Process implements Runnable {
    File selectedFile;
    InfoTable infoTable;
    private ExcelManager excelManager;
    private int sizeRowTable;
    private int column;
    private ProductService productService;

    @Autowired
    public Process(ProductService productService) {
        this.productService = productService;
    }

    public void initialize(File selectedFile) {
        this.selectedFile = selectedFile;
        this.infoTable = InfoTable.getInfoTable(selectedFile);
        excelManager = infoTable.getExcelManager();
        sizeRowTable = infoTable.getSizeRowTable();
        column = infoTable.getColumn();
        System.out.println("Номер колонки" + column);
    }

    @Override
    public void run() {
        System.out.println("START!");

        int row = 0;
        while (row < sizeRowTable) {
            String nameProduct = excelManager.getNameProduct(0, row, column);
            System.out.println(nameProduct);
            Product productByName = productService.findByName(nameProduct);
            if(productByName != null) {
                String urlProduct = productByName.getUrl();
                if (urlProduct == null) {
                    System.out.println("НЕ НАЙДЕН В БАЗЕ ДАННЫХ");
                    row++;
                    continue;
                }
                System.out.println(urlProduct);
                excelManager.setLinkProduct(0, row, column, nameProduct, urlProduct);
            }
            row++;
        }
        excelManager.writeExcel();
        System.out.println("РАБОТА ПРОГРАММЫ ОКОНЧЕНА");
    }
}
