package com.easyErp.project.controller;

import java.util.ArrayList;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.util.Callback;
import javafx.util.StringConverter;

public interface BaseController {
		
	default <T> void getTable(TableView<T> table, TableColumn<T, ?>[] columns){
		table.getColumns().addAll(columns);
	}
	default void addOkFilter(Node node) {
		node.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.ENTER))
				onOk();
		});
	}

	default void addCancelFilter(Node node) {
		node.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			if (AppMainController.isKeyPressed(event, KeyCode.ESCAPE))
				onCancelar();
		});
	}
	
	default boolean checkField(JFXPasswordField field) {
		if (field.getText().isEmpty()) {
			field.setUnFocusColor(Paint.valueOf("#ce2020"));
			field.setPromptText("Campo Obligatorio");
			return false;
		}
		field.setUnFocusColor(Paint.valueOf("#4d4d4d"));
		return true;
	}
	
	default boolean checkField(JFXTextField field) {
		if (field.getText().isEmpty()) {
			field.setUnFocusColor(Paint.valueOf("#ce2020"));
			field.setPromptText("Campo Obligatorio");
			return false;
		}
		field.setUnFocusColor(Paint.valueOf("#4d4d4d"));
		return true;
	}

	default <S> boolean checkField(JFXComboBox<S> field) {

		if (field.getValue() == null) {
			field.setUnFocusColor(Paint.valueOf("#ce2020"));
			field.setPromptText("Campo Obligatorio");
			return false;
		}
		field.setUnFocusColor(Paint.valueOf("#4d4d4d"));
		return true;
	}
	
	default <T> void initComboBox(JFXComboBox<T> combo, Class<T> clase, ArrayList<T> array) {
		array.add(0, null);
		combo.setItems(FXCollections.observableArrayList(array));
		combo.getSelectionModel().selectFirst();

		combo.setCellFactory(new Callback<ListView<T>, ListCell<T>>() {
			@Override
			public ListCell<T> call(ListView<T> l) {
				return new ListCell<T>() {
					@Override
					protected void updateItem(T item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setGraphic(null);
						} else {
							setText(item.toString());
						}
					}
				};
			}
		});

		combo.setConverter(new StringConverter<T>() {
			@Override
			public String toString(T object) {
				if (object == null) {
					return null;
				} else {
					return object.toString();
				}
			}

			@Override
			public T fromString(String string) {
				return null;
			}
		});
	}
	
	abstract void onOk();

	abstract void onCancelar();
	
	
}

final class ClearListenerJFXText implements ChangeListener<Boolean>{
	
	private TextField text;
	
	public ClearListenerJFXText(JFXTextField text) {
		this.text = text;
	}
	
	public ClearListenerJFXText(TextField text) {
		this.text = text;
	}
	
	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue)
				text.setText("");
	}
}

final class ClearListenerJFXPWD implements ChangeListener<Boolean>{
	
	private PasswordField text;
	
	public ClearListenerJFXPWD(JFXPasswordField text) {
		this.text = text;
	}
	public ClearListenerJFXPWD(PasswordField text) {
		this.text = text;
	}
	
	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
			if (newValue)
				text.setText("");
	}
}


