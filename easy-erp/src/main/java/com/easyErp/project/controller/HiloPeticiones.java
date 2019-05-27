package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.QueryManager;

import javafx.scene.Scene;

public class HiloPeticiones<S> extends Thread {

	private QueryManager<S> queryManager;
	private ArrayList<S> respuesta;

	public HiloPeticiones(QueryManager<S> queryManager) {
		this.queryManager = queryManager;
	}

	@Override
	public void run() {
			this.respuesta = queryManager.readAll().getObjectsArray();
	}

	public ArrayList<S> getRespuesta() {
		return this.respuesta;
	}

}
