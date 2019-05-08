package com.easyErp.project.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class SystemLog {
	
	private static Map<Date, String> registre = initializeLog();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.SSS");
		
	public SystemLog() {
		
	}
	public void print(String message) {
		registre.put(new Date(), message);
	}
	
	private static Map<Date, String>initializeLog() {
		return new TreeMap<Date, String>();
	}
	
	public static String viewLog() {
		String informe = "";
		for(Entry<Date, String> entrada:registre.entrySet()) {
			informe += sdf.format(entrada.getKey()) + ": " + entrada.getValue() + System.lineSeparator();
		}
		return informe;
	}
}
