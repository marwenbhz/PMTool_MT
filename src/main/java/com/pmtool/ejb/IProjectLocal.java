package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface IProjectLocal {
	
		
	public void createProject (String tenant_id, ProjectPost p) throws JsonParseException, IOException;
	public Project getProjectById (String tenant_id, int id);
	public List<Project> getAllProjects(String tenant_id); 
	public void updateProject(String tenant_id, int id, ProjectPut p) throws ClientProtocolException, IOException;
	public void deleteProject (String tenant_id,int id) throws ClientProtocolException, IOException;
	public List<Task> getTasksByProject (int id, String tenant_id);

	

}
