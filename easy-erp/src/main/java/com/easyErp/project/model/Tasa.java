package com.easyErp.project.model;

public class Tasa {
	
	private Integer id;
	private String nombre;
	private Double ratio_tasa;
	
	protected Tasa() {
		
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
	
	
}
