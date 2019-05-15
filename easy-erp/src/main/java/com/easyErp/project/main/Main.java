package com.easyErp.project.main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AppMain.fxml"));
		VBox root = (VBox)loader.load();
		
		Scene scene = new Scene(root,800,600);
		primaryStage.setScene(scene);
		primaryStage.setTitle("EasyERP");
		primaryStage.setMinWidth(800);
		primaryStage.setMinHeight(600);
		primaryStage.show();
	}

	public static void main(String[] args){
		launch(args);
	}

}
