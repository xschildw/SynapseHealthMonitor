package org.sagebionetworks.synapsehealthmonitor;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;
import org.sagebionetworks.repo.model.search.SearchResults;
import org.sagebionetworks.repo.model.search.query.SearchQuery;
import org.sagebionetworks.schema.adapter.JSONObjectAdapterException;

/**
 *
 * @author xavier
 */
public class SearchMonitor implements Job {
	private String repoEndpoint;
	private String authEndpoint;
	private String userName;
	private String password;
	private Synapse conn;
	
	public SearchMonitor() {
		this.conn = new SynapseConnectionBuilderImpl().createSynapseConnection();
	}
	
	public SearchMonitor(Synapse conn) {
		this.conn = conn;
	}

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
		System.out.println("SearchMonitor executing...");
//		JobKey key = ctxt.getJobDetail().getKey();
//		JobDataMap dataMap = ctxt.getMergedJobDataMap();
//		conn.setAuthEndpoint(authEndpoint);
//		conn.setRepositoryEndpoint(repoEndpoint);
//		conn.setUserName(userName);
//
//		try {
//			search();
//			//uploadDownloadS3Data();
//		} catch (SynapseException e) {
//			throw new JobExecutionException("Encountered Synapse exception", e);
//		} catch (UnsupportedEncodingException e) {
//			throw new JobExecutionException("Encountered UnsupportedEncoding exception", e);
//		} catch (JSONObjectAdapterException e) {
//			throw new JobExecutionException("Encountered JSONObjectAdapter exception", e);
//		}
	}
	
	public void search() throws SynapseException, UnsupportedEncodingException, JSONObjectAdapterException {
		System.out.println("Connecting to " + repoEndpoint + " with user " + userName);
		conn.login(userName, password);
		System.out.println("\tSearching for cancer...");
		SearchQuery searchQuery = new SearchQuery();
		List<String> queryTerms = new ArrayList<String>();
		queryTerms.add("cancer");
		searchQuery.setQueryTerm(queryTerms);
		List<String> returnFields = new ArrayList<String>();
		returnFields.add("id");
		returnFields.add("name");
		searchQuery.setReturnFields(returnFields);
		Long startTime, endTime, elapsedTime;
		startTime = System.nanoTime();
		SearchResults results = conn.search(searchQuery);
		endTime = System.nanoTime();
		elapsedTime = endTime - startTime;
		System.out.println("\t\tFound " + results.getHits().size() + " hits in " + elapsedTime/1000 + " ms");
	}
}
