package com.easyErp.project.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;


public class EditarProductosController{
	
	
	@FXML private BorderPane vistaFormProductos;
    @FXML private TableView<Producto> productosTable;
	@FXML private TableColumn<Producto, Number> id;
	@FXML private TableColumn<Producto, String> nombre;
	@FXML private TableColumn<Producto, String> categoria;
	@FXML private TableColumn<Producto, String> ean13;
	@FXML private TableColumn<Producto, String> referencia;
	@FXML private TableColumn<Producto, String> atributoValor;
	@FXML private TableColumn<Producto, Double> precio;
	@FXML private TableColumn<Producto, String> iva;
	@FXML private TableColumn<Producto, Boolean> activo;
	@FXML private TableColumn<Producto, Boolean> ver;
    
	
	@FXML
    private void initialize() throws IOException{
		
		QueryManager<Producto> queryManager = Producto.getQueryManager();
		ArrayList<Producto> p = queryManager.readAll().getObjectsArray();
    
    	
    	
    	System.out.println("entra:");
    	
		this.nombre.setMinWidth(90);
		this.nombre.setMaxWidth(120);
		this.nombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombre"));
		
		this.categoria.setMinWidth(80);
		this.categoria.setMaxWidth(120);
		this.categoria.setCellValueFactory(new PropertyValueFactory<Producto,String>("CategoriaNombre"));
		
		this.referencia.setMinWidth(90);
		this.referencia.setMaxWidth(120);
		this.referencia.setCellValueFactory(new PropertyValueFactory<Producto,String>("referencia"));
		
		this.ean13.setMinWidth(90);
		this.ean13.setMaxWidth(120);
		this.ean13.setCellValueFactory(new PropertyValueFactory<Producto,String>("ean13"));
		
		this.atributoValor.setMinWidth(80);
		this.atributoValor.setMaxWidth(120);
		this.atributoValor.setCellValueFactory(new PropertyValueFactory<Producto,String>("atributo_valor"));
		

		this.iva.setMinWidth(50);
		this.iva.setMaxWidth(90);
		this.iva.setCellValueFactory(new PropertyValueFactory<Producto,String>("tasaNombre"));

		this.id.setMinWidth(40);
		this.id.setMaxWidth(50);
		this.id.setCellValueFactory(new PropertyValueFactory<Producto,Number>("id"));
		
		this.precio.setMinWidth(50);
		this.precio.setMaxWidth(90);
		this.precio.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precio"));
		
		this.activo.setMinWidth(40);
		this.activo.setMaxWidth(80);
		this.activo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> producto) {
				if (producto.getValue().isActivo()) { 
					return new SimpleBooleanProperty(true);
				}
				else {
					return null;
				}
			}
			
			
		});
		
		this.activo.setCellFactory(new ColumnButton<Producto, Boolean>( "activo", new Image(getClass().getResourceAsStream("/image/ok.png"))) {
			@Override
			public void buttonAction(Producto producto) {
				//Caixer.getInstance().getMenuController().movimentsUsuari(operacio.getUsuari());
			}
		});
		
		this.ver.setMinWidth(40);
		this.ver.setMaxWidth(80);
		this.ver.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Producto, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Producto, Boolean> button) {
				return new SimpleBooleanProperty(true);
			}
		});
		
		this.ver.setCellFactory(new ColumnButton<Producto, Boolean>( "ver", new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
			@Override
			public void buttonAction(Producto producto) {
				//Caixer.getInstance().getMenuController().movimentsUsuari(operacio.getUsuari());
			}
		});
		
		
		this.productosTable.setEditable(false);
		this.productosTable.getSelectionModel().setCellSelectionEnabled(true);
		this.productosTable.getItems().clear();
		this.productosTable.setItems( FXCollections.observableArrayList(p) );
		this.productosTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    	
	
	}
	

}
