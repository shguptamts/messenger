package org.shgup.ss.messenger.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

@Path("/demo")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class InjectDemoResource {
	
	@GET
	@Path("/annotation")
	public String getParamsUsingAnnotations(@MatrixParam("param") String matrixParam,
											 @CookieParam("auth") String cookieParam,
											 @HeaderParam("value") String headerParam) {
		return "Matrix param is "+ matrixParam +"  Cookie param: "+ cookieParam +" Header param: "+ headerParam;
	} 
	
	
	@GET
	@Path("/context")
	public String getContext(@Context UriInfo uriInfo , @Context HttpHeaders httpHeader) {
		
		// HttpHeaders can be used to get headers details
		//httpHeader.getRequestHeaders();
		
		return uriInfo.getAbsolutePath().toString();
		
	}

}
