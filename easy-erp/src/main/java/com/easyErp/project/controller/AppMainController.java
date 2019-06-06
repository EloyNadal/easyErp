package com.easyErp.project.controller;

import java.io.IOException;
import java.net.URL;

import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.Tienda;
import com.easyErp.project.utils.AppManager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Clase para controlar la vista principal de la aplicación.
 * Contiene los menús.
 * 
 */
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
	@FXML
	private MenuItem menuPedido;
	@FXML
	private MenuItem menuEstadoPedido;
	
	public void verClientes() {
		loadScene("/view/verClientesView.fxml");
	}
	
	public void pedido() {
		loadScene("/view/crearPedidoView.fxml");
	}
	
	public void estadoPedido() {
		loadScene("/view/verEstadoPedidos.fxml");
	}
	
	public void verProductos() {
		loadScene("/view/productoView.fxml");
	}
	public void nuevoProducto() {
		editarProducto(null);
	}
	public void nuevoCliente() {
		editarCliente(null);
	}
	public void addUser() {
		try {
			getNewWindow(getClass().getResource("/view/crearUsuarioView.fxml"), "Añadir Usuario").show();
		} catch (Exception e) {
			AppManager.showError("Error al cargar la vista: crearUsuarioView.fxml");
		}
		
	}
	
	public void showInfo() {
		try {
			getNewWindow(getClass().getResource("/view/infoView.fxml"), "Acerca de").show();
		} catch (Exception e) {
			AppManager.showError("Error al cargar la vista: infoView.fxml");
		}
	}
	
	public void editarProducto(Producto producto) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editarProductoView.fxml"));
		Node vista;
		try {
			vista = loader.load();
			EditarProductoController controller = loader.getController();
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

	public Stage verTienda(Tienda tienda) throws IOException {
		if(tienda == null) return null;
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/tiendaView.fxml"));
		AnchorPane scene = loader.load();
		TiendaController controller = loader.getController();
		controller.cargarTienda(tienda);
		Stage stage = new Stage();
		stage.setTitle("Información tienda");
		stage.setResizable(false);
		stage.setScene(new Scene(scene));
		stage.getIcons().add(new Image("/image/icon.png"));
		return stage; 
	}
	public void editarCliente(Cliente cliente) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/editarClienteView.fxml"));
		Node vista;
		try {
			vista = loader.load();
			EditarClienteController controller = loader.getController();
			controller.cargarCliente(cliente, vista);
			for(Node node:this.mainWindow.getChildren()) {
				node.setVisible(false);
			}
			this.mainWindow.getChildren().add(vista);
			
			AnchorPane.setTopAnchor(vista,0.0);
			AnchorPane.setBottomAnchor(vista,0.0);
			AnchorPane.setLeftAnchor(vista, 0.0);
			AnchorPane.setRightAnchor(vista, 0.0);

		} catch (Exception e) {
			AppManager.printError("Error al cargar cliente");
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
		loadScene("/view/verProductosView.fxml");
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
		stage.getIcons().add(new Image("/image/icon.png"));
		return stage; 
	}

	@FXML
	public void initialize() {
		AppManager.getInstance().setAppMain(this);
	}
}
