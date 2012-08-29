package org.sagebionetworks.synapsehealthmonitor;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;

import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;

		;

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
	
	public CrudMonitor() {
		
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
		System.out.println("CrudMonitor executing...");
		JobKey key = ctxt.getJobDetail().getKey();
		JobDataMap dataMap = ctxt.getMergedJobDataMap();
		conn = new SynapseConnectionBuilder()
				.withAuthEndpoint(authEndpoint)
				.withRepoEndpoint(repoEndpoint)
				.withUserName(userName)
				.createSynapseConnection();
	}


	
}
