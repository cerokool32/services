package com.lds.exito.ic.client.jenkins;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lds.exito.ic.model.jenkins.Build;
import com.lds.exito.ic.model.jenkins.BuildDetail;
import com.lds.exito.ic.model.jenkins.Hudson;
import com.lds.exito.ic.model.jenkins.Job;

/**
 * Cliente para consumo de operaciones de jenkins remotamente
 * @author marlon
 *
 */
public class JenkinsRestClient {

	private static Logger log = LoggerFactory.getLogger(JenkinsRestClient.class);

	// auth data
	private boolean requireAuth;
	private String user;
	private String pass;
	private String token;

	// jenkins data
	private String jenkinsUrl;
	private String jsonFormatApi = "/api/json";
	private String xmlFormatApi = "/api/xml";
	private String job_tag = "/job/";

	// jenkins actions
	private static String BUILD = "/build";
	private static String BUILD_WITH_TOKEN = "/build?token=";
	private static String CONFIGURE = "/configure";

	// digester
	private static Gson gson = new GsonBuilder().create();

	// internal data
	private String httpResponseBody;

	// internal constants
	private static String DEFAULT_ENCODING = "UTF-8";
	private static int HTTP_200 = 200;
	private static int HTTP_300 = 300;
	private int buildValidationThreshold = 1000;

	/**
	 * Construcción de cliente sin credenciales
	 * @param jenkinsUrl
	 */
	public JenkinsRestClient(String jenkinsUrl) {
		requireAuth = false;
		this.jenkinsUrl = jenkinsUrl;
	}

	/**
	 * Construcción de cliente con credenciales y token de seguridad para compilación de proyectos
	 * @param jenkinsUrl URL a jenkins CI
	 * @param user Usuario con permisos de compilación
	 * @param pass Password 
	 * @param token Token de seguridad para compilación. Debe estar configurado en cada proyecto
	 */
	public JenkinsRestClient(String jenkinsUrl, String user, String pass, String token) {
		requireAuth = true;
		this.jenkinsUrl = jenkinsUrl;
		this.user = user;
		this.pass = pass;
		this.token = token;
	}

	/**
	 * Obtiene el set de proyectos registrados en Jenkins CI
	 * @return Set de nombres de proyectos suministrados pro Jenkins CI
	 */
	public Set<String> getJobSet() {
		Set<String> response = new HashSet<String>();
		if (httpGetRequest(jenkinsUrl + jsonFormatApi)) {
			Hudson hudson = gson.fromJson(httpResponseBody, Hudson.class);
			for (Job job : hudson.getJobs()) {
				response.add(job.getName());
			}
		}
		log.debug("Obtenidos {} componentes", response.size());
		return response;
	}

	/**
	 * Obtiene un proyecto especifico, identificado por el nombre.
	 * @param jobName Nombre del proyecto, tal cual esta registrado en Jenkins CI
	 * @return Objeto de trabajo (proyecto + builds)
	 */
	public Job getJob(String jobName) {
		Job response = null;
		log.debug("Resolviendo componente {}", jobName);
		if (httpGetRequest(jenkinsUrl + job_tag + jobName + jsonFormatApi)) {
			response = gson.fromJson(httpResponseBody, Job.class);
			log.debug("Resolviendo historia del componente");
			for (Build build : response.getBuilds()) {
				if (httpGetRequest(build.getUrl() + jsonFormatApi)) {
					build.setDetail(gson.fromJson(httpResponseBody, BuildDetail.class));
				}
			}
			if (response.getLastBuild() != null) {
				if (httpGetRequest(response.getLastBuild().getUrl() + jsonFormatApi)) {
					response.getLastBuild().setDetail(gson.fromJson(httpResponseBody, BuildDetail.class));
				}
			}
			if (response.getLastCompletedBuild() != null) {
				if (httpGetRequest(response.getLastCompletedBuild().getUrl() + jsonFormatApi)) {
					response.getLastCompletedBuild().setDetail(gson.fromJson(httpResponseBody, BuildDetail.class));
				}
			}
			if (response.getLastStableBuild() != null) {
				if (httpGetRequest(response.getLastStableBuild().getUrl() + jsonFormatApi)) {
					response.getLastStableBuild().setDetail(gson.fromJson(httpResponseBody, BuildDetail.class));
				}
			}
			if (response.getLastSuccessfulBuild() != null) {
				if (httpGetRequest(response.getLastSuccessfulBuild().getUrl() + jsonFormatApi)) {
					response.getLastSuccessfulBuild().setDetail(gson.fromJson(httpResponseBody, BuildDetail.class));
				}
			}
		}
		return response;
	}

	/**
	 * Lanza la construcción de un proyecto
	 * @param Nombre del proyecto, tal cual esta registrado en Jenkins CI
	 * @return Objeto de detalle de la compilación
	 */
	public BuildDetail buildJob(String jobName) {
		BuildDetail response = null;
		Job job = getJob(jobName);
		response = buildJob(job);
		return response;
	}

	/**
	 * Lanza la construcción de un proyecto
	 * @param job Objeto de trabajo, tal cual esta registrado en Jenkins CI
	 * @return Objeto de detalle de la compilación
	 */
	public BuildDetail buildJob(Job job){
		BuildDetail response = null;
		if(job != null){
			if(job.isBuildable()){
				response = launchJobbuild(job);
			} else {
				log.debug("El componente no esta activo, no se puede compilar");
			}
		} else{
			log.debug("Componente no existe, no se puede compilar");
		}
		return response;
	}

	/**
	 * Lógica especifica de compilación, sin reglas de negocio con respecto a las precondiciones de compilación.
	 * @param job Objeto de trabajo, tal cual esta registrado en Jenkins CI
	 * @return Objeto de detalle de la compilación
	 */
	private BuildDetail launchJobbuild(Job job) {
		BuildDetail response = null;
		int futureBuild = job.getNextBuildNumber();
		log.trace("se inicia con el proceso de compilación #{}", futureBuild);
		String buildUrl = job.getUrl();
		if (requireAuth) {
			buildUrl += BUILD_WITH_TOKEN + token;
		} else {
			buildUrl += BUILD;
		}
		if (httpGetRequest(buildUrl)) {
			boolean building = true;
			while (building) {
				try {
					Thread.sleep(buildValidationThreshold);
				} catch (InterruptedException e) {
					// do nothing
				}
				if (httpGetRequest(job.getUrl() + "/" + futureBuild + jsonFormatApi)) {
					response = gson.fromJson(httpResponseBody, BuildDetail.class);
					building = response.isBuilding();
				}
			}
			log.trace("Compilación finalizada con estado {}", response.getResult());
		}
		return response;
	}

	public boolean deleteJob(String jobName) {
		return false;
	}

	public Job createJob(String jobName, String baseJobName) {
		return null;
	}

	/**
	 * Realiza una petición GET no parametrizada
	 * @param url Destino
	 * @return true si se obtuvo un código de respuesta entre 200 y 299, false en caso contrario
	 */
	private boolean httpGetRequest(String url) {
		boolean response = false;
		CloseableHttpClient client;
		if (requireAuth) {
			log.trace("Registrando credenciales para comunicación");
			Credentials creds = new UsernamePasswordCredentials(this.user, this.pass);
			CredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
			client = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
		} else {
			log.trace("Creando cliente sin credenciales");
			client = HttpClients.createDefault();
		}
		HttpGet get = new HttpGet(url);
		log.trace("Destino: " + url);
		try {
			HttpResponse httpResponse = client.execute(get);
			HttpEntity entity = httpResponse.getEntity();
			log.trace(httpResponse.getStatusLine().toString());
			if (httpResponse.getStatusLine().getStatusCode() >= HTTP_200
					&& httpResponse.getStatusLine().getStatusCode() < HTTP_300) {
				httpResponseBody = EntityUtils.toString(entity, DEFAULT_ENCODING);
				response = true;
			}
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			log.error("Encoding no soportado", e);
		} catch (ClientProtocolException e) {
			log.error("Error en protocolo de comunicación", e);
		} catch (IOException e) {
			log.error("Error en proceso I/O", e);
		}
		return response;
	}

	/**
	 * Realiza una petición POST parametrizada con un cuerpo especificado por el usuario.
	 * @param url Destino
	 * @param content Cuerpo de la petición
	 * @return true si se obtuvo un código de respuesta entre 200 y 299, false en caso contrario
	 */
	private boolean httpPostRequest(String url, String content) {
		boolean response = false;
		CloseableHttpClient client;
		if (requireAuth) {
			log.trace("Registrando credenciales para comunicación");
			Credentials creds = new UsernamePasswordCredentials(this.user, this.pass);
			CredentialsProvider provider = new BasicCredentialsProvider();
			provider.setCredentials(new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT), creds);
			client = HttpClients.custom().setDefaultCredentialsProvider(provider).build();
		} else {
			log.trace("Creando cliente sin credenciales");
			client = HttpClients.createDefault();
		}
		log.trace("Destino: " + url);
		HttpPost post = new HttpPost(url);
		try {
			log.trace("Cargando datos al cuerpo del request");
			HttpEntity entity = new ByteArrayEntity(content.getBytes(DEFAULT_ENCODING));
			post.setEntity(entity);
			log.trace("Lanzando petición a servidor");
			HttpResponse httpResponse = client.execute(post);
			entity = httpResponse.getEntity();
			log.debug(httpResponse.getStatusLine().toString());
			if (httpResponse.getStatusLine().getStatusCode() >= HTTP_200
					&& httpResponse.getStatusLine().getStatusCode() < HTTP_300) {
				httpResponseBody = EntityUtils.toString(entity, DEFAULT_ENCODING);
				response = true;
			}
			EntityUtils.consume(entity);
		} catch (UnsupportedEncodingException e) {
			log.error("Encoding no soportado", e);
		} catch (ClientProtocolException e) {
			log.error("Error en protocolo de comunicación", e);
		} catch (IOException e) {
			log.error("Error en proceso I/O", e);
		}
		return response;
	}

	/**
	 * Ventana de espera entre validaciones de compilación completa a Jenkins CI
	 * @return Valor actual de la ventana de espera.
	 */
	public int getBuildValidationThreshold() {
		return buildValidationThreshold;
	}

	/**
	 * Ventana de espera entre validaciones de compilación completa a Jenkins CI
	 * @param buildValidationThreshold Valor de espera
	 */
	public void setBuildValidationThreshold(int buildValidationThreshold) {
		this.buildValidationThreshold = buildValidationThreshold;
	}
}
