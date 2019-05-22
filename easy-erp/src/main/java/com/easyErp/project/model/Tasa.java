package com.easyErp.project.model;

public class Tasa {
	
	private static QueryManager<Tasa> manager;
	private Integer id;
	private String nombre;
	private Double ratio_tasa;
	
	public Tasa() {
		
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

	public Double getRatio_tasa() {
		return ratio_tasa;
	}

	public void setRatio_tasa(Double ratio_tasa) {
		this.ratio_tasa = ratio_tasa;
	}
	
	public static QueryManager<Tasa> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<Tasa>(Tasa.class, Tasa[].class, "tasa/");
		return manager;
	}
	
	
}
