package com.easyErp.project.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class EasyErpException {
	
	private static Map<Date, String> registre = initializeRegistre();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
	
	public EasyErpException(String message) {
		Date date = new Date();
		synchronized (registre) {
			registre.put(date, message);
		}
		File dir = new File("./log");
		File file = new File(dir, "log_errores.txt");
		if(!file.exists())
			try {
				dir.mkdirs();
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		FileWriter writer = null;
		
		try {
			writer = new FileWriter(file , true);
			writer.write(sdf.format(date) +": " + message + System.lineSeparator());
		} catch (FileNotFoundException exc) {
			
		} catch (IOException exc) {
			exc.printStackTrace();
		} finally {
			if(writer != null) {
				try {
					writer.close();
				}catch(IOException exc) {
					exc.printStackTrace();
				}
			}
		}
	}
	
	private static Map<Date, String>initializeRegistre() {
		return new TreeMap<Date, String>();
	}
	
	public static String registreErrors() {
		String informe = "";
		synchronized (registre) {
			for (Entry<Date, String> entrada : registre.entrySet()) {
				informe += sdf.format(entrada.getKey()) + ": " + entrada.getValue() + System.lineSeparator();
			}
		}
		return informe;
	}
}
