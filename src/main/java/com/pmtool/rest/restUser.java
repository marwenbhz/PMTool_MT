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
import com.pmtool.ejb.IUserLocal;
import com.pmtool.model.Banque;
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.User;
import com.pmtool.model.UserPost;
import com.pmtool.model.UserPut;
import com.pmtool.model.Task;


@Stateless
@Path("/User")
public class restUser {

	@EJB
	private IUserLocal metier;
	
	//http://localhost:18080/PMTool.rest/User/id?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getUserById(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool.rest/User?tenant_id=
	@GET
	//@Path("All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllUsers(tenant_id);
	}
	
	//http://localhost:18080/PMTool.rest/User?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createUser (@QueryParam("tenant_id") String tenant_id, UserPost p) throws JsonParseException, IOException
	{
		metier.createUser(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool.rest/User?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteUser (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteUser(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool.rest/User?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateUser (@PathParam("id")int id,@QueryParam("tenant_id") String tenant_id, UserPost oldp) throws ClientProtocolException, IOException
	{
		metier.updateUser(tenant_id, id, oldp);
	}

}
