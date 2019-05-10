package com.easyErp.project.controller;

import com.easyErp.project.model.AppManager;
import com.easyErp.project.model.Cliente;
import com.easyErp.project.model.QueryManager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PruebaController {
	private static AppManager manager;
    @FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtCodigo;
    @FXML private TextField txtPais;
    @FXML private TextField txtEmail;
    @FXML private TextField txtDni;
    
    private static String url = "http://localhost:8000/";
    
    @FXML 
    public void initialize() {
    	manager = AppManager.getInstance();
    	
    }
    @FXML void buscarFilm(MouseEvent event) {
    	QueryManager<Cliente> queryManager = new QueryManager<Cliente>(Cliente.class);
    	Integer id = Integer.parseInt(txtId.getText());
    	String jsonString = url + "cliente/"+id;
    	Cliente cliente = queryManager.readOneById(jsonString);
//    	JsonParser parser = new JsonParser();
//    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
//    	Integer id = Integer.parseInt(txtId.getText());
//    	try {
//    		
//    		String jsonString = run(url + "cliente/"+id);
//        	
//    		
//        	JsonObject json = parser.parse(jsonString).getAsJsonObject();
//        	Cliente cliente = gson.fromJson(json.get("data"), Cliente.class);
        	
        	txtNombre.setText(cliente.getNombre());
        	txtApellido.setText(cliente.getApellidos());
        	txtDireccion.setText(cliente.getDireccion());
        	txtCiudad.setText(cliente.getCiudad());
        	txtTelefono.setText(cliente.getTelefono());
        	txtCodigo.setText(cliente.getCodigo_postal());
        	txtPais.setText(cliente.getPais());
        	txtEmail.setText(cliente.getEmail());
        	txtDni.setText(cliente.getDni());
			
			
//		} catch (Exception e) {
//			new EasyErpException(e.getMessage());
//		}
//    	

    }
    
    public static String run(String url) throws Exception {
    	OkHttpClient client = new OkHttpClient();
    	  Request request = new Request.Builder()
    			  .header("Authorization", manager.getToken())
    			  .url(url)
    			  .build();

    	  try (Response response = client.newCall(request).execute()) {
    	    return response.body().string();
    	  }
    	  
    }

}
