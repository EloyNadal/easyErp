package com.easyErp.project.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import com.easyErp.project.controller.AppMainController;
import com.easyErp.project.log.EasyErpException;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


public class AppManager {
//	192.168.2.251
	private static boolean logged = false;
	private static String baseUrl;
	private static int idTienda;
	private static AppManager manager = new AppManager();
	private AppMainController main;

	public AppManager() {
		inicializarVariables();
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
			new EasyErpException(exc.getMessage());
		} catch (IOException exc) {
			new EasyErpException(exc.getMessage());
		} finally {
			try {
				lector.close();
			} catch (IOException exc) {
				new EasyErpException(exc.getMessage());
			}
		}
		baseUrl = "http://" + variables.get("Url") + ":" + variables.get("port") + "/";
		idTienda = Integer.parseInt(variables.get("idTienda"));
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
		Alert alert =
                new Alert(Alert.AlertType.CONFIRMATION,
                        message,
                        ButtonType.OK,
                        ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(null);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) return true;
        return false;
	}
}
