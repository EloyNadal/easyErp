package com.easyErp.project.controller;

import java.io.IOException;
import java.net.URL;

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
	private MenuItem menuAddUser;
	
	
	public void verClientes() {
		loadScene("/view/inicio.fxml");
	}

	
	public void verProductos() {
		loadScene("/view/productoView.fxml");
	}
	
	public void addUser() {
		try {
			getNewWindow(getClass().getResource("/view/usuarioView.fxml"), "Añadir Usuario").show();
		} catch (Exception e) {
			AppManager.showError("Error al cargar la vista: usuarioView.fxml");
		}
		
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
			this.mainWindow.getChildren().add(vista);
			
			AnchorPane.setTopAnchor(vista,0.0);
			AnchorPane.setBottomAnchor(vista,0.0);
			AnchorPane.setLeftAnchor(vista, 0.0);
			AnchorPane.setRightAnchor(vista, 0.0);

		} catch (Exception e) {
			AppManager.printError("Error al cargar producto");
		}

	}

	public void volver(Node oldNode) {
		this.mainWindow.getChildren().remove(oldNode);
		for(Node node:this.mainWindow.getChildren()) {
			node.setVisible(true);
		}
	}
	
	
	public void cargarTPV() {
		loadScene("/view/TPVView.fxml");
	}

	public static boolean isKeyPressed(KeyEvent keyEvent, KeyCode key) {
		return keyEvent.getCode() == key ? true : false;
	}

	
	public void verProveedores() {
		loadScene("/view/proveedorView.fxml");
	}

	
	public void editarProductos() {
		loadScene("/view/editarProductosView.fxml");
	}

	public void loadScene(String location) {
		try {
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

	public static Stage getNewWindow(URL location, String title) throws Exception {
		FXMLLoader loader = new FXMLLoader(location);
		AnchorPane scene;
		scene = loader.load();
		Stage stage = new Stage();
		stage.setTitle(title);
		stage.setResizable(false);
		stage.setScene(new Scene(scene));
		return stage; 
	}

	@FXML
	public void initialize() {
		AppManager.getInstance().setAppMain(this);
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
