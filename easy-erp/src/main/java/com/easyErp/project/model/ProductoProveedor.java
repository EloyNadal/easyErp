package com.easyErp.project.model;

public class ProductoProveedor {
	
	private static QueryManager<ProductoProveedor> manager;
	private Integer id;
	private Integer producto_id;
	private Double proveedor_id;
	
	public ProductoProveedor() {
		
	}

	public static QueryManager<ProductoProveedor> getQueryManager(){
		if(null == manager)
			manager = new QueryManager<ProductoProveedor>(ProductoProveedor.class, ProductoProveedor[].class, "productoproveedor/");
		return manager;
	}
	
	
}
