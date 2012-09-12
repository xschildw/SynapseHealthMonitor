package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;

/**
 *
 * @author xavier
 */
public interface SynapseConnectionBuilder {

	Synapse createSynapseConnection();

	SynapseConnectionBuilder withAuthEndpoint(String authEndpoint);

	SynapseConnectionBuilder withRepoEndpoint(String repoEndpoint);

	SynapseConnectionBuilder withUserName(String userName);
	
}
