package com.easyErp.project.controller;

import java.util.ArrayList;

import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.GrupoClientes;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.ColumnButton;
import com.easyErp.project.utils.ExportData;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;

public class VerClientesController implements BaseController{
	@FXML
	private BorderPane vistaFormClientes;
	private TableView<Cliente> clientesTable = crearTabla();
	@FXML
	private JFXComboBox<GrupoClientes> cmbGrupoCliente;
	@FXML
	private JFXComboBox<String> cmbCiudad;
	@FXML
	private JFXComboBox<String> cmbPais;
	@FXML
	private JFXTextField txtNombre;
	@FXML
	private JFXTextField txtEmail;
	@FXML
	private JFXTextField txtApellidos;
	@FXML
	private JFXTextField txtDNI;
	@FXML
	private JFXButton btnFiltrar;
	@FXML
	private JFXButton btnQuitarFiltro;
	@FXML
	private Button btnExc;
	@FXML
	private Pagination paginacion;
	
	private ArrayList<Cliente> clientes;
	private ArrayList<Cliente> clientesFiltrados;
	private ArrayList<String> paises;
	private ArrayList<String> ciudades;
	
	private final static int FILASPORPAGINA = 12;

	@FXML
	private void initialize() {
		QueryManager<Cliente> queryManager = Cliente.getQueryManager();
		this.clientes = queryManager.readAll().getObjectsArray();
		QueryManager<GrupoClientes> queryManagerGrupoCliente = GrupoClientes.getQueryManager();
		ArrayList<GrupoClientes> grupos = queryManagerGrupoCliente.readAll().getObjectsArray();
		this.ciudades = new ArrayList<String>();
		for(Cliente cliente:this.clientes) {
			if(!this.ciudades.contains(cliente.getCiudad()))this.ciudades.add(cliente.getCiudad());
		}
		this.paises = new ArrayList<String>();
		for(Cliente cliente:this.clientes) {
			if(!this.paises.contains(cliente.getPais()))this.paises.add(cliente.getPais());
		}
		initComboBox(cmbGrupoCliente, GrupoClientes.class, grupos);
		initComboBox(cmbCiudad, String.class, ciudades);
		initComboBox(cmbPais, String.class, paises);

		this.paginacion.setPageCount(this.clientes.size() != 0?
				(int) Math.ceil((double) this.clientes.size() / (double) FILASPORPAGINA):1);
		this.paginacion.setPageFactory(this::crearPagina);
		
		ImageView i = new ImageView(new Image(getClass().getResourceAsStream("/image/excelIcon.png")));
		i.setFitHeight(25);
		i.setFitWidth(25);
		btnExc.setGraphic(i);
		btnExc.setText(null);
	}

	private Node crearPagina(int paginaIndice) {

		int desdeIndice = paginaIndice * FILASPORPAGINA;
		int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, this.clientes.size());
		clientesTable.setItems(FXCollections.observableArrayList(this.clientes.subList(desdeIndice, hastaIndice)));
		clientesTable.setEditable(true);
		return clientesTable;
	}

	private Node recargarPagina(int paginaIndice) {

		int desdeIndice = paginaIndice * FILASPORPAGINA;
		int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, clientesFiltrados.size());
		clientesTable.setItems(FXCollections.observableArrayList(clientesFiltrados.subList(desdeIndice, hastaIndice)));
		return clientesTable;
	}

	private TableView<Cliente> crearTabla() {
		
		TableView<Cliente> tabla = new TableView<Cliente>();
		TableColumn<Cliente, Number> id = new TableColumn<Cliente, Number>("ID");
		TableColumn<Cliente, String> nombre = new TableColumn<Cliente, String>("Nombre");
		TableColumn<Cliente, String> apellidos = new TableColumn<Cliente, String>("Apellidos");
		TableColumn<Cliente, String> direccion = new TableColumn<Cliente, String>("Dirección");
		TableColumn<Cliente, String> telefono = new TableColumn<Cliente, String>("Teléfono");
		TableColumn<Cliente, String> ciudad = new TableColumn<Cliente, String>("Ciudad");
		TableColumn<Cliente, String> pais = new TableColumn<Cliente, String>("País");
		TableColumn<Cliente, String> email = new TableColumn<Cliente, String>("Email");
		TableColumn<Cliente, String> dni = new TableColumn<Cliente, String>("DNI");
		TableColumn<Cliente, Boolean> ver = new TableColumn<Cliente, Boolean>("Ver");
		
		nombre.setMinWidth(90);
		nombre.setMaxWidth(120);
		nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));

		apellidos.setMinWidth(80);
		apellidos.setMaxWidth(200);
		apellidos.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellidos"));

		direccion.setMinWidth(90);
		direccion.setMaxWidth(200);
		direccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));

		telefono.setMinWidth(90);
		telefono.setMaxWidth(120);
		telefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));

		ciudad.setMinWidth(80);
		ciudad.setMaxWidth(140);
		ciudad.setCellValueFactory(new PropertyValueFactory<Cliente, String>("ciudad"));

		pais.setMinWidth(50);
		pais.setMaxWidth(145);
		pais.setCellValueFactory(new PropertyValueFactory<Cliente, String>("pais"));

		id.setMinWidth(40);
		id.setMaxWidth(50);
		id.setCellValueFactory(new PropertyValueFactory<Cliente, Number>("id"));

		email.setMinWidth(50);
		email.setMaxWidth(170);
		email.setCellValueFactory(new PropertyValueFactory<Cliente, String>("email"));

		dni.setMinWidth(50);
		dni.setMaxWidth(90);
		dni.setCellValueFactory(new PropertyValueFactory<Cliente, String>("dni"));

		ver.setMinWidth(40);
		ver.setMaxWidth(80);
		ver.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Cliente, Boolean>, ObservableValue<Boolean>>() {
					@Override
					public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Cliente, Boolean> button) {
						return new SimpleBooleanProperty(true);
					}
				});

		ver.setCellFactory(new ColumnButton<Cliente, Boolean>("ver",
				new Image(getClass().getResourceAsStream("/image/view-details.png"))) {
			@Override
			public void buttonAction(Cliente cliente) {
				AppManager.getInstance().getAppMain().editarCliente(cliente);
			}
		});
		tabla.getColumns().addAll(id, nombre, apellidos, direccion, telefono, ciudad, pais, email, dni, ver);
		tabla.setEditable(false);
		tabla.getSelectionModel().getSelectedItem();
		tabla.getSelectionModel().setCellSelectionEnabled(true);
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		return tabla;
	}

	

	@FXML
	private void filtrar() {
		this.clientesFiltrados = filtrarClientes();
		this.paginacion.setPageCount(this.clientesFiltrados.size() != 0?
				(int) Math.ceil(((double)this.clientesFiltrados.size()/(double)FILASPORPAGINA)):1);
    	this.paginacion.setPageFactory(this::recargarPagina);
	}
	
	private ArrayList<Cliente> filtrarClientes(){
		ArrayList<Cliente> copiaClientes = new ArrayList<Cliente>();
		Cliente cliente = new Cliente();
		cliente.setGrupo_cliente_id(this.cmbGrupoCliente.getValue() != null ? this.cmbGrupoCliente.getValue().getId() : null);
		cliente.setEmail(checkNotEmpty(this.txtEmail.getText()) ? this.txtEmail.getText() : "");
		cliente.setApellidos(checkNotEmpty(this.txtApellidos.getText()) ? this.txtApellidos.getText() : "");
		cliente.setNombre(checkNotEmpty(this.txtNombre.getText()) ? this.txtNombre.getText() : "");
		cliente.setCiudad(this.cmbCiudad.getValue() != null ? this.cmbCiudad.getValue() : null);
		cliente.setPais(this.cmbPais.getValue() != null ? this.cmbPais.getValue() : null);
		cliente.setDni(checkNotEmpty(this.txtDNI.getText()) ? this.txtDNI.getText() : "");
		for (Cliente c : this.clientes) {
			if (checkClientes(cliente, c))
				copiaClientes.add(c);
		}
		return copiaClientes;
	}
	private boolean checkClientes(Cliente cliente, Cliente c) {
		if (cliente.getGrupo_cliente_id() != null && cliente.getGrupo_cliente_id() != c.getGrupo_cliente_id())
			return false;
		if (!c.getEmail().toLowerCase().contains(cliente.getEmail().toLowerCase()))
			return false;
		if (!c.getNombre().toLowerCase().contains(cliente.getNombre().toLowerCase()))
			return false;
		if (!c.getApellidos().toLowerCase().contains(cliente.getApellidos().toLowerCase()))
			return false;
		if (cliente.getCiudad() != null && !c.getCiudad().toLowerCase().contains(cliente.getCiudad().toLowerCase()))
			return false;
		if (cliente.getPais() != null && !c.getPais().contains(cliente.getPais().toLowerCase()))
			return false;
		if (!c.getDni().toLowerCase().contains(cliente.getDni().toLowerCase()))
			return false;
		return true;
	}

	private boolean checkNotEmpty(String string) {
		if (string.equals("")) {
			return false;
		}
		return true;
	}

	public void newProducto() {
		AppManager.getInstance().getAppMain().editarCliente(null);
	}

	@Override
	public void onOk() {
		filtrar();
	}

	@FXML
	@Override
	public void onCancelar() {
		this.cmbPais.getSelectionModel().selectFirst();
		this.cmbGrupoCliente.getSelectionModel().selectFirst();
		this.txtEmail.setText("");
		this.txtApellidos.setText("");
		this.txtNombre.setText("");
		this.cmbCiudad.getSelectionModel().selectFirst();
		this.txtDNI.setText("");
		this.paginacion.setPageCount(this.clientes.size() != 0?
				(int) Math.ceil((double) this.clientes.size() / (double) FILASPORPAGINA):1);
		this.paginacion.setPageFactory(this::crearPagina);
	}
	
	@FXML
	private void imprimirResultados() {
			ExportData.crearArchivoExcel(this.clientesTable);
	}
	
	
}
