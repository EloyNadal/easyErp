package com.easyErp.project.utils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.LinkedList;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class ExportData {
	
	private static JasperReport report;
	private static JasperPrint reportPrint;

public static <T> boolean crearArchivoExcel(TableView<T> tabla) {
		
		LinkedList<String> header = new LinkedList<String>(); 
		for (TableColumn<T, ?> cabeceras : tabla.getColumns()) {
			header.add(cabeceras.getText());
		}
		
		String[][] rows = new String[tabla.getItems().size()][tabla.getVisibleLeafColumns().size()];
		
		for (int i = 0; i < tabla.getItems().size(); i++) {
			for (int j = 0; j < tabla.getVisibleLeafColumns().size(); j++) {
				if (tabla.getVisibleLeafColumns().get(j).getCellData(i) != null){
					rows[i][j] = tabla.getVisibleLeafColumns().get(j).getCellData(i).toString();
				}
				else {
					rows[i][j] = "";
				}
			}
		}
		
		File file = AppManager.openFileChooser("Nombre del archivo", false, "XLS", "*.xlsx");
		if(file == null) return false;
		crearResultadoExcel(rows, header, file);
		return true;
	}

	private static void crearResultadoExcel(String[][] data, LinkedList<String> cabecera ,File file) {

		XSSFWorkbook libro = new XSSFWorkbook();
		
		XSSFSheet hoja1 = libro.createSheet("hola");
		CellStyle style = libro.createCellStyle();
		
		Font font = libro.createFont();
		font.setBold(true);
		style.setFont(font);
		
		CellStyle totales = libro.createCellStyle();
		totales.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
		totales.setFillPattern(FillPatternType.THICK_BACKWARD_DIAG);

		int fila = 0;

		XSSFRow rowCabecera = hoja1.createRow(0);

		for (int i = 0; i < cabecera.size(); i++) {
			XSSFCell cellCabecera = rowCabecera.createCell(i);
			cellCabecera.setCellStyle(style);
			cellCabecera.setCellValue(cabecera.get(i));
		}

		fila++;
		
		for (int i = 0; i < data.length; i++) {
			
			XSSFRow row = hoja1.createRow(fila);
			
			for (int k = 0; k < data[i].length; k++) {
				XSSFCell cell = row.createCell(k);
				cell.setCellValue(data[i][k]);
			}
			
			fila++;			
		}
		for (int i = 0; i < cabecera.size(); i++) {
			hoja1.autoSizeColumn(i);
		}

		FileOutputStream fileOS = null;

		try {
			fileOS = new FileOutputStream(file);
			libro.write(fileOS);
			fileOS.flush();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileOS.close();
				libro.close();
				AppManager.showInfo("Tabla exportada correctamente");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	
	
	/**
	 * Método para crear archivos pdf desde un json file junto con varios parametros
	 * @param rawJson json array, lista de elementos a mostrar en la tabla
	 * @param params parametros a mostrar en cabezeras (proveedor y tienda origen)
	 */
	public static boolean crearPdf(String rawJson, Map<String, Object> params, String pdfResource) {
		
		try {
			report = (JasperReport) JRLoader.loadObject(new File(pdfResource));
			params.put(JsonQueryExecuterFactory.JSON_INPUT_STREAM, new ByteArrayInputStream(rawJson.getBytes("UTF-8")));
			reportPrint = JasperFillManager.fillReport(report, params);
			return true;
						
		} catch (JRException e) {
			AppManager.printError(e.getMessage());
		} catch (UnsupportedEncodingException e) {
			AppManager.printError(e.getMessage());
		} 
		return false;

	}
	
	public static void verPdf() {
		
		if (reportPrint != null) {
			JasperViewer.viewReport(reportPrint);
		}
		
	}
	
	public static void guardarPdf(String pathGuardar) {
		if (reportPrint != null) {
			try {
				JasperExportManager.exportReportToPdfFile(reportPrint, pathGuardar);
				AppManager.showInfo("PDF creado correctamente");
			} catch (JRException e) {
				AppManager.printError(e.getMessage());
			}
		}
	}
	
	
	
}
