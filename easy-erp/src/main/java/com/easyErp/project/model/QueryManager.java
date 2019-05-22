package com.easyErp.project.model;

import java.util.ArrayList;
import java.util.Arrays;

import com.easyErp.project.log.EasyErpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class QueryManager<T> {

	private static String token;
	private static String baseUrl = AppManager.getBaseUrl();
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private String url;
	private Class<T> objectClass;
	private static JsonParser parser = new JsonParser();
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Class<T[]> objectClassArray;
	private static OkHttpClient client = new OkHttpClient();
	private Respuesta<T> respuesta;
	protected QueryManager(Class<T> objectClass, Class<T[]> objectClassArray, String url) {
		this.url = baseUrl + url;
		this.objectClassArray = objectClassArray;
		this.objectClass = objectClass;
		this.respuesta = new Respuesta<T>();
	}

	public Respuesta<T> getByPk(String endPoint) {
		Request request = new Request.Builder().header("Authorization", token).url(this.url + endPoint).build();
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else 
				EasyErpException.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		return respuesta;
	}

	public Respuesta<T> readAll() {
		Request request = new Request.Builder().header("Authorization", token).url(this.url).build();
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			T[] array = gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClassArray);
			respuesta.setObjectsArray(new ArrayList<T>(Arrays.asList(array)));
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		return respuesta;
	}

	public Respuesta<T> readQuery(RequestBody body, int metod) {
		Request request = new Request.Builder()
				.header("Authorization", token)
				.url(this.url + metod)
				.post(body)
				.build();
		
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			T[] array = gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClassArray);
			respuesta.setObjectsArray(new ArrayList<T>(Arrays.asList(array)));
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		
		return respuesta;
	}
	
	public Respuesta<T> updateForId(Integer id, RequestBody body) {
		Request request = new Request.Builder()
				.header("Authorization", token)
				.url(this.url + id)
				.put(body)
				.build();
		
		this.respuesta.clear();
		
		try {
			Response response = client.newCall(request).execute();
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else 
				EasyErpException.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		return respuesta;
	}
	
	public Respuesta<T> insertOne(RequestBody body) {
		Request request = new Request.Builder()
				.header("Authorization", token)
				.url(this.url)
				.post(body)
				.build();
		
		this.respuesta.clear();
		
		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.code());
			System.out.println(response.toString());
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else 
				EasyErpException.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		return respuesta;
	}
	

	public Respuesta<T> insertOne(Respuesta<T> respuesta){
		this.respuesta.clear();
//		RequestBody req = new Form.Builder().setType(MultipartBody.FORM).addFormDataPart("user_name", email)
//				.addFormDataPart("password", password).build();
		
		return respuesta;
	}
	
	public static boolean login(String email, String password) {
		RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("user_name", email)
				.addFormDataPart("password", password).build();
		Request request = new Request.Builder().url(baseUrl + "login").post(req).build();
		try {
			Response response = client.newCall(request).execute();
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			token = gson.fromJson(json.get("data"), Usuario.class).getApiToken();
			if(response.isSuccessful()) AppManager.setLogged(true);
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
			return false;
		}
		return true;
	}
}
