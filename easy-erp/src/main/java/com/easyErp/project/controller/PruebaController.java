package com.easyErp.project.controller;

import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.easyErp.project.model.*;

public class PruebaController {

    @FXML private Button btn_id;
    @FXML private TextField txt_id;
    @FXML private Label formatoJson;
    @FXML private Label title;
    
    private static String url = "http://localhost:8000/";
    private static String token = "1 ZnRaYnZjM2piblBUaThJSDlUUlI3eTdFMmpzWFNxcmhuQ2dnTFA4TQ=="; 
    
    @FXML void buscarFilm(MouseEvent event) {
    	
    	
    	JsonParser parser = new JsonParser();
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	Integer id = Integer.parseInt(txt_id.getText());
    	try {
    		
    		String jsonString = run(url + "cliente/"+id);
        	
    		
        	JsonObject json = parser.parse(jsonString).getAsJsonObject();
        	Cliente cliente = gson.fromJson(json.get("data"), Cliente.class);
        	
        	
        	formatoJson.setText(gson.toJson(json.get("data")));
        	
        	title.setText(cliente.getId().toString());
			
			
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
