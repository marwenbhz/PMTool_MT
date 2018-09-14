package com.pmtool.ejb;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.pmtool.model.Banque;
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.Project;
import com.pmtool.model.ProjectPost;
import com.pmtool.model.ProjectPut;
import com.pmtool.model.Task;



@Stateless(name="ProjectEJB")
public class ProjectEJB implements IProjectLocal {

	@Override 
	public void createProject(String tenant_id, ProjectPost p) throws JsonParseException, IOException {
		
		p.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Project/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(p));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
        
	}

	@Override //get:https://pmtool-api.azurewebsites.net/Project/14
	public Project getProjectById(String tenant_id, int id) {
			
		String url = "http://api-pmtool.azurewebsites.net/Project/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;;
		Project p = new Project();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        p = gson.fromJson(json, Project.class);


	    } catch (IOException ex) {
	    } 
	    return p;
	}
	
	@Override //get:https://pmtool-api.azurewebsites.net/Project
	public List<Project> getAllProjects(String tenant_id) {
			
		   ArrayList<Project> projects = new ArrayList<Project>();
		   
			String url = "http://api-pmtool.azurewebsites.net/Project/Get?tenant_id="+tenant_id;
						
		    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

		        HttpGet request = new HttpGet(url);   
		        request.addHeader("content-type", "application/json");
		        HttpResponse result = httpClient.execute(request);

		        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

		        com.google.gson.Gson gson = new com.google.gson.Gson();
		        
		        Project[] response = gson.fromJson(json, Project[].class);
		        
		        for(Project p : response)
		        {
		            projects.add(p);
		             
		        }


		    } catch (IOException ex) {
		    } 
		    return projects;
		}

	@Override //put:https://pmtool-api.azurewebsites.net/Project/14
	public void updateProject(String tenant_id, int id, ProjectPut oldp) throws ClientProtocolException, IOException {
		
		ProjectPost newp = new ProjectPost();
		
		newp.ProjectName = oldp.ProjectName;
		newp.Description = oldp.Description;
		newp.Plan = oldp.Plan;
		newp.Goals = oldp.Goals;
		newp.StartDate = oldp.StartDate;
		newp.DeadLine = oldp.DeadLine;
		newp.EstimatedTime = oldp.EstimatedTime;
		
		newp.CategoryCode = oldp.CategoryCode;
		newp.CompanyCode = oldp.CompanyCode;
		newp.BanqueCode = oldp.BanqueCode;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Project/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newp));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
		
	}

	@Override //delete:https://pmtool-api.azurewebsites.net/Project/14
	public void deleteProject(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Project/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<Task> getTasksByProject(int id, String tenant_id) {

	    ArrayList<Task> Tasks = new ArrayList<Task>();

		String url = "http://api-pmtool.azurewebsites.net/Project/GetTasks/"+Integer.toString(id)+"?tenant_id="+tenant_id;
					
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	        
	        Task[] response = gson.fromJson(json, Task[].class);
	        
	        for(Task c : response)
	        {
	            Tasks.add(c);
	             
	        }


	    } catch (IOException ex) {
	    } 
	    return Tasks;
		
	}
	
}
