package com.easyErp.project.controller;

import com.easyErp.project.log.EasyErpException;
import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.QueryManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductoController {
	
	private static AppManager manager;
	@FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtEan;
    @FXML private TextField txtReferencia;
    @FXML private TextField txtAtributo;
    @FXML private TextField txtValor;
    @FXML private TextField txtNombre;
    @FXML private TextField txtUnidad;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtTasa;
    @FXML private TextField txtStock;
    @FXML private TextField txtFabricante;
    @FXML private TextField txtImagen;
    @FXML private CheckBox chbActivo;

    
   
    @FXML void buscarFilm(MouseEvent event) {
    	
//    	QueryManager<Producto> queryManager = Producto.getQueryManager();
//    	Producto producto = queryManager.getByPk(txtId.getText());
//        	
//        	txtCategoria.setText(producto.getCategoria_id().toString());
//        	txtEan.setText(producto.getEan13());
//        	txtReferencia.setText(producto.getReferencia());
//        	txtAtributo.setText(producto.getAtributo());
//        	txtValor.setText(producto.getAtributo_valor());
//        	txtNombre.setText(producto.getNombre());
//        	txtUnidad.setText(producto.getUnidad_mesura());
//        	txtPrecio.setText(producto.getPrecio().toString());
//        	txtTasa.setText(producto.getTasa_id().toString());
//        	txtStock.setText(producto.getStock_minimo().toString());
//        	txtFabricante.setText(producto.getFabricante());
//        	txtImagen.setText(producto.getImagen());
//        	chbActivo.setSelected(producto.isActivo());     	

    	
    }
    
 
}
