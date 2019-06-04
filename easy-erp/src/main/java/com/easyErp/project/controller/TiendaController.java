package com.easyErp.project.controller;

import com.easyErp.project.model.Tienda;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;

public class TiendaController {

	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextField txtDireccion;
	@FXML
	private JFXTextField txtCiudad;
	@FXML
	private JFXTextField txtTelefono;

	@FXML
	public void initialize() {

	}

	public void cargarTienda(Tienda tienda) {
		this.txtNombre.setText(tienda.getNombre());
		this.txtDireccion.setText(tienda.getDireccion());
		this.txtCiudad.setText(tienda.getCiudad());
		this.txtTelefono.setText(tienda.getTelefono());
	}
}
