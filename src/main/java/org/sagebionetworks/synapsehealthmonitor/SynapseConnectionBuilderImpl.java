package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;

public class SynapseConnectionBuilderImpl implements SynapseConnectionBuilder {
	private String userName;
	private String repoEndpoint;
	private String authEndpoint;

	public SynapseConnectionBuilderImpl() {
	}

	@Override
	public SynapseConnectionBuilder withUserName(String userName) {
		this.userName = userName;
		return this;
	}

	@Override
	public SynapseConnectionBuilder withRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
		return this;
	}

	@Override
	public SynapseConnectionBuilder withAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
		return this;
	}

	@Override
	public Synapse createSynapseConnection() {
		Synapse conn = new Synapse();
		if ((this.authEndpoint == null) || (this.repoEndpoint == null) || (this.userName == null)) {
			throw new IllegalArgumentException("Properties authEndpoint, repoEndpoint and userName cannot be null.");
		}
		conn.setAuthEndpoint(authEndpoint);
		conn.setRepositoryEndpoint(repoEndpoint);
		conn.setUserName(userName);
		return conn;
	}
	
}

