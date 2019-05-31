package com.easyErp.project.model;

import java.util.List;
import java.util.Locale;

import com.easyErp.project.utils.AppManager;

public class Producto {
	
	private Integer id;	
	private Integer categoria_id;	
	private String ean13;	
	private String referencia;	
	private String atributo;
	private String atributo_valor;
	private String nombre;
	private String unidad_mesura;
	private Double precio;
	private Integer tasa_id;
	private Integer stock_minimo;
	private Integer activo;
	private String fabricante;
	private String imagen;
	private Locale created_at;
	private Locale updated_at;
	private Categoria categoria;
	private Tasa tasa;
	
	//TODO es para pruebas
	private List<Stock> stocks; 
	private List<Compra> compras;
	private List<Venta> ventas;
	private Proveedor proveedor;
	
	private static QueryManager<Producto> manager;
	
	public Producto() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoria_id() {
		return categoria_id;
	}

	public void setCategoria_id(Integer categoria_id) {
		this.categoria_id = categoria_id;
	}

	public String getEan13() {
		return ean13;
	}

	public void setEan13(String ean13) {
		this.ean13 = ean13;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getAtributo() {
		return atributo;
	}

	public void setAtributo(String atributo) {
		this.atributo = atributo;
	}

	public String getAtributo_valor() {
		return atributo_valor;
	}

	public void setAtributo_valor(String atributo_valor) {
		this.atributo_valor = atributo_valor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUnidad_mesura() {
		return unidad_mesura;
	}

	public void setUnidad_mesura(String unidad_mesura) {
		this.unidad_mesura = unidad_mesura;
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

	public Integer getStock_minimo() {
		return stock_minimo;
	}

	public void setStock_minimo(Integer stock_minimo) {
		this.stock_minimo = stock_minimo;
	}

	public Boolean isActivo() {
		return activo == 1;
	}

	public void setActivo(Integer activo) {
		this.activo = activo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public void setFabricante(String fabricante) {
		this.fabricante = fabricante;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
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
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public String getCategoriaNombre() {
		return categoria.getNombre();
	}

	public Tasa getTasa() {
		return tasa;
	}
	
	public String getTasaNombre() {
		return tasa.getNombre();
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setTasa(Tasa tasa) {
		this.tasa = tasa;
	}
	

	public List<Stock> getStock() {
		return this.stocks;
	}

	public void setStock(List<Stock> stock) {
		this.stocks = stock;
	}
	
	
	public List<Compra> getCompras() {
		return compras;
	}

	public List<Venta> getVentas() {
		return ventas;
	}

	public void setCompras(List<Compra> compras) {
		this.compras = compras;
	}

	public void setVentas(List<Venta> ventas) {
		this.ventas = ventas;
	}

	public Double getStockTienda() {
		
		for (Stock stock : stocks) {
			if (stock.getTienda_id() == AppManager.getIdTienda()) {
				return stock.getCantidad();
			}
		}
		return 0.00;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}


	public static QueryManager<Producto> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<Producto>(Producto.class, Producto[].class, "producto/");
		return manager;
	}

//	@Override
//	public String toString() {
//		return this.getNombre();
//	}
//	
	
}
