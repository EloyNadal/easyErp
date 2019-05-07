package com.easyErp.project.model;

public class Cliente extends Persona{

	private Integer id;
	private Integer grupo_cliente_id;
	

	public Cliente() {
	}

	public Integer getId() {
		return id;
	}

	public Integer getGrupo_cliente_id() {
		return grupo_cliente_id;
	}

	
	public void setId(Integer id) {
		this.id = id;
	}

	public void setGrupo_cliente_id(Integer grupo_cliente_id) {
		this.grupo_cliente_id = grupo_cliente_id;
	}

	
}
