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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class UserController extends Stage implements BaseController {

	public static final int MIN_PWD_SIZE = 4;
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
	@FXML
	private Label lblError;

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
		if (this.txtPwd.getText().length() < MIN_PWD_SIZE) {
			lblError.setText("La contraseña debe tener una longitud mínima de 4 caracteres");
			lblError.setVisible(true);
			return false;
		}
		if (this.txtPwd.getText().equals(this.txtConfirmPwd.getText()))
			return true;
		txtPwd.setUnFocusColor(Paint.valueOf("#ce2020"));
		txtConfirmPwd.setUnFocusColor(Paint.valueOf("#ce2020"));
		lblError.setText("Las contraseñas deben coincidir");
		lblError.setVisible(true);
		return false;
	}

	@FXML
	@Override
	public void onOk() {
		if (!validation()) {
			lblError.setText("Campos necesarios por rellenar");
			lblError.setVisible(true);
			return;
		}

		Usuario user = new Usuario();
		user.setGrupo_usuario_id(cmbGrupoUsuario.getValue().getId());
		user.setEmpleado_id(cmbEmpleado.getValue().getId());
		user.setPassword(txtPwd.getText());
		user.setUser_name(txtNombre.getText());
		Usuario.getQueryManager().insertOne(user);
		if (Usuario.getQueryManager().insertOne(user).isSuccessful()) {
			AppManager.showInfo("Usuario agregado correctamente");
			onCancelar();
		} else {
			lblError.setText("Error al insertar");
			lblError.setVisible(true);
		}
	}

	@FXML
	@Override
	public void onCancelar() {
		((Stage) txtNombre.getScene().getWindow()).close();
	}

	public void clear() {
		txtNombre.setText("");
		txtPwd.setText("");
		txtConfirmPwd.setText("");
		cmbEmpleado.setValue(null);
		cmbGrupoUsuario.setValue(null);
	}
}
