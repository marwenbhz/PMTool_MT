package com.pmtool.ejb;

import java.io.IOException;
import java.util.List;

import javax.ejb.Local;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.model.*;

@Local
public interface ICompanyLocal {
	
	public String helloworld();
	
	public void createCompany (String tenant_id, CompanyPost c) throws JsonParseException, IOException;
	public Company getCompanyById (String tenant_id,int id);
	public List<Company> getAllCompanies(String tenant_id); 
	public void updateCompany(String tenant_id,int id, CompanyPost c) throws ClientProtocolException, IOException;
	public void deleteCompany (String tenant_id, int id) throws ClientProtocolException, IOException;
	public List<Project> getProjectsByCompany (int id, String tenant_id); 


}
