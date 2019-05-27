//package com.easyErp.project.log;
//
//import java.text.SimpleDateFormat;
//
//import javafx.fxml.FXML;
//import javafx.scene.control.CheckBox;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextArea;
//import javafx.scene.layout.Pane;
//import javafx.scene.paint.Color;
//import javafx.scene.text.Text;
//
//public class LogController extends Thread{
//
//	@FXML
//	CheckBox chbErrores;
//	@FXML
//	CheckBox chbDebug;
//	@FXML
//	CheckBox chbConexiones;
//	@FXML
//	TextArea txtLog;
//	@FXML
//	ScrollPane scroll;
//	@FXML
//	Pane pane;
//	
//	private SystemLog sysLog;
//	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//	
//	@FXML
//	public void initialize() {
//		sysLog = SystemLog.getInstance();
//		chbErrores.setOnMouseClicked(event -> {
//			sysLog.setErrores(chbErrores.isSelected());
//
//		});
//		chbDebug.setOnMouseClicked(event -> {
//			sysLog.setDebug(chbDebug.isSelected());
//
//		});
//		chbConexiones.setOnMouseClicked(event -> {
//			sysLog.setConexiones(chbConexiones.isSelected());
//
//		});
//		
//	}
//
//	public void addMessage(LogMessage message) {
//		
//	}
//	@Override
//	public void run() {
//		
//		while (true) {
//			if (!sysLog.isEmpty()) {
//				LogMessage message = sysLog.getMessage();
//				Text text = new Text(SDF.format(message.getDate()) + ": " + message.getMessage() + System.lineSeparator());
//				text.setFill(Color.web(message.getType().getColor()));
//				pane.getChildren().add(text);
//			}
//		}
//	}
//
//	@Override
//	protected void finalize() throws Throwable {
//		pane.getChildren().clear();
//		super.finalize();
//	}
//	
//	
//}
