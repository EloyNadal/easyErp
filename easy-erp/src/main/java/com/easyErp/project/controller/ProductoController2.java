package com.easyErp.project.controller;

import com.easyErp.project.model.Producto;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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
	
	@FXML private JFXCheckBox chkActivo;

	@FXML private ImageView imagen;
	
	@FXML private TableView<Producto> table;
	
	@FXML private JFXButton btnAlmacen;
	@FXML private JFXButton btnTiendas;
	

    public void crearProducto() {
    	
    	changeEditableFields(true);
    	txtNombre.setPromptText("Introduce un nombre");
    	
    	
    }
    
    public void cargarProducto(Producto producto) {
    	
    	if (producto == null) crearProducto();
    	
    	this.producto = producto; 
    	changeEditableFields(false);
    	
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
    
    
    public void clearTable() {
    	this.table.getColumns().clear();
    }
    
    public void crearTablaProducto() {
    	
    	if (this.producto == null) return;
    	
    	TableColumn<Producto, String> colReferencia = new TableColumn<Producto, String>("Referencia");
    	TableColumn<Producto, String> colNombre = new TableColumn<Producto, String>("Nombre");
    	TableColumn<Producto, Number> colStock = new TableColumn<Producto, Number>("Stock");
    	TableColumn<Producto, Number> colPendiente = new TableColumn<Producto, Number>("Stock Pendiente");
    	
    	table.getColumns().addAll(colReferencia, colNombre, colStock, colPendiente);
    	
    	colReferencia.setMinWidth(90);
    	colReferencia.setMaxWidth(300);
    	colReferencia.setCellValueFactory(new PropertyValueFactory<Producto,String>("referencia"));
    	
    	colNombre.setMinWidth(90);
    	colNombre.setMaxWidth(300);
    	colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
    	
    	colStock.setMinWidth(90);
    	colStock.setMaxWidth(300);
    	colStock.setCellValueFactory(new PropertyValueFactory<Producto,Number>("stock_minimo"));
    	
    	colPendiente.setMinWidth(90);
    	colPendiente.setMaxWidth(300);
    	colPendiente.setCellValueFactory(new PropertyValueFactory<Producto,Number>("stock_minimo"));
    	
    	this.table.setEditable(false);
		this.table.getSelectionModel().setCellSelectionEnabled(true);
		this.table.getItems().clear();
		this.table.setItems( FXCollections.observableArrayList(this.producto) );
		this.table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	
    }
    
    public void changeEditableFields(Boolean edit) {
    	txtValorAtributo.setEditable(edit);
    	txtFabricante.setEditable(edit);
    	txtUnidadMesura.setEditable(edit);
    	txtStockMin.setEditable(edit);    	
    	chkActivo.setSelected(edit);
    	txtCategoria.setEditable(edit);
    	txtEan.setEditable(edit);
    	txtReferencia.setEditable(edit);
    	txtAtributo.setEditable(edit);
    	txtNombre.setEditable(edit);
    	txtPrecio.setEditable(edit);
    	txtTasa.setEditable(edit);
    }
    
    
    
    
 
}
