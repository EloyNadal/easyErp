package com.easyErp.project.model;

public class Empleado extends Persona {

	private Integer id_empleado;
	private Integer grupo_usuario_id;
	
	public Empleado() {
		
	}

	public Integer getId_empleado() {
		return id_empleado;
	}

	public void setId_empleado(Integer id_empleado) {
		this.id_empleado = id_empleado;
	}

	public Integer getGrupo_usuario_id() {
		return grupo_usuario_id;
	}

	public void setGrupo_usuario_id(Integer grupo_usuario_id) {
		this.grupo_usuario_id = grupo_usuario_id;
	}
	
	
}
