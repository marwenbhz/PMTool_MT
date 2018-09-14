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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.ejb.ICategoryLocal;
import com.pmtool.model.Category;
import com.pmtool.model.Project;

@Stateless
@Path("/Category")
public class restCategory {

	@EJB
	private ICategoryLocal metier;
	
	//http://localhost:18080/PMTool_MT/Category/5?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCategoryById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		Category c = metier.getCategoryById(tenant_id, id);
		return Response.status(Status.OK).entity(c).build();
	}
	
	//http://localhost:18080/PMTool_MT/Category/GetProjects/{id}?tenant_id=
	@GET
	@Path("/GetProjects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjectsByCategory(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getProjectsByCategory(id, tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Category?tenant_id=
	@GET
	//@Path("/All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getAllCategorys(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllCategories(tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Category?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createCategory (@QueryParam("tenant_id") String tenant_id, Category p) throws JsonParseException, IOException
	{
		metier.createCategory(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool_MT/Category/id?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteCategory (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteCategory(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Category/?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateCategory (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id, Category oldc) throws ClientProtocolException, IOException
	{
		metier.updateCategory(tenant_id, id, oldc);
	}


}
