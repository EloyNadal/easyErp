package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.GrupoClientes;
import com.easyErp.project.model.HiloPeticiones;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Stock;
import com.easyErp.project.model.Venta;
import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.ColumnButton;
import com.easyErp.project.utils.TablaFormaters;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class EditarClienteController implements BaseController{
	private Cliente cliente;

	@FXML
	private AnchorPane pane;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextField txtApellidos;
	@FXML
	private JFXTextField txtDireccion;
	@FXML
	private JFXTextField txtCiudad;
	@FXML
	private JFXTextField txtTelefono;
	@FXML
	private JFXTextField txtCp;
	@FXML
	private JFXTextField txtPais;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextField txtDni;
	@FXML
	private JFXTextField txtCodigo;
	@FXML
	private JFXComboBox<GrupoClientes> cmbGrupoCliente;
	@FXML
	private JFXTextField txtGrupoCliente;
	@FXML
	private JFXButton btnEditar;
	@FXML
	private JFXButton btnGuardar;
	@FXML
	private JFXButton btnCompras;
	@FXML
	private JFXButton btnBuscar;
	@FXML
	private Line lineVentas;
	@FXML
	private HBox tableBox;
	@FXML
	private Label lblHistorico;
	
	private Node node;
	private TableView<Venta> table;
	private QueryManager<Cliente> queryManagerCliente = Cliente.getQueryManager();

	private HiloPeticiones<GrupoClientes> peticionGrupoClientes;
	
	private ArrayList<Venta> ventas;

	@FXML
	private void initialize() {

		QueryManager<GrupoClientes> queryManagerGrupoClientes = GrupoClientes.getQueryManager();
		peticionGrupoClientes = new HiloPeticiones<>(queryManagerGrupoClientes);
		peticionGrupoClientes.run();
		
	}

	public void crearCliente() {
		this.lblHistorico.setVisible(false);
		changeEditableFields(true);
		this.btnGuardar.setVisible(true);
		this.btnEditar.setText("CANCELAR");
		txtNombre.setPromptText("Introduce un nombre");
		initComboBox();
		

	}

	public void cargarCliente(Cliente cliente, Node node) {
		this.node = node;
		if (cliente == null) {
			crearCliente();
			return;
		}

		this.btnGuardar.setVisible(false);
		this.cliente = cliente;
		changeEditableFields(false);
		this.btnEditar.setText("EDITAR");
		atributosCliente();
		crearTablaCompras();

	}

	public void atributosCliente() {
		if(cliente.getApellidos() != null) this.txtApellidos.setText(cliente.getApellidos());
		if(cliente.getDireccion() != null) this.txtDireccion.setText(cliente.getDireccion());
		if(cliente.getTelefono() != null) this.txtTelefono.setText(cliente.getTelefono());
		if(cliente.getCiudad() != null) this.txtCiudad.setText(cliente.getCiudad().toString());
		this.txtEmail.setText(cliente.getEmail());
		if (cliente.getCodigo_postal() != null) this.txtCp.setText(cliente.getCodigo_postal());
		this.txtDni.setText(cliente.getDni());
		this.txtCodigo.setText(cliente.getCodigo());
		if (cliente.getPais() != null) this.txtPais.setText(cliente.getPais());
		this.txtNombre.setText(cliente.getNombre());
		//TODO Sale mal
		for (GrupoClientes grupo:peticionGrupoClientes.getRespuesta())
			if(grupo.getId() == cliente.getGrupo_cliente_id() )
				this.txtGrupoCliente.setText(grupo.getNombre());

	}

	
	public void crearTablaCompras() {
		
		if (this.cliente == null)
			return;		
		RequestBody formBody = new FormBody.Builder().add("cliente_id", this.cliente.getId().toString()).build();
		this.ventas = Venta.getQueryManager().readQuery(formBody, false).getObjectsArray();

		if (ventas != null) {
			
			this.table = new TableView<Venta>();
			this.pane.getChildren().add(this.table);

			TableColumn<Venta, Double> colTotal = new TableColumn<Venta, Double>("Total");
			TableColumn<Venta, String> colFecha = new TableColumn<Venta, String>("Fecha");
			TableColumn<Venta, Boolean> factura = new TableColumn<Venta, Boolean>("Factura");
			
			this.table.getColumns().addAll(colFecha, colTotal, factura);


			colTotal.setMinWidth(100);
			colTotal.setMaxWidth(100);
			colTotal.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
			colTotal.setCellValueFactory(new PropertyValueFactory<Venta, Double>("precio_total"));
			
			colFecha.setMinWidth(150);
			colFecha.setMaxWidth(150);
			colFecha.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Venta, String>, ObservableValue<String>>() {
						@Override
						public ObservableValue<String> call(TableColumn.CellDataFeatures<Venta, String> param) {
							if (param.getValue().getCreated_at() == null)
								return new SimpleStringProperty("No hay fecha");
							return new SimpleStringProperty(param.getValue().getCreated_at());
						}
					});
			factura.setMinWidth(70);
			factura.setMaxWidth(70);
			factura.setCellValueFactory(
					new Callback<TableColumn.CellDataFeatures<Venta, Boolean>, ObservableValue<Boolean>>() {
						@Override
						public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Venta, Boolean> button) {
							return new SimpleBooleanProperty(true);
						}
					});

			factura.setCellFactory(new ColumnButton<Venta, Boolean>("ver",
					new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
				@Override
				public void buttonAction(Venta venta) {

				}
			});

			tableBox.getChildren().add(table);
			table.setEditable(false);
			table.getSelectionModel().setCellSelectionEnabled(true);
			table.setItems(FXCollections.observableArrayList(ventas));
			table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		}else {
			AppManager.print("No hay compras que mostrar");
		}

	}

	public void changeEditableFields(Boolean edit) {
		txtApellidos.setEditable(edit);
		txtDireccion.setEditable(edit);
		txtCiudad.setEditable(edit);
		txtTelefono.setEditable(edit);
		txtCp.setEditable(edit);
		txtPais.setEditable(edit);
		txtEmail.setEditable(edit);
		txtNombre.setEditable(edit);
		txtDni.setEditable(edit);
		cmbGrupoCliente.setVisible(edit);
		txtGrupoCliente.setVisible(!edit);
	}

	/**
	 * Funciones del boton editar/canelar
	 */
	
	public void editarCliente() {

		if (btnEditar.getText().equals("EDITAR")) {

			if (this.cliente == null)
				return;

			this.btnGuardar.setVisible(true);
			this.btnEditar.setText("CANCELAR");
			this.btnEditar.setStyle("-fx-background-color: #ef9a9a");
			
			initComboBox();
			changeEditableFields(true);

		} else {
			if (this.cliente == null) {

				AppManager.getInstance().getAppMain().volver(this.node);
				return;

			}
			this.btnGuardar.setVisible(false);
			this.btnEditar.setText("EDITAR");
			this.btnEditar.setStyle("-fx-background-color: #80cbc4");
			atributosCliente();
			changeEditableFields(false);
		}
	}
	
	//TODO
	public void guardarCliente() {

//		if(!valida()) return;
//		
//		RequestBody formBody = construirBodyProducto();
//
//		Producto producto = null;
//		lblResultado.setTextFill(Paint.valueOf("#54934c"));
//		if (this.producto != null) {			
//			producto = queryManagerProducto.updateForId(this.producto.getId(), formBody).getObject();
//			if (producto != null)
//				lblResultado.setText("Producto actualizado!");
//		} else {
//			producto = queryManagerProducto.insertOne(formBody).getObject();
//			if (producto != null)
//				lblResultado.setText("Producto guardado!");
//		}
//
//		if (producto != null) {
//			if (this.fileImagen != null) {
//				if (queryManagerProducto.uploadFile(this.fileImagen, producto.getId().toString())) {
//					this.producto = (queryManagerProducto.getByPk(producto.getId().toString()).getObject());
//					atributosCliente();
//				} else {
//					AppManager.printError("imagen no guardada");
//				}
//			}
//			this.producto = queryManagerProducto.getByPk(producto.getId().toString()).getObject();
//			editarCliente();
//		} else {
//			lblResultado.setTextFill(Paint.valueOf("#ce2020"));
//			lblResultado.setText("Error en la operacion!");
//		}
//		lblResultado.setVisible(true);
	}
//TODO
	public boolean valida() {
//		boolean valida = checkField(this.txtNombre);
//		valida = checkField(this.txtEan) && valida ;
//		valida = checkField(this.txtReferencia) && valida;
//		valida = checkField(this.txtUnidadMesura) && valida;
//		valida = checkField(this.txtStockMin) && valida;
//		valida = checkField(this.txtPrecio) && valida;
//		valida = checkField(this.cmbGrupoCliente) && valida;
//		valida = checkField(this.cmbTasas) && valida;
//		return valida;
		return false;
	}
	
	/**
	 * De una lista de nodos, deja visibles en pantalla los enviados por parametro
	 * 
	 * @param nodos que se dejaram visibles
	 */
	

	/**
	 * Recupera el valor de los hilos y los carga en los comboBox
	 */
	public void initComboBox() {
		try {
			peticionGrupoClientes.join();

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if(!(this.cmbGrupoCliente.getItems().size() > 0)) {
			initComboBox(this.cmbGrupoCliente, GrupoClientes.class, peticionGrupoClientes.getRespuesta());
		}

		if (this.cliente != null) {
			this.cmbGrupoCliente.getSelectionModel().select(this.cliente.getGrupo_cliente_id());
		}

	}

	@Override
	public void onOk() {
		
	}

	@Override
	public void onCancelar() {
		AppManager.getInstance().getAppMain().volver(this.node);
	}
}
