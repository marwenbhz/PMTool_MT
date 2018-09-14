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
import com.pmtool.ejb.ITaskLocal;
import com.pmtool.model.Banque;
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.Task;
import com.pmtool.model.TaskPost;
import com.pmtool.model.TaskPut;
import com.pmtool.model.User;
import com.pmtool.model.Task;


@Stateless
@Path("/Task")
public class restTask {

	@EJB
	private ITaskLocal metier;
	
	//http://localhost:18080/PMTool.rest/Task/id?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Task getTaskById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getTaskById(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool.rest/Task?tenant_id=
	@GET
	//@Path("All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Task> getAllTasks(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllTasks(tenant_id);
	}
	
	//http://localhost:18080/PMTool.rest/Task?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createTask (@QueryParam("tenant_id") String tenant_id, TaskPost p) throws JsonParseException, IOException
	{
		metier.createTask(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool.rest/Task?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTask (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteTask(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Task/GetMembers/{id}?tenant_id=
	@GET
	@Path("/GetMembers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getMembersByTask(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getUsersByTask(id, tenant_id);
	}
	
	//http://localhost:18080/PMTool.rest/Task?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTask (@PathParam("id")int id,@QueryParam("tenant_id") String tenant_id, TaskPut oldp) throws ClientProtocolException, IOException
	{
		metier.updateTask(tenant_id, id, oldp);
	}

}
