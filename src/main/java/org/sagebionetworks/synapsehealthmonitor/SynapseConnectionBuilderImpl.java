package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;

public class SynapseConnectionBuilderImpl implements SynapseConnectionBuilder {
	private String userName;
	private String repoEndpoint;
	private String authEndpoint;

	public SynapseConnectionBuilderImpl() {
	}

	
	public SynapseConnectionBuilder withUserName(String userName) {
		this.userName = userName;
		return this;
	}

	
	public SynapseConnectionBuilder withRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
		return this;
	}

	
	public SynapseConnectionBuilder withAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
		return this;
	}

	
	public Synapse createSynapseConnection() {
		Synapse conn = new Synapse();
		conn.setAuthEndpoint(authEndpoint);
		conn.setRepositoryEndpoint(repoEndpoint);
		conn.setUserName(userName);
		return conn;
	}
	
}

