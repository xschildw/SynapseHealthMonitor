package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockito.Mockito;

/**
 *
 * @author xavier
 */
public class CrudMonitorTest {
	CrudMonitor crudMonitor;
	Synapse conn;
	
	@BeforeClass
	public void setup() {
		crudMonitor = new CrudMonitor();
		crudMonitor.setRepoEndpoint("repoEndpoint");
		crudMonitor.setAuthEndpoint("authEndpoint");
		crudMonitor.setUserName("userName");
		crudMonitor.setPassword("password");
	}
	
	@Test
	public void testGenerateCrud() {
		
	}
	
}
