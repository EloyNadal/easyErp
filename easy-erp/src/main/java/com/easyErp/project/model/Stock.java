package com.easyErp.project.model;

public class Stock {
	
	private static QueryManager<Stock> manager;
	private Integer id;
	private Integer tienda_id;
	private Integer producto_id;
	private Double cantidad;
	
	private Stock() {
		
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

	public Integer getProducto_id() {
		return producto_id;
	}

	public void setProducto_id(Integer producto_id) {
		this.producto_id = producto_id;
	}

	public Double getCantidad() {
		return cantidad;
	}

	public void setCantidad(Double cantidad) {
		this.cantidad = cantidad;
	}
	
	public static QueryManager<Stock> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<Stock>(Stock.class, Stock[].class, "stock/");
		return manager;
	}
	
	
}
