package org.sagebionetworks.synapsehealthmonitor;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;
import org.sagebionetworks.repo.model.Project;
import org.sagebionetworks.repo.model.Annotations;
import org.sagebionetworks.repo.model.Data;
import org.sagebionetworks.repo.model.Folder;
import org.sagebionetworks.repo.model.LayerTypeNames;
import org.sagebionetworks.repo.model.LocationData;
import org.sagebionetworks.repo.model.LocationTypeNames;
import org.sagebionetworks.utils.HttpClientHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import org.sagebionetworks.repo.model.search.Hit;
import org.sagebionetworks.repo.model.search.SearchResults;
import org.sagebionetworks.repo.model.search.query.SearchQuery;
import org.sagebionetworks.schema.adapter.JSONObjectAdapterException;

/**
 *
 * @author xschildw
 */
public class SearchMonitorTest {
	private static SearchMonitor searchMonitor;
	private static Synapse mockConn;

	@BeforeClass
	public static void beforeClass() {
		mockConn = new MockSynapseConnectionBuilder().createSynapseConnection();
		searchMonitor = new SearchMonitor(mockConn);
		searchMonitor.setRepoEndpoint("repoEndpoint");
		searchMonitor.setAuthEndpoint("authEndpoint");
		searchMonitor.setUserName("userName");
		searchMonitor.setPassword("password");
	}
	
	/**
	 *
	 * @throws SynapseException
	 * @throws UnsupportedEncodingException
	 * @throws JSONObjectAdapterException
	 */
	@Test
	public void testSearch() throws SynapseException, UnsupportedEncodingException, JSONObjectAdapterException{
		SearchQuery q = new SearchQuery();
		List<String> queryTerms = new ArrayList<String>();
		queryTerms.add("liver");
		q.setQueryTerm(queryTerms);
		List<String> returnFields = new ArrayList<String>();
		returnFields.add("id");
		returnFields.add("name");
		q.setReturnFields(returnFields);
				
		SearchResults expectedRes = new SearchResults();
		List<Hit> hits = new ArrayList<Hit>();
		Hit hit = new Hit();
		hit.setId("syn1");
		hits.add(hit);
		hit = new Hit();
		hit.setId("syn2");
		hits.add(hit);
		expectedRes.setHits(hits);
		expectedRes.setFound(2L);
		
		when(mockConn.search(q)).thenReturn(expectedRes);
		
		searchMonitor.search();
	}
}
