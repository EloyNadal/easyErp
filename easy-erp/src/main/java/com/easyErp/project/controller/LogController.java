package com.easyErp.project.controller;

import com.easyErp.project.log.SystemLog;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;

public class LogController extends Thread{

	@FXML CheckBox chbErrores;
	@FXML CheckBox chbDebug;
	@FXML CheckBox chbConexiones;
	@FXML TextArea txtLog;
	
	@FXML
	public void initialize() {
		chbErrores.setOnMouseClicked(event->{
			SystemLog.setErrores(chbErrores.isSelected());
			
		});
		chbDebug.setOnMouseClicked(event->{
			SystemLog.setDebug(chbDebug.isSelected());
			
		});
		chbConexiones.setOnMouseClicked(event->{
			SystemLog.setConexiones(chbConexiones.isSelected());
			
		});
		txtLog.setText(" ");
		this.start();
		
	}
	@Override
	public void run() {
		while(true) {
			txtLog.setText(txtLog.getText() + SystemLog.viewLog());
		}
	}
	
}
