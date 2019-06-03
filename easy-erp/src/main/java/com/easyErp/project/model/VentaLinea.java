package com.easyErp.project.model;

public class VentaLinea {
	
	
	private Integer id;
	private Integer tienda_id;
	private Integer venta_id;
	private Integer producto_id;
	private Double precio;
	private Integer tasa_id;
	private Double cantidad;
	private String productoNombre;
	

	private Tienda tienda;
	private Producto producto;
	private Venta venta;
	
	private static QueryManager<VentaLinea> manager;
	
	public VentaLinea() {

		
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
	
	public Integer getTienda_id() {
		return tienda_id;
	}

	public Tienda getTienda() {
		return tienda;
	}

	public Producto getProducto() {
		return producto;
	}

	public Venta getVenta() {
		return venta;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}

	public void setTienda(Tienda tienda) {
		this.tienda = tienda;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public void setVenta(Venta venta) {
		this.venta = venta;
	}
	

	public String getProductoNombre() {
		return productoNombre;
	}

	public void setProductoNombre(String productoNombre) {
		this.productoNombre = productoNombre;
	}

	public static QueryManager<VentaLinea> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<VentaLinea>(VentaLinea.class, VentaLinea[].class, "ventalinea/");
		return manager;
	}
	
}
