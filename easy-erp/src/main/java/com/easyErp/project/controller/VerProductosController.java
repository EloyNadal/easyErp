package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.Categoria;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Tasa;
import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.ColumnButton;
import com.easyErp.project.utils.ExportData;
import com.easyErp.project.utils.TablaFormaters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class VerProductosController implements BaseController {

	@FXML
	private BorderPane vistaFormProductos;
	private TableView<Producto> productosTable = crearTabla();
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
	@FXML
	private Button btnExc;
	@FXML
	private Pagination paginacion;
	
	private ArrayList<Producto> productos;
	private ArrayList<Producto> productosFiltrados;
	
	private final static int FILASPORPAGINA = 12;

	@FXML
	private void initialize() {
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
		QueryManager<Producto> queryManager = Producto.getQueryManager();
		this.productos = queryManager.readAll().getObjectsArray();
		QueryManager<Categoria> queryManagerCategoria = Categoria.getQueryManager();
		ArrayList<Categoria> categorias = queryManagerCategoria.readAll().getObjectsArray();
		QueryManager<Tasa> queryManagerTasa = Tasa.getQueryManager();
		ArrayList<Tasa> tasas = queryManagerTasa.readAll().getObjectsArray();

		initComboBox(cmbCategoria, Categoria.class, categorias);
		initComboBox(cmbIva, Tasa.class, tasas);

		this.paginacion.setPageCount(this.productos.size() != 0?
				(int) Math.ceil((double) this.productos.size() / (double) FILASPORPAGINA):1);
		this.paginacion.setPageFactory(this::crearPagina);
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("/image/excelIcon.png")));
		i.setFitHeight(25);
		i.setFitWidth(25);
		btnExc.setGraphic(i);
		btnExc.setText(null);
	}

	private Node crearPagina(int paginaIndice) {

		int desdeIndice = paginaIndice * FILASPORPAGINA;
		int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, this.productos.size());
		productosTable.setItems(FXCollections.observableArrayList(this.productos.subList(desdeIndice, hastaIndice)));
		productosTable.setEditable(true);
		return productosTable;
	}

	private Node recargarPagina(int paginaIndice) {

		int desdeIndice = paginaIndice * FILASPORPAGINA;
		int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, productosFiltrados.size());
		productosTable.setItems(FXCollections.observableArrayList(productosFiltrados.subList(desdeIndice, hastaIndice)));
		return productosTable;
	}

	private TableView<Producto> crearTabla() {
		
		TableView<Producto> tabla = new TableView<Producto>();
		TableColumn<Producto, Number> id = new TableColumn<Producto, Number>("ID");
		TableColumn<Producto, String> nombre = new TableColumn<Producto, String>("Nombre");
		TableColumn<Producto, String> categoria = new TableColumn<Producto, String>("Categoria");
		TableColumn<Producto, String> ean13 = new TableColumn<Producto, String>("Ean13");
		TableColumn<Producto, String> referencia = new TableColumn<Producto, String>("Referencia");
		TableColumn<Producto, String> atributoValor = new TableColumn<Producto, String>("Atributo");
		TableColumn<Producto, Double> precio = new TableColumn<Producto, Double>("Precio");
		TableColumn<Producto, String> iva = new TableColumn<Producto, String>("IVA");
		TableColumn<Producto, Boolean> activo = new TableColumn<Producto, Boolean>("Activo");
		TableColumn<Producto, Boolean> ver = new TableColumn<Producto, Boolean>("Ver");
		
		nombre.setMinWidth(90);
		nombre.setMaxWidth(120);
		nombre.setCellValueFactory(new PropertyValueFactory<Producto, String>("nombre"));

		categoria.setMinWidth(80);
		categoria.setMaxWidth(140);
		categoria.setCellValueFactory(new PropertyValueFactory<Producto, String>("CategoriaNombre"));

		referencia.setMinWidth(90);
		referencia.setMaxWidth(120);
		referencia.setCellValueFactory(new PropertyValueFactory<Producto, String>("referencia"));

		ean13.setMinWidth(90);
		ean13.setMaxWidth(130);
		ean13.setCellValueFactory(new PropertyValueFactory<Producto, String>("ean13"));

		atributoValor.setMinWidth(80);
		atributoValor.setMaxWidth(140);
		atributoValor.setCellValueFactory(new PropertyValueFactory<Producto, String>("atributo_valor"));

		iva.setMinWidth(50);
		iva.setMaxWidth(90);
		iva.setCellValueFactory(new PropertyValueFactory<Producto, String>("tasaNombre"));

		id.setMinWidth(40);
		id.setMaxWidth(50);
		id.setCellValueFactory(new PropertyValueFactory<Producto, Number>("id"));

		precio.setMinWidth(50);
		precio.setMaxWidth(90);
		precio.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
		precio.setCellValueFactory(new PropertyValueFactory<Producto, Double>("precio"));

		activo.setMinWidth(40);
		activo.setMaxWidth(80);

		// muestra un valor segun condicion
		activo.setCellValueFactory(
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
		activo.setCellFactory(new ColumnButton<Producto, Boolean>("activo",
				new Image(getClass().getResourceAsStream("/image/ok.png"))) {
			@Override
			public void buttonAction(Producto producto) {
			}
		});

		ver.setMinWidth(40);
		ver.setMaxWidth(80);
		ver.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> button) {
						return new SimpleBooleanProperty(true);
					}
				});

		ver.setCellFactory(new ColumnButton<Producto, Boolean>("ver",
				new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
			@Override
			public void buttonAction(Producto producto) {
				AppManager.getInstance().getAppMain().editarProducto(producto);
			}
		});
		tabla.getColumns().addAll(id, nombre, categoria, ean13, referencia, atributoValor, precio, iva, activo, ver);
		tabla.setEditable(false);
		tabla.getSelectionModel().getSelectedItem();
		tabla.getSelectionModel().setCellSelectionEnabled(true);
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tabla;
	}

	

	@FXML
	private void filtrar() {
		this.productosFiltrados = filtrarProductos();
		this.paginacion.setPageCount(this.productosFiltrados.size() != 0?
				(int) Math.ceil(((double)this.productosFiltrados.size()/(double)FILASPORPAGINA)):1);
    	this.paginacion.setPageFactory(this::recargarPagina);
	}
	
	private ArrayList<Producto> filtrarProductos(){
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
		return copiaProductos;
	}
	private boolean checkProductos(Producto producto, Producto p, double min, double max) {
		if (producto.getCategoria_id() != null && producto.getCategoria_id() != p.getCategoria_id())
			return false;
		if (!p.getEan13().toLowerCase().contains(producto.getEan13().toLowerCase()))
			return false;
		if (!p.getNombre().toLowerCase().contains(producto.getNombre().toLowerCase()))
			return false;
		if (!p.getReferencia().toLowerCase().contains(producto.getReferencia().toLowerCase()))
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
		AppManager.getInstance().getAppMain().editarProducto(null);
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
		this.paginacion.setPageCount(this.productos.size() != 0?
				(int) Math.ceil((double) this.productos.size() / (double) FILASPORPAGINA):1);
		this.paginacion.setPageFactory(this::crearPagina);
	}
	
	@FXML
	private void imprimirResultados() {
		ExportData.crearArchivoExcel(this.productosTable);
	}
	
}
