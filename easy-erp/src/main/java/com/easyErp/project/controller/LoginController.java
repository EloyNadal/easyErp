package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.QueryManager;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController extends Stage implements BaseController {

	@FXML
	private JFXTextField txtUser;
	@FXML
	private JFXPasswordField txtPwd;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancelar;
	@FXML
	private AnchorPane screen;
	private boolean opOk;

	@FXML
	public void login() {
		QueryManager.login(this.txtUser.getText(), this.txtPwd.getText());
		if (AppManager.isLogged())
			onCancelar();
	}

	@FXML
	public void initialize() {

		addOkFilter(screen);
		addCancelFilter(screen);

	}

	@FXML
	@Override
	public void onOk() {
		opOk = checkField(txtPwd) && checkField(txtUser);
		if (opOk)
			login();
	}

	@FXML
	@Override
	public void onCancelar() {
		Stage stage = (Stage) this.btnOk.getScene().getWindow();
		stage.close();
	}
}
