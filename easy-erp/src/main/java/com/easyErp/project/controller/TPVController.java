package com.easyErp.project.controller;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.VentaLinea;
import com.jfoenix.controls.JFXButton;
import com.sun.glass.events.WindowEvent;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class TPVController {
	@FXML
	JFXButton btnErase;
	@FXML
	TextField txtLineaTeclado;
	@FXML
	TextField txtLineaProducto;
	@FXML
	TextField txtTotal;
	@FXML
	TableView<VentaLinea> tableVentaLinea;

	private ArrayList<Producto> productos;
	private ArrayList<VentaLinea> lineas;
	private double total = 0;
	private static final DecimalFormat DF = new DecimalFormat("##.00");
	@FXML
	private void borrar() {
		txtLineaTeclado.setText("");
	}

	@FXML
	public void initialize() {
		txtLineaProducto.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.ENTER)) {
				buscarProducto();
			}
		});
		txtLineaProducto.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (!newValue) { 
						txtLineaProducto.requestFocus();
					}
				}
		});
		this.productos = new ArrayList<Producto>();
		this.lineas = new ArrayList<VentaLinea>();
		this.tableVentaLinea.getColumns().clear();

		TableColumn<VentaLinea, Integer> colCantidad = new TableColumn<VentaLinea, Integer>("Cantidad");
		TableColumn<VentaLinea, String> colNombre = new TableColumn<VentaLinea, String>("Producto");
		TableColumn<VentaLinea, String> colPrecio = new TableColumn<VentaLinea, String>("Precio");

		this.tableVentaLinea.getColumns().addAll(colCantidad, colNombre, colPrecio);

		colCantidad.setMinWidth(30);
		colCantidad.setMaxWidth(400);
		colCantidad.setCellValueFactory(new PropertyValueFactory<VentaLinea, Integer>("cantidad"));

		colNombre.setMinWidth(120);
		colNombre.setMaxWidth(400);
		colNombre.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VentaLinea, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<VentaLinea, String> linea) {
						return new SimpleStringProperty(getNombreProductoFromId(linea.getValue().getProducto_id()));
					}
				});
		colPrecio.setMinWidth(40);
		colPrecio.setMaxWidth(400);
		colPrecio.getStyleClass().add("{-fx-alignment: center-right;}");
		colPrecio.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<VentaLinea, String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(TableColumn.CellDataFeatures<VentaLinea, String> linea) {
				return new SimpleStringProperty(DF.format(linea.getValue().getPrecio()) + "€");
			}
		});

		this.tableVentaLinea.setEditable(false);
		this.tableVentaLinea.getSelectionModel().setCellSelectionEnabled(true);
		this.tableVentaLinea.getItems().clear();
		this.tableVentaLinea.setItems(FXCollections.observableArrayList(lineas));
		this.tableVentaLinea.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	private void buscarProducto() {
		Producto producto = null;
		String prodCode = txtLineaProducto.getText();
		txtLineaProducto.setText("");
		int cantidad;
		cantidad = txtLineaTeclado.getText().equals("")?1:Integer.parseInt(txtLineaTeclado.getText());
		producto = comprobarProducto(producto, prodCode);
		if (null == producto) {
			RequestBody request = new FormBody.Builder().add("ean13", prodCode).build();
			try {
				producto = Producto.getQueryManager().readQuery(request, 1).getObjectsArray().get(0);
				this.productos.add(producto);
				this.lineas.add(getLineaFromProduct(producto, cantidad));
			} catch (Exception e) {
				AppManager.showError("Producto no encontrado");
			}
		} else {
			anadirUnidad(producto, cantidad);
		}
		if(null!=producto)
		actualizarTotal(producto, cantidad);
		this.tableVentaLinea.setItems(FXCollections.observableArrayList(lineas));
		this.tableVentaLinea.refresh();
		borrar();

	}

	private String getNombreProductoFromId(Integer id) {
		int i = 0;
		while (this.productos.get(i).getId() != id)
			i++;
		return this.productos.get(i).getNombre();
	}

	private Producto comprobarProducto(Producto producto, String prodCode) {
		if (this.productos.size() == 0)
			return null;
		int i = 0;
		while (i < this.productos.size() && producto == null) {
			if (this.productos.get(i).getEan13().equals(prodCode)
					|| this.productos.get(i).getReferencia().equals(prodCode)
					|| this.productos.get(i).getNombre().equals(prodCode)) {
				producto = this.productos.get(i);
				return producto;
			}
			i++;
		}
		return null;
	}

	private void anadirUnidad(Producto producto, int cantidad) {
		int i = 0;
		while (this.lineas.get(i).getProducto_id() != producto.getId())
			i++;
		lineas.get(i).setCantidad(lineas.get(i).getCantidad() + cantidad);
		lineas.get(i).setPrecio(lineas.get(i).getPrecio() + producto.getPrecio()* cantidad);
	}

	private VentaLinea getLineaFromProduct(Producto producto, int cantidad) {
		VentaLinea linea = new VentaLinea();
		linea.setProducto_id(producto.getId());
		linea.setTasa_id(producto.getTasa_id());
		linea.setCantidad((double)cantidad);
		linea.setPrecio(producto.getPrecio()*cantidad);
		return linea;
	}
	
	private void actualizarTotal(Producto producto, int cantidad){
		this.total += producto.getPrecio()*cantidad;
		txtTotal.setText(DF.format(total) + "€");
	}
	
	@FXML
	private void insertarNumero(Event event) {
		txtLineaTeclado.setText(txtLineaTeclado.getText() + ((JFXButton) event.getSource()).getText());
	}
}
