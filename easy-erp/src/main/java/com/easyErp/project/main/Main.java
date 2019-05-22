package com.easyErp.project.main;

import com.easyErp.project.controller.AppMainController;
import com.easyErp.project.model.AppManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppMain.fxml"));
		VBox root = (VBox) loader.load();

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("EasyERP");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.F11)) {
				AppManager.getInstance().getAppMain().mostrarLog();
			}

		});
		primaryStage.addEventFilter( WindowEvent.WINDOW_CLOSE_REQUEST, event->{
			event.consume();
			if (AppManager.showYesNoQuestion("Cerrar",
					"Deseas cerrar la aplicacion?"))
				System.exit(0);
		}
		);
		primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}

}
