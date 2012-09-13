package org.sagebionetworks.synapsehealthmonitor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.json.JSONArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;
import org.sagebionetworks.repo.model.Annotations;
import org.sagebionetworks.repo.model.Data;
import org.sagebionetworks.repo.model.Folder;
import org.sagebionetworks.repo.model.LayerTypeNames;
import org.sagebionetworks.repo.model.LocationData;
import org.sagebionetworks.repo.model.LocationTypeNames;
import org.sagebionetworks.repo.model.Project;
import org.sagebionetworks.utils.DefaultHttpClientSingleton;
import org.sagebionetworks.utils.HttpClientHelper;
import org.sagebionetworks.utils.HttpClientHelperException;



/**
 *
 * @author xavier
 */
public class CrudMonitor implements Job {
	private String repoEndpoint;
	private String authEndpoint;
	private String userName;
	private String password;
	private Synapse conn;

	private static Logger logger = Logger.getLogger(HealthMonitorApp.class);


	public CrudMonitor() {
		super();
		conn = new SynapseConnectionBuilderImpl().createSynapseConnection();
	}
	
	public CrudMonitor(Synapse conn) {
		super();
		this.conn = conn;
	}
//
	/**
	 * @param repoEndpoint the repoEndpoint to set
	 */
	public void setRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
	}

	/**
	 * @param authEndpoint the authEndpoint to set
	 */
	public void setAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 *
	 * @param ctxt
	 * @throws JobExecutionException
	 */
	public void execute(JobExecutionContext ctxt) throws JobExecutionException {
		logger.debug("CrudMonitor executing...");
		JobKey key = ctxt.getJobDetail().getKey();
		JobDataMap dataMap = ctxt.getMergedJobDataMap();
		conn.setAuthEndpoint(authEndpoint);
		conn.setRepositoryEndpoint(repoEndpoint);
		conn.setUserName(userName);

		try {
			logger.debug("Connecting to " + repoEndpoint + " with user " + userName);
			conn.login(userName, password);

			generateCrud();
			query();
			uploadDownloadS3Data();
		} catch (SynapseException e) {
			logger.warn("CrudMonitor: Synapse exception");
			throw new JobExecutionException("Encountered Synapse exception", e);
		} catch (JSONException e) {
			logger.warn("CrudMonitor: JSON exception");
			throw new JobExecutionException("Encountered JSON exception", e);
		} catch (IOException e) {
			logger.warn("CrudMonitor: IO exception");
			throw new JobExecutionException("Encountered IO exception", e);
		} catch (HttpClientHelperException e) {
			logger.warn("CrudMonitor: HttpClientHelper exception");
			throw new JobExecutionException("Encountered HttpClientHelper exception", e);
		}
	}

	/**
	 *
	 * @throws SynapseException
	 */
	public void generateCrud() throws SynapseException, JSONException {
		logger.debug("\tAdding project...");
		Project project = new Project();
//		project.setName("crudMonitorProject");
		project = conn.createEntity(project);
		logger.debug("\tAdding folder...");
		Folder folder = new Folder();
//		folder.setName("crudMonitorFolder");
		folder.setParentId(project.getId());
		folder = conn.createEntity(folder);
		logger.debug("\tAdding data...");
		Data data = new Data();
//		data.setName("crudMonitorData");
		data.setParentId(folder.getId());
		data = conn.createEntity(data);
		logger.debug("\tAdding annotations...");
		Annotations annots = conn.getAnnotations(data.getId());
		annots.addAnnotation("someCrudAnnot", "someCrudAnnotValue");
		Annotations updatedAnnots = conn.updateAnnotations(data.getId(), annots);
		logger.debug("\tDeleting project...");
		conn.deleteEntityById(project.getId());
	}
	
	/**
	 *
	 * @throws SynapseException
	 */
	public void query() throws SynapseException, JSONException {
		long startTime = System.nanoTime();
		JSONObject result = conn.query("select id from entity where parentId == 'syn301181' limit 100 offset 1");
		long endTime = System.nanoTime();
		int rowCount = result.getInt("totalNumberOfResults");
		long queryTime = (endTime - startTime) / 1000000;
		logger.info("CrudMonitor.query: " + queryTime);
	}
	
	/**
	 *
	 * @throws SynapseException
	 * @throws java.io.IOException.HttpClientHelperException
	 */
	public void uploadDownloadS3Data() throws SynapseException, IOException, HttpClientHelperException {
		long startTime, endTime, uploadTime, downloadTime;
		File dataSourceFile = File.createTempFile("crudMonitor", ".txt");
		dataSourceFile.deleteOnExit();
		FileWriter writer = new FileWriter(dataSourceFile);
		writer.write("Hello world!");
		writer.close();

		Project project = new Project();
//		project.setName("crudMonitorProject");
		project = conn.createEntity(project);
		logger.debug("\tAdding folder...");
		Folder folder = new Folder();
//		folder.setName("crudMonitorFolder");
		folder.setParentId(project.getId());
		folder = conn.createEntity(folder);
		logger.debug("\tAdding data...");
		Data data = new Data();
//		data.setName("crudMonitorData");
		data.setParentId(folder.getId());
		data.setType(LayerTypeNames.E);
		data = conn.createEntity(data);

		startTime = System.nanoTime();
		data = (Data) conn.uploadLocationableToSynapse(data, dataSourceFile);
		endTime = System.nanoTime();
		uploadTime = endTime - startTime;
		
		List<LocationData> locations = data.getLocations();
		LocationData location = locations.get(0);
		
		File dataDestinationFile = File.createTempFile("crudMonitor", ".download");
		dataDestinationFile.deleteOnExit();
		startTime = System.nanoTime();
		HttpClientHelper.getContent(DefaultHttpClientSingleton.getInstance(), location.getPath(), dataDestinationFile);
		endTime = System.nanoTime();
		downloadTime = endTime - startTime;
		
		logger.debug("\tDeleting project");
		conn.deleteEntityById(project.getId());
		
		logger.info("CrudMonitor.uploadDownloadToS3: " + uploadTime/1000000 + "/" + downloadTime/1000000);
	}
}
