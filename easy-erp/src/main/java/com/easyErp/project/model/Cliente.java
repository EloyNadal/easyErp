package com.easyErp.project.model;

public class Cliente extends Persona{

	private static QueryManager<Cliente> manager;
	private Integer id;
	private Integer grupo_cliente_id;
	private String codigo;
	
	public Cliente() {

	}

	public Cliente(Integer id) {
		this.grupo_cliente_id = id;
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

	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public static QueryManager<Cliente> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<Cliente>(Cliente.class, Cliente[].class, "cliente/");
		return manager;

	}

}
