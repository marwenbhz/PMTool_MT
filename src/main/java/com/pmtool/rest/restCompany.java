package com.pmtool.rest;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.ejb.ICompanyLocal;
import com.pmtool.model.Company;
import com.pmtool.model.CompanyPost;
import com.pmtool.model.Project;


@Stateless
@Path("/Company")
public class restCompany {

	@EJB
	private ICompanyLocal metier;
	
	//http://localhost:18080/PMTool_MT/Company/id?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Company getCompanyById(@QueryParam("tenant_id") String tenant_id, @PathParam("id") int id)
	{
		return metier.getCompanyById(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Company/GetProjects/{id}?tenant_id=
	@GET
	@Path("/GetProjects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjectsByCompany(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getProjectsByCompany(id, tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Company?tenant_id=
	@GET
	//@Path("/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Company> getAllCompanys(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllCompanies(tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Company?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createCompany (CompanyPost c, @QueryParam("tenant_id") String tenant_id) throws JsonParseException, IOException
	{
		metier.createCompany(tenant_id, c);
	}
	
	//http://localhost:18080/PMTool_MT/Company/id?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCompany (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteCompany(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Company/id?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCompany (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id, CompanyPost oldc) throws ClientProtocolException, IOException
	{
		metier.updateCompany(tenant_id, id, oldc);
	}


}
