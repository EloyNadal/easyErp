package com.easyErp.project.main;

import java.io.File;
import java.io.IOException;

import com.easyErp.project.controller.AppMainController;
import com.easyErp.project.log.LogPro;
import com.easyErp.project.model.AppManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			comprobacionFicheroConfiguracion();
		} catch (Exception e1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Error");
			alert.setContentText("Error al cargar la pantalla de configuración");
			alert.setHeaderText(null);
			alert.showAndWait();
		}
		LogPro.getInstance();
		AppManager.getInstance().setStage(primaryStage);
		login();
		
//		Se inicializa la variable stage del AppManager
		AppManager.getInstance().setStage(primaryStage);
//		Se carga la vista principal de la aplicación
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppMain.fxml"));
		VBox root = (VBox) loader.load();
		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("EasyERP");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
//		Se establece acceso rápido al log de errores durante la ejecución
		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.F11)) {
				if(!LogPro.getInstance().isShowing()) {
					LogPro.getInstance().show();
					
				}
					
				else
					LogPro.getInstance().requestFocus();
			}

		});
//		Se previene el cierre accidental de la aplicación
		primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
			event.consume();
			if (AppManager.showYesNoQuestion("Cerrar", "Deseas cerrar la aplicacion?"))
				System.exit(0);
		});
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Método para comprobar la existencia del fichero de configuración La
	 * aplicación no se inicia hasta que exista el fichero de configuracion, ya que
	 * necesitamos los datos de conexión al servidor
	 */
	private void comprobacionFicheroConfiguracion() throws Exception {
		File file = new File("./config.conf");
		while (!file.exists()) {
			Stage stage = new Stage();
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/configView.fxml"));
			AnchorPane root;
			root = loader.load();
			Scene scene = new Scene(root, 400, 400);
			stage.setScene(scene);
			stage.setTitle("EasyERP Configuracion");
			stage.showAndWait();
		}
	}

	/**
	 * Método que realiza la carga de la pantalla del login
	 */
	private void login() {
		while (!AppManager.isLogged()) {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/loginView.fxml"));
			AnchorPane scene;
			try {
				scene = myLoader.load();
				Stage login = new Stage();
				login.setTitle("Login");
				login.setResizable(false);
				login.setScene(new Scene(scene));
				login.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
					event.consume();
					if (!AppManager.isLogged() && AppManager.showYesNoQuestion("Cerrar",
							"No se ha efectuado el login\nDeseas cerrar la aplicacion?"))
						System.exit(0);
				});
				login.showAndWait();
			} catch (IOException e) {
				AppManager.printError("Error al cargar el login");
			}
		}
	}
}
