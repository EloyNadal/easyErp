package com.easyErp.project.controller;

import java.io.IOException;

import com.easyErp.project.log.EasyErpException;
import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
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
			new EasyErpException(e.getMessage());
		}
	}

	public void mostrarLog() {
//		if (!LogController.isLogActive()) {
			try {
				FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/logView.fxml"));
				AnchorPane scene = myLoader.load();
				Stage log = new Stage();
				log.setTitle("Log");
				log.setScene(new Scene(scene));
				log.show();
			} catch (IOException e) {
				new EasyErpException(e.getMessage());
			}
//		}

	}

	@FXML
	public void initialize() {

		FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/loginView.fxml"));
		AnchorPane scene;
		try {
			scene = myLoader.load();
			Stage log = new Stage();
			log.setTitle("Login");
			log.setResizable(false);
			log.setScene(new Scene(scene));
			log.showAndWait();
		} catch (IOException e) {
			new EasyErpException(e.getMessage());
		}

	}

	// TODO eliminar
	@FXML
	public void lanzarExcepcion() {
		new EasyErpException("Prueba de error");
	}
	//TODO eliminar
	@FXML
	public void printar() {
		QueryManager<Producto> query = new QueryManager<Producto>(Producto.class, Producto[].class);
		for(Producto producto:query.readAll(AppManager.BASE_URL + "producto")) {
			System.out.println(producto.getNombre());
		}
	}
	
}
