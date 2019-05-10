package com.easyErp.project.model;

import com.easyErp.project.log.EasyErpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AppManager {
//	192.168.2.251
	
	public static final MediaType JSON = 
			MediaType.parse("application/json; charset=utf-8");
	
	private static String token;
	private static final String BASE_URL = "http://localhost:8000/";
	private static AppManager manager = new AppManager();
	private static JsonParser parser = new JsonParser();
	
	public static AppManager getInstance() {
		return manager;
	}
	
	public static boolean login(String email, String password) {
		OkHttpClient client = new OkHttpClient();
		RequestBody req = new MultipartBody.Builder()
		        .setType(MultipartBody.FORM)
		        .addFormDataPart("user_name", email)
		        .addFormDataPart("password", password)
		        .build();
  	  	Request request = new Request.Builder()
  			  .url(BASE_URL + "login")
  			  .post(req)
  			  .build();
  	  try {
  		  
  		  Gson gson = new GsonBuilder().setPrettyPrinting().create();
  		  Response response = client.newCall(request).execute();
  		  JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
  		  token = gson.fromJson(json.get("data"), Usuario.class).getApiToken();
  	  }catch(Exception e) {
  		  new EasyErpException(e.getMessage());
  		  return false;
  	  }
  	  return true;
	}
	
	public String getToken() {
		return token;
	}
}

