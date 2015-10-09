package org.limingnihao.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class ExcelUtil {
	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/**
	 * 将Map导出Excel文件 一个map对应一个工作表，map的键值为工作表名称，map的vector为工作表内容
	 * 
	 * @param outputStream
	 * @param data
	 * @throws IOException
	 * @throws RowsExceededException
	 * @throws WriteException
	 */
	public static void exportExcel(final OutputStream outputStream, final Map<String, List<List<String>>> data) throws IOException, RowsExceededException, WriteException {
		WritableWorkbook workbook = Workbook.createWorkbook(outputStream);
		int s = 0;
		for (final String label : data.keySet()) {
			WritableSheet sheet = workbook.createSheet(label.replaceAll("\\[|\\]|\\\\|\\:|\\?|\\/", "_"), s++);
			List<List<String>> value = data.get(label);
			for (int i = 0; i < value.size(); i++) {
				List<String> items = value.get(i);
				for (int j = 0; j < items.size(); j++) {
					if (items.get(j) != null && !items.get(j).trim().equals("")) {
						sheet.addCell(new Label(j, i, items.get(j)));
					}
				}
			}
		}
		workbook.write();
		workbook.close();
		outputStream.close();
	}

	
	public static ArrayList<ArrayList<String>> importExcel(String filePath, int sheetIndex) {
		logger.info("importExcel - filePath=" + filePath);
		File file = new File(filePath);
		if (!file.exists()) {
			logger.info("importExcel - 文件不存在");
			return null;
		}
		logger.info("importExcel - 1.文件存在 - filePath=" + filePath);
		ArrayList<ArrayList<String>> rowList = new ArrayList<ArrayList<String>>();
		try {
			POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(filePath));
			// 得到Excel工作簿对象
			HSSFWorkbook workbook = new HSSFWorkbook(fs);
			// 得到Excel工作表对象
			HSSFSheet sheet = workbook.getSheetAt(sheetIndex);
			int rowNumLast = sheet.getLastRowNum();
			logger.info("importExcel - 2.rowNumLast=" + rowNumLast);
			// i循环行
			for (int i = 1; i < rowNumLast; i++) {
				ArrayList<String> columnList = new ArrayList<String>();
				// 得到Excel工作表的行
				HSSFRow row = sheet.getRow(i);
				// j循环列
				for (int j = 0; j < row.getLastCellNum(); j++) {
					// 得到Excel工作表指定行的单元格
					HSSFCell cell = row.getCell(j);
					Object value = null;
					if (cell != null) {
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							Double d = cell.getNumericCellValue();
							value = d.intValue();
						} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							value = cell.getStringCellValue();
						}
						columnList.add(value.toString());
					}
				}
				//logger.info("i=" + i + "" + Arrays.toString(columnList.toArray()));
				rowList.add(columnList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowList;
	}
}
