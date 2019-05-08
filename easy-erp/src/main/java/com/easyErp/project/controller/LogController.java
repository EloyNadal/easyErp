package com.easyErp.project.controller;

import com.easyErp.project.log.EasyErpException;
import com.easyErp.project.log.SystemLog;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class LogController extends Thread{

	@FXML TextArea txtErrores;
	@FXML TextArea txtMensajes;
	@FXML TextArea txtConexiones;
	
	@FXML
	public void initialize() {
		this.start();
	}
	@Override
	public void run() {
		while(true) {
			txtErrores.setText(EasyErpException.registreErrors());
			txtConexiones.setText(SystemLog.viewLog());
		}
	}
	
}
