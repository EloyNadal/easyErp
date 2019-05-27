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

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;

public class AppManager {
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
	private Scene scene;
	private static Integer sessionUserId;

	private AppManager() {
		inicializarVariables();
		sysLog = SystemLog.getInstance();
	}
	
	public void setScene(Scene scene) {
		this.scene = scene;
	}
	public Scene getScene() {
		return scene;
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

	public static Integer getSessionUserId() {
		return sessionUserId;
	}

	public static void setSessionUser(int sessionUserId) {
		AppManager.sessionUserId = sessionUserId;
	}

	private static void inicializarVariables() {
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
				AppManager.baseUrl = "http://" + variables.get("Url") + ":" + variables.get("port") + "/";
				AppManager.idTienda = Integer.parseInt(variables.get("idTienda"));
			} catch (Exception exc) {
				printError("Error con el fichero de configuración");
			}

		}

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

	public static String showMessageForStringResult(String title, String message) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setGraphic(null);
		dialog.setContentText(null);
		dialog.setHeaderText(message);
		dialog.setTitle(title);
		dialog.showAndWait();
		return dialog.getResult();
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return this.stage;
	}

	public static void showInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}

}
