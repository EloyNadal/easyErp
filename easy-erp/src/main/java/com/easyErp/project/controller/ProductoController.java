package com.easyErp.project.controller;

import java.io.IOException;
import java.util.ArrayList;

import com.easyErp.project.model.Producto;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ProductoController {
	
	@FXML private Button btnSearch;
    @FXML private TextField txtId;
    @FXML private TextField txtCategoria;
    @FXML private TextField txtEan;
    @FXML private TextField txtReferencia;
    @FXML private TextField txtAtributo;
    @FXML private TextField txtValor;
    @FXML private TextField txtNombre;
    @FXML private TextField txtUnidad;
    @FXML private TextField txtPrecio;
    @FXML private TextField txtTasa;
    @FXML private TextField txtStock;
    @FXML private TextField txtFabricante;
    @FXML private TextField txtImagen;
    @FXML private CheckBox chbActivo;
   
    private static String url = "http://192.168.2.251:8000/";
    private static String token = "1 VkNLd3NFaEZzTHk4ZmdxRHhqZlVWRmQwRzN4Y1NjNEtUQUpGWEx6Zg=="; 
    
    @FXML void buscarFilm(MouseEvent event) {
    	
    	
    	JsonParser parser = new JsonParser();
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	Integer id = Integer.parseInt(txtId.getText());
    	try {
    		
    		String jsonString = run(url + "producto/id=" + id);
        	
    		
        	JsonObject json = parser.parse(jsonString).getAsJsonObject();
        	System.out.println(json.get("data"));
        	Producto producto = gson.fromJson(json.get("data"), Producto[].class)[0];
        	
        	txtCategoria.setText(producto.getCategoria_id().toString());
        	txtEan.setText(producto.getEan13());
        	txtReferencia.setText(producto.getReferencia());
        	txtAtributo.setText(producto.getAtributo());
        	txtValor.setText(producto.getAtributo_valor());
        	txtNombre.setText(producto.getNombre());
        	txtUnidad.setText(producto.getUnidad_mesura());
        	txtPrecio.setText(producto.getPrecio().toString());
        	txtTasa.setText(producto.getTasa_id().toString());
        	txtStock.setText(producto.getStock_minimo().toString());
        	txtFabricante.setText(producto.getFabricante());
        	txtImagen.setText(producto.getImagen());
        	chbActivo.setSelected(producto.isActivo());     	
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
