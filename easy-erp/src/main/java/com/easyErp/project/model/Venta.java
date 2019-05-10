package com.easyErp.project.model;

import java.util.Locale;

public class Venta {

	private Integer id;
	private Integer tienda_id;
	private Integer cliente_id;
	private Integer usuario_id;
	private Double precio_sin_tasas;
	private Double total_tasas;
	private Double precio_total;
	private Locale created_at;
	private Locale updated_at;
	
	protected Venta() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTienda_id() {
		return tienda_id;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}

	public Integer getCliente_id() {
		return cliente_id;
	}

	public void setCliente_id(Integer cliente_id) {
		this.cliente_id = cliente_id;
	}

	public Integer getUsuario_id() {
		return usuario_id;
	}

	public void setUsuario_id(Integer usuario_id) {
		this.usuario_id = usuario_id;
	}

	public Double getPrecio_sin_tasas() {
		return precio_sin_tasas;
	}

	public void setPrecio_sin_tasas(Double precio_sin_tasas) {
		this.precio_sin_tasas = precio_sin_tasas;
	}

	public Double getTotal_tasas() {
		return total_tasas;
	}

	public void setTotal_tasas(Double total_tasas) {
		this.total_tasas = total_tasas;
	}

	public Double getPrecio_total() {
		return precio_total;
	}

	public void setPrecio_total(Double precio_total) {
		this.precio_total = precio_total;
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
	
	
}