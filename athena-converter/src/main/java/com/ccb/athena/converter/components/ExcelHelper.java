package com.ccb.athena.converter.components;

import org.apache.poi.ss.usermodel.Cell;

public class ExcelHelper {
	public static String getCellValue(Cell cell) {
		if(cell == null)
			return "";
		
		switch(cell.getCellType()) {
		case Cell.CELL_TYPE_NUMERIC:
			cell.setCellType(Cell.CELL_TYPE_STRING);
			return cell.getStringCellValue();
//			Integer.parseInt(new java.text.DecimalFormat("0").format(cell.getNumericCellValue()));
//			return String.valueOf(new java.text.DecimalFormat("0").format(cell.getNumericCellValue()));
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
		case Cell.CELL_TYPE_BLANK:
			return "";
		default:
			throw new RuntimeException("Excel cell type not valid");
		}
		 
	}
}
