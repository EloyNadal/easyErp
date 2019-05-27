package com.easyErp.project.model;

import java.util.Locale;

public class Persona {

	private String nombre;
	private String apellidos;
	private String direccion;
	private String ciudad;
	private String telefono;
	private String codigo_postal;
	private String pais;
	private String email;
	private String dni;
	private Locale created_at;
	private Locale updated_at;

	public Persona() {

	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getCiudad() {
		return ciudad;
	}

	public String getTelefono() {
		return telefono;
	}

	public String getCodigo_postal() {
		return codigo_postal;
	}

	public String getPais() {
		return pais;
	}

	public String getEmail() {
		return email;
	}

	public String getDni() {
		return dni;
	}

	public Locale getCreated_at() {
		return created_at;
	}

	public Locale getUpdated_at() {
		return updated_at;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public void setCodigo_postal(String codigo_postal) {
		this.codigo_postal = codigo_postal;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public void setCreated_at(Locale created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_at(Locale updated_at) {
		this.updated_at = updated_at;
	}
	
	@Override
	public String toString() {
		return nombre + " " + apellidos;
	}
}
