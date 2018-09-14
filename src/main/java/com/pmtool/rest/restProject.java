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
import com.pmtool.ejb.IProjectLocal;
import com.pmtool.model.Banque;
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.Project;
import com.pmtool.model.ProjectPost;
import com.pmtool.model.ProjectPut;
import com.pmtool.model.Task;


@Stateless
@Path("/Project")
public class restProject {

	@EJB
	private IProjectLocal metier;
	
	//http://localhost:18080/PMTool_MT/Project/id?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Project getProjectById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getProjectById(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Project/GetTasks/{id}?tenant_id=
	@GET
	@Path("/GetTasks/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getTasksByProject(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getTasksByProject(id, tenant_id);
	}
	
	
	//http://localhost:18080/PMTool_MT/Project?tenant_id=
	@GET
	//@Path("All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getAllProjects(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllProjects(tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Project?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createProject (@QueryParam("tenant_id") String tenant_id, ProjectPost p) throws JsonParseException, IOException
	{
		metier.createProject(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool_MT/Project?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteProject (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteProject(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Project?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateProject (@PathParam("id")int id,@QueryParam("tenant_id") String tenant_id, ProjectPut oldp) throws ClientProtocolException, IOException
	{
		metier.updateProject(tenant_id, id, oldp);
	}

}
