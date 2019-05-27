package com.easyErp.project.model;

public class CompraLinea {
	
	private Integer id;
    private Integer tienda_id;
    private Integer compra_id;
    private Integer producto_id;
    private Double precio;
    private Integer tasa_id;
    private Integer cantidad;
    private String productoNombre;
    private String productoEan13;
    
    
	public CompraLinea() {
	}

	public Integer getId() {
		return id;
	}

	public Integer getTienda_id() {
		return tienda_id;
	}

	public Integer getCompra_id() {
		return compra_id;
	}

	public Integer getProducto_id() {
		return producto_id;
	}

	public Double getPrecio() {
		return precio;
	}

	public Integer getTasa_id() {
		return tasa_id;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}

	public void setCompra_id(Integer compra_id) {
		this.compra_id = compra_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public void setTasa_id(Integer tasa_id) {
		this.tasa_id = tasa_id;
	}

	public void setCantidad(Integer cantidad) { 
		this.cantidad = cantidad;
	}

	public String getProductoNombre() {
		return productoNombre;
	}

	public String getProductoEan13() {
		return productoEan13;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public void setProductoEan13(String productoEan13) {
		this.productoEan13 = productoEan13;
	}
	
	

	
}
