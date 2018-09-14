package com.pmtool.ejb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.apache.http.HttpEntity;
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
import com.google.gson.JsonParseException;
import com.pmtool.model.Task;
import com.pmtool.model.TaskPost;
import com.pmtool.model.TaskPut;
import com.pmtool.model.User;
import com.pmtool.model.Company;
import com.pmtool.model.Project;

@Stateless(name="TaskEJB")
public class TaskEJB implements ITaskLocal {

	@Override
	public void createTask(String tenant_id, TaskPost c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        //http://api-pmtool.azurewebsites.net/Task?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Task/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public Task getTaskById(String tenant_id, int id) {
		
		//http://api-pmtool.azurewebsites.net/Task/1?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
		String url = "http://api-pmtool.azurewebsites.net/Task/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		Task c = new Task();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, Task.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<Task> getAllTasks(String tenant_id) {

		    ArrayList<Task> Tasks = new ArrayList<Task>();
		   
		    //http://api-pmtool.azurewebsites.net/Task?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
			String url = "http://api-pmtool.azurewebsites.net/Task/Get?tenant_id="+tenant_id;
						
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

	@Override
	public void updateTask(String tenant_id, int id, TaskPut oldc) throws ClientProtocolException, IOException {
		
		TaskPost newc = new TaskPost();
		newc.TaskName = oldc.TaskName;
		newc.Description = oldc.Description;
		newc.Plan = oldc.Plan;
		newc.Goals = oldc.Goals;
		newc.Requirement = oldc.Requirement;
		newc.Tools = oldc.Tools;
		newc.StartDate = oldc.StartDate;
		newc.DeadLine = oldc.DeadLine;
		newc.EstimatedTime = oldc.EstimatedTime;
		newc.Complexity = oldc.Complexity;
		newc.State = oldc.State;
		newc.TeamLeaderCode = oldc.TeamLeaderCode;
		newc.ProjectCode = oldc.ProjectCode;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Task/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteTask(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Task/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<User> getUsersByTask(int id, String tenant_id) {

	    ArrayList<User> Users = new ArrayList<User>();
		 
	    String url = "http://api-pmtool.azurewebsites.net/Task/GetMembers/"+Integer.toString(id)+"?tenant_id="+tenant_id;
					
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	        
	        User[] response = gson.fromJson(json, User[].class);
	        
	        for(User c : response)
	        {
	            Users.add(c);
	             
	        }


	    } catch (IOException ex) {
	    } 
	    return Users;
		
	}

}
