package com.easyErp.project.model;


public class Categoria {
	
	private static QueryManager<Categoria> manager;
	private Integer id;
	private String nombre;
	private Integer categoria_id;
	
	public Categoria() {}


	public Integer getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public Integer getCategoria_id() {
		return categoria_id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}
	
	public static QueryManager<Categoria> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<Categoria>(Categoria.class, Categoria[].class, "categoria/");
		return manager;

	}
	
	
}
