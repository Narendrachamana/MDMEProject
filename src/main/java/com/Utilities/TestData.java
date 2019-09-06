package com.Utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.Test;

public class TestData {
	static Workbook book;
	static Sheet sheet;
	

	public static Object[][] getTestData(String sheetname) {
		 String TestData_Path = "C:\\Users\\nchamana\\Java_Selenium\\OrangeHRM\\TestData\\LoginTestData.xlsx";
		FileInputStream file = null;

		try {
			file = new FileInputStream(TestData_Path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		}
		try {
			book = WorkbookFactory.create(file);
		} catch (InvalidFormatException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		sheet = book.getSheet(sheetname);
		Object[][] data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];
		for (int i = 0; i < sheet.getLastRowNum(); i++) {
			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {
				data[i][k] = sheet.getRow(i + 1).getCell(k).toString();
			}
		}
		System.out.println("Exel data"+data);
		return data;
		
		
		

	}
	
	

}
