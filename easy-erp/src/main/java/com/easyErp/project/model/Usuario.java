package com.easyErp.project.model;

public class Usuario {
	
	private Integer id;
	private Integer empleado_id;
	private String user_name;
	private Integer grupo_usuario_id;
	private String api_token;
	
	public Integer getGrupo_usuario_id() {
		return grupo_usuario_id;
	}

	public void setGrupo_usuario_id(Integer grupo_usuario_id) {
		this.grupo_usuario_id = grupo_usuario_id;
	}
	
	protected String getApiToken() {
		return this.api_token;
	}

	public String getUser_name() {
		return user_name;
	}
	
}
