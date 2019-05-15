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

	private Stage logView;
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
		if (!logView.isShowing()) {
			logView.show();
		}

	}

	@FXML
	public void initialize() {
		while (!AppManager.isLogged()) {
			FXMLLoader myLoader = new FXMLLoader(getClass().getResource("/view/loginView.fxml"));
			AnchorPane scene;
			try {
				scene = myLoader.load();
				Stage log = new Stage();
				log.setTitle("Login");
				log.setResizable(false);
				log.setScene(new Scene(scene));
				log.setOnCloseRequest(event -> {
					if (AppManager.showYesNoQuestion("Cerrar",
							"No se ha efectuado el login\nDeseas cerrar la aplicacion?"))
						System.exit(0);
				});
				log.showAndWait();
				FXMLLoader myLoader2 = new FXMLLoader(getClass().getResource("/view/logView.fxml"));
				AnchorPane scene2 = myLoader2.load();
				logView = new Stage();
				logView.setTitle("Log");
				logView.setScene(new Scene(scene2));
//				logView.setOnCloseRequest(event -> {
//					LogController.setActive(false);
//				});
			} catch (IOException e) {
				new EasyErpException(e.getMessage());
			}
			if (!AppManager.isLogged())
				AppManager.showError("No se ha podido realizar el login");
		}

	}

	// TODO eliminar
	@FXML
	public void lanzarExcepcion() {
		new EasyErpException("Prueba de error");
	}

	// TODO eliminar
	@FXML
	public void printar() {
		QueryManager<Producto> query = Producto.getQueryManager();
		for (Producto producto : query.readAll().getObjectsArray()) {
			System.out.println(producto.getNombre());
		}
	}

}
