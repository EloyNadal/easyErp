package com.easyErp.project.main;

import java.io.IOException;

import com.easyErp.project.controller.AppMainController;
import com.easyErp.project.model.AppManager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	private Stage logView;

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppMain.fxml"));
		VBox root = (VBox) loader.load();
		AppManager.getInstance().setStage(primaryStage);

		Scene scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("EasyERP");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/logView.fxml"));
		AnchorPane scene2;
		try {
			scene2 = myLoader.load();
			AnchorPane.setTopAnchor(scene2, 0.0);
			AnchorPane.setBottomAnchor(scene2, 0.0);
			AnchorPane.setLeftAnchor(scene2, 0.0);
			AnchorPane.setRightAnchor(scene2, 0.0);
			this.logView = new Stage();
			this.logView.setTitle("Log");
			this.logView.setScene(new Scene(scene2));
			this.logView.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
				event.consume();
				logView.hide();
			});
		} catch (IOException e) {
			AppManager.printError(e.getMessage());
		}
		primaryStage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.F11)) {
				if (!logView.isShowing())
				logView.show();
			}

		});
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
}
