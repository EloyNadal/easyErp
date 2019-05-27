package com.easyErp.project.model;

public class Empleado extends Persona {

	private static QueryManager<Empleado> manager;
	private Integer id;
	private Integer tienda_id;
	
	public Empleado() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId_empleado(Integer id) {
		this.id = id;
	}

	
	public Integer getTienda_id() {
		return tienda_id;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}
	
	public static QueryManager<Empleado> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<Empleado>(Empleado.class, Empleado[].class, "empleado/");
		return manager;

	}
	
	
}
