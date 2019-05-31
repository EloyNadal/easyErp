package com.easyErp.project.controller;

import com.easyErp.project.model.Proveedor;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Respuesta;
import com.easyErp.project.utils.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProveedorController {
	
	@FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCP;
    @FXML private TextField txtPais;
   

    @FXML void buscarFilm(MouseEvent event) {
    	
    	QueryManager<Proveedor> queryManager = Proveedor.getQueryManager();
    	Respuesta<Proveedor> response = queryManager.getByPk(txtId.getText());
    	if(!response.isSuccessful()) {
    		AppManager.showError("No se ha encontrado el proveedor");
    		
    	}else {
    		Proveedor proveedor = response.getObject();
    		txtNombre.setText(proveedor.getNombre());
        	txtDireccion.setText(proveedor.getDireccion());
        	txtCiudad.setText(proveedor.getCiudad());
        	txtTelefono.setText(proveedor.getTelefono());
        	txtEmail.setText(proveedor.getEmail());
        	txtCP.setText(proveedor.getCodigo_postal());
        	txtPais.setText(proveedor.getPais());
    	}
        	

    }
    
}
