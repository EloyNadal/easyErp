package com.easyErp.project.controller;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.easyErp.project.model.Compra;
import com.easyErp.project.model.QueryManager;
import com.easyErp.project.model.Tienda;
import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.ColumnButton;
import com.easyErp.project.utils.PDFController;
import com.easyErp.project.utils.TablaFormaters;
import com.google.gson.Gson;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.util.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;

public class VerEstadoPedidosController {

    @FXML private BorderPane paneEstadoPedidos; 
    @FXML private JFXComboBox<String> cmbEstados;
    @FXML private JFXButton btnFiltrar;
    @FXML private JFXButton btnGuardar;
    @FXML private Pagination paginacion;
    @FXML private JFXDatePicker dateDesde;
    @FXML private JFXDatePicker dateHasta;
    private String[] estados = {"Todos", "creado", "enviado", "recibido", "finalizado"};
    private String[] estadosPedido = {"creado", "enviado", "recibido", "finalizado"};
    private QueryManager<Compra> queryCompras = Compra.getQueryManager();
    private Tienda tienda = Tienda.getQueryManager().getByPk(String.valueOf(AppManager.getIdTienda())).getObject();
    private ArrayList<Compra> compras = buscar();
    private ArrayList<Compra> comprasFiltradas;
    private ArrayList<Compra> comprasEditadas;
    private TableView<Compra> tabla = crearTabla();
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    private final static int FILASPORPAGINA = 9;
         
    public TableView<Compra> crearTabla(){
    	
    	TableView<Compra> tabla = new TableView<Compra>();
    	
    	TableColumn<Compra, String> colId = new TableColumn<Compra, String>("Id");
        TableColumn<Compra, String> colProveedor = new TableColumn<Compra, String>("Proveedor");
        TableColumn<Compra, Double> colPrecioSinTasas = new TableColumn<Compra, Double>("Precio s/IVA");
        TableColumn<Compra, Double> colTasas = new TableColumn<Compra, Double>("IVA");
        TableColumn<Compra, Double> colPrecioTotal = new TableColumn<Compra, Double>("Precio Total");
        TableColumn<Compra, String> colFecha = new TableColumn<Compra, String>("Fecha realización");
        TableColumn<Compra, String> colEstado = new TableColumn<Compra, String>("Estado");
        TableColumn<Compra, Boolean> colPdf = new TableColumn<Compra, Boolean>("PDF");
        
        tabla.getColumns().addAll(colId, colProveedor, colPrecioSinTasas, colTasas, colPrecioTotal, colFecha, colEstado, colPdf);
        
        colId.setMinWidth(50);
		colId.setMaxWidth(70);
		colId.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Compra, String> param) {
						return new SimpleObjectProperty<>(param.getValue().getId().toString());
					}
				});
        
        colProveedor.setMinWidth(100);
		colProveedor.setMaxWidth(300);
		colProveedor.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Compra, String> param) {
						return new SimpleObjectProperty<>(param.getValue().getProveedor().getNombre());
					}
				});
		
		colPrecioSinTasas.setMinWidth(100);
		colPrecioSinTasas.setMaxWidth(200);
		colPrecioSinTasas.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, Double>, ObservableValue<Double>>() { 
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<Compra, Double> param) {
						return new SimpleObjectProperty<>(param.getValue().getPrecio_sin_tasas());
					}
				});
		colPrecioSinTasas.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
		
		colTasas.setMinWidth(100);
		colTasas.setMaxWidth(200);
		colTasas.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, Double>, ObservableValue<Double>>() { 
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<Compra, Double> param) {
						return new SimpleObjectProperty<>(param.getValue().getTotal_tasas());
					}
				});
		colTasas.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
		
		colPrecioTotal.setMinWidth(100);
		colPrecioTotal.setMaxWidth(200);
		colPrecioTotal.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, Double>, ObservableValue<Double>>() { 
					@Override
					public ObservableValue<Double> call(TableColumn.CellDataFeatures<Compra, Double> param) {
						return new SimpleObjectProperty<>(param.getValue().getPrecio_total());
					}
				});
		colPrecioTotal.setCellFactory(TextFieldTableCell.forTableColumn(TablaFormaters.getModedaFormatter()));
		
		colFecha.setMinWidth(100);
		colFecha.setMaxWidth(300);
		colFecha.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Compra, String> param) {
						if (param.getValue().getCreated_at() != null) {
							return new SimpleObjectProperty<>(param.getValue().getCreated_at());
						}
						return new SimpleObjectProperty<>("error de fecha");
					}
				});
				
		colEstado.setMinWidth(90);
		colEstado.setMaxWidth(200);
		colEstado.setCellValueFactory(
				new Callback<TableColumn.CellDataFeatures<Compra, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TableColumn.CellDataFeatures<Compra, String> param) {
						return new SimpleObjectProperty<>(param.getValue().getEstado());
					}
				});
		
			colEstado.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(this.estadosPedido)));
			colEstado.setOnEditCommit((CellEditEvent<Compra, String> event) -> {

	            for (Compra compra : compras) {
					if (compra.getId() == event.getRowValue().getId()) {
						compra.setEstado(event.getNewValue());
						
						if (comprasEditadas == null) {
							comprasEditadas = new ArrayList<Compra>();
						}
						comprasEditadas.add(compra);
					}
				}
	            
	        });	

		colPdf.setMinWidth(40);
		colPdf.setMaxWidth(80);
		colPdf.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Compra, Boolean>, ObservableValue<Boolean>>() {
			@Override
			public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Compra, Boolean> button) {
				return new SimpleBooleanProperty(true);
			}
		});
		
		colPdf.setCellFactory(new ColumnButton<Compra, Boolean>( "ver", new Image(getClass().getResourceAsStream("/image/pdficon.png"))) {
			@Override
			public void buttonAction(Compra compra) {
				generarPdf(compra);
			}
		});
		
		
		tabla.setEditable(false);
		tabla.getSelectionModel().getSelectedItem();
		tabla.getSelectionModel().setCellSelectionEnabled(true);
		tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    	return tabla;
    }
    
    public ArrayList<Compra> buscar(){
    	
    	RequestBody body = new FormBody.Builder().
    			add("tienda_id", String.valueOf(AppManager.getIdTienda()))
    			.build();
    	
    	return queryCompras.readQuery(body, false).getObjectsArray();
    }
    
    public ArrayList<Compra> filtrarProductos(){
    	
    	ArrayList<Compra> p = new ArrayList<>(this.compras);
    	
    	for (Compra compra: this.compras) {
			
    		if (!cmbEstados.getValue().equals("Todos") && !cmbEstados.getValue().equalsIgnoreCase(compra.getEstado())) {
    			p.remove(compra);
    		}
    		if (compra.getCreated_at() == null || (dateHasta.getValue() != null
					&& LocalDate.parse(compra.getCreated_at(), formatter).isAfter(dateHasta.getValue()))) {
				p.remove(compra);
			}
			if (compra.getCreated_at() == null ||  (dateDesde.getValue() != null
					&& LocalDate.parse(compra.getCreated_at(), formatter).isBefore(dateDesde.getValue()))) {
				p.remove(compra);
			}
		}
    	return p;
    }
    
    
    @FXML
    private void initialize() { 
    	cmbEstados.setItems(FXCollections.observableArrayList(estados));
    	cmbEstados.getSelectionModel().selectFirst();
		this.paginacion.setPageCount(this.compras.size() != 0?
				(int) Math.ceil((double)this.compras.size()/(double)FILASPORPAGINA):1);
		this.paginacion.setPageFactory(this::crearPagina);
    }
    
    private Node crearPagina(int paginaIndice) {

    	int desdeIndice = paginaIndice * FILASPORPAGINA;
    	int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, this.compras.size());
    	tabla.setItems(FXCollections.observableArrayList(this.compras.subList(desdeIndice, hastaIndice)));
    	tabla.setEditable(true);
    	return tabla;
    }
    
    private Node recargarPagina(int paginaIndice) {

    	int desdeIndice = paginaIndice * FILASPORPAGINA;
    	int hastaIndice = Math.min(desdeIndice + FILASPORPAGINA, this.comprasFiltradas.size());
    	tabla.setItems(FXCollections.observableArrayList(this.comprasFiltradas.subList(desdeIndice, hastaIndice)));
    	return tabla;
    }
    
    
    @FXML
    void filtrar() {
    	this.comprasFiltradas = filtrarProductos();
    	this.paginacion.setPageCount(this.comprasFiltradas.size() != 0?
    			(int) Math.ceil(((double)this.comprasFiltradas.size()/(double)FILASPORPAGINA)):1);
    	this.paginacion.setPageFactory(this::recargarPagina);
    }
	
	
	public boolean generarPdf(Compra compra) {
		
		Gson gson = new Gson();
		String json = gson.toJson(compra);
		Map<String, Object> params = (Map<String, Object>) anadirParametros(compra);
		
		if(PDFController.crearPdf(json, params)) {
			File file = openFileChooser("Guardar PDF", false);
			PDFController.guardarPdf(file.getAbsolutePath());
		}
		return true;
	}

	private Map<String, Object> anadirParametros(Compra compra) {
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("proveedorNombre", compra.getProveedor().getNombre());
		params.put("proveedorCiudad", compra.getProveedor().getCiudad());
		params.put("proveedorCP", compra.getProveedor().getCodigo_postal());
		params.put("proveedorDireccion", compra.getProveedor().getDireccion());
		params.put("proveedorTelefono", compra.getProveedor().getTelefono());
		params.put("proveedorEmail", compra.getProveedor().getEmail());
		
		params.put("tiendaNombre", tienda.getNombre());
		params.put("tiendaCiudad", tienda.getCiudad());
		params.put("tiendaCP", tienda.getCodigo_postal());
		params.put("tiendaDireccion", tienda.getDireccion());
		params.put("tiendaTelefono", tienda.getTelefono());
		params.put("tiendaEmail", tienda.getEmail());
		
		params.put("compra_id", compra.getId().toString());
		params.put("precio_sin_tasas", compra.getPrecio_sin_tasas().toString());
		params.put("created_at", compra.getCreated_at());
		
		return params;
	}
	
	public File openFileChooser(String title, boolean open) {

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle(title);
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF", "*.pdf"));
		File file;
		if (open)
			file = fileChooser.showOpenDialog(AppManager.getInstance().getStage());
		else
			file = fileChooser.showSaveDialog(AppManager.getInstance().getStage());
		return file;
	}
	@FXML
	public void guardarEstado() {
		
		for (Compra compra : comprasEditadas) {
			
			String estado = (compra.getEstado() != null) ? compra.getEstado() : "creado"; 
			
			RequestBody body = new FormBody.Builder()
					.add("estado", estado).build();
			
			queryCompras.updateForId(compra.getId(), body);
		}
		comprasEditadas.clear();
		AppManager.showInfo("Estados guardados");
		filtrar();
	}
}

	