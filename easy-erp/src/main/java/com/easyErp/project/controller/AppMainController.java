package com.easyErp.project.controller;

import java.io.IOException;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AppMainController {

	@FXML
	private MenuItem menuClientes;
	@FXML
	private MenuItem menuProductos;
	@FXML
	private MenuItem menuProveedores;
	@FXML
	private MenuItem menuTPV;
	@FXML
	private AnchorPane mainWindow;
	@FXML
	private MenuItem menuLog;
	@FXML
	private MenuItem menuEditProductos;

	@FXML
	public void verClientes() {
		loadScene("/view/inicio.fxml");
	}

	@FXML
	public void verProductos() {
		loadScene("/view/productoView.fxml");
	}

	public void verProductos(Producto producto) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/productoView2.fxml"));
		Node vista;
		try {
			vista = loader.load();
			ProductoController2 controller = loader.getController();
			controller.cargarProducto(producto, vista);
			for(Node node:this.mainWindow.getChildren()) {
				node.setVisible(false);
			}
//			this.mainWindow.getChildren().clear();
			this.mainWindow.getChildren().add(vista);
			
			AnchorPane.setTopAnchor(vista,0.0);
			AnchorPane.setBottomAnchor(vista,0.0);
			AnchorPane.setLeftAnchor(vista, 0.0);
			AnchorPane.setRightAnchor(vista, 0.0);

		} catch (IOException e) {
			AppManager.printError("Error al cargar producto");
		}

	}

	public void volver(Node oldNode) {
		this.mainWindow.getChildren().remove(oldNode);
		for(Node node:this.mainWindow.getChildren()) {
			node.setVisible(true);
		}
	}
	
	@FXML
	public void cargarTPV() {
		loadScene("/view/TPVView.fxml");
	}

	public static boolean isKeyPressed(KeyEvent keyEvent, KeyCode key) {
		return keyEvent.getCode() == key ? true : false;
	}

	@FXML
	public void verProveedores() {
		loadScene("/view/proveedorView.fxml");
	}

	@FXML
	public void editarProductos() {
		loadScene("/view/editarProductosView.fxml");
	}

	public void loadScene(String location) {
		try {
			AppManager.print("Antes del nodo");
			Node node = FXMLLoader.load(getClass().getResource(location));
			mainWindow.getChildren().clear();
			mainWindow.getChildren().add(node);

			AnchorPane.setTopAnchor(node, 0.0);
			AnchorPane.setBottomAnchor(node, 0.0);
			AnchorPane.setLeftAnchor(node, 0.0);
			AnchorPane.setRightAnchor(node, 0.0);

		} catch (IOException e) {
			AppManager.printError(e.getMessage());
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
				AppManager.getInstance().setAppMain(this);
			} catch (IOException e) {
				AppManager.printError(e.getMessage());
			}
			if (!AppManager.isLogged())
				AppManager.showError("No se ha podido realizar el login");
		}
	}

	// TODO eliminar
	@FXML
	public void lanzarExcepcion() {
		AppManager.printError("Prueba de error");
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
