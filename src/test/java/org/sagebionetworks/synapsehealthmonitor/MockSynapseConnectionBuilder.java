/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.sagebionetworks.synapsehealthmonitor;

import org.mockito.Mockito;

import org.sagebionetworks.client.Synapse;

/**
 *
 * @author xavier
 */
public class MockSynapseConnectionBuilder implements SynapseConnectionBuilder {

	Synapse conn;
	String repoEndpoint;
	String authEndpoint;
	String userName;
	String password;
	
	public MockSynapseConnectionBuilder() {
		conn = Mockito.mock(Synapse.class);
	}
	
	public Synapse createSynapseConnection() {
		return conn;
	}

	public SynapseConnectionBuilder withAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
		return this;
	}

	public SynapseConnectionBuilder withRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
		return this;
	}

	public SynapseConnectionBuilder withUserName(String userName) {
		this.userName = userName;
		return this;
	}
	
}
