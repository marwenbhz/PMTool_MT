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
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.Project;

@Stateless(name="CategoryEJB")
public class CategoryEJB implements ICategoryLocal {

	@Override
	public void createCategory(String tenant_id, Category c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        //http://api-pmtool.azurewebsites.net/Category?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Category/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public Category getCategoryById(String tenant_id, int id) {
		
		//http://api-pmtool.azurewebsites.net/Category/1?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
		String url = "http://api-pmtool.azurewebsites.net/Category/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		Category c = new Category();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, Category.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<Category> getAllCategories(String tenant_id) {

		    ArrayList<Category> categories = new ArrayList<Category>();
		   
		    //http://api-pmtool.azurewebsites.net/Category?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
			String url = "http://api-pmtool.azurewebsites.net/Category/Get?tenant_id="+tenant_id;
						
		    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

		        HttpGet request = new HttpGet(url);   
		        request.addHeader("content-type", "application/json");
		        HttpResponse result = httpClient.execute(request);

		        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

		        com.google.gson.Gson gson = new com.google.gson.Gson();
		        
		        Category[] response = gson.fromJson(json, Category[].class);
		        
		        for(Category c : response)
		        {
		            categories.add(c);
		             
		        }


		    } catch (IOException ex) {
		    } 
		    return categories;
		
		
	}

	@Override
	public void updateCategory(String tenant_id, int id, Category oldc) throws ClientProtocolException, IOException {
		
		Category newc = new Category();
		newc.CategoryName = oldc.CategoryName;
		newc.CategoryDescription = oldc.CategoryDescription;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Category/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteCategory(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Category/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<Project> getProjectsByCategory(int id, String tenant_id) {

		   ArrayList<Project> projects = new ArrayList<Project>();
		   
			String url = "http://api-pmtool.azurewebsites.net/Category/GetProjects/"+Integer.toString(id)+"?tenant_id="+tenant_id;
						
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


}
