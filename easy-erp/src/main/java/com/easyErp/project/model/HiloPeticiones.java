package com.easyErp.project.model;

import java.util.ArrayList;

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
