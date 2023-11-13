package com.lawencon.ocr.service.impl;

//import java.awt.Graphics2D;
//import java.awt.Image;
import java.awt.image.BufferedImage;
//import java.awt.image.RescaleOp;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import javax.imageio.ImageIO;

//import org.opencv.core.Core;
//import org.opencv.core.Mat;
//import org.opencv.core.Size;
//import org.opencv.highgui.HighGui;
//import org.opencv.imgcodecs.Imgcodecs;
//import org.opencv.imgproc.Imgproc;
import org.springframework.stereotype.Service;

import com.lawencon.ocr.contants.TemplateInvoiceHeaderFooter;
import com.lawencon.ocr.dto.TemplateResponseDto;
import com.lawencon.ocr.dto.request.FileRequestDto;
import com.lawencon.ocr.service.OcrService;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
//import nu.pattern.OpenCV;

@Service
public class OcrServiceImpl implements OcrService {

	static final double MINIMUM_DESKEW_THRESHOLD = 0.0005d;

	@Override
	public Object doOcr(FileRequestDto fileRequestDto, int templateNo) {
		final String[] textArr = ocr(fileRequestDto);

		TemplateInvoiceHeaderFooter templateInvoiceData = null;
		List<String[]> dataOutput = null;
		final StringBuilder headerSb = new StringBuilder();
		final StringBuilder footerSb = new StringBuilder();

		if (templateNo == 1) {
			templateInvoiceData = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_A;
			dataOutput = processingTemplateAB(templateInvoiceData, textArr, headerSb, footerSb);
		} else if (templateNo == 2) {
			templateInvoiceData = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_B;
			dataOutput = processingTemplateAB(templateInvoiceData, textArr, headerSb, footerSb);
		} else if (templateNo == 3) {
			templateInvoiceData = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_C;
			dataOutput = processingTemplateC(templateInvoiceData, textArr, headerSb, footerSb);
		} else if (templateNo == 4) {
			templateInvoiceData = TemplateInvoiceHeaderFooter.TEMPLATE_INVOICE_D;
			dataOutput = processingTemplateD(templateInvoiceData, textArr, headerSb, footerSb);
		}

		TemplateResponseDto response = new TemplateResponseDto();
		String[] headers = templateInvoiceData.getHeaderData().split(" ");
		final String headerData = headerSb.toString().trim();
		final String footerData = footerSb.toString().trim();
		response.setHeaderData(headerData);
		response.setHeaderColumns(headers);
		response.setDataTable(dataOutput);
		response.setFooterData(footerData);

		return response;
	}

	private List<String[]> processingTemplateAB(TemplateInvoiceHeaderFooter templateInvoiceData, String[] textArr,
			StringBuilder headerSb, StringBuilder footerSb) {

		final List<String[]> listDataTableInvoice = new ArrayList<>();
		boolean dataTableExtractProcessing = false;
		boolean dataHeaderProcessing = true;
		boolean dataFooterProcessing = false;

		for (int i = 0; i < textArr.length; i++) {
			String currentLine = textArr[i].trim();
			if (currentLine.isBlank() || currentLine.equals("\n") || currentLine.isEmpty()) {
				continue;
			}

			if (templateInvoiceData.getHeaderData().replace("_", " ").equalsIgnoreCase(currentLine)) {
				dataHeaderProcessing = false;
				dataTableExtractProcessing = true;
				continue;
			}

			if (dataHeaderProcessing) {
				headerSb.append(currentLine);
				headerSb.append("\n");
			} else if (dataFooterProcessing) {
				footerSb.append(currentLine);
				footerSb.append("\n");
			} else if (dataTableExtractProcessing) {
				final String[] dataArr = new String[4];
				final String[] parts = currentLine.split(" ");
				final int partsLength = parts.length;

				if (currentLine.toLowerCase().contains(templateInvoiceData.getFooterData())) {
					footerSb.append(currentLine);
					footerSb.append("\n");

					dataTableExtractProcessing = false;
					dataHeaderProcessing = false;
					dataFooterProcessing = true;
					continue;
				}

				final StringBuilder stringBuilder = new StringBuilder();
				for (int j = 0; j < partsLength - 3; j++) {
					stringBuilder.append(parts[j]);
					stringBuilder.append(" ");
				}

				dataArr[0] = stringBuilder.toString().trim();
				dataArr[1] = parts[partsLength - 3];
				dataArr[2] = parts[partsLength - 2];
				dataArr[3] = parts[partsLength - 1];

				listDataTableInvoice.add(dataArr);
			}
		}
		return listDataTableInvoice;
	}

	private List<String[]> processingTemplateC(TemplateInvoiceHeaderFooter templateInvoiceData, String[] textArr,
			StringBuilder headerSb, StringBuilder footerSb) {

		final List<String[]> listDataTableIncident = new ArrayList<>();
		final String[] dataArr = new String[6];
		for (String line : textArr) {
			if (line.startsWith("NOMOR :")) {
				dataArr[0] = line.replace("NOMOR :", "").trim();
			} else if (line.startsWith("4. Nama ")) {
				dataArr[1] = line.replace("4. Nama ", "").trim();
			} else if (line.startsWith("6 Nomor Telp/ HP : ")) {
				dataArr[2] = line.replace("6 Nomor Telp/ HP : ", "").trim();
			} else if (line.startsWith("7. Nik + ")) {
				dataArr[3] = line.replace("7. Nik + ", "").trim();
			} else if (line.startsWith("3. Apa yang terjadi ‘ ")) {
				dataArr[4] = line.replace("3. Apa yang terjadi ‘ ", "").trim();
			} else if (line.startsWith("b. Terlapor = ")) {
				dataArr[5] = line.replace("b. Terlapor = ", "").trim();
			}
		}
		listDataTableIncident.add(dataArr);
		return listDataTableIncident;

	}

	private List<String[]> processingTemplateD(TemplateInvoiceHeaderFooter templateInvoiceData, String[] textArr,
			StringBuilder headerSb, StringBuilder footerSb) {

		final List<String[]> listDataTableIncident = new ArrayList<>();
		final String[] dataArr = new String[12];

		for (String line : textArr) {
			if (line.startsWith("Nomor :")) {
				line = line.replace("Nomor :", "").trim();
				int index = line.indexOf(" ", line.indexOf(" ") + 1);
				dataArr[0] = line.substring(0, index);
				dataArr[1] = line.substring(index + 1);
			} else if (line.startsWith("Klasifikasi : ")) {
				dataArr[2] = line.replace("Klasifikasi : ", "").trim();
			} else if (line.startsWith("Lampiran : ")) {
				dataArr[3] = line.replace("Lampiran : ", "").trim();
			} else if (line.startsWith("Perihal : ")) {
				dataArr[4] = line.replace("Perihal : ", "").trim();
			} else if (line.startsWith("Nama : ")) {
				dataArr[5] = line.replace("Nama : ", "").trim();
			} else if (line.startsWith("Jenis kelamin : ")) {
				dataArr[6] = line.replace("Jenis kelamin : ", "").trim();
			} else if (line.startsWith("Tempat / tanggal Lahir : ")) {
				dataArr[7] = line.replace("Tempat / tanggal Lahir : ", "").trim();
			} else if (line.startsWith("Alamat + ") || line.startsWith("Kec")) {
				if (line.startsWith("Kec")) {
					dataArr[8] += " " + line;
				} else {
					dataArr[8] = line.replace("Alamat + ", "").trim();
				}
			} else if (line.startsWith("Pekerjaan : ")) {
				dataArr[9] = line.replace("Pekerjaan : ", "").trim();
			} else if (line.startsWith("Kewarganegaraan : ")) {
				dataArr[10] = line.replace("Kewarganegaraan : ", "").trim();
			} else if (line.startsWith("Agama : ")) {
				dataArr[11] = line.replace("Agama : ", "").trim();
			}
		}
		listDataTableIncident.add(dataArr);
		return listDataTableIncident;

	}

	@Override
	public String[] ocr(FileRequestDto fileRequestDto) {
		final Tesseract tesseract = new Tesseract();

		try {
//			tesseract.setDatapath("/usr/share/tesseract-ocr/5/tessdata");
//			tesseract.setDatapath("/usr/share/tesseract/tessdata/");
			tesseract.setDatapath("./tessdata");
			BufferedImage image = null;
			byte[] decodeByte;
			File file = null;
			decodeByte = Base64.getDecoder().decode(fileRequestDto.getBase64().getBytes(StandardCharsets.UTF_8));

			if ("png".equals(fileRequestDto.getFileType())) {
				ByteArrayInputStream bis = new ByteArrayInputStream(decodeByte);
				image = ImageIO.read(bis);
				bis.close();

				file = new File("image.png");
				ImageIO.write(image, "png", file);
			} else {

				final String fileType = fileRequestDto.getFileType();
				final String filename = "output." + fileType;
				Path destinationFile = Paths.get(".", filename);

				Files.write(destinationFile, decodeByte);
				file = new File(destinationFile.toString());

			}

//			Image img = adaptiveThreshold(file, fileRequestDto);
//			BufferedImage ipimage = (BufferedImage) img;
			BufferedImage ipimage = ImageIO.read(file);
//			BufferedImage finalOutputimage = null;
//			double d = ipimage.getRGB(ipimage.getTileWidth() / 2, ipimage.getTileHeight() / 2);
//			if (d >= -1.4211511E7 && d < -7254228) {
//				finalOutputimage = improveQuality(ipimage, 3f, -10f);
//			} else if (d >= -7254228 && d < -2171170) {
//				finalOutputimage = improveQuality(ipimage, 1.455f, -47f);
//			} else if (d >= -2171170 && d < -1907998) {
//				finalOutputimage = improveQuality(ipimage, 1.35f, -10f);
//			} else if (d >= -1907998 && d < -257) {
//				finalOutputimage = improveQuality(ipimage, 1.19f, 0.5f);
//			} else if (d >= -257 && d < -1) {
//				finalOutputimage = improveQuality(ipimage, 1f, 0.5f);
//			} else if (d >= -1 && d < 2) {
//				finalOutputimage = improveQuality(ipimage, 1f, 0.35f);
//			}
//
			BufferedImage imageEdited = processImg(ipimage);
//			ImageDeskew id = new ImageDeskew(imageEdited);
//			double imageSkewAngle = id.getSkewAngle(); // determine skew angle
//			if ((imageSkewAngle > MINIMUM_DESKEW_THRESHOLD || imageSkewAngle < -(MINIMUM_DESKEW_THRESHOLD))) {
//				imageEdited = ImageHelper.rotateImage(imageEdited, -imageSkewAngle); // deskew image
//			}
//
			ImageIO.write(imageEdited, fileRequestDto.getFileType(), file);
			tesseract.setLanguage("eng");
			tesseract.setPageSegMode(1);
			tesseract.setOcrEngineMode(1);
			final String text = tesseract.doOCR(file);
			final String[] textArr = text.split("\n");
			if (file.exists()) {
				file.delete();
			}

			return textArr;
		} catch (Exception e) {
			e.printStackTrace();
			return new String[] {};
		}
	}

	private BufferedImage processImg(BufferedImage img) throws IOException, TesseractException {
		int width = img.getWidth();
		int height = img.getHeight();
		int[] pixels = img.getRGB(0, 0, width, height, null, 0, width);
		for (int i = 0; i < pixels.length; i++) {
			int p = pixels[i];
			int a = (p >> 24) & 0xff;
			int r = (p >> 16) & 0xff;
			int g = (p >> 8) & 0xff;
			int b = p & 0xff;
			int avg = (r + g + b) / 3;
			p = (a << 24) | (avg << 16) | (avg << 8) | avg;
			pixels[i] = p;
		}
		img.setRGB(0, 0, width, height, pixels, 0, width);
		return img;
	}

//	private Image adaptiveThreshold(File file, FileRequestDto fileRequestDto) throws IOException {
//		OpenCV.loadLocally();
//		final String fileType = fileRequestDto.getFileType();
//		final String filename = "output." + fileType;
//		Path destinationFile = Paths.get(".", filename);
//		Mat src = Imgcodecs.imread(destinationFile.toString(), Imgcodecs.CV_LOAD_IMAGE_COLOR);
//		Mat dst = new Mat(src.rows(), src.cols(), src.type());
//		// Filtering
//		Imgproc.GaussianBlur(src, dst, new Size(0, 0), 10);
//		Core.addWeighted(src, 1.5, dst, -0.5, 0, dst);
//		Image img = HighGui.toBufferedImage(dst);
//		return img;
//	}
//
//	private BufferedImage improveQuality(BufferedImage inputImage, float scaleFactor, float offset)
//			throws IOException, TesseractException {
//		BufferedImage outputImage = new BufferedImage(1050, 1024, inputImage.getType());
//		Graphics2D grp = outputImage.createGraphics();
//		grp.drawImage(inputImage, 0, 0, 1050, 1024, null);
//		grp.dispose();
//		RescaleOp rescaleOutput = new RescaleOp(scaleFactor, offset, null);
//		BufferedImage finalOutputimage = rescaleOutput.filter(outputImage, null);
//		return finalOutputimage;
//	}

}
