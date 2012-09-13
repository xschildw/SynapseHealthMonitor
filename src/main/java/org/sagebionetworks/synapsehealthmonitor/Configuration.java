package org.sagebionetworks.synapsehealthmonitor;

import java.util.Properties;

/**
 *
 * @author xavier
 */
public class Configuration {
	public static final String REPO_ENDPOINT_KEY = "org.sagebionetworks.synapsehealthmonitor.repoendpoint";
	public static final String AUTH_ENDPOINT_KEY = "org.sagebionetworks.synapsehealthmonitor.authendpoint";
	public static final String USER_NAME_KEY = "org.sagebionetworks.synapsehealthmonitor.username";
	public static final String USER_PASSWORD_KEY = "org.sagebionetworks.synapsehealthmonitor.userpassword";

	public Configuration(Properties props) {
		repoEndpoint = props.getProperty(REPO_ENDPOINT_KEY);
		authEndpoint = props.getProperty(AUTH_ENDPOINT_KEY);
		userName = props.getProperty(USER_NAME_KEY);
		userPassword = props.getProperty(USER_PASSWORD_KEY);
	}
	
	private String repoEndpoint;

	/**
	 * Get the value of repoEndpoint
	 *
	 * @return the value of repoEndpoint
	 */
	public String getRepoEndpoint() {
		return repoEndpoint;
	}

	/**
	 * Set the value of repoEndpoint
	 *
	 * @param repoEndpoint new value of repoEndpoint
	 */
	public void setRepoEndpoint(String repoEndpoint) {
		this.repoEndpoint = repoEndpoint;
	}
	private String authEndpoint;

	/**
	 * Get the value of authEndpoint
	 *
	 * @return the value of authEndpoint
	 */
	public String getAuthEndpoint() {
		return authEndpoint;
	}

	/**
	 * Set the value of authEndpoint
	 *
	 * @param authEndpoint new value of authEndpoint
	 */
	public void setAuthEndpoint(String authEndpoint) {
		this.authEndpoint = authEndpoint;
	}
	private String userName;

	/**
	 * Get the value of userName
	 *
	 * @return the value of userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Set the value of userName
	 *
	 * @param userName new value of userName
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	private String userPassword;

	/**
	 * Get the value of userPassword
	 *
	 * @return the value of userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * Set the value of userPassword
	 *
	 * @param userPassword new value of userPassword
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public static void validateProperties(Properties props) {
		if (! ((props.containsKey(REPO_ENDPOINT_KEY)) && (props.containsKey(AUTH_ENDPOINT_KEY)) && (props.containsKey(USER_NAME_KEY)) &&(props.containsKey(USER_PASSWORD_KEY)))) {
			throw new IllegalArgumentException("Configuration file missing a required key.");
		}
	}

}
