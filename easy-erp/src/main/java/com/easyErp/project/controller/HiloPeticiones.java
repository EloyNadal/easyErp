package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.QueryManager;

public class HiloPeticiones<S> extends Thread{

	private QueryManager<S> queryManager;
	private ArrayList<S> respuesta;
	
	public HiloPeticiones(QueryManager<S> queryManager){
		this.queryManager = queryManager;
	}
	
	@Override
	public void run() {
		this.respuesta = queryManager.readAll().getObjectsArray();
		this.respuesta.add(0, null);
	}
	
	
	public ArrayList<S> getRespuesta(){
		return this.respuesta;
	}
	

}
