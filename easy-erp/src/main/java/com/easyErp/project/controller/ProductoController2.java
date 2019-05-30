package com.easyErp.project.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Categoria;
import com.easyErp.project.model.Producto;
import com.easyErp.project.model.Proveedor;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Stock;
import com.easyErp.project.model.TablaFormaters;
import com.easyErp.project.model.Tasa;
import com.easyErp.project.model.VentaLinea;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;

public class ProductoController2 implements BaseController{

	Producto producto;

	@FXML
	private AnchorPane pane;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextField txtReferencia;
	@FXML
	private JFXTextField txtCategoria;
	@FXML
	private JFXTextField txtAtributo;
	@FXML
	private JFXTextField txtValorAtributo;
	@FXML
	private JFXTextField txtEan;
	@FXML
	private JFXTextField txtFabricante;
	@FXML
	private JFXTextField txtPrecio;
	@FXML
	private JFXTextField txtUnidadMesura;
	@FXML
	private JFXTextField txtTasa;
	@FXML
	private JFXTextField txtStockMin;
	@FXML
	private JFXTextField txtProveedor;
	@FXML
	private JFXCheckBox chkActivo;
	@FXML
	private ImageView imagen;
	@FXML
	private JFXButton btnAlmacen;
	@FXML
	private JFXButton btnTiendas;
	@FXML
	private JFXButton btnEditar;
	@FXML
	private JFXButton btnGuardar;
	@FXML
	private JFXButton btnCompras;
	@FXML
	private JFXButton btnVentas;
	@FXML
	private JFXButton btnBuscar;
	@FXML
	private Line lineAlmacen;
	@FXML
	private Line lineTiendas;
	@FXML
	private Line lineCompras;
	@FXML
	private Line lineVentas;
	@FXML
	private JFXDatePicker dateHasta;
	@FXML
	private JFXDatePicker dateDesde;
	@FXML
	private JFXComboBox<Categoria> cmbCategorias;
	@FXML
	private JFXComboBox<Tasa> cmbTasas;
	@FXML
	private JFXComboBox<Proveedor> cmbProveedores;
	@FXML
	private Label lblResultado;
	@FXML
	private HBox hbxTotales;
	private Node node;
	private TableView<VentaLinea> table;
	private boolean searchTotales;
	private File fileImagen;

	
	private QueryManager<Stock> queryManagerStocks = Stock.getQueryManager();
	private QueryManager<VentaLinea> queryManagerVentas = VentaLinea.getQueryManager();
	private QueryManager<Producto> queryManagerProducto = Producto.getQueryManager();

	private HiloPeticiones<Categoria> peticionCategorias;
	private HiloPeticiones<Tasa> peticionTasas;
	private HiloPeticiones<Proveedor> peticionProveedores;

	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private ArrayList<VentaLinea> ventas;

	@FXML
	private void initialize() {

		QueryManager<Categoria> queryManagerCategoria = Categoria.getQueryManager();
		QueryManager<Tasa> queryManagerTasa = Tasa.getQueryManager();
		QueryManager<Proveedor> queryManagerProveedor = Proveedor.getQueryManager();

		peticionCategorias = new HiloPeticiones<>(queryManagerCategoria);
		peticionTasas = new HiloPeticiones<>(queryManagerTasa);
		peticionProveedores = new HiloPeticiones<>(queryManagerProveedor);

		peticionCategorias.run();
		peticionTasas.run();
		peticionProveedores.run();

	}

	public void crearProducto() {

		nodosVisibles(new Node[] {});
		changeEditableFields(true);
		this.btnGuardar.setVisible(true);
		this.btnEditar.setText("CANCELAR");
		txtNombre.setPromptText("Introduce un nombre");

		initComboBox();

	}

	public void cargarProducto(Producto producto, Node node) {
		this.node = node;
		if (producto == null) {
			crearProducto();
			return;
		}

		this.btnGuardar.setVisible(false);
		this.producto = producto;
		changeEditableFields(false);
		this.btnEditar.setText("EDITAR");
		atributosProducto();
		crearTablaProducto();

	}

	public void atributosProducto() {

		if(producto.getAtributo_valor() != null) this.txtValorAtributo.setText(producto.getAtributo_valor());
		if(producto.getFabricante() != null) this.txtFabricante.setText(producto.getFabricante());
		if(producto.getUnidad_mesura() != null) this.txtUnidadMesura.setText(producto.getUnidad_mesura());
		if(producto.getStock_minimo() != null) this.txtStockMin.setText(producto.getStock_minimo().toString());
		this.chkActivo.setSelected(producto.isActivo());
		this.txtCategoria.setText(producto.getCategoriaNombre());
		this.txtEan.setText(producto.getEan13());
		this.txtReferencia.setText(producto.getReferencia());
		if (producto.getAtributo() != null) this.txtAtributo.setText(producto.getAtributo());
		this.txtNombre.setText(producto.getNombre());
		this.txtPrecio.setText(producto.getPrecio().toString());
		this.txtTasa.setText(producto.getTasaNombre());

		if (producto.getImagen() != null && !producto.getImagen().isEmpty()) {
			try {

				Image image = new Image(producto.getImagen());
				this.imagen.setImage(image);
			} catch (Exception e) {
				AppManager.printError(e.getMessage());
			}
		} else {
			this.imagen.setImage(new Image(getClass().getResourceAsStream("/image/anadirImagen.jpg")));
		}

		if (producto.getProveedor() != null) {
			txtProveedor.setText(producto.getProveedor().getNombre());
		}
	}


	public void crearTablaProducto() {

		clearTable();
		TableView<Stock> table = new TableView<Stock>();;
		table.setId("tabla");
		nodosVisibles(new Node[] { this.lineAlmacen });
		colorNodos(this.btnAlmacen);

		if (this.producto == null)
			return;

		RequestBody formBody = new FormBody.Builder().add("producto_id", this.producto.getId().toString())
				.add("tienda_id", String.valueOf(AppManager.getIdTienda())).build();

		ArrayList<Stock> stocks = queryManagerStocks.readQuery(formBody, 0).getObjectsArray();

		TableColumn<Stock, String> colReferencia = new TableColumn<Stock, String>("Referencia");
		TableColumn<Stock, String> colNombre = new TableColumn<Stock, String>("Nombre");
		TableColumn<Stock, Double> colStock = new TableColumn<Stock, Double>("Stock");
		
		

		colReferencia.setMinWidth(90);
		colReferencia.setMaxWidth(400);
		colReferencia.setCellValueFactory(
				(param) -> new SimpleStringProperty(param.getValue().getProducto().getReferencia()));

		colNombre.setMinWidth(90);
		colNombre.setMaxWidth(400);
		colNombre.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stock, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Stock, String> param) {
						return new SimpleStringProperty(param.getValue().getProducto().getNombre());
					}
				});

		colStock.setMinWidth(90);
		colStock.setMaxWidth(400);
		colStock.setCellValueFactory(new PropertyValueFactory<Stock, Double>("cantidad"));
		
		getTable(table, new TableColumn[]{colReferencia, colNombre, colStock});
		
		propiedadesTabla(table, stocks);
		anadirTotales(stocks, table);
	}

	public void otrasTiendas() {

		clearTable();
		nodosVisibles(new Node[] { this.lineTiendas });
		colorNodos(this.btnTiendas);

		if (this.producto == null)
			return;

		TableView<Stock> table = new TableView<Stock>();
		table.setId("tabla");

		RequestBody formBody = new FormBody.Builder().add("producto_id", this.producto.getId().toString()).build();

		ArrayList<Stock> stocks = queryManagerStocks.readQuery(formBody, 0).getObjectsArray();

		TableColumn<Stock, String> colTienda = new TableColumn<Stock, String>("Tienda");
		TableColumn<Stock, String> colCiudad = new TableColumn<Stock, String>("Ciudad");
		TableColumn<Stock, Double> colStock = new TableColumn<Stock, Double>("Stock");
		TableColumn<Stock, Boolean> verTienda = new TableColumn<Stock, Boolean>("Info");

		table.getColumns().addAll(colTienda, colCiudad, colStock, verTienda);
		colTienda.setMinWidth(90);
		colTienda.setMaxWidth(400);
		colTienda.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTienda().getNombre()));

		colCiudad.setMinWidth(90);
		colCiudad.setMaxWidth(400);

		colCiudad.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTienda().getCiudad()));

		colStock.setMinWidth(90);
		colStock.setMaxWidth(400);
		colStock.setCellValueFactory(new PropertyValueFactory<Stock, Double>("cantidad"));

		verTienda.setMinWidth(40);
		verTienda.setMaxWidth(100);
		verTienda.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Stock, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Stock, Boolean> button) {
						return new SimpleBooleanProperty(true);
					}
				});

		verTienda.setCellFactory(new ColumnButton<Stock, Boolean>("ver",
				new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
			@Override
			public void buttonAction(Stock tienda) {

				// AppManager.getInstance().getAppMain().verProductos(producto);
			}
		});

		propiedadesTabla(table, stocks);
		anadirTotales(stocks, table);
	}

	// TODO
	public void anadirTotales(ArrayList<Stock> array, TableView<Stock> table) {

		hbxTotales.getChildren().clear();
		Double totalCantidad = 0.00;
		for (Stock stock : array) {
			totalCantidad += stock.getCantidad();
		}

		Label label;
		for (int i = 0; i < table.getColumns().size(); i++) {
			if (i == 0) {
				label = new Label("Total");
			} else if (table.getColumns().get(i).getText() == "Stock") {
				label = new Label(TPVController.DF.format(totalCantidad));
				label.setAlignment(Pos.CENTER);
			} else {
				label = new Label(" ");
			}

			hbxTotales.getChildren().add(label);
			label.setMinWidth(table.getColumns().get(i).getMinWidth());
			label.setMaxWidth(table.getColumns().get(i).getMaxWidth());
			HBox.setHgrow(label, Priority.ALWAYS);
			hbxTotales.setVisible(true);
		}
	}

	public void anadirTotalesVenta(ArrayList<VentaLinea> array, TableView<VentaLinea> table) {

		hbxTotales.getChildren().clear();
		Double totalCantidad = 0.00;
		Double totalPrecio = 0.00;
		for (VentaLinea venta : array) {
			totalCantidad += venta.getCantidad();
			totalPrecio += (venta.getPrecio() * venta.getCantidad());
		}

		Label label;
		for (int i = 0; i < table.getColumns().size(); i++) {
			if (i == 0) {
				label = new Label("Total");
			} else if (table.getColumns().get(i).getText() == "Cantidad") {
				label = new Label(totalCantidad.toString());
				label.setAlignment(Pos.CENTER);
			} else if (table.getColumns().get(i).getText() == "Total") {
				label = new Label(TPVController.DF.format(totalPrecio));
				label.setAlignment(Pos.CENTER);
			}

			else {
				label = new Label(" ");
			}

			hbxTotales.getChildren().add(label);
			label.setMinWidth(table.getColumns().get(i).getMinWidth());
			label.setMaxWidth(table.getColumns().get(i).getMaxWidth());
			HBox.setHgrow(label, Priority.ALWAYS);
			hbxTotales.setVisible(true);
		}
	}

	public void historicoCompras() {
		clearTable();
		nodosVisibles(new Node[] { this.lineCompras, this.dateDesde, this.dateHasta, this.btnBuscar });
		colorNodos(this.btnCompras);

		if (this.producto == null)
			return;

		Headers headers = new Headers.Builder().add("producto_id", this.producto.getId().toString()).build();

		this.ventas = queryManagerVentas.readWithDate(headers).getObjectsArray();

		if (ventas != null) {

			this.searchTotales = true;

			this.table = new TableView<VentaLinea>();
			table.setId("tabla");

			TableColumn<VentaLinea, String> colNombreTienda = new TableColumn<VentaLinea, String>("Nombre");
			TableColumn<VentaLinea, Double> colCantidad = new TableColumn<VentaLinea, Double>("Cantidad");
			TableColumn<VentaLinea, Double> colTotal = new TableColumn<VentaLinea, Double>("Precio");
			
			table.getColumns().addAll(colNombreTienda, colCantidad, colTotal);

			colNombreTienda.setMinWidth(90);
			colNombreTienda.setMaxWidth(400);
			colNombreTienda
					.setCellValueFactory((param) -> new SimpleStringProperty(param.getValue().getTienda().getNombre()));

			colCantidad.setMinWidth(90);
			colCantidad.setMaxWidth(400);
			colCantidad.setCellValueFactory(new PropertyValueFactory<VentaLinea, Double>("cantidad"));

			colTotal.setMinWidth(90);
			colTotal.setMaxWidth(400);
			colTotal.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
			colTotal.setCellValueFactory(new PropertyValueFactory<VentaLinea, Double>("precio"));

			propiedadesTabla(table, ventas);
			anadirTotalesVenta(ventas, table);
		} else {
			AppManager.printError("No existen ventas de este producto");
		}
	}

	public void historicoVentas() {
		clearTable();
		nodosVisibles(new Node[] { this.lineVentas, this.dateDesde, this.dateHasta, this.btnBuscar });
		colorNodos(this.btnVentas);

		if (this.producto == null)
			return;

		RequestBody formBody = new FormBody.Builder().add("producto_id", this.producto.getId().toString())
				.add("tienda_id", String.valueOf(AppManager.getIdTienda())).build();

		this.ventas = queryManagerVentas.readQuery(formBody, 0).getObjectsArray();

		if (ventas != null) {

			this.searchTotales = false;
			this.table = new TableView<VentaLinea>();

			table.setId("tabla");

			TableColumn<VentaLinea, String> colNombre = new TableColumn<VentaLinea, String>("Nombre");
			TableColumn<VentaLinea, Double> colCantidad = new TableColumn<VentaLinea, Double>("Cantidad");
			TableColumn<VentaLinea, Double> colPrecio = new TableColumn<VentaLinea, Double>("Precio");
			TableColumn<VentaLinea, String> colTotal = new TableColumn<VentaLinea, String>("Total");
			TableColumn<VentaLinea, String> colFecha = new TableColumn<VentaLinea, String>("Fecha");

			table.getColumns().addAll(colNombre, colCantidad, colPrecio, colTotal, colFecha);

			colNombre.setMinWidth(90);
			colNombre.setMaxWidth(400);
			colNombre.setCellValueFactory(
					(param) -> new SimpleStringProperty(param.getValue().getProducto().getNombre()));

			colCantidad.setMinWidth(90);
			colCantidad.setMaxWidth(400);
			colCantidad.setCellValueFactory(new PropertyValueFactory<VentaLinea, Double>("cantidad"));

			colPrecio.setMinWidth(90);
			colPrecio.setMaxWidth(400);
			colPrecio.setCellValueFactory(new PropertyValueFactory<VentaLinea, Double>("precio"));

			colTotal.setMinWidth(40);
			colTotal.setMaxWidth(300);
			colTotal.setCellValueFactory((param) -> new SimpleStringProperty((param.getValue().getPrecio()) + "€"));

			colFecha.setMinWidth(90);
			colFecha.setMaxWidth(400);
			colFecha.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<VentaLinea, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(TableColumn.CellDataFeatures<VentaLinea, String> param) {
							if (param.getValue().getVenta().getCreated_at() == null)
								return new SimpleStringProperty("No hay fecha");
							return new SimpleStringProperty(param.getValue().getVenta().getCreated_at());
						}
					});

			propiedadesTabla(table, ventas);
			anadirTotalesVenta(ventas, table);
		} else {
			AppManager.printError("No existen ventas de este producto");
		}
	}
	
	public void filtroDate() {

		ObservableList<VentaLinea> array = null;

		LocalDate desde = dateDesde.getValue();
		LocalDate hasta = dateHasta.getValue();

		if (this.table != null) {

			if (this.searchTotales) {

				if (desde == null)
					desde = LocalDate.parse("1900-01-01");
				if (hasta == null)
					hasta = LocalDate.parse("2999-12-31");

				Headers headers = new Headers.Builder().add("producto_id", this.producto.getId().toString())
						.add("desde", desde.toString()).add("hasta", hasta.toString()).build();

				array = FXCollections.observableArrayList(queryManagerVentas.readWithDate(headers).getObjectsArray());
			} else {
				array = FXCollections.observableArrayList(this.ventas);
			}

			if (array.size() > 0) {

				if (!searchTotales) {

					for (VentaLinea ventaLinea : FXCollections.observableArrayList(this.ventas)) {
						if (hasta != null
								&& LocalDate.parse(ventaLinea.getVenta().getCreated_at(), formatter).isAfter(hasta)) {
							array.remove(ventaLinea);
						}
						if (desde != null
								&& LocalDate.parse(ventaLinea.getVenta().getCreated_at(), formatter).isBefore(desde)) {
							array.remove(ventaLinea);
						}
					}
				}

				this.table.getItems().clear();
				this.table.setItems(array);
				anadirTotalesVenta(new ArrayList<VentaLinea>(array), table);

			} else {
				AppManager.printError("No existen ventas de este producto");
			}
		}
	}

	private <S> void propiedadesTabla(TableView<S> table, ArrayList<S> array) {

		this.pane.getChildren().add(table);
		AnchorPane.setBottomAnchor(table, 100d);
		AnchorPane.setTopAnchor(table, 325d);
		AnchorPane.setRightAnchor(table, 20d);
		AnchorPane.setLeftAnchor(table, 20d);

		table.setEditable(false);
		table.getSelectionModel().setCellSelectionEnabled(true);
		table.getItems().clear();
		table.setItems(FXCollections.observableArrayList(array));
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	}

	public void changeEditableFields(Boolean edit) {
		txtValorAtributo.setEditable(edit);
		txtFabricante.setEditable(edit);
		txtUnidadMesura.setEditable(edit);
		txtStockMin.setEditable(edit);
		chkActivo.setDisable(!edit);
		txtEan.setEditable(edit);
		txtReferencia.setEditable(edit);
		txtAtributo.setEditable(edit);
		txtNombre.setEditable(edit);
		txtPrecio.setEditable(edit);
		cmbCategorias.setVisible(edit);
		cmbTasas.setVisible(edit);
		cmbProveedores.setVisible(edit);
		txtCategoria.setVisible(!edit);
		txtTasa.setVisible(!edit);
		txtProveedor.setVisible(!edit);
		this.imagen.setDisable(!edit);
	}

	/**
	 * Funciones del boton editar/canelar
	 */
	public void editarProducto() {

		if (btnEditar.getText().equals("EDITAR")) {

			lblResultado.setVisible(false);
			if (this.producto == null)
				return;

			this.btnGuardar.setVisible(true);
			this.btnEditar.setText("CANCELAR");
			this.btnEditar.setStyle("-fx-background-color: #ef9a9a");
			
			initComboBox();
			changeEditableFields(true);

		} else {
			// cancelar
			if (this.producto == null) {

				AppManager.getInstance().getAppMain().volver(this.node);
				return;

			}
			//TODO metodo cerrar ventana
//			AppManager.getInstance().getAppMain().volver(this.node);
			this.btnGuardar.setVisible(false);
			this.btnEditar.setText("EDITAR");
			this.btnEditar.setStyle("-fx-background-color: #80cbc4");
			atributosProducto();
			changeEditableFields(false);
		}
	}

	public void guardarProducto() {

		if(!valida()) return;
		
		//RequestBody formBody = construirBodyProducto();

		Producto producto = construirBodyProducto();
		lblResultado.setTextFill(Paint.valueOf("#54934c"));
		if (this.producto != null) {			
			producto = queryManagerProducto.updateForId(this.producto.getId(), producto).getObject();
			if (producto != null)
				lblResultado.setText("Producto actualizado!");
		} else {
			producto = queryManagerProducto.insertOne(producto).getObject();
			if (producto != null)
				lblResultado.setText("Producto guardado!");
		}

		if (producto != null) {
			if (this.fileImagen != null) {
				if (queryManagerProducto.uploadFile(this.fileImagen, producto.getId().toString())) {
					this.producto = (queryManagerProducto.getByPk(producto.getId().toString()).getObject());
					atributosProducto();
				} else {
					AppManager.printError("imagen no guardada");
				}
			}
			this.producto = queryManagerProducto.getByPk(producto.getId().toString()).getObject();
			editarProducto();
		} else {
			lblResultado.setTextFill(Paint.valueOf("#ce2020"));
			lblResultado.setText("Error en la operacion!");
		}
		lblResultado.setVisible(true);
	}

	public Producto construirBodyProducto() {
		
		Producto producto = new Producto();
		producto.setNombre(this.txtNombre.getText().trim());
		producto.setEan13(this.txtEan.getText().trim());
		producto.setReferencia(this.txtReferencia.getText().trim());
		producto.setCategoria_id(this.cmbCategorias.getValue().getId());
		producto.setUnidad_mesura(this.txtUnidadMesura.getText());
		producto.setActivo(chkActivo.isSelected() ? 1 : 0);
		producto.setStock_minimo(this.txtStockMin.getText().trim().isEmpty() ? 0 : Integer.parseInt(this.txtStockMin.getText()));
		producto.setAtributo(txtAtributo.getText());
		producto.setAtributo_valor(txtValorAtributo.getText());
		producto.setFabricante(txtFabricante.getText());
		producto.setPrecio(Double.parseDouble(this.txtPrecio.getText()));
		producto.setTasa_id(this.cmbTasas.getValue().getId());
		if(cmbProveedores.getValue() != null) producto.setProveedor(cmbProveedores.getValue());
		
		return producto;
	}

	public boolean valida() {
		boolean valida = checkField(this.txtNombre);
		valida = checkField(this.txtEan) && valida ;
		valida = checkField(this.txtReferencia) && valida;
		valida = checkField(this.txtPrecio) && valida;
		valida = checkField(this.cmbCategorias) && valida;
		valida = checkField(this.cmbTasas) && valida;
		return valida;
	}
	
	public void colorNodos(JFXButton button) {
		JFXButton[] buttons = { this.btnAlmacen, this.btnCompras, this.btnTiendas, this.btnVentas };

		for (JFXButton jfxButton : buttons) {
			if (jfxButton.getId() == button.getId()) {
				jfxButton.setStyle("-fx-background-color:  #80cbc4");
			} else {
				jfxButton.setStyle("-fx-background-color:   #e0e0e0");
			}
		}

	}

	/**
	 * De una lista de nodos, deja visibles en pantalla los enviados por parametro
	 * 
	 * @param nodos que se dejaram visibles
	 */
	public void nodosVisibles(Node[] nodo) {
		Node[] nodoCambiantes = { this.lineAlmacen, this.lineCompras, this.lineTiendas, this.lineVentas, this.dateDesde,
				this.dateHasta, this.btnBuscar, this.lblResultado, this.hbxTotales };

		if (nodo.length == 0) {
			for (Node node : nodoCambiantes) {
				node.setVisible(false);
			}
			return;
		}

		for (Node node : nodoCambiantes) {
			for (Node nodoaCambiar : nodo) {
				if (nodoaCambiar.getId() == node.getId()) {
					node.setVisible(true);
					break;
				} else {
					node.setVisible(false);
				}
			}
		}
	}

	/**
	 * Eliminar la tabla del anchorPane
	 */
	public void clearTable() {
		// this.table.getColumns().clear();
		for (int i = 0; i < pane.getChildren().size(); i++) {
			if (pane.getChildren().get(i).getId() == "tabla") {
				this.pane.getChildren().remove(i);
			}
		}
	}

	/**
	 * Recupera el valor de los hilos y los carga en los comboBox
	 */
	public void initComboBox() {

		try {
			peticionCategorias.join();
			peticionProveedores.join();
			peticionTasas.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(!(this.cmbCategorias.getItems().size() > 0)) {
			initComboBox(this.cmbCategorias, Categoria.class, peticionCategorias.getRespuesta());
		}
		
		if(!(this.cmbTasas.getItems().size() > 0)) {
			initComboBox(this.cmbTasas, Tasa.class, peticionTasas.getRespuesta());
		}
		if(!(this.cmbProveedores.getItems().size() > 0)) {
			initComboBox(this.cmbProveedores, Proveedor.class, peticionProveedores.getRespuesta());
		}

		if (this.producto != null) {
			this.cmbCategorias.getSelectionModel().select(this.producto.getCategoria().getId());
			this.cmbTasas.getSelectionModel().select(this.producto.getTasa().getId());
			if(this.producto.getProveedor() != null) {
				this.cmbProveedores.getSelectionModel().select(this.producto.getProveedor().getId());
			}
		}

	}

	public void image() {
		File file = openFileChooser("Seleccionar imagen", true);

		if (file != null && file.exists()) {
			
			this.imagen.setImage(new Image("file:///" + file.getPath()));
			this.fileImagen = file;

		}
	}

	public static File openFileChooser(String title, boolean open) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Todos", "*.*"),
				new FileChooser.ExtensionFilter("JPEG", "*.jpg"), new FileChooser.ExtensionFilter("PNG", "*.png"));
		File file;
		if (open)
			file = fileChooser.showOpenDialog(AppManager.getInstance().getStage());
		else
			file = fileChooser.showSaveDialog(AppManager.getInstance().getStage());
		return file;
	}

	@Override
	public void onOk() {
		
	}

	@Override
	public void onCancelar() {
		
	}
}
