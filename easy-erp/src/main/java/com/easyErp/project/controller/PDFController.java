package com.easyErp.project.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import com.easyErp.project.model.AppManager;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.query.JsonQueryExecuterFactory;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

public class PDFController{

	private static JasperReport report;
	private static JasperPrint reportPrint;
	private static JasperViewer reportViewer;
	
	
	/**
	 * Crar archivos pdf desde un json file junto con varios parametros
	 * @param rawJson json array, lista de elementos a mostrar en la tabla
	 * @param params parametros a mostrar en cabezeras (proveedor y tienda origen)
	 */
	public static boolean CrearPdf(String rawJson, Map<String, Object> params) {
		
		try {
			report = (JasperReport) JRLoader.loadObject(new File("src/main/resources/pdf/Invoice.jasper"));
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
			} catch (JRException e) {
				AppManager.printError(e.getMessage());
			}
		}
	}
	
	
}
