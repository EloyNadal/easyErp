package com.easyErp.project.controller;

import java.io.IOException;

import com.easyErp.project.log.EasyErpException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AppMainController {

	@FXML
	MenuItem menuClientes;
	@FXML
	MenuItem menuProductos;
	@FXML
	MenuItem menuProveedores;
	@FXML
	AnchorPane mainWindow;
	@FXML
	MenuItem menuLog;
	
	@FXML
	public void verClientes() {
		loadScene("/view/inicio.fxml");
	}

	@FXML
	public void verProductos() {
		loadScene("/view/productoView.fxml");
	}

	@FXML
	public void verProveedores() {
		loadScene("/view/proveedorView.fxml");
	}

	public void loadScene(String location) {
		try {
			Node node = FXMLLoader.load(getClass().getResource(location));
			mainWindow.getChildren().clear();
			mainWindow.getChildren().add(node);

		} catch (IOException e) {

		}
	}
	
	public void mostrarLog() {
        
		try {	
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/logView.fxml"));
			AnchorPane scene = myLoader.load();
			Stage log = new Stage();
			log.setTitle("Log");
			log.setScene(new Scene(scene));
			log.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void lanzarExcepcion() {
		new EasyErpException("Prueba de error");
	}
}
