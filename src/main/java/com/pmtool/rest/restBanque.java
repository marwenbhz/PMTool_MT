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
import javax.ws.rs.core.Response.Status;

import org.apache.http.client.ClientProtocolException;

import com.google.gson.JsonParseException;
import com.pmtool.ejb.IBanqueLocal;
import com.pmtool.model.Banque;
import com.pmtool.model.Project;


@Stateless
@Path("/Banque")
public class restBanque {

	@EJB
	private IBanqueLocal metier;
	
	//http://localhost:18080/PMTool_MT/Banque/5?tenant_id=
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getBanqueById(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		Banque c = metier.getBanqueById(tenant_id, id);
		return Response.status(Status.OK).entity(c).build();
	}
	
	//http://localhost:18080/PMTool_MT/Banque?tenant_id
	@GET
	//@Path("/All/{tenant_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Banque> getAllBanques(@QueryParam("tenant_id") String tenant_id)
	{
		return metier.getAllBanques(tenant_id);
	}

	//http://localhost:18080/PMTool_MT/Banque/GetProjects/{id}?tenant_id=
	@GET
	@Path("/GetProjects/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Project> getProjectsByBank(@PathParam("id") int id, @QueryParam("tenant_id") String tenant_id)
	{
		return metier.getProjectsByBank(id, tenant_id);
	}
	
	//http://localhost:18080/PMTool_MT/Banque?tenant_id=
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	//@Path("/Post/{tenant_id}")
	public void createBanque (@QueryParam("tenant_id") String tenant_id, Banque p) throws JsonParseException, IOException
	{
		metier.createBanque(tenant_id, p);
	}
	
	//http://localhost:18080/PMTool_MT/Banque/id?tenant_id=
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public void deleteBanque (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id) throws ClientProtocolException, IOException
	{
		metier.deleteBanque(tenant_id, id);
	}
	
	//http://localhost:18080/PMTool_MT/Banque/id?tenant_id=
	@PUT
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public void updateBanque (@PathParam("id")int id, @QueryParam("tenant_id") String tenant_id, Banque oldc) throws ClientProtocolException, IOException
	{
		metier.updateBanque(tenant_id, id, oldc);
	}

}
