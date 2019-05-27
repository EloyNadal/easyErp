package com.easyErp.project.log;

import java.text.SimpleDateFormat;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class LogPro extends Stage{
	
	private TextFlow textFlow = new TextFlow();
	private static LogPro logPro = new LogPro();
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	
	private LogPro() {
		ScrollPane pane = new ScrollPane(textFlow);
		pane.setPadding(new Insets(3, 10, 2, 10));
		this.setTitle("Log");
		this.setScene(new Scene(pane, 400, 500));
		this.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, event -> {
			event.consume();
			this.hide();
		});
	}
		
	public static LogPro getInstance() {
		return logPro;
	}	
	
	public void addMessage(LogMessage message) {
		Text text = new Text(SDF.format(message.getDate()) + ": " + message.getMessage() + System.lineSeparator());
		text.setFill(Color.web(message.getType().getColor()));
		
		textFlow.getChildren().add(text);
	}
}
