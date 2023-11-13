package com.lawencon.ocr.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.ocr.contants.TemplateInvoiceHeaderFooter;
import com.lawencon.ocr.model.Incident;
import com.lawencon.ocr.model.Letter;
import com.lawencon.ocr.service.ExcelGeneratorService;
import com.lawencon.ocr.service.IncidentService;
import com.lawencon.ocr.service.LetterService;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class ExcelGeneratorServiceImpl implements ExcelGeneratorService {

	@Autowired
	private IncidentService incidentService;

	@Autowired
	private LetterService letterService;

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	private long count = 0;

//	public ExcelGeneratorServiceImpl() {
//		workbook = new XSSFWorkbook();
//	}

	private void writeHeader(TemplateInvoiceHeaderFooter template, String title) {
		count++;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet(title +"_"+ count);
		Row row = sheet.createRow(0);
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		style.setFont(font);
		String[] headerData = template.getHeaderData().split(" ");
		for (int i = 0; i < headerData.length; i++) {
			createCell(row, i, headerData[i], style);
		}
	}

	private void createCell(Row row, int columnCount, Object valueOfCell, CellStyle style) {
		sheet.autoSizeColumn(columnCount);
		Cell cell = row.createCell(columnCount);
		if (valueOfCell instanceof Integer) {
			cell.setCellValue((Integer) valueOfCell);
		} else if (valueOfCell instanceof Long) {
			cell.setCellValue((Long) valueOfCell);
		} else if (valueOfCell instanceof String) {
			cell.setCellValue((String) valueOfCell);
		} else {
			cell.setCellValue((Boolean) valueOfCell);
		}
		cell.setCellStyle(style);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	}

	private <T> void write(List<T> list, Class<T> clazz) {
		int rowCount = 1;
		CellStyle style = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		style.setFont(font);

		Field[] fields = clazz.getDeclaredFields();

		for (T obj : list) {
			Row row = sheet.createRow(rowCount++);
			int columnCount = 0;

			for (Field field : fields) {
				field.setAccessible(true);
				try {
					Object value = field.get(obj);
					createCell(row, columnCount++, value, style);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public HashMap<String, String> generateExcelFile(int templateNo, HttpServletResponse response) throws IOException {
		TemplateInvoiceHeaderFooter template = null;
		HashMap<String, String> map = new HashMap<>();
		if (templateNo == 1) {
			template = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_A;
		} else if (templateNo == 2) {
			template = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_B;
		} else if (templateNo == 3) {
			template = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_C;
			List<Incident> listOfIncident = incidentService.getListIncidentDownload();
			writeHeader(template, "Incident");
			write(listOfIncident, Incident.class);
			map.put("fileName", "Incident");
		} else if (templateNo == 4) {
			template = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_D;
			List<Letter> listOfLetter = letterService.getListLetterDownload();
			writeHeader(template, "Letter");
			write(listOfLetter, Letter.class);
			map.put("fileName", "Letter");
		}

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		workbook.write(byteArrayOutputStream);
		workbook.close();
		byte[] excelBytes = byteArrayOutputStream.toByteArray();
		String base64Encoded = Base64.getEncoder().encodeToString(excelBytes);
		map.put("fileType", "xlsx");
		map.put("base64", base64Encoded);
		return map;
	}

}
