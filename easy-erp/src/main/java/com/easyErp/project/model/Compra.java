package com.easyErp.project.model;

import java.util.List;

public class Compra {
	
	
	private static QueryManager<Compra> manager;
	private Integer id;
	private Integer tienda_id;
	private Integer proveedor_id;
    private Double precio;
    private Double iva;
    private Double precio_total;
    private String created_at;
    private String updated_at;
    private List<CompraLinea> compra_linea;
    
	public Compra() {
	}

	public Integer getId() {
		return id;
	}

	public Integer getTienda_id() {
		return tienda_id;
	}

	public Integer getProveedor_id() {
		return proveedor_id;
	}

	public Double getPrecio() {
		return precio;
	}

	public Double getIva() {
		return iva;
	}

	public Double getPrecio_total() {
		return precio_total;
	}

	public String getCreated_at() {
		return created_at;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public List<CompraLinea> getCompra_linea() {
		return compra_linea;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setTienda_id(Integer tienda_id) {
		this.tienda_id = tienda_id;
	}

	public void setProveedor_id(Integer proveedor_id) {
		this.proveedor_id = proveedor_id;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public void setPrecio_total(Double precio_total) {
		this.precio_total = precio_total;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}

	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}

	public void setCompra_linea(List<CompraLinea> compra_linea) {
		this.compra_linea = compra_linea;
	}
	
	public static QueryManager<Compra> getQueryManager() {
		if (null == manager)
			manager = new QueryManager<Compra>(Compra.class, Compra[].class, "compra/");
		return manager;

	}
	
}
