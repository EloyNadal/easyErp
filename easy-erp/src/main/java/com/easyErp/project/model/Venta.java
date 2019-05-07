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
	
	
}
