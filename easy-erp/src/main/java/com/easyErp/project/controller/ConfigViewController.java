package com.easyErp.project.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.easyErp.project.utils.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ConfigViewController {
	@FXML
	private TextField txtIp;
	@FXML
	private TextField txtPort;
	@FXML
	private TextField txtTienda;
	@FXML
	private Button btnSave;
	@FXML
	private Button btnCancel;

	private static File file = new File("./config.conf");

	@FXML
	public void initialize() {

		if (file.exists()) {
			BufferedReader reader = null;
			try {
				reader = new BufferedReader(new FileReader("./config.conf"));
				String[] values;
				String line;
				while ((line = reader.readLine()) != null) {
					values = line.split(":");
					loadValues(values);
				}
			} catch (FileNotFoundException exc) {
				AppManager.printError(exc.getMessage());
			} catch (IOException exc) {
				AppManager.printError(exc.getMessage());
			} finally {
				try {
					reader.close();
				} catch (Exception exc) {
					AppManager.printError(exc.getMessage());
				}
			}
		}
	}

	private void loadValues(String[] values) {
		switch (values[0]) {
		case "Url":
			this.txtIp.setText(values[1]);
			break;
		case "port":
			this.txtPort.setText(values[1]);
			break;
		case "idTienda":
			this.txtTienda.setText(values[1]);
			break;
		}
	}

	@FXML
	private void guardar() throws Exception {
		if (checkFields()) {
			FileWriter writer = null;
			writer = new FileWriter(file, true);
			writer.write("Url:" + txtIp.getText().trim() + System.lineSeparator());
			writer.write("port:" + txtPort.getText().trim() + System.lineSeparator());
			writer.write("idTienda:" + txtTienda.getText().trim() + System.lineSeparator());
			writer.close();
			cerrar();
		}
	}

	private boolean checkFields() {
		if (txtIp.getText().trim().isEmpty() || txtPort.getText().trim().isEmpty()
				|| txtTienda.getText().trim().isEmpty())
			return false;
		return true;
	}
	
	@FXML
	private void cerrar() {
		Stage stage = (Stage) this.btnSave.getScene().getWindow();
		stage.close();
	}
}
