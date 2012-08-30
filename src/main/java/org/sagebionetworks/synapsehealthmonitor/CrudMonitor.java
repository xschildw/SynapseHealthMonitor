package org.sagebionetworks.synapsehealthmonitor;

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
import org.sagebionetworks.repo.model.Project;

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
		try {
			generateCrud();
		} catch (SynapseException e) {
			throw new JobExecutionException("Encountered Synapse exception");
		}
	}

	private void generateCrud() throws SynapseException {
		System.out.println("Connecting to " + repoEndpoint + " with user " + userName);
		conn.login(userName, password);
		System.out.println("\tAdding project...");
		Project project = new Project();
		project.setName("crudMonitorProject");
		project = conn.createEntity(project);
		System.out.println("\tAdding folder...");
		Folder folder = new Folder();
		folder.setName("crudMonitorFolder");
		folder.setParentId(project.getId());
		folder = conn.createEntity(folder);
		System.out.println("\tAdding data...");
		Data data = new Data();
		data.setName("crudMonitorData");
		data.setParentId(folder.getId());
		data = conn.createEntity(data);
		System.out.println("\tAdding annotations...");
		Annotations annots = conn.getAnnotations(data.getId());
		annots.addAnnotation("someCrudAnnot", "someCrudAnnotValue");
		Annotations updatedAnnots = conn.updateAnnotations(data.getId(), annots);
		System.out.println("\tDeleting project...");
		conn.deleteEntityById(project.getId());
	}
}
