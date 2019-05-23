package com.easyErp.project.log;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class LogController extends Thread{

	@FXML
	CheckBox chbErrores;
	@FXML
	CheckBox chbDebug;
	@FXML
	CheckBox chbConexiones;
	@FXML
	TextArea txtLog;
	@FXML
	ScrollPane scroll;
	@FXML
	AnchorPane pane;
	@FXML
	VBox box;
	
	private SystemLog sysLog;
	
	@FXML
	public void initialize() {
		sysLog = SystemLog.getInstance();
		chbErrores.setOnMouseClicked(event -> {
			sysLog.setErrores(chbErrores.isSelected());

		});
		chbDebug.setOnMouseClicked(event -> {
			sysLog.setDebug(chbDebug.isSelected());

		});
		chbConexiones.setOnMouseClicked(event -> {
			sysLog.setConexiones(chbConexiones.isSelected());

		});
		this.start();
	}

	@Override
	public void run() {
		sysLog.setErrores(true);
		while (true) {
			if (!sysLog.isEmpty()) {
				LogMessage message = sysLog.getMessage();
				txtLog.setText(txtLog.getText() + message.getMessage() + System.lineSeparator());
			}
		}
	}
}
