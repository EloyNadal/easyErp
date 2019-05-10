package com.easyErp.project.log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EasyErpException {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

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

}
