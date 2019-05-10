package com.easyErp.project.log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

public class SystemLog {
	
	private static boolean errores = true;
	private static boolean conexiones = false;
	private static boolean debug = false;
	private static Map<Date, String> registre = initializeLog();
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		
	public SystemLog() {
		
	}
	
	public static boolean isErrores() {
		return errores;
	}

	public static void setErrores(boolean errores) {
		SystemLog.print("cambio errores");
		SystemLog.errores = errores;
	}

	public static boolean isConexiones() {
		return conexiones;
	}

	public static void setConexiones(boolean conexiones) {
		SystemLog.print("cambio conexiones");
		SystemLog.conexiones = conexiones;
	}

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		SystemLog.print("cambio Debug");
		SystemLog.debug = debug;
	}

	public static void print(String message) {
		synchronized (registre) {
			registre.put(new Date(), message);
		}
	}
	
	public static void print (Date date, String message) {
		synchronized (registre) {
			registre.put(date, message);
		}
	}
	
	private static Map<Date, String>initializeLog() {
		return new TreeMap<Date, String>();
	}
	
	public static String viewLog() {
		String informe = "";
		synchronized (registre) {
			for (Entry<Date, String> entrada : registre.entrySet()) {
				informe += sdf.format(entrada.getKey()) + ": " + entrada.getValue() + System.lineSeparator();
			}
			registre.clear();
		}
		return informe;
	}
	
}
