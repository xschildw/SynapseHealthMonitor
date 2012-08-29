package org.sagebionetworks.synapsehealthmonitor;

import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;

/**
 *
 * @author xavier
 */
public class SynapseConnection {
	private String userName;
	private String password;
	private String repoEndpoint;
	private String authEndpoint;
	private Synapse conn;

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the repoEndpoint
	 */
	public String getRepoEndpoint() {
		return repoEndpoint;
	}

	/**
	 * @param repoEndpoint the repoEndpoint to set
	 */
	public void setRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
	}

	/**
	 * @return the authEndpoint
	 */
	public String getAuthEndpoint() {
		return authEndpoint;
	}

	/**
	 * @param authEndpoint the authEndpoint to set
	 */
	public void setAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
	}

	/**
	 * @return the conn
	 */
	public Synapse getConn() {
		return conn;
	}

	public SynapseConnection() {
		super();
	}

	public SynapseConnection(String userName, String password, String repoEndpoint, String authEndpoint) {
		super();
		this.userName = userName;
		this.password = password;
		this.repoEndpoint = repoEndpoint;
		this.authEndpoint = authEndpoint;
	}

}
