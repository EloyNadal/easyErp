package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController{

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
		QueryManager.login(txtUser.getText(), txtPwd.getText());
		Stage stage = (Stage) btnOk.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void onCancel() {
		if (AppManager.showYesNoQuestion("Cerrar", "Deseas cerrar la aplicación?"))
			System.exit(0);
	}


}
