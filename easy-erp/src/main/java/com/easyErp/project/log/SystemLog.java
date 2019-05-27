package com.easyErp.project.log;

import java.util.Date;
import java.util.LinkedList;

import com.easyErp.project.log.SystemLog.LogType;

public class SystemLog {
	
	private boolean errores = true;
	private boolean conexiones = true;
	private boolean debug = true;
	private LinkedList<LogMessage> registre = initializeLog();
	private static SystemLog sysLog = new SystemLog();
	private LogPro log;
	
	enum LogType {
		ERROR("#FF0000"), INFO("#006400"), CONNECTION("#00008B");
		private String color;

		LogType(String c) {
			color = c;
		}

		String getColor() {
			return color;
		}
	}
	
	private SystemLog() {
		log = LogPro.getInstance();
	}

	public static SystemLog getInstance() {
		return sysLog;
	}

	public boolean isErroresActive() {
		return this.errores;
	}

	public void setErrores(boolean errores) {
		this.errores = errores;
	}

	public boolean isConexionesActive() {
		return this.conexiones;
	}

	public void setConexiones(boolean conexiones) {
		this.conexiones = conexiones;
	}

	public boolean isDebugActive() {
		return this.debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public void print(String message) {
		if (isDebugActive())
			synchronized (this.registre) {
//				if(log.isShowing())
				log.addMessage(new LogMessage(LogType.INFO, new Date(), message));
				registre.addFirst(new LogMessage(LogType.INFO, new Date(), message));
			}
	}

	public void printConnection(String message) {
		if (isConexionesActive())
			synchronized (this.registre) {
//				if(log.isShowing())
				log.addMessage(new LogMessage(LogType.CONNECTION, new Date(), message));
				registre.addFirst(new LogMessage(LogType.CONNECTION, new Date(), message));
			}
	}

	public void printError(Date date, String message) {
		if (isErroresActive())
			synchronized (this.registre) {
//				if(log.isShowing())
				log.addMessage(new LogMessage(LogType.ERROR, new Date(), message));
				registre.addFirst(new LogMessage(LogType.ERROR, date, message));
			}
	}

	private LinkedList<LogMessage> initializeLog() {
		return new LinkedList<LogMessage>();
	}

	public LogMessage getMessage() {
		synchronized (this.registre) {
			return registre.removeLast();
		}
	}

	public boolean isEmpty() {
		synchronized (this.registre) {
			return registre.isEmpty();
		}
	}
}

class LogMessage {
	private LogType type;
	private Date date;
	private String message;

	public LogMessage(LogType type, Date date, String message) {
		this.type = type;
		this.date = date;
		this.message = message;
	}

	public LogType getType() {
		return type;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}

}
