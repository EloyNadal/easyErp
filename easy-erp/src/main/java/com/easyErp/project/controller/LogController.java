package com.easyErp.project.controller;

import com.easyErp.project.log.SystemLog;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class LogController extends Application {

	@FXML
	CheckBox chbErrores;
	@FXML
	CheckBox chbDebug;
	@FXML
	CheckBox chbConexiones;
	@FXML
	Label txtLog;

	private static Boolean active = false;

	@FXML
	public void initialize() {
		setActive(true);
		chbErrores.setSelected(true);
		chbErrores.setOnMouseClicked(event -> {
			SystemLog.setErrores(chbErrores.isSelected());

		});
		chbDebug.setOnMouseClicked(event -> {
			SystemLog.setDebug(chbDebug.isSelected());

		});
		chbConexiones.setOnMouseClicked(event -> {
			SystemLog.setConexiones(chbConexiones.isSelected());

		});
	}

	private void addText(String lines, Label label) {

		label.setText(label.getText() + lines);

	}

	public static boolean isActive() {
		synchronized (LogController.active) {
			return active;
		}
	}

	public static void setActive(boolean active) {
		synchronized (LogController.active) {
			LogController.active = active;
		}
	}

	@Override
	public void start(Stage arg0) throws Exception {
		while (isActive()) {
			addText(SystemLog.viewLog(), txtLog);
		}
	}

}
