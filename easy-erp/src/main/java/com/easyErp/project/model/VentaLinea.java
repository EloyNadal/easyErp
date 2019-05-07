package com.easyErp.project.model;

public class VentaLinea {
	
	private Integer id;
	private Integer venta_id;
	private Integer producto_id;
	private Double precio;
	private Integer tasa_id;
	private Double cantidad;
	
	protected VentaLinea() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVenta_id() {
		return venta_id;
	}

	public void setVenta_id(Integer venta_id) {
		this.venta_id = venta_id;
	}

	public Integer getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}

	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public Integer getTasa_id() {
		return tasa_id;
	}

	public void setTasa_id(Integer tasa_id) {
		this.tasa_id = tasa_id;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
