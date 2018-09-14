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
import com.pmtool.model.User;
import com.pmtool.model.UserPost;
import com.pmtool.model.UserPut;
import com.pmtool.model.Company;
import com.pmtool.model.Project;

@Stateless(name="UserEJB")
public class UserEJB implements IUserLocal {

	@Override
	public void createUser(String tenant_id, UserPost c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        //http://api-pmtool.azurewebsites.net/User?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/User/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public User getUserById(String tenant_id, int id) {
		
		//http://api-pmtool.azurewebsites.net/User/1?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
		String url = "http://api-pmtool.azurewebsites.net/User/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		User c = new User();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, User.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<User> getAllUsers(String tenant_id) {

		    ArrayList<User> Users = new ArrayList<User>();
		   
		    //http://api-pmtool.azurewebsites.net/User?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
			String url = "http://api-pmtool.azurewebsites.net/User/Get?tenant_id="+tenant_id;
						
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

	@Override
	public void updateUser(String tenant_id, int id, UserPost newc) throws ClientProtocolException, IOException {
		
		UserPut oldc = new UserPut();
		
		oldc.TaskCode = newc.TaskCode;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/User/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(oldc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteUser(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/User/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

}
