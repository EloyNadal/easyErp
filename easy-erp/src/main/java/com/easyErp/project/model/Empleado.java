package com.easyErp.project.model;

public class Empleado extends Persona {

	private Integer id_empleado;

	private Integer tienda_id;

	
	protected Empleado() {
		
	}

	public Integer getId_empleado() {
		return id_empleado;
	}

	public void setId_empleado(Integer id_empleado) {
		this.id_empleado = id_empleado;
	}

	
	public Integer getTienda_id() {
		return tienda_id;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}
	
	
}
