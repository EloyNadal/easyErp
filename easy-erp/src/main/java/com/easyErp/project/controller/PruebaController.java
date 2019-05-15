package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PruebaController {
	private static AppManager manager;
    @FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPais;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDni;

    
    @FXML void buscarFilm(MouseEvent event) {
//    	QueryManager<Cliente> queryManager = Cliente.getQueryManager();
//    	Cliente cliente = queryManager.getByPk(txtId.getText());
//   
//        	txtNombre.setText(cliente.getNombre());
//        	txtApellido.setText(cliente.getApellidos());
//        	txtDireccion.setText(cliente.getDireccion());
//        	txtCiudad.setText(cliente.getCiudad());
//        	txtTelefono.setText(cliente.getTelefono());
//        	txtCodigo.setText(cliente.getCodigo_postal());
//        	txtPais.setText(cliente.getPais());
//        	txtEmail.setText(cliente.getEmail());
//        	txtDni.setText(cliente.getDni());
			
			
	
    }
    


}
