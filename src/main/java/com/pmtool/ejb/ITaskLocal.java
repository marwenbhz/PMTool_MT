package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface ITaskLocal {
	
		
	public void createTask (String tenant_id, TaskPost b) throws JsonParseException, IOException;
	public Task getTaskById (String tenant_id, int id);
	public List<Task> getAllTasks(String tenant_id); 
	public void updateTask(String tenant_id, int id, TaskPut b) throws ClientProtocolException, IOException;
	public void deleteTask (String tenant_id, int id) throws ClientProtocolException, IOException;
	public List<User> getUsersByTask (int id, String tenant_id);
	
}
