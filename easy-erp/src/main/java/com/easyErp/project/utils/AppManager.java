package com.easyErp.project.utils;

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
import com.easyErp.project.log.LogPro;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Clase para controlar los diferentes eventos dentro de la aplicación.
 * Se encarga de gestionar el fichero de configuración y capturar los posibles errores.
 * 
 */
public class AppManager {
	private static boolean logged = false;
	private static String baseUrl;
	private static int idTienda;
	private static AppManager manager = new AppManager();
	private AppMainController main;
	private Stage stage;
	private static LogPro sysLog;
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	private static String[] ERRORES = {"Elemento no encontrado", "Elemento duplicado, no se puede crear/"
			+ "Faltan campos requeridos", "Problema de conexion con el servidor", "Acceso no autorizado",
			"Faltan campos por rellenar"};
	private Scene scene;
	private static Integer sessionUserId;

	private AppManager() {
		inicializarVariables();
		sysLog = LogPro.getInstance();
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
	/**
	 * Método que recupera de un el valor de las variables necesarias para el funcionamiento de la aplicación 
	 */
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

	/**
	 * Método que crea un fichero de texto para guardar un registro de errores. También se comunica con el Log 
	 * para mostrar los errores durante la ejecución.
	 * @param message Contenido del error
	 */
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

	/**
	 * Método que se comunica con el Log para mostrar las conexiones con el WebService durante la ejecución.
	 * @param message Contenido de la comunicación
	 */
	public static void printConnection(String message) {
		sysLog.printConnection(message);
	}

	/**
	 * Método que se comunica con el Log para mostrar mensajes durante la ejecución. Se utiliza para el debug
	 * @param message Contenido del mensaje
	 */
	public static void print(String message) {
		sysLog.print(message);
	}

	/**
	 * Método que crea un mensaje de error dado un código recibido del WebService
	 * @param codigoError
	 */
	public static void createExceptionFromErrorCode(int codigoError) {
		switch (codigoError) {
		case 404:
			printError(ERRORES[0]);
			break;
		case 422:
			printError(ERRORES[1]);
			break;
		case 500:
			printError(ERRORES[2]);
			break;
		case 401:
			showError(ERRORES[3]);
			break;
		default:
			printError("UnknownError");
		}
	}

	/**
	 * Método que permite mostrar un mensaje de error al usuario
	 * @param message Contenido del error
	 */
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

	/**
	 * Método que permite la comunicación con el usuario a través de una pregunta con respuesta si o no
	 * @param title Título del mensaje
	 * @param message Pregunta a realizar
	 * @return Cierto en caso de respuesta afirmativa, falso en caso contrario
	 */
	public static boolean showYesNoQuestion(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, ButtonType.OK, ButtonType.CANCEL);
		alert.setTitle(title);
		alert.setHeaderText(null);
		Optional<ButtonType> result = alert.showAndWait();

		if (result.get() == ButtonType.OK)
			return true;
		return false;
	}

	/**
	 * Método que permite la comunicación con el usuario para recibir un dato
	 * @param title Título del mensaje
	 * @param message Pregunta a realizar
	 * @return Dato introducido por el usuario. Éste será nulo si el usuario no intrduce ningún dato
	 */
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

	/**
	 * Método que permite la comunicación con el usuario informándole de algún suceso durante la ejecución
	 * @param message Pregunta a realizar
	 */
	public static void showInfo(String message) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Información");
		alert.setContentText(message);
		alert.setHeaderText(null);
		alert.showAndWait();
	}
	
	public static File openFileChooser(String title, boolean open, String type, String extension) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(type, extension));
		File file;
		if (open)
			file = fileChooser.showOpenDialog(AppManager.getInstance().getStage());
		else
			file = fileChooser.showSaveDialog(AppManager.getInstance().getStage());
		return file;
	}
	
	

}
