package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Categoria;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Tasa;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class EditarProductosController implements BaseController {

	@FXML
	private BorderPane vistaFormProductos;
	@FXML
	private TableView<Producto> productosTable;
	@FXML
	private TableColumn<Producto, Number> id;
	@FXML
	private TableColumn<Producto, String> nombre;
	@FXML
	private TableColumn<Producto, String> categoria;
	@FXML
	private TableColumn<Producto, String> ean13;
	@FXML
	private TableColumn<Producto, String> referencia;
	@FXML
	private TableColumn<Producto, String> atributoValor;
	@FXML
	private TableColumn<Producto, Double> precio;
	@FXML
	private TableColumn<Producto, String> iva;
	@FXML
	private TableColumn<Producto, Boolean> activo;
	@FXML
	private TableColumn<Producto, Boolean> ver;
	@FXML
	private JFXComboBox<Categoria> cmbCategoria;
	@FXML
	private JFXComboBox<Tasa> cmbIva;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextField txtReferencia;
	@FXML
	private JFXTextField txtEan;
	@FXML
	private JFXTextField txtPrecioMin;
	@FXML
	private JFXTextField txtPrecioMax;
	@FXML
	private JFXCheckBox chkActivo;
	@FXML
	private JFXButton btnFiltrar;
	@FXML
	private JFXButton btnQuitarFiltro;

	private ArrayList<Producto> productos;

	@FXML
	private void initialize() {

		QueryManager<Producto> queryManager = Producto.getQueryManager();
		this.productos = queryManager.readAll().getObjectsArray();
		this.txtPrecioMax.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					try {
						Double.parseDouble(txtPrecioMax.getText());
					} catch (Exception ex) {
						txtPrecioMax.setText(txtPrecioMin.getText());
					}
					if (txtPrecioMax.getText().compareTo(txtPrecioMin.getText()) < 0) {
						AppManager.showError("El valor \"A\" debe ser superior o igual al valor \"de\"");
						txtPrecioMax.setText(txtPrecioMin.getText());
					}
				} else
					txtPrecioMax.setText("");
			}
		});
		this.txtPrecioMin.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					try {
						Double.parseDouble(txtPrecioMin.getText());
					} catch (Exception ex) {
						txtPrecioMin.setText("0.00");
					}
				} else
					txtPrecioMin.setText("");
			}
		});
		QueryManager<Categoria> queryManagerCategoria = Categoria.getQueryManager();
		ArrayList<Categoria> categorias = queryManagerCategoria.readAll().getObjectsArray();
		QueryManager<Tasa> queryManagerTasa = Tasa.getQueryManager();
		ArrayList<Tasa> tasas = queryManagerTasa.readAll().getObjectsArray();

		this.nombre.setMinWidth(90);
		this.nombre.setMaxWidth(120);
		this.nombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));

		this.categoria.setMinWidth(80);
		this.categoria.setMaxWidth(120);
		this.categoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("CategoriaNombre"));

		this.referencia.setMinWidth(90);
		this.referencia.setMaxWidth(120);
		this.referencia.setCellValueFactory(new PropertyValueFactory<Producto, String>("referencia"));

		this.ean13.setMinWidth(90);
		this.ean13.setMaxWidth(120);
		this.ean13.setCellValueFactory(new PropertyValueFactory<Producto, String>("ean13"));

		this.atributoValor.setMinWidth(80);
		this.atributoValor.setMaxWidth(120);
		this.atributoValor.setCellValueFactory(new PropertyValueFactory<Producto, String>("atributo_valor"));

		this.iva.setMinWidth(50);
		this.iva.setMaxWidth(90);
		this.iva.setCellValueFactory(new PropertyValueFactory<Producto, String>("tasaNombre"));

		this.id.setMinWidth(40);
		this.id.setMaxWidth(50);
		this.id.setCellValueFactory(new PropertyValueFactory<Producto, Number>("id"));

		this.precio.setMinWidth(50);
		this.precio.setMaxWidth(90);
		this.precio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));

		this.activo.setMinWidth(40);
		this.activo.setMaxWidth(80);

		// muestra un valor segun condicion
		this.activo.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> producto) {
						if (producto.getValue().isActivo()) {
							return new SimpleBooleanProperty(true);
						} else {
							return null;
						}
					}

				});

		// En este caso, como el valor que muestra es true o null, en caso de ser true
		// muestra una imagen
		this.activo.setCellFactory(new ColumnButton<Producto, Boolean>("activo",
				new Image(getClass().getResourceAsStream("/image/check.png"))) {
			@Override
			public void buttonAction(Producto producto) {
			}
		});

		this.ver.setMinWidth(40);
		this.ver.setMaxWidth(80);
		this.ver.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> button) {
						return new SimpleBooleanProperty(true);
					}
				});

		this.ver.setCellFactory(new ColumnButton<Producto, Boolean>("ver",
				new Image(getClass().getResourceAsStream("/image/edit.png"))) {
			@Override
			public void buttonAction(Producto producto) {
				AppManager.getInstance().getAppMain().verProductos(producto);
			}
		});

		this.productosTable.setEditable(false);
		this.productosTable.getSelectionModel().setCellSelectionEnabled(true);
		this.productosTable.getItems().clear();
		actualizarTabla(productos);

		initComboBox(cmbCategoria, Categoria.class, categorias);
		initComboBox(cmbIva, Tasa.class, tasas);
	}

	private void actualizarTabla(ArrayList<Producto> tabla) {
		this.productosTable.getItems().clear();
		this.productosTable.setItems(FXCollections.observableArrayList(tabla));
		this.productosTable.refresh();
	}

	@FXML
	private void filtrar() {
		ArrayList<Producto> copiaProductos = new ArrayList<Producto>();
		Producto producto = new Producto();
		producto.setActivo(this.chkActivo.isSelected() ? 1 : 0);
		producto.setCategoria_id(this.cmbCategoria.getValue() != null ? this.cmbCategoria.getValue().getId() : null);
		producto.setReferencia(checkNotEmpty(this.txtReferencia.getText()) ? this.txtReferencia.getText() : "");
		producto.setEan13(checkNotEmpty(this.txtEan.getText()) ? this.txtEan.getText() : "");
		producto.setNombre(checkNotEmpty(this.txtNombre.getText()) ? this.txtNombre.getText() : "");
		producto.setTasa_id(this.cmbIva.getValue() != null ? this.cmbIva.getValue().getId() : null);
		double precio_min = Double.parseDouble(this.txtPrecioMin.getText());
		double precio_max = Double.parseDouble(this.txtPrecioMax.getText());
		for (Producto p : this.productos) {
			if (checkProductos(producto, p, precio_min, precio_max))
				copiaProductos.add(p);
		}
		actualizarTabla(copiaProductos);
	}

	private boolean checkProductos(Producto producto, Producto p, double min, double max) {
		if (producto.getCategoria_id() != null && producto.getCategoria_id() != p.getCategoria_id())
			return false;
		if (p.getEan13() != "" && !p.getEan13().contains(producto.getEan13()))
			return false;
		if (p.getNombre() != "" && !p.getNombre().contains(producto.getNombre()))
			return false;
		if (p.getReferencia() != "" && !p.getReferencia().contains(producto.getReferencia()))
			return false;
		if (producto.getTasa_id() != null && producto.getTasa_id() != p.getTasa_id())
			return false;
		if (producto.isActivo() != p.isActivo())
			return false;
		if (p.getPrecio() < min && max != 0 && p.getPrecio() > max)
			return false;
		return true;
	}

	private boolean checkNotEmpty(String string) {
		if (string.equals("")) {
			return false;
		}
		return true;
	}

	public void newProducto() {
		AppManager.getInstance().getAppMain().verProductos(null);
	}

	@Override
	public void onOk() {
		filtrar();
	}

	@FXML
	@Override
	public void onCancelar() {
		this.chkActivo.setSelected(false);
		this.cmbCategoria.getSelectionModel().selectFirst();
		this.txtReferencia.setText("");
		this.txtEan.setText("");
		this.txtNombre.setText("");
		this.cmbIva.getSelectionModel().selectFirst();
		this.txtPrecioMin.setText("0.00");
		this.txtPrecioMax.setText("0.00");
		actualizarTabla(this.productos);
	}
}
