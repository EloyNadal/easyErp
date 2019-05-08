package com.easyErp.project.controller;

import java.io.IOException;

import com.easyErp.project.model.Proveedor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProveedorController {
	
	@FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtNombre;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtCiudad;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtEmail;
    @FXML private TextField txtCP;
    @FXML private TextField txtPais;
   
    private static String url = "http://localhost:8000/";
    private static String token = "1 T0hyRXJqaW1vd3R4ZnZEVmVpZGZQUTg4MXdkbkwyb2Zpa0Y2MGV2cA=="; 
    
    @FXML void buscarFilm(MouseEvent event) {
    	
    	
    	JsonParser parser = new JsonParser();
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	Integer id = Integer.parseInt(txtId.getText());
    	try {
    		
    		String jsonString = run(url + "proveedor/"+id);
        	
    		
        	JsonObject json = parser.parse(jsonString).getAsJsonObject();
        	Proveedor proveedor = gson.fromJson(json.get("data"), Proveedor.class);
        	
        	txtNombre.setText(proveedor.getNombre());
        	txtDireccion.setText(proveedor.getDireccion());
        	txtCiudad.setText(proveedor.getCiudad());
        	txtTelefono.setText(proveedor.getTelefono());
        	txtEmail.setText(proveedor.getEmail());
        	txtCP.setText(proveedor.getCodigo_postal());
        	txtPais.setText(proveedor.getPais());
        	
		} catch (IOException e) {
			//System.out.println("por aqui");
			e.printStackTrace();
		}
    	

    }
    
    public static String run(String url) throws IOException {
    	OkHttpClient client = new OkHttpClient();
    	  Request request = new Request.Builder()
    			  .header("Authorization", token)
    			  .url(url)
    			  .build();

    	  try (Response response = client.newCall(request).execute()) {
    	    return response.body().string();
    	  }
    	  
    }
}
