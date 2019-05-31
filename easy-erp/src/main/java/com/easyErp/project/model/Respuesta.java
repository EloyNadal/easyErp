package com.easyErp.project.model;

import java.util.ArrayList;

public class Respuesta<T> {

	public static final int MIN_SUCCESS_CODE = 200;
	public static final int MAX_SUCCESS_CODE = 299;
	private T object;
	private boolean success;
	private ArrayList<T> objectsArray;
	
	public Respuesta() {
		
	}
	public T getObject() {
		return object;
	}
	public void setObject(T object) {
		this.object = object;
	}
	public boolean isSuccessful() {
		return success;
	}
	public void setSuccessful(int code) {
		this.success = code >= MIN_SUCCESS_CODE && code <= MAX_SUCCESS_CODE? true:false;
	}
	
	public ArrayList<T> getObjectsArray() {
		return objectsArray;
	}
	public void setObjectsArray(ArrayList<T> objectsArray) {
		this.objectsArray = objectsArray;
	}
	
	public void clear() {
		this.object = ((T) new Object());
		this.success = false;
		this.objectsArray = new ArrayList<T>();
	}
	
}
