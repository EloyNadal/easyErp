package com.easyErp.project.controller;

import java.text.DecimalFormat;
import java.util.LinkedList;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.Venta;
import com.easyErp.project.model.VentaLinea;
import com.jfoenix.controls.JFXButton;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class TPVController implements BaseController {
	@FXML
	JFXButton btnErase;
	@FXML
	TextField txtLineaTeclado;
	@FXML
	TextField txtLineaProducto;
	@FXML
	TextField txtTotal;
	@FXML
	TableView<VentaLinea> tableVentaLinea;
	@FXML 
	AnchorPane parent;

	private LinkedList<Producto> productos;
	private LinkedList<VentaLinea> lineas;
	private double total = 0;
	private Cliente cliente;
	public static final DecimalFormat DF = new DecimalFormat("#0.00");
	private String formaDePago;

	@FXML
	public void initialize() {
		
		addOkFilter(parent);
		txtLineaProducto.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (!newValue) {
					txtLineaProducto.requestFocus();
				}
			}
		});
		this.productos = new LinkedList<Producto>();
		this.lineas = new LinkedList<VentaLinea>();
		this.tableVentaLinea.getColumns().clear();

		TableColumn<VentaLinea, Integer> colCantidad = new TableColumn<VentaLinea, Integer>("Cantidad");
		TableColumn<VentaLinea, String> colNombre = new TableColumn<VentaLinea, String>("Producto");
		TableColumn<VentaLinea, String> colPrecio = new TableColumn<VentaLinea, String>("Precio");

		getTable(tableVentaLinea, new TableColumn[] {colCantidad, colNombre, colPrecio});
		

		colCantidad.setMinWidth(30);
		colCantidad.setMaxWidth(400);
		colCantidad.setCellValueFactory(new PropertyValueFactory<VentaLinea, Integer>("cantidad"));

		colNombre.setMinWidth(120);
		colNombre.setMaxWidth(400);
		colNombre.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VentaLinea, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<VentaLinea, String> linea) {
						return new SimpleStringProperty(
								getProductoFromId(linea.getValue().getProducto_id()).getNombre());
					}
				});
		colPrecio.setMinWidth(40);
		colPrecio.setMaxWidth(400);
		colPrecio.getStyleClass().add("{-fx-alignment: center-right;}");
		colPrecio.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<VentaLinea, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<VentaLinea, String> linea) {
						return new SimpleStringProperty(DF.format(linea.getValue().getPrecio()) + "€");
					}
				});

		this.tableVentaLinea.setEditable(false);
		this.tableVentaLinea.getSelectionModel().setCellSelectionEnabled(true);
		this.tableVentaLinea.getItems().clear();
		this.tableVentaLinea.setItems(FXCollections.observableArrayList(lineas));
		this.tableVentaLinea.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	@FXML
	private void pagarTarjeta() {
		setFormaDePago("TARJETA");
		cerrarVenta();
	}

	@FXML
	private void pagarEfectivo() {
		setFormaDePago("EFECTIVO");
		cerrarVenta();
	}

	private void setFormaDePago(String formaPago) {
		this.formaDePago = formaPago;
	}

	@FXML
	private void borrar() {
		txtLineaTeclado.setText("");
	}

	private void buscarProducto() {
		if(txtLineaProducto.getText().equals("")) return;
		String prodCode = txtLineaProducto.getText();
		txtLineaProducto.setText("");
		Producto producto = null;
		int cantidad;
		cantidad = txtLineaTeclado.getText().equals("") ? 1 : Integer.parseInt(txtLineaTeclado.getText());
		producto = comprobarProducto(producto, prodCode);
		if (null == producto && cantidad > 0) {
			RequestBody request = new FormBody.Builder().add("ean13", prodCode).build();
			try {
				producto = Producto.getQueryManager().readQuery(request, 1).getObjectsArray().get(0);
				this.productos.addLast(producto);
				this.lineas.addLast(getLineaFromProduct(producto, cantidad));
			} catch (Exception e) {
				AppManager.showError("Producto no encontrado");
			}
		} else if (null != producto) {
			anadirEliminarUnidad(producto, cantidad);

		}
		actualizarTabla();
		borrar();

	}

	private Producto getProductoFromId(Integer id) {
		int i = 0;
		while (this.productos.get(i).getId() != id)
			i++;
		return this.productos.get(i);
	}

	private Producto comprobarProducto(Producto producto, String prodCode) {
		if (this.productos.size() == 0)
			return null;
		int i = 0;
		while (i < this.productos.size() && producto == null) {
			if (this.productos.get(i).getEan13().equals(prodCode)
					|| this.productos.get(i).getReferencia().equals(prodCode)
					|| this.productos.get(i).getNombre().equals(prodCode)) {
				producto = this.productos.get(i);
				return producto;
			}
			i++;
		}
		return null;
	}

	private void anadirEliminarUnidad(Producto producto, int cantidad) {
		int i = 0;
		while (this.lineas.get(i).getProducto_id() != producto.getId())
			i++;
		this.lineas.get(i).setCantidad(this.lineas.get(i).getCantidad() + cantidad);
		this.lineas.get(i).setPrecio(this.lineas.get(i).getPrecio() + producto.getPrecio() * cantidad);
		if (lineas.get(i).getCantidad() <= 0)
			eliminar(producto, this.lineas.get(i));
	}

	private void actualizarTabla() {
		actualizarTotal();
		this.tableVentaLinea.setItems(FXCollections.observableArrayList(lineas));
		this.tableVentaLinea.refresh();
	}

	private VentaLinea getLineaFromProduct(Producto producto, int cantidad) {
		VentaLinea linea = new VentaLinea();
		linea.setProducto_id(producto.getId());
		linea.setTasa_id(producto.getTasa_id());
		linea.setCantidad((double) cantidad);
		linea.setPrecio(producto.getPrecio() * cantidad);
		return linea;
	}

	private void actualizarTotal() {
		this.total = 0;
		for (VentaLinea linea : lineas) {
			this.total += linea.getPrecio();
		}
		txtTotal.setText(DF.format(total) + "€");
	}

	@FXML
	private void insertarNumero(Event event) {
		if (((JFXButton) event.getSource()).getText().equals("-"))
			txtLineaTeclado.setText("-");
		else
			txtLineaTeclado.setText(txtLineaTeclado.getText() + ((JFXButton) event.getSource()).getText());
	}

	@FXML
	private void eliminarLinea() {
		if (!this.lineas.isEmpty()) {
			this.lineas.removeLast();
			this.productos.removeLast();
			actualizarTabla();
		}
	}

	private void eliminar(Producto producto, VentaLinea linea) {
		this.lineas.remove(linea);
		this.productos.remove(producto);
	}

	@FXML
	private void insertarCliente() {
		String codCliente = AppManager.showMessageForStringResult("Cliente",
				"Introduzca numero de socio/DNI del Cliente");
		if (null != codCliente) {
			RequestBody request = new FormBody.Builder().add("codigo", codCliente).add("codigo", codCliente).build();
			try {
				cliente = Cliente.getQueryManager().readQuery(request, 1).getObjectsArray().get(0);
			} catch (Exception e) {
				AppManager.showError("Cliente no encontrado");
			}
		}
	}

	private double calcularPrecioSinIva(VentaLinea linea) {
		double precio = 0d;
		precio = (linea.getPrecio() * 100) / (100 + 
				getProductoFromId(linea.getProducto_id()).getTasa().getRatio_tasa() * 100);
		return precio;
	}

	@FXML
	private void cerrarVenta() {
		if (this.lineas.isEmpty()) {
			AppManager.showError("La venta está vacía");
			return;
		}
		Venta venta = new Venta();
		venta.setCliente_id(null != cliente ? cliente.getId() : null);
		venta.setTienda_id(AppManager.getIdTienda());
		venta.setUsuario_id(AppManager.getSessionUserId());
		venta.setVenta_linea(this.lineas);
		venta.setEfectivo(formaDePago.equals("EFECTIVO")?1:0);
		venta.setTarjeta(formaDePago.equals("TARJETA")?1:0);
		double totalSinTasas = 0;
		for (VentaLinea linea : this.lineas) {
			totalSinTasas += calcularPrecioSinIva(linea);
		}
		venta.setPrecio_sin_tasas(totalSinTasas);
		venta.setPrecio_total(this.total);
		venta.setTotal_tasas(this.total - totalSinTasas);
		Venta.getQueryManager().insertOne(venta);
		clear();
	}

	private void clear() {
		this.productos.clear();;
		this.lineas.clear();
		this.cliente = null;
		actualizarTabla();
		
	}
	
	@FXML
	@Override
	public void onOk() {
		buscarProducto();
	}
	
	
	@Override
	public void onCancelar() {
		// TODO Auto-generated method stub
		
	}
}
