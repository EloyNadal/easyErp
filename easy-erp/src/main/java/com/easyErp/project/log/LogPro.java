package com.easyErp.project.log;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.easyErp.project.log.LogPro.LogProType;
import com.easyErp.project.utils.AppManager;
import com.jfoenix.controls.JFXToggleButton;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
/**
 * Clase singletone que controla el log de errores de la aplicación durante la ejecución
 * 
 *
 */
public class LogPro extends AnchorPane{

	@FXML
	private JFXToggleButton btnErrores;
	@FXML
	private JFXToggleButton btnDebug;
	@FXML
	private JFXToggleButton btnConexiones;
	@FXML
	private TextFlow textFlow;
	private static Stage stage;
	private static LogPro controller = new LogPro();
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**
	 * Enumeración para controlar el color de los mensajes mostrados en el log
	 * Error rojo
	 * Info verde
	 * Connection azul
	 *
	 */
	enum LogProType {
		ERROR("#FF0000"), INFO("#006400"), CONNECTION("#00008B");
		private String color;

		LogProType(String c) {
			color = c;
		}

		String getColor() {
			return color;
		}
	}
	private LogPro() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/logView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        AnchorPane pane = null;
        try {
            pane = fxmlLoader.load();
        } catch (IOException exception) {
        	AppManager.printError("Error al cargar el log");
        }
        AnchorPane.setTopAnchor(pane,0.0);
		AnchorPane.setBottomAnchor(pane,0.0);
		AnchorPane.setLeftAnchor(pane, 0.0);
		AnchorPane.setRightAnchor(pane, 0.0);
        stage = new Stage();
        stage.setTitle("Log");
		stage.setScene(new Scene(pane));
		stage.getIcons().add(new Image("/image/icon.png"));
	}
	public Stage getStage() {
		return stage;
	}
	public static LogPro getInstance() {
		return controller;
	}
	@FXML
	public void initialize() {		
		
	}

	/**
	 * Mostrará un mensaje informativo, usado para debugar
	 * @param message String del mensaje a mostrar
	 */
	public void print(String message) {
		if (this.btnDebug.isSelected())
			addMessage(new LogProMessage(LogProType.INFO, new Date(), message));
	}
	/**
	 * Mostrará las conexiones con el WebService
	 * @param message String del contenido de la petición/respuesta al WebService
	 */
	public void printConnection(String message) {
		if (this.btnConexiones.isSelected())
			addMessage(new LogProMessage(LogProType.CONNECTION, new Date(), message));
	}
	/**
	 * Mostrará mensajes de error
	 * @param message String del error capturado
	 * @param date momento en que se ha capturado el error
	 */
	public void printError(Date date, String message) {
		if (this.btnErrores.isSelected())
				addMessage(new LogProMessage(LogProType.ERROR, date, message));
	}
	private void addMessage(LogProMessage message) {
		Text text = new Text(SDF.format(message.getDate()) + ": " + message.getMessage() + System.lineSeparator());
		text.setFill(Color.web(message.getType().getColor()));
		textFlow.getChildren().add(text);
	}	
}

class LogProMessage {
	private LogProType type;
	private Date date;
	private String message;

	public LogProMessage(LogProType type, Date date, String message) {
		this.type = type;
		this.date = date;
		this.message = message;
	}

	public LogProType getType() {
		return type;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}

