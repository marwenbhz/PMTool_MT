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
import com.google.gson.JsonParseException;
import com.pmtool.model.Company;
import com.pmtool.model.CompanyPost;
import com.pmtool.model.Project;
import com.pmtool.model.Task;


@Stateless(name="CompanyEJB")
public class CompanyEJB implements ICompanyLocal {

	@Override
	public String helloworld() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void createCompany(String tenant_id, CompanyPost c) throws JsonParseException, IOException {
		
		c.Owner = tenant_id;
        HttpClient httpClient = HttpClientBuilder.create().build(); 
        HttpPost post = new HttpPost("http://api-pmtool.azurewebsites.net/Company/Post?tenant_id="+tenant_id);
        Gson gson = new Gson();
        StringEntity postingString = new StringEntity(gson.toJson(c));
        post.setEntity(postingString);
        post.setHeader("Content-type", "application/json");
        HttpResponse  response = httpClient.execute(post);
		
	}

	@Override
	public Company getCompanyById(String tenant_id, int id) {

		String url = "http://api-pmtool.azurewebsites.net/Company/Get/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		Company c = new Company();
		
	    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

	        HttpGet request = new HttpGet(url);   
	        request.addHeader("content-type", "application/json");
	        HttpResponse result = httpClient.execute(request);

	        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

	        com.google.gson.Gson gson = new com.google.gson.Gson();
	                       
	        c = gson.fromJson(json, Company.class);


	    } catch (IOException ex) {
	    } 
	    return c;
		
	}

	@Override
	public List<Company> getAllCompanies(String tenant_id) {
		
		   ArrayList<Company> companies = new ArrayList<Company>();
		   
			String url = "http://api-pmtool.azurewebsites.net/Company/Get?tenant_id="+tenant_id;
						
		    try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {

		        HttpGet request = new HttpGet(url);   
		        request.addHeader("content-type", "application/json");
		        HttpResponse result = httpClient.execute(request);

		        String json = EntityUtils.toString(result.getEntity(), "UTF-8");  

		        com.google.gson.Gson gson = new com.google.gson.Gson();
		        
		        Company[] response = gson.fromJson(json, Company[].class);
		        
		        for(Company c : response)
		        {
		            companies.add(c);
		             
		        }


		    } catch (IOException ex) {
		    } 
		    return companies;
		
	}

	@Override
	public void updateCompany(String tenant_id, int id, CompanyPost oldc) throws ClientProtocolException, IOException {
		
		CompanyPost newc = new CompanyPost();
		newc.CompanyName = oldc.CompanyName;
		newc.Address = oldc.Address;
		newc.PhoneNumber = oldc.PhoneNumber;
		newc.Country = oldc.Country;
		
		HttpClient httpClient = HttpClientBuilder.create().build(); 
		String url = "http://api-pmtool.azurewebsites.net/Company/Put/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpPut put = new HttpPut(url);
		Gson gson = new Gson();
		put.setHeader("Content-Type", "application/json"); 
		StringEntity postingString = new StringEntity(gson.toJson(newc));
		put.setEntity(postingString);
		HttpResponse  response = httpClient.execute(put);
		
	}

	@Override
	public void deleteCompany(String tenant_id, int id) throws ClientProtocolException, IOException {
		
		String url = "http://api-pmtool.azurewebsites.net/Company/Delete/"+Integer.toString(id)+"?tenant_id="+tenant_id;
		HttpClient httpClient = new DefaultHttpClient();
		HttpDelete httpDelete = new HttpDelete(url); 
		HttpResponse response = httpClient.execute(httpDelete); 
		
	}

	@Override
	public List<Project> getProjectsByCompany(int id, String tenant_id) {

		   ArrayList<Project> projects = new ArrayList<Project>();
		   
			String url = "http://api-pmtool.azurewebsites.net/Company/GetProjects/"+Integer.toString(id)+"?tenant_id="+tenant_id;
						
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
