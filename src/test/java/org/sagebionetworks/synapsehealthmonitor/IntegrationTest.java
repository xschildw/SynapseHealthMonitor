/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sagebionetworks.synapsehealthmonitor;

import java.io.UnsupportedEncodingException;
import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;
import org.sagebionetworks.repo.model.Annotations;
import org.sagebionetworks.repo.model.Data;
import org.sagebionetworks.repo.model.Folder;
import org.sagebionetworks.repo.model.Project;
import org.sagebionetworks.schema.adapter.JSONObjectAdapterException;

/**
 *
 * @author xavier
 */
public class IntegrationTest {
	private static Synapse conn;
	private static CrudMonitor crudMonitor;
	private static SearchMonitor searchMonitor;

	
	public IntegrationTest() {
	}
	
	/**
	 *
	 * @throws SynapseException
	 */
	@BeforeClass
	public static void setUpClass() throws SynapseException {
		conn = new SynapseConnectionBuilderImpl().createSynapseConnection();
		conn.setAuthEndpoint("http://localhost:8080/services-authentication-develop-SNAPSHOT/auth/v1");
		conn.setRepositoryEndpoint("http://localhost:8080/services-repository-develop-SNAPSHOT/repo/v1");
		conn.setSearchEndpoint("http://localhost:8080/services-search-develop-SNAPSHOT/search/v1");
		conn.login("", "");
		crudMonitor = new CrudMonitor(conn);
		searchMonitor = new SearchMonitor(conn);
	}
	
	@AfterClass
	public static void tearDownClass() {
	}
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
	}
	 @Test
	 public void testCrudMonitor() throws SynapseException, JSONException {
		 crudMonitor.generateCrud();
	 }
	 
	 @Test
	 public void testSearchMonitor() throws SynapseException, UnsupportedEncodingException, JSONObjectAdapterException {
		 searchMonitor.search();
		 
	 }
}
