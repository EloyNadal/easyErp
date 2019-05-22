package com.easyErp.project.controller;

import java.io.IOException;

import com.easyErp.project.log.EasyErpException;
import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
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
	MenuItem menuTPV;
	@FXML
	AnchorPane mainWindow;
	@FXML
	MenuItem menuLog;
	@FXML
	MenuItem menuEditProductos;
	LogController logController; 
	@FXML
	public void verClientes() {
		loadScene("/view/inicio.fxml");
	}

	@FXML
	public void verProductos() {
		loadScene("/view/productoView.fxml");
	}

	public void verProductos(Producto producto) {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/productoView.fxml"));
		Node vista;
		try {
			vista = loader.load();
			if (producto != null) {
				ProductoController controller = loader.getController();
				controller.cargarProducto(producto);
			}

			this.mainWindow.getChildren().clear();
			this.mainWindow.getChildren().add(vista);

			AnchorPane.setTopAnchor(vista, 0.0);
			AnchorPane.setBottomAnchor(vista, 0.0);
			AnchorPane.setLeftAnchor(vista, 0.0);
			AnchorPane.setRightAnchor(vista, 0.0);

		} catch (IOException e) {
			new EasyErpException("Error al cargar producto");
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
	public void editarProductos() throws IOException {
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
				logController = myLoader2.getController();
				logView = new Stage();
				logView.setTitle("Log");
				logView.setScene(new Scene(scene2));
				AppManager.getInstance().setAppMain(this);
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
