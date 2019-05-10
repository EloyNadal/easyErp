package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	TextField txtUser;
	@FXML
	PasswordField txtPwd;
	@FXML
	Button btnOk;
	@FXML
	Button btnCancel;

	@FXML
	public void login() {
		if (AppManager.login(txtUser.getText(), txtPwd.getText())) {

			Stage stage = (Stage) btnOk.getScene().getWindow();
			stage.close();

		}
	}
}
