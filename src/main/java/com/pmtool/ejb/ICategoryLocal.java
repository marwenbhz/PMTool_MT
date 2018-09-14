package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface ICategoryLocal {
	
		
	public void createCategory (String tenant_id, Category c) throws JsonParseException, IOException;
	public Category getCategoryById (String tenant_id, int id);
	public List<Category> getAllCategories(String tenant_id); 
	public void updateCategory(String tenant_id, int id, Category c) throws ClientProtocolException, IOException;
	public void deleteCategory (String tenant_id, int id) throws ClientProtocolException, IOException;
	public List<Project> getProjectsByCategory (int id, String tenant_id); 


}
