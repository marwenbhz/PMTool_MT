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
import com.pmtool.model.Team;
import com.pmtool.model.User;
import com.pmtool.model.Company;
import com.pmtool.model.Project;

@Stateless(name="TeamEJB")
public class TeamEJB implements ITeamLocal {

	@Override
	public void createTeam(String tenant_id, Team c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        //http://api-pmtool.azurewebsites.net/Team?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Team/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public Team getTeamById(String tenant_id, int id) {
		
		//http://api-pmtool.azurewebsites.net/Team/1?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
		String url = "http://api-pmtool.azurewebsites.net/Team/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		Team c = new Team();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, Team.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<Team> getAllTeams(String tenant_id) {

		    ArrayList<Team> Teams = new ArrayList<Team>();
		   
		    //http://api-pmtool.azurewebsites.net/Team?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
			String url = "http://api-pmtool.azurewebsites.net/Team/Get?tenant_id="+tenant_id;
						
		    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

		        HttpGet request = new HttpGet(url);   
		        request.addHeader("content-type", "application/json");
		        HttpResponse result = httpClient.execute(request);

		        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

		        com.google.gson.Gson gson = new com.google.gson.Gson();
		        
		        Team[] response = gson.fromJson(json, Team[].class);
		        
		        for(Team c : response)
		        {
		            Teams.add(c);
		             
		        }


		    } catch (IOException ex) {
		    } 
		    return Teams;
		
		
	}

	@Override
	public void updateTeam(String tenant_id, int id, Team oldc) throws ClientProtocolException, IOException {
		
		Team newc = new Team();
		newc.TeamName = oldc.TeamName;
		newc.TeamName = oldc.TeamName;
		newc.TeamDescription = oldc.TeamDescription;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Team/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteTeam(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Team/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<User> getUsersByTeam(int id, String tenant_id) {

	    ArrayList<User> Users = new ArrayList<User>();
		 
	    String url = "http://api-pmtool.azurewebsites.net/Team/GetMembers/"+Integer.toString(id)+"?tenant_id="+tenant_id;
					
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
