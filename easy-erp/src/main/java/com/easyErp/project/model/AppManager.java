package com.easyErp.project.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.easyErp.project.controller.AppMainController;
import com.easyErp.project.log.SystemLog;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class AppManager {
//	192.168.2.251
	private static boolean logged = false;
	private static String baseUrl;
	private static int idTienda;
	private static AppManager manager = new AppManager();
	private AppMainController main;
	private Stage stage;
	private static SystemLog sysLog;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static String[] ERRORES = { "Problema de conexion con el servidor", "Elemento no encontrado",
			"Faltan campos por rellenar" };

	private AppManager() {
		inicializarVariables();
		sysLog = SystemLog.getInstance();
//		baseUrl = "http://localhost:8000/";
	}

	public void setAppMain(AppMainController main) {
		this.main = main;
	}

	public AppMainController getAppMain() {
		return this.main;
	}

	public static AppManager getInstance() {
		return manager;
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static int getIdTienda() {
		return idTienda;
	}

	public static void printError(String message) {
		Date date = new Date();
		sysLog.printError(date, message);
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
			writer.write(SDF.format(date) + ": " + message + System.lineSeparator());
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

	public static void printConnection(String message) {
		sysLog.printConnection(message);
	}
	
	public static void print(String message) {
		sysLog.print(message);
	}
	private void inicializarVariables() {
		Map<String, String> variables = new HashMap<String, String>();
		BufferedReader lector = null;
		try {
			lector = new BufferedReader(new FileReader("./config.conf"));
			String[] entrada;
			String linea;
			while ((linea = lector.readLine()) != null) {
				entrada = linea.split(":");
				variables.put(entrada[0], entrada[1]);
			}
		} catch (FileNotFoundException exc) {
			printError(exc.getMessage());
		} catch (IOException exc) {
			printError(exc.getMessage());
		} finally {
			try {
				lector.close();
			} catch (IOException exc) {
				printError(exc.getMessage());
			}
		}
		baseUrl = "http://" + variables.get("Url") + ":" + variables.get("port") + "/";
		idTienda = Integer.parseInt(variables.get("idTienda"));
	}

	public static void createExceptionFromErrorCode(int codigoError) {
		switch (codigoError) {
		case 404:
			printError(ERRORES[0]);
			break;
		default:
			printError("UnknownError");
		}
	}

	public static void showError(String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

	public static void setLogged(boolean loged) {
		logged = loged;
	}

	public static boolean isLogged() {
		return logged;
	}

	public static boolean showYesNoQuestion(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle(title);
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
			return true;
		return false;
	}

	public void setStage(Stage stage) {
		AppManager.print("aqui stage");
		this.stage = stage;
	}
	
	public Stage getStage() {
		return this.stage; 
	}
}

