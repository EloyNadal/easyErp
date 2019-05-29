package com.easyErp.project.model;

public class GrupoClientes {

	private static QueryManager<GrupoClientes> manager;
	private Integer id;
	private String nombre;
	private Float ratio_descuento;
	
	public GrupoClientes() {
		
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Float getRatio_descuento() {
		return ratio_descuento;
	}
	public void setRatio_descuento(Float ratio_descuento) {
		this.ratio_descuento = ratio_descuento;
	}
	
	@Override
	public String toString() {
		return nombre;
	}
	public static QueryManager<GrupoClientes> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<GrupoClientes>(GrupoClientes.class, GrupoClientes[].class, "grupocliente/");
		return manager;

	}
	
}
