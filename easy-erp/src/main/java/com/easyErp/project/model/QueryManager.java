package com.easyErp.project.model;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyErp.project.utils.AppManager;
import com.easyErp.project.utils.Encrypt;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * Clase genérica que se encarga de hacer las peticiones al WebService
 * 
 * @param <T> CLase perteneciente al modelo de la base de datos
 */
public class QueryManager<T> {
	//Guardamos esta variable al inicio de la conexión ya que la necesitaremos para autenticarnos con cada petición
	private static String token; 
	//Se obtiene del fichero de configuración, es la ruta  y puerto de acceso al servidor
	private static String baseUrl = AppManager.getBaseUrl();
	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private String url;
	private Class<T> objectClass;
	private static JsonParser parser = new JsonParser();
	private static Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private Class<T[]> objectClassArray;
	private static OkHttpClient client = new OkHttpClient();
	private Respuesta<T> respuesta;
	/**
	 * Constructor de la clase
	 * @param objectClass clase propietaria del objeto de conexión
	 * @param objectClassArray clase array del objeto de conexion
	 * @param url destino de la peticion
	 */
	protected QueryManager(Class<T> objectClass, Class<T[]> objectClassArray, String url) {
		this.url = baseUrl + url;
		this.objectClassArray = objectClassArray;
		this.objectClass = objectClass;
		this.respuesta = new Respuesta<T>();
		Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);
	}

	/**
	 * Método para realizar la petición de un objeto al WebService dado su id
	 * @param endPoint string con el id del objeto(la dirección de la llamada apunta a la tabla donde se encuentra)
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y un objeto de la clase <T> en caso de error el objeto será nulo
	 */
	public Respuesta<T> getByPk(String endPoint) {
		Request request = new Request.Builder().header("Authorization", token).url(this.url + endPoint).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			respuesta.setSuccessful(response.code());
			AppManager.printConnection(response.toString());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else
				AppManager.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}

	/**
	 * Método para realizar la petición de todos los objetos de una tabla al WebService 
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y una lista de objetos de la clase <T>
	 */
	public Respuesta<T> readAll() {
		Request request = new Request.Builder().header("Authorization", token).url(this.url).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			if(!response.isSuccessful()) return respuesta;
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
			T[] array = gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClassArray);
			respuesta.setObjectsArray(new ArrayList<T>(Arrays.asList(array)));
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}
	/**
	 * Método para realizar la petición de los objetos que cumplen los parámtros body de una tabla al WebService 
	 * @param body parametro de entrada conteniendo los parámetros a tener en cuenta en la consulta
	 * @param metod indica si la consulta se realiza con un OR en caso de existir más de un parámetro en el body
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y una lista de objetos de la clase <T> encontrados
	 */
	public Respuesta<T> readQuery(RequestBody body, boolean metod) {
		Request request = new Request.Builder().header("Authorization", token).url(this.url + (metod? "1":"0")).post(body).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			if(!response.isSuccessful()) return respuesta;
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
			T[] array = gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClassArray);
			respuesta.setObjectsArray(new ArrayList<T>(Arrays.asList(array)));
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}

		return respuesta;
	}

	/**
	 * Método para actualizar un objeto dado su id
	 * @param id identificador del objeto en la tabla destino
	 * @param body nuevos datos para el objeto
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y el objeto modificado en caso de error este será nulo
	 */
	public Respuesta<T> updateForId(Integer id, RequestBody body) {
		
		Request request = new Request.Builder().header("Authorization", token).url(this.url + id).put(body).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();

		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else
				AppManager.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}
	
	/**
	 * @see #updateForId(Integer id, RequestBody body)
	 * @param id identificador del objeto en la tabla destino
	 * @param object objeto con los nuevos datos 
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y el objeto modificado en caso de error este será nulo
	 */
	public Respuesta<T> updateForId(Integer id, T object) {
		String jason = gson.toJson(object);
		Request request = new Request.Builder().header("Authorization", token).url(this.url + id).
				put(RequestBody.create(JSON, jason)).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();

		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
				AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
			} else
				AppManager.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}
	
//	public Respuesta<T> insertOne(RequestBody body) {
//		Request request = new Request.Builder().header("Authorization", token).url(this.url).post(body).build();
//		AppManager.printConnection(request.toString());
//		this.respuesta.clear();
//
//		try {
//			Response response = client.newCall(request).execute();
//			AppManager.printConnection(response.toString());
//			respuesta.setSuccessful(response.code());
//			if (respuesta.isSuccessful()) {
//				JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
//				AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
//				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClass));
//			} else
//				AppManager.createExceptionFromErrorCode(response.code());
//		} catch (Exception e) {
//			AppManager.printError(e.getMessage());
//		}
//		return respuesta;
//	}

	/**
	 * Método para insertar un nuevo objeto en una tabla
	 * @param object objeto a insertar 
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y el objeto en caso de error este será nulo
	 */
	public Respuesta<T> insertOne(T object) {
		String json = gson.toJson(object);
		Request request = new Request.Builder().header("Authorization", token).url(this.url)
				.post(RequestBody.create(JSON, json)).build();
		AppManager.printConnection(json);
		this.respuesta.clear();

		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			respuesta.setSuccessful(response.code());
			if (respuesta.isSuccessful()) {
				JsonObject jason = parser.parse(response.body().string()).getAsJsonObject();
				AppManager.printConnection(Encrypt.getDecrypted(jason.get("data").getAsString()));
				respuesta.setObject(gson.fromJson(Encrypt.getDecrypted(jason.get("data").getAsString()), objectClass));
			} else
				AppManager.createExceptionFromErrorCode(response.code());
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}

	/**
	 * Método para recuperar una lista de objetos dadas una fecha de inicio y fin
	 * @param headers objeto que contiene las fechas de inicio y fin
	 * @return Respuesta<T> respuesta contiene el resultado de la operación y una lista de objetos de la clase <T> encontrados
	 */
	public Respuesta<T> readWithDate(Headers headers) {
		Request request = new Request.Builder().header("Authorization", token).headers(headers).url(this.url).build();
		AppManager.printConnection(request.toString());
		this.respuesta.clear();
		try {
			Response response = client.newCall(request).execute();
			AppManager.printConnection(response.toString());
			if(!response.isSuccessful()) return respuesta;
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			T[] array = gson.fromJson(Encrypt.getDecrypted(json.get("data").getAsString()), objectClassArray);
			AppManager.printConnection(Encrypt.getDecrypted(json.get("data").getAsString()));
			respuesta.setObjectsArray(new ArrayList<T>(Arrays.asList(array)));
		} catch (Exception e) {
			AppManager.printError(e.getMessage());
		}
		return respuesta;
	}

	/**
	 * Método para enviar un fichero al WebService para su posterior almacenamiento
	 * @param file fichero a enviar
	 * @param id objeto de la tabla a la que se enlazará el fichero
	 * @return cierto si la operación es realizada, falso en caso de error
	 */
	public Boolean uploadFile(File file, String id) {
		try {
			String url = baseUrl + "upimage";
			RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
					.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file))
					.addFormDataPart("id", id).build();
			Request request = new Request.Builder().url(url).post(requestBody).build();
			Response response = client.newCall(request).execute();
			if (response.isSuccessful())
				return true;
		} catch (Exception e) {
			AppManager.print(e.getMessage());
		}
		return false;
	}

	/**
	 * Método para realizar la autenticación de un usuario contra el WebService
	 * @param email email del usuario
	 * @param password password del usuario
	 * @return cierto si la autenticación es correcta, falso en caso de error
	 */
	public static boolean login(String email, String password) {
		RequestBody req = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("user_name", email)
				.addFormDataPart("password", password).build();
		Request request = new Request.Builder().url(baseUrl + "login").post(req).build();
		Response response = null;
		try {
			response = client.newCall(request).execute();
			JsonObject json = parser.parse(response.body().string()).getAsJsonObject();
			Usuario user = gson.fromJson(json.get("data"), Usuario.class);
			token = Encrypt.getEncrypted(user.getApiToken());
			AppManager.setSessionUser(user.getId());
			if (response.isSuccessful())
				AppManager.setLogged(true);
		} catch (Exception e) {
			if (null == response)
				AppManager.showError("No hay respuesta del servidor, compruebe conexion");
			return false;
		}
		return true;
	}

}
