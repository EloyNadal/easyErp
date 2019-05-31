package com.easyErp.project.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import com.easyErp.project.model.Categoria;
import com.easyErp.project.model.Compra;
import com.easyErp.project.model.CompraLinea;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.Proveedor;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Stock;
import com.easyErp.project.model.VentaLinea;
import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.TablaFormaters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import javafx.util.StringConverter;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;

public class CrearPedidoController {

    @FXML private BorderPane vistaFormProductos; 
    @FXML private JFXComboBox<Categoria> cmbCategoria;
    @FXML private JFXCheckBox chkVentas;
    @FXML private JFXButton btnFiltrar;
    @FXML private JFXButton btnGuardar;
    @FXML private Pagination paginacion;
    
    QueryManager<Producto> queryManagerProducto = Producto.getQueryManager();
	QueryManager<Stock> queryManagerStock = Stock.getQueryManager();
	QueryManager<VentaLinea> queryManagerVenta = VentaLinea.getQueryManager();
	QueryManager<Categoria> queryManagerCategoria = Categoria.getQueryManager();
	QueryManager<Compra> queryCompra = Compra.getQueryManager();
    
    private ArrayList<Producto> productos = buscarProductos();
    private ArrayList<Producto> prodFiltrado;
    private TableView<Producto> tabla = crearTabla();
    private final static int filasPorPagina = 13;
    HashMap<Integer, Double> mapVenta = new HashMap<Integer, Double>();
    HashMap<Producto, Integer> mapPedido = new HashMap<Producto, Integer>();
    LinkedHashSet<Proveedor> listProveedores = new LinkedHashSet<>();
    HashMap<Proveedor, HashMap<Producto, Integer>> mapCompra = new HashMap<Proveedor, HashMap<Producto, Integer>>(); 

    public TableView<Producto> crearTabla(){
    	
    	TableView<Producto> tabla = new TableView<Producto>();
    	
        TableColumn<Producto, Integer> colId = new TableColumn<Producto, Integer>("Id");
        TableColumn<Producto, String> colNombre = new TableColumn<Producto, String>("Nombre");
        TableColumn<Producto, String> colCategoria = new TableColumn<Producto, String>("Categoria");
        TableColumn<Producto, String> colUltimaSemana = new TableColumn<Producto, String>("Venta Semana");
	    TableColumn<Producto, String> colStock = new TableColumn<Producto, String>("Stock");
        TableColumn<Producto, Integer> colPedido = new TableColumn<Producto, Integer>("Pedido");
        
        tabla.getColumns().addAll(colId, colNombre, colCategoria, colUltimaSemana, colStock, colPedido);
        
        //colId.setEditable(false);
        colId.setMinWidth(70);
		colId.setMaxWidth(90);
		colId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Producto, Integer> param) {
						return new SimpleObjectProperty<>(param.getValue().getId());
					}
				});
        
		//colNombre.setEditable(false);
        colNombre.setMinWidth(90);
		colNombre.setMaxWidth(400);
		colNombre.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> param) {
						return new SimpleStringProperty(param.getValue().getNombre());
					}
				});
		
		//colCategoria.setEditable(false);
		colCategoria.setMinWidth(90);
		colCategoria.setMaxWidth(400);
		colCategoria.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> param) {
						return new SimpleStringProperty(param.getValue().getCategoria().getNombre());
					}
				});
		
		//colUltimaSemana.setEditable(false);
		colUltimaSemana.setMinWidth(90);
		colUltimaSemana.setMaxWidth(120);
		colUltimaSemana.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, String>, ObservableValue<String>>() {
					
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> param) {
						
						
						LocalDate hasta = LocalDate.now();
						LocalDate desde = hasta.minusDays(7);
						
						Headers headers = new Headers.Builder()
								.add("producto_id", param.getValue().getId().toString())
								.add("desde", desde.toString())
								.add("hasta", hasta.toString())
								.add("tienda_id", String.valueOf(AppManager.getIdTienda()))
								.build();
						
						ArrayList<VentaLinea> vl = queryManagerVenta.readWithDate(headers).getObjectsArray();
						if (vl == null || vl.size() == 0) {
							return new SimpleStringProperty("0");
						}
						mapVenta.put(param.getValue().getId(), vl.get(0).getCantidad());
						return new SimpleStringProperty(vl.get(0).getCantidad().toString());
					}
				});
		
		colStock.setEditable(false);
		colStock.setMinWidth(90);
		colStock.setMaxWidth(120);
		colStock.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, String>,ObservableValue<String>>() {

					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Producto, String> param) {
						
						RequestBody body = new FormBody.Builder()
								.add("producto_id",param.getValue().getId().toString())
								.add("tienda_id", String.valueOf(AppManager.getIdTienda()))
								.build();
						
						ArrayList<Stock> stok = queryManagerStock.readQuery(body, false).getObjectsArray();
						if (stok.size() == 0) {
							return new SimpleStringProperty("0");
						}
						return new SimpleStringProperty(stok.get(0).getCantidad().toString());						
					}
				});
		
		colPedido.setEditable(true);
		colPedido.setMinWidth(70);
		colPedido.setMaxWidth(90);
		colPedido.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Producto, Integer>, ObservableValue<Integer>>() {
					@Override
					public ObservableValue<Integer> call(TableColumn.CellDataFeatures<Producto, Integer> param) {
						
						if (mapPedido.get(param.getValue()) != null) {
							return new SimpleObjectProperty(mapPedido.get(param.getValue().getId())); 
						}
						return new SimpleObjectProperty(0);
					}
				});
		colPedido.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getIntegerFormatter()));
		colPedido.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Producto,Integer>>() {
			
			@Override
			public void handle(CellEditEvent<Producto, Integer> event) {
				
				mapPedido.put(event.getRowValue(), event.getNewValue());
				if(event.getRowValue().getProveedor() != null) {
					listProveedores.add(event.getRowValue().getProveedor());
				}
			}
		});
		
		
		
		tabla.setEditable(true);
		tabla.getSelectionModel().getSelectedItem();
		tabla.getSelectionModel().setCellSelectionEnabled(true);
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	return tabla;
    }
    
    public ArrayList<Producto> buscarProductos(){
    	return queryManagerProducto.readAll().getObjectsArray();
    }
    
    public ArrayList<Producto> filtrarProductos(){
    	
    	ArrayList<Producto> p = new ArrayList<>(this.productos);
    	
    	for (Producto producto : this.productos) {
			
    		if (cmbCategoria.getValue() != null 
    				&& cmbCategoria.getValue().getId() !=  producto.getCategoria_id()) {
    			p.remove(producto);
    		}
    		if (chkVentas.isSelected()
    				&& mapVenta.get(producto.getId()) == null) {
    			p.remove(producto);
    		}
    		
		}
    	return p;
    }
    
    
    @FXML
    private void initialize() {
    	
    	ArrayList<Categoria> c = queryManagerCategoria.readAll().getObjectsArray();
    	initComboBox(cmbCategoria, c);
		this.paginacion.setPageCount(this.productos.size() != 0?
				(int) Math.ceil((double)this.productos.size()/(double)filasPorPagina):1);
		this.paginacion.setPageFactory(this::crearPagina);
    	
    }
    
    private Node crearPagina(int paginaIndice) {
    	int desdeIndice = paginaIndice * filasPorPagina;
    	int hastaIndice = Math.min(desdeIndice + filasPorPagina, this.productos.size());
    	tabla.setItems(FXCollections.observableArrayList(this.productos.subList(desdeIndice, hastaIndice)));
    	tabla.setEditable(true);
    	return tabla;
    }
    
    private Node recargarPagina(int paginaIndice) {
    	int desdeIndice = paginaIndice * filasPorPagina;
    	int hastaIndice = Math.min(desdeIndice + filasPorPagina, this.prodFiltrado.size());
    	tabla.setItems(FXCollections.observableArrayList(this.prodFiltrado.subList(desdeIndice, hastaIndice)));
    	return tabla;
    }
    
    
    @FXML
    void filtrar() {
    	this.prodFiltrado = filtrarProductos();
    	this.paginacion.setPageCount(this.prodFiltrado.size() != 0?
    			(int) Math.ceil((double)this.prodFiltrado.size()/(double)filasPorPagina):1);
    	this.paginacion.setPageFactory(this::recargarPagina);
    }
           
    
    
	public void initComboBox(JFXComboBox<Categoria> combo, ArrayList<Categoria> array) {
		
		array.add(0, null);
		combo.setItems(FXCollections.observableArrayList(array));
		combo.getSelectionModel().selectFirst();

		combo.setCellFactory(new Callback<ListView<Categoria>, ListCell<Categoria>>() {
			@Override
			public ListCell<Categoria> call(ListView<Categoria> l) {
				return new ListCell<Categoria>() {
					@Override
					protected void updateItem(Categoria item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText("Sin Selección");
						} else {
							setText(item.getNombre());
						}
					}
				};
			}
		});

		combo.setConverter(new StringConverter<Categoria>() {
			@Override
			public String toString(Categoria object) {
				if (object == null) {
					
					return "Sin Selección";
				} else {
					return object.getNombre();
				}
			}

			@Override
			public Categoria fromString(String string)  {
				return null;
			}
		});
	}
	
	public void crearPedido() {
		
		ArrayList<Compra> compras = new ArrayList<>();
		
		for (Proveedor proveedores : listProveedores) {
			
			Compra compra = new Compra();
			compra.setPrecio_sin_tasas(0.00);
			compra.setTotal_tasas(0.00);
			
			List<CompraLinea> l = new ArrayList<>(); 
			
			compra.setTienda_id(AppManager.getIdTienda());
			compra.setProveedor_id(proveedores.getId());
			
			HashMap<Producto, Integer> productoProveedor = new HashMap<>();
			
			for (Map.Entry<Producto, Integer> productos : mapPedido.entrySet()) {
				
				if (productos.getKey().getProveedor() != null && productos.getKey().getProveedor().getId() == proveedores.getId()) {
					productoProveedor.put(productos.getKey(), productos.getValue());
					
					CompraLinea cl = new CompraLinea();
					
					cl.setCantidad(productos.getValue());
					cl.setPrecio(productos.getKey().getPrecio() * productos.getValue());
					cl.setProducto_id(productos.getKey().getId());
					cl.setTasa_id(productos.getKey().getTasa_id());
					Double iva = (cl.getPrecio() * productos.getKey().getTasa().getRatio_tasa());
					
					compra.setPrecio_sin_tasas(compra.getPrecio_sin_tasas() + cl.getPrecio());
					compra.setTotal_tasas(compra.getTotal_tasas() + iva);
					
					l.add(cl);
				}
			}
			
			compra.setPrecio_total(compra.getPrecio_sin_tasas() + compra.getTotal_tasas());
			compra.setCompra_linea(l);
			compras.add(compra);
			
			mapCompra.put(proveedores, new HashMap<>(productoProveedor));
		}
		
		for (Compra compra : compras) {
			queryCompra.insertOne(compra);
		}
		mapPedido.clear();
		tabla.getItems().clear();
		filtrar();
	}
	
	
	//TODO: para pruebas
	public void imprimirCompras(ArrayList<Compra> compras) {
		
		System.out.println("Compra:");
		for (Compra compra : compras) {
			
			System.out.println("\n");
			System.out.println("tienda: " + compra.getTienda_id());
			System.out.println("proveedor: " + compra.getProveedor_id());
			
			System.out.println("\tProductos:");
			for (int i = 0; i < compra.getCompra_linea().size(); i++) {
				
				System.out.println("\tp-> " + compra.getCompra_linea().get(i).getProducto_id() + " c-> " + compra.getCompra_linea().get(i).getCantidad()
						+ " p-> " + compra.getCompra_linea().get(i).getPrecio());
			}
			
			System.out.println("Precio:" + compra.getPrecio_sin_tasas());
		}
		
	}
	
	//TODO: para pruebas
	public void imprimirMap() {

		/*
		 * recorrer el map
		 */
		for (Map.Entry<Proveedor, HashMap<Producto, Integer>> proveedores : mapCompra.entrySet()) {

			System.out.println("pedido:");
			System.out.println("proveedor: " + proveedores.getKey().getNombre());

			for (Map.Entry<Producto, Integer> productos : proveedores.getValue().entrySet()) {
				System.out.println(
						"\tproducto: " + productos.getKey().getNombre() + " cantidad: " + productos.getValue());
			}
		}

	}
}

	