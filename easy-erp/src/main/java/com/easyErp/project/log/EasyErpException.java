package com.easyErp.project.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EasyErpException extends Exception{
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static String[] ERRORES = {"Problema de conexion con el servidor", "Elemento no encontrado", "Faltan campos por rellenar"};
	
	public EasyErpException(String message) {
		Date date = new Date();
		SystemLog.print(date, message);
		File dir = new File("./log");
		File file = new File(dir, "log_errores.txt");
		if (!file.exists())
			try {
				dir.mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		FileWriter writer = null;

		try {
			writer = new FileWriter(file, true);
			writer.write(sdf.format(date) + ": " + message + System.lineSeparator());
		} catch (FileNotFoundException exc) {

		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
			}
		}
	}
	
	public static void createExceptionFromErrorCode(int codigoError) {
		switch(codigoError) {
		case 404:
			new EasyErpException(ERRORES[0]);
			break;
		}
	}

}
