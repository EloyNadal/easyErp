package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LoginController {

	@FXML
	private TextField txtUser;
	@FXML
	private PasswordField txtPwd;
	@FXML
	private Button btnOk;
	@FXML
	private Button btnCancel;
	@FXML
	private AnchorPane screen;
	
	@FXML
	public void login() {
		QueryManager.login(this.txtUser.getText(), this.txtPwd.getText());
		Stage stage = (Stage) this.btnOk.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void onCancelar() {
		if (AppManager.showYesNoQuestion("Cerrar", "Deseas cerrar la aplicación?"))
			System.exit(0);
	}

	@FXML
	public void initialize() {
		screen.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.ENTER))
				login();			
		});
		screen.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.ESCAPE))
				onCancelar();			
		});

	}
}
