package com.easyErp.project.controller;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Stock;
import com.easyErp.project.model.Tienda;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class ProductoController2 {
	
	Producto producto;

	@FXML private JFXTextField txtNombre;
	@FXML private JFXTextField txtReferencia;
	@FXML private JFXTextField txtCategoria;
	@FXML private JFXTextField txtAtributo;
	@FXML private JFXTextField txtValorAtributo;
	@FXML private JFXTextField txtEan;
	@FXML private JFXTextField txtFabricante;
	@FXML private JFXTextField txtPrecio;
	@FXML private JFXTextField txtUnidadMesura;
	@FXML private JFXTextField txtTasa;
	@FXML private JFXTextField txtStockMin;
	@FXML private JFXTextField txtProveedor;
	@FXML private JFXTextField txtStockInicial;
	
	
	@FXML private JFXCheckBox chkActivo;

	@FXML private ImageView imagen;
	
	@FXML private TableView<Stock> table;
	
	@FXML private JFXButton btnAlmacen;
	@FXML private JFXButton btnTiendas;
	@FXML private JFXButton btnEditar;
	@FXML private JFXButton btnGuardar;
	@FXML private JFXButton btnCompras;
	@FXML private JFXButton btnVentas;
	
	@FXML private Line lineAlmacen;
	@FXML private Line lineTiendas;
	@FXML private Line lineCompras;
	@FXML private Line lineVentas;
	
	private QueryManager<Stock> queryManagerStocks = Stock.getQueryManager();

    public void crearProducto() {
    	
    	changeEditableFields(true);
    	this.btnGuardar.setVisible(true);
    	this.btnEditar.setText("CANCELAR");
    	
    	txtNombre.setPromptText("Introduce un nombre");
    	
    }
    
    public void cargarProducto(Producto producto) {
    	
    	if (producto == null) crearProducto();
    	
    	this.btnGuardar.setVisible(false);
    	this.producto = producto; 
    	changeEditableFields(false);
    	this.btnEditar.setText("EDITAR");
    	
    	txtValorAtributo.setText(producto.getAtributo_valor());
    	txtFabricante.setText(producto.getFabricante());
    	txtUnidadMesura.setText(producto.getUnidad_mesura());
    	txtStockMin.setText(producto.getStock_minimo().toString());    	
    	chkActivo.setSelected(producto.isActivo());
    	imagen.setImage(new Image(producto.getImagen()));
    	txtCategoria.setText(producto.getCategoriaNombre());
    	txtEan.setText(producto.getEan13());
    	txtReferencia.setText(producto.getReferencia());
    	txtAtributo.setText(producto.getAtributo());
    	txtNombre.setText(producto.getNombre());
    	txtPrecio.setText(producto.getPrecio().toString());
    	txtTasa.setText(producto.getTasaNombre());
    	
    	crearTablaProducto();
    	
    }
    
    public void editarProducto() {
    	
    	if (btnEditar.getText().equals("EDITAR")) {
    		
    		if (this.producto == null) return;
    		
    		this.btnGuardar.setVisible(true);
        	this.btnEditar.setText("CANCELAR");
        	changeEditableFields(true);
        	
    	}
    	else {
    		
    		this.btnGuardar.setVisible(false);
        	this.btnEditar.setText("EDITAR");
        	changeEditableFields(false);
    	}
    	
    }
    
    
    public void clearTable() {
    	this.table.getColumns().clear();
    }
    
    public void crearTablaProducto() {
    	
    	clearTable();
    	lineAlmacen.setVisible(true);
    	lineTiendas.setVisible(false);
    	lineCompras.setVisible(false);
    	lineVentas.setVisible(false);
    	
    	if (this.producto == null) return;
    	
    	RequestBody formBody = new FormBody.Builder()
    			.add("producto_id", this.producto.getId().toString())
    			.add("tienda_id", String.valueOf(AppManager.getIdTienda()))
    			.build();
    	
    	ArrayList<Stock> stocks = queryManagerStocks.readQuery(formBody, 0).getObjectsArray();
    	
    	
    	
    	TableColumn<Stock, String> colReferencia = new TableColumn<Stock, String>("Referencia");
    	TableColumn<Stock, String> colNombre = new TableColumn<Stock, String>("Nombre");
    	TableColumn<Stock, Double> colStock = new TableColumn<Stock, Double>("Stock");
    	
    	table.getColumns().addAll(colReferencia, colNombre, colStock);
    	
    	colReferencia.setMinWidth(90);
    	colReferencia.setMaxWidth(400);
    	colReferencia.setCellValueFactory(new PropertyValueFactory<Stock,String>("referenciaProducto"));
    	
    	colNombre.setMinWidth(90);
    	colNombre.setMaxWidth(400);
    	colNombre.setCellValueFactory(new PropertyValueFactory<Stock,String>("nombreProducto"));
    	
    	colStock.setMinWidth(90);
    	colStock.setMaxWidth(400);
    	colStock.setCellValueFactory(new PropertyValueFactory<Stock,Double>("cantidad"));
    	
    	this.table.setEditable(false);
		this.table.getSelectionModel().setCellSelectionEnabled(true);
		this.table.getItems().clear();
		this.table.setItems( FXCollections.observableArrayList(stocks) );
		this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }
    
    public void changeEditableFields(Boolean edit) {
    	txtValorAtributo.setEditable(edit);
    	txtFabricante.setEditable(edit);
    	txtUnidadMesura.setEditable(edit);
    	txtStockMin.setEditable(edit);    	
    	chkActivo.setDisable(!edit);
    	txtCategoria.setEditable(edit);
    	txtEan.setEditable(edit);
    	txtReferencia.setEditable(edit);
    	txtAtributo.setEditable(edit);
    	txtNombre.setEditable(edit);
    	txtPrecio.setEditable(edit);
    	txtTasa.setEditable(edit);
    	txtProveedor.setEditable(edit);
    	txtStockInicial.setEditable(edit);
    }
    
    public void otrasTiendas() {
    	clearTable();
    	lineAlmacen.setVisible(false);
    	lineTiendas.setVisible(true);
    	lineCompras.setVisible(false);
    	lineVentas.setVisible(false);
    	
    	if (this.producto == null) return;
    	
    	RequestBody formBody = new FormBody.Builder()
    			.add("producto_id", this.producto.getId().toString())
    			.build();
    	
    	ArrayList<Stock> stocks = queryManagerStocks.readQuery(formBody, 0).getObjectsArray();
    	
    	TableColumn<Stock, String> colTienda = new TableColumn<Stock, String>("Tienda");
    	TableColumn<Stock, String> colCiudad = new TableColumn<Stock, String>("Ciudad");
    	TableColumn<Stock, Double> colStock = new TableColumn<Stock, Double>("Stock");
    	TableColumn<Stock, Boolean> verTienda = new TableColumn<Stock, Boolean>("Info");
    	
    	table.getColumns().addAll(colTienda, colCiudad, colStock, verTienda);
    	
    	colTienda.setMinWidth(90);
    	colTienda.setMaxWidth(400);
    	colTienda.setCellValueFactory(new PropertyValueFactory<Stock,String>("nombreTienda"));
    	
    	colCiudad.setMinWidth(90);
    	colCiudad.setMaxWidth(400);
    	colCiudad.setCellValueFactory(new PropertyValueFactory<Stock,String>("ciudadTienda"));
    	
    	colStock.setMinWidth(90);
    	colStock.setMaxWidth(400);
    	colStock.setCellValueFactory(new PropertyValueFactory<Stock,Double>("cantidad"));
    	
    	verTienda.setMinWidth(40);
    	verTienda.setMaxWidth(100);
    	verTienda.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Stock, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Stock, Boolean> button) {
				return new SimpleBooleanProperty(true);
			}
		});
		
    	verTienda.setCellFactory(new ColumnButton<Stock, Boolean>( "ver", new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
			@Override
			public void buttonAction(Stock tienda) {
				
				//AppManager.getInstance().getAppMain().verProductos(producto);
			}
		});
    	
    	
    	this.table.setEditable(false);
		this.table.getSelectionModel().setCellSelectionEnabled(true);
		this.table.getItems().clear();
		this.table.setItems( FXCollections.observableArrayList(stocks) );
		this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	
    }
    
    public void historicoCompras() {
    	clearTable();
    	lineAlmacen.setVisible(false);
    	lineTiendas.setVisible(false);
    	lineCompras.setVisible(true);
    	lineVentas.setVisible(false);
    }
    
    public void historicoVentas() {
    	clearTable();
    	lineAlmacen.setVisible(false);
    	lineTiendas.setVisible(false);
    	lineCompras.setVisible(false);
    	lineVentas.setVisible(true);
    }

}
