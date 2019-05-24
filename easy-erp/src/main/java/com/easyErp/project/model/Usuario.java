package com.easyErp.project.model;

public class Usuario {
	
	private static QueryManager<Usuario> manager;
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
	
	protected void setApi_token(String api_token) {
		this.api_token = api_token;
	}

	protected String getApiToken() {
		return this.api_token;
	}

	public String getUser_name() {
		return user_name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEmpleado_id() {
		return empleado_id;
	}

	public void setEmpleado_id(Integer empleado_id) {
		this.empleado_id = empleado_id;
	}
	
	public static QueryManager<Usuario> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<Usuario>(Usuario.class, Usuario[].class, "usuario/");
		return manager;
	}
}
