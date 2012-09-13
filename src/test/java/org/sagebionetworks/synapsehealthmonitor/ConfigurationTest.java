package org.sagebionetworks.synapsehealthmonitor;

import java.util.Properties;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author xavier
 */
public class ConfigurationTest {
	
	public ConfigurationTest() {
	}
	
	@BeforeClass
	public static void setUpClass() {
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

	/**
	 * Test of getRepoEndpoint method, of class Configuration.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testValidateInvalidProps() {
		Properties props = new Properties();
		props.put(Configuration.REPO_ENDPOINT_KEY, "repoEndpoint");
		props.put(Configuration.AUTH_ENDPOINT_KEY, "authEndpoint");
		Configuration.validateProperties(props);
	}
	
	@Test
	public void testConstructor() {
		Properties props = new Properties();
		props.put(Configuration.REPO_ENDPOINT_KEY, "repoEndpoint");
		props.put(Configuration.AUTH_ENDPOINT_KEY, "authEndpoint");
		props.put(Configuration.USER_NAME_KEY, "user");
		props.put(Configuration.USER_PASSWORD_KEY, "password");
		Configuration config = new Configuration(props);
		assertEquals(props.getProperty(Configuration.REPO_ENDPOINT_KEY), config.getRepoEndpoint());
		assertEquals(props.getProperty(Configuration.AUTH_ENDPOINT_KEY), config.getAuthEndpoint());
		assertEquals(props.getProperty(Configuration.USER_NAME_KEY), config.getUserName());
		assertEquals(props.getProperty(Configuration.USER_PASSWORD_KEY), config.getUserPassword());
		
	}
}
