package com.easyErp.project.model;

public class GrupoUsuario {

	private static QueryManager<GrupoUsuario> manager;
	private Integer id;
	private String nombre;
	private char permiso;
	
	public GrupoUsuario() {
		
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
	
	
	public char getPermiso() {
		return permiso;
	}

	public void setPermiso(char permiso) {
		this.permiso = permiso;
	}

	@Override
	public String toString() {
		return nombre;
	}
	
	public static QueryManager<GrupoUsuario> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<GrupoUsuario>(GrupoUsuario.class, GrupoUsuario[].class, "grupocliente/");
		return manager;

	}
	
}
