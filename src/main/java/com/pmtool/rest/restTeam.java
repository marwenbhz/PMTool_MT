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
import com.pmtool.ejb.ITeamLocal;
import com.pmtool.model.Banque;
import com.pmtool.model.Category;
import com.pmtool.model.Company;
import com.pmtool.model.Team;
import com.pmtool.model.User;
import com.pmtool.model.Task;


@Stateless
@Path("/Team")
public class restTeam {

	@EJB
	private ITeamLocal metier;
	
	//http://localhost:18080/PMTool_MT/Team?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Team getTeamById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getTeamById(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Team/GetMembers/{id}?tenant_id=
	@GET
	@Path("/GetMembers/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<User> getMembersByTask(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getUsersByTeam(id, tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Team?tenant_id=
	@GET
	//@Path("All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Team> getAllTeams(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllTeams(tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Team?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createTeam (@QueryParam("tenant_id") String tenant_id, Team p) throws JsonParseException, IOException
	{
		metier.createTeam(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool_MT/Team?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteTeam (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteTeam(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Team?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateTeam (@PathParam("id")int id,@QueryParam("tenant_id") String tenant_id, Team oldp) throws ClientProtocolException, IOException
	{
		metier.updateTeam(tenant_id, id, oldp);
	}

}
