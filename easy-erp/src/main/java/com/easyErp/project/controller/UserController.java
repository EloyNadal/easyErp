package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Empleado;
import com.easyErp.project.model.GrupoUsuario;
import com.easyErp.project.model.Usuario;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class UserController extends Stage implements BaseController {

	@FXML
	GridPane parent;
	@FXML
	private AnchorPane pane;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXComboBox<Empleado> cmbEmpleado;
	@FXML
	private JFXComboBox<GrupoUsuario> cmbGrupoUsuario;
	@FXML
	private JFXPasswordField txtPwd;
	@FXML
	private JFXPasswordField txtConfirmPwd;
	@FXML
	private JFXButton btnGuardar;
	@FXML
	private JFXButton btnCancelar;
	private HiloPeticiones<Empleado> peticionEmpleado;
	private HiloPeticiones<GrupoUsuario> peticionGrupoUsuario;

	@FXML
	public void initialize() {
		this.peticionEmpleado = new HiloPeticiones<>(Empleado.getQueryManager());
		this.peticionGrupoUsuario = new HiloPeticiones<>(GrupoUsuario.getQueryManager());

		this.peticionEmpleado.run();
		this.peticionGrupoUsuario.run();

		addOkFilter(pane);
		addCancelFilter(pane);
		initComboBox();
		
		this.txtNombre.focusedProperty().addListener(new ClearListenerJFXText(this.txtNombre));
		this.txtPwd.focusedProperty().addListener(new ClearListenerJFXPWD(this.txtPwd));
		this.txtConfirmPwd.focusedProperty().addListener(new ClearListenerJFXPWD(this.txtConfirmPwd));
	}

	public void initComboBox() {
		try {
			this.peticionEmpleado.join();
			this.peticionGrupoUsuario.join();

		} catch (InterruptedException e) {
			AppManager.printError("Error al esperar respuesta de los hilos");
		}
		initComboBox(this.cmbEmpleado, Empleado.class, peticionEmpleado.getRespuesta());
		initComboBox(this.cmbGrupoUsuario, GrupoUsuario.class, peticionGrupoUsuario.getRespuesta());

	}

	public boolean validation() {
		boolean valida = checkField(this.txtNombre);
		valida = checkField(this.txtPwd) && valida;
		valida = checkField(this.txtConfirmPwd) && valida;
		valida = checkField(this.cmbEmpleado) && valida;
		valida = checkField(this.cmbGrupoUsuario) && valida;
		valida = checkPassword() && valida;
		return valida;
	}

	private boolean checkPassword() {
		if (this.txtPwd.getText().length() < 4) {
			AppManager.showError("La contraseña debe tener una longitud mínima de 4 caracteres");
			return false;
		}
		if (this.txtPwd.getText().equals(this.txtConfirmPwd.getText()))
			return true;
		txtPwd.setUnFocusColor(Paint.valueOf("#ce2020"));
		txtConfirmPwd.setUnFocusColor(Paint.valueOf("#ce2020"));
		AppManager.showError("Las contraseñas deben coincidir");
		return false;
	}

	@FXML
	@Override
	public void onOk() {
		if (!validation())
			return;
		Usuario user = new Usuario();
		user.setGrupo_usuario_id(cmbGrupoUsuario.getValue().getId());
		user.setEmpleado_id(4);
		user.setPassword(txtPwd.getText());
		user.setUser_name(txtNombre.getText());
		Usuario.getQueryManager().insertOne(user);
		AppManager.showInfo("Usuario agregado correctamente");
		clear();
	}

	@FXML
	@Override
	public void onCancelar() {
		this.close();
	}
	
	public void clear() {
		txtNombre.setText("");
		txtPwd.setText("");
		txtConfirmPwd.setText("");
		cmbEmpleado.setValue(null);
		cmbGrupoUsuario.setValue(null);
	}
}
