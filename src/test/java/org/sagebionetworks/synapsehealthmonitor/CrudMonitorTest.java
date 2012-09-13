package org.sagebionetworks.synapsehealthmonitor;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;
import org.sagebionetworks.client.Synapse;
import org.sagebionetworks.client.exceptions.SynapseException;
import org.sagebionetworks.repo.model.Project;
import org.sagebionetworks.repo.model.Annotations;
import org.sagebionetworks.repo.model.Data;
import org.sagebionetworks.repo.model.Folder;
import org.sagebionetworks.repo.model.LayerTypeNames;
import org.sagebionetworks.repo.model.LocationData;
import org.sagebionetworks.repo.model.LocationTypeNames;
import org.sagebionetworks.utils.HttpClientHelper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Ignore;

import org.mockito.Mockito;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


/**
 *
 * @author xavier
 */

public class CrudMonitorTest {
	private static CrudMonitor crudMonitor;
	private static Synapse mockConn;
	private static HttpClientHelper mockHttpClientHelper;
	
	@BeforeClass
	public static void beforeClass() {
		mockConn = new MockSynapseConnectionBuilder().createSynapseConnection();
		mockHttpClientHelper = Mockito.mock(HttpClientHelper.class);
		crudMonitor = new CrudMonitor(mockConn);
		crudMonitor.setRepoEndpoint("repoEndpoint");
		crudMonitor.setAuthEndpoint("authEndpoint");
		crudMonitor.setUserName("userName");
		crudMonitor.setPassword("password");
	}
	
	@Test
	public void testGenerateCrud() throws SynapseException {
		Project p = new Project();
		p.setName("crudMonitorProject");
		Project expectedProj = new Project();
		expectedProj.setName(p.getName());
		expectedProj.setId("syn0");
		
		Folder f = new Folder();
		f.setName("crudMonitorFolder");
		f.setParentId(expectedProj.getId());
		Folder expectedFolder = new Folder();
		expectedFolder.setName(f.getName());
		expectedFolder.setParentId(f.getParentId());
		expectedFolder.setId("syn1");
		
		Data d = new Data();
		d.setName("crudMonitorData");
		d.setParentId(expectedFolder.getId());
		Data expectedData = new Data();
		expectedData.setName(d.getName());
		expectedData.setParentId(d.getParentId());
		expectedData.setId("syn3");
		
		Annotations annots = new Annotations();
		annots.addAnnotation("someAnnot", "someAnnotValue");
		Annotations updatedAnnots = new Annotations();
		updatedAnnots.addAnnotation("someAnnot", "someAnnotValue");
		updatedAnnots.addAnnotation("someCrudAnnot", "someCrudAnnotValue");

		when(mockConn.createEntity(p)).thenReturn(expectedProj);
		when(mockConn.createEntity(f)).thenReturn(expectedFolder);
		when(mockConn.createEntity(d)).thenReturn(expectedData);
		when(mockConn.getAnnotations(expectedData.getId())).thenReturn(annots);
		when(mockConn.updateAnnotations(expectedData.getId(), updatedAnnots)).thenReturn(updatedAnnots);
		
		// TODO: develop further

		crudMonitor.generateCrud();
	}
	
	@Test
	public void testQuery() throws SynapseException, JSONException {
		String query = "select id from entity where parentId == 'syn301181' limit 100 offset 1";
		JSONObject result = new JSONObject();
		result.put("totalNumberOfResults", 100);
		when(mockConn.query(query)).thenReturn(result);
		
		crudMonitor.query();
	}
	
	@Ignore
	@Test
	public void testUploadDownloadS3Data() throws SynapseException {
		Project p = new Project();
		p.setName("crudMonitorProject");
		Project expectedProj = new Project();
		expectedProj.setName(p.getName());
		expectedProj.setId("syn0");
		
		Folder f = new Folder();
		f.setName("crudMonitorFolder");
		f.setParentId(expectedProj.getId());
		Folder expectedFolder = new Folder();
		expectedFolder.setName(f.getName());
		expectedFolder.setParentId(f.getParentId());
		expectedFolder.setId("syn1");
		
		Data d = new Data();
		d.setName("crudMonitorData");
		d.setType(LayerTypeNames.E);
		d.setParentId(expectedFolder.getId());
		Data expectedData = new Data();
		expectedData.setName(d.getName());
		expectedData.setType(d.getType());
		expectedData.setParentId(d.getParentId());
		expectedData.setId("syn3");
		LocationData locData = new LocationData();
		locData.setType(LocationTypeNames.awss3);
		locData.setPath("somePathonS3");
		List<LocationData> locations = new ArrayList<LocationData>();
		locations.add(locData);
		expectedData.setLocations(locations);

		when(mockConn.createEntity(p)).thenReturn(expectedProj);
		when(mockConn.createEntity(f)).thenReturn(expectedFolder);
		when(mockConn.createEntity(d)).thenReturn(expectedData);
		when(mockConn.uploadLocationableToSynapse(d, any(File.class))).thenReturn(d);
		
		
	}
}
