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
import com.pmtool.model.Banque;
import com.pmtool.model.Company;
import com.pmtool.model.Project;

@Stateless(name="BanqueEJB")
public class BanqueEJB implements IBanqueLocal {

	@Override
	public void createBanque(String tenant_id, Banque c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        //http://api-pmtool.azurewebsites.net/Banque?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Banque/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public Banque getBanqueById(String tenant_id, int id) {
		
		//http://api-pmtool.azurewebsites.net/Banque/1?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
		String url = "http://api-pmtool.azurewebsites.net/Banque/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		Banque c = new Banque();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, Banque.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<Banque> getAllBanques(String tenant_id) {

		    ArrayList<Banque> banques = new ArrayList<Banque>();
		   
		    //http://api-pmtool.azurewebsites.net/Banque?tenant_id=4ea20902-065a-45dc-a166-de8110439f55
			String url = "http://api-pmtool.azurewebsites.net/Banque/Get?tenant_id="+tenant_id;
						
		    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

		        HttpGet request = new HttpGet(url);   
		        request.addHeader("content-type", "application/json");
		        HttpResponse result = httpClient.execute(request);

		        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

		        com.google.gson.Gson gson = new com.google.gson.Gson();
		        
		        Banque[] response = gson.fromJson(json, Banque[].class);
		        
		        for(Banque c : response)
		        {
		            banques.add(c);
		             
		        }


		    } catch (IOException ex) {
		    } 
		    return banques;
		
		
	}

	@Override
	public void updateBanque(String tenant_id, int id, Banque oldc) throws ClientProtocolException, IOException {
		
		Banque newc = new Banque();
		newc.BanqueName = oldc.BanqueName;
		newc.Description = oldc.Description;
		
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Banque/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteBanque(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Banque/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<Project> getProjectsByBank(int id, String tenant_id) {
		
		   ArrayList<Project> projects = new ArrayList<Project>();
		   
			String url = "http://api-pmtool.azurewebsites.net/Banque/GetProjects/"+Integer.toString(id)+"?tenant_id="+tenant_id;
						
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
