package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author xavier
 */
public class SynapseConnectionBuilderTest {
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSynapseConnectionNullAuthEndpoint() {
		Synapse conn = new SynapseConnectionBuilder().withRepoEndpoint("repoEndpoint").withUserName("userName").createSynapseConnection();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSynapseConnectionNullRepoEndpoint() {
		Synapse conn = new SynapseConnectionBuilder().withAuthEndpoint("authEndpoint").withUserName("userName").createSynapseConnection();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateSynapseConnectionNullUserName() {
		Synapse conn = new SynapseConnectionBuilder().withRepoEndpoint("repoEndpoint").withAuthEndpoint("authEndpoint").createSynapseConnection();
	}
	@Test
	public void testCreateSnapseConnection() {
		Synapse expectedConn = new Synapse();
		expectedConn.setAuthEndpoint("authEndpoint");
		expectedConn.setRepositoryEndpoint("repoEndpoint");
		expectedConn.setUserName("userName");
		Synapse conn = new SynapseConnectionBuilder().withRepoEndpoint("repoEndpoint").withAuthEndpoint("authEndpoint").withUserName("userName").createSynapseConnection();
		assertEquals(expectedConn.getAuthEndpoint(), conn.getAuthEndpoint());
		assertEquals(expectedConn.getRepoEndpoint(), conn.getRepoEndpoint());
		assertEquals(expectedConn.getUserName(), conn.getUserName());
	}
}
