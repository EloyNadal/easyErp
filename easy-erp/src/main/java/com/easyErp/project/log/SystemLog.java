package com.easyErp.project.log;

import java.util.Date;
import java.util.LinkedList;

import com.easyErp.project.log.SystemLog.LogType;

public class SystemLog {
	
	private boolean errores = false;
	private boolean conexiones = false;
	private boolean debug = false;
	private LinkedList<LogMessage> registre = initializeLog();
	private static SystemLog sysLog = new SystemLog();

	enum LogType {
		ERROR("#FF0000"), INFO("#7CFC00"), CONNECTION("#0000FF");
		private String color;

		LogType(String c) {
			color = c;
		}

		String getColor() {
			return color;
		}
	}

	private SystemLog() {
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
				registre.addFirst(new LogMessage(LogType.INFO, new Date(), message));
			}
	}

	public void printConnection(String message) {
		if (isConexionesActive())
			synchronized (this.registre) {
				registre.addFirst(new LogMessage(LogType.CONNECTION, new Date(), message));
			}
	}

	public void printError(Date date, String message) {
		if (isErroresActive())
			synchronized (this.registre) {
				registre.addFirst(new LogMessage(LogType.CONNECTION, date, message));
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
			return registre.size() == 0;
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
