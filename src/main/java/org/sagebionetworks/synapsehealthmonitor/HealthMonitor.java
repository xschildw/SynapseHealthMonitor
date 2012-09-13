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
public class HealthMonitor implements Job{
	
	private String repoEndpoint;
	private String authEndpoint;
	private String userName;
	private String password;
	private Synapse conn;

	private static Logger logger = Logger.getLogger(HealthMonitorApp.class);


	public HealthMonitor() {
		super();
		conn = new SynapseConnectionBuilderImpl().createSynapseConnection();
	}
	
	public HealthMonitor(Synapse conn) {
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

		} catch (Exception e) {
			logger.warn("CrudMonitor: Synapse exception");
			throw new JobExecutionException("Encountered Synapse exception", e);
		}
//		} catch (JSONException e) {
//			logger.warn("CrudMonitor: JSON exception");
//			throw new JobExecutionException("Encountered JSON exception", e);
//		} catch (IOException e) {
//			logger.warn("CrudMonitor: IO exception");
//			throw new JobExecutionException("Encountered IO exception", e);
//		} catch (HttpClientHelperException e) {
//			logger.warn("CrudMonitor: HttpClientHelper exception");
//			throw new JobExecutionException("Encountered HttpClientHelper exception", e);
//		}
	}
	
	public void queryHealth() {
		
	}
}
