/**   
 * Description: TODO(描述)
 * @FileName: OptExcel.java
 * @Package: main
 * @author: 杨哲
 * @date: 2017年9月27日 上午11:06:44
 */

package main;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 * Description: TODO(描述)
 * 
 * @ClassName: OptExcel
 * @author: 杨哲
 * @date: 2017年9月27日 上午11:06:44
 */

public class OptExcel {
	public static void writeExcel(String filePathName, List<String> dataList) {
		InputStream inputStream = null;
		Workbook workbook = null;
		WritableWorkbook writableWorkbook = null;
		try {
			inputStream = new FileInputStream(filePathName);
			workbook = Workbook.getWorkbook(inputStream);
			writableWorkbook = Workbook.createWorkbook(new File(filePathName), workbook);
			Sheet sheet = workbook.getSheet(0);
			WritableSheet writeSheet = writableWorkbook.getSheet(0);
			if (sheet.getRows() <= 1 || sheet.getColumns() == 0) {
				return;
			} else {
				for (int i = 3; i < sheet.getRows(); i++) {
					String type = sheet.getCell(0, i).getContents().trim();
					String path = sheet.getCell(1, i).getContents().trim();
					if (!"over".equals(type) && dataList.contains(path)) {
						writeSheet.addCell(new Label(0, i, "over"));
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (workbook != null)
					workbook.close();
				if (writableWorkbook != null) {
					writableWorkbook.write();
					writableWorkbook.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			} catch (WriteException e) {
				e.printStackTrace();
			}
		}
	}

	public static List<Map<String, String>> readExcel(String filePathName) {
		InputStream inputStream = null;
		Workbook workbook = null;
		try {
			inputStream = new FileInputStream(filePathName);
			workbook = Workbook.getWorkbook(inputStream);
			Sheet sheet = workbook.getSheet(0);
			if (sheet.getRows() <= 1 || sheet.getColumns() == 0) {
				return null;
			} else {
				List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
				for (int i = 3; i < sheet.getRows(); i++) {
					String type = sheet.getCell(0, i).getContents().trim();
					String file = sheet.getCell(1, i).getContents().trim();
					String interfaceCode = sheet.getCell(2, i).getContents().trim();
					String information = sheet.getCell(3, i).getContents().trim();
					String author = sheet.getCell(4, i).getContents().trim();
					if (!"over".equals(type) && !"".equals(file)) {
						Map<String, String> map = new HashMap<String, String>();
						map.put("file", file);
						map.put("interfaceCode", interfaceCode);
						map.put("information", information);
						map.put("author", author);
						dataList.add(map);
					}
				}
				return dataList;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			try {
				if (inputStream != null)
					inputStream.close();
				if (workbook != null)
					workbook.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
