package com.easyErp.project.model;

import com.easyErp.project.log.EasyErpException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class QueryManager<T> {

	private Class<T> objectClass;
	private AppManager manager;
	private JsonParser parser;
	private Gson gson;
	
	public QueryManager(Class<T> objectClass) {
		this.objectClass = objectClass;
		manager = AppManager.getInstance();
		this.parser = new JsonParser();
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	public T readOneById(String url) {
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder().header("Authorization", manager.getToken()).url(url).build();
		T object = null;
		try {
			Response response = client.newCall(request).execute();
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			object =  gson.fromJson(json.get("data"), objectClass);
			
		} catch (Exception e) {
			new EasyErpException(e.getMessage());
		}
		return object;
	}
}
