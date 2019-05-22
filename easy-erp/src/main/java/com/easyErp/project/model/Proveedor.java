package com.easyErp.project.model;

import java.util.Locale;

public class Proveedor {
	
	private static QueryManager<Proveedor> manager;
	private Integer id;
	private String nombre;
	private String direccion;
	private String ciudad;
	private String telefono;
	private String email;
	private String codigo_postal;
	private String pais;
	private Locale created_at;
	private Locale updated_at;
	
	public Proveedor() {
		
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

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public Locale getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Locale created_at) {
		this.created_at = created_at;
	}

	public Locale getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(Locale updated_at) {
		this.updated_at = updated_at;
	}
	
	@Override
	public String toString() {
		return this.getNombre();
	}
	
	public static QueryManager<Proveedor> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<Proveedor>(Proveedor.class, Proveedor[].class, "proveedor/");
		return manager;
	}
	
	
}