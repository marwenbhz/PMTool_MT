package com.pmtool.rest;

import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/HelloWorld")
public class HelloWorld {

	//http://localhost:18080/PMTool_MT/HelloWorld/hello
	@GET
	@Path("hello")
	@Produces(MediaType.APPLICATION_JSON)
	public String helloworld()
	{
		return "Hello World !";
	}
	
	
}
