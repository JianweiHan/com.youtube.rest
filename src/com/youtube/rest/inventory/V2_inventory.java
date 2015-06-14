package com.youtube.rest.inventory;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jettison.json.JSONArray;

import com.youtube.dao.SchemaMySql;

@Path("/v2/inventory")
public class V2_inventory {
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {
		String returnString = null;
		JSONArray json = new JSONArray();
		try{
			if(brand == null){
				return Response.status(400).entity("Error: please specify brand for this search").build();
			}
			SchemaMySql dao = new SchemaMySql();
			
			json = dao.queryReturnbrandParts(brand);
			returnString = json.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
		
    }

/*
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrandParts(@QueryParam("brand") String brand) throws Exception {

		return Response.status(400).entity("Error: please specify brand for this search").build();
		
    }
*/	

	@Path("/{brand}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response returnBrand(@PathParam("brand") String brand) throws Exception{
		String returnString = null;
		JSONArray json = new JSONArray();
		
		try{

			SchemaMySql dao = new SchemaMySql();
			
			json = dao.queryReturnbrandParts(brand);
			returnString = json.toString();
			
		}
		catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		return Response.ok(returnString).build();
	}
	
	
	
	@POST
	@Consumes({MediaType.APPLICATION_FORM_URLENCODED,MediaType.APPLICATION_JSON})
	//@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPcParts(String incomingData) throws Exception {
		
		String returnString = null;
		//JSONArray jsonArray = new JSONArray(); //not needed
		SchemaMySql dao = new SchemaMySql();
		
		try {
			System.out.println("incomingData: " + incomingData);
			
			/*
			 * ObjectMapper is from Jackson Processor framework
			 * http://jackson.codehaus.org/
			 * 
			 * Using the readValue method, you can parse the json from the http request
			 * and data bind it to a Java Class.
			 */
			ObjectMapper mapper = new ObjectMapper();  
			ItemEntry itemEntry = mapper.readValue(incomingData, ItemEntry.class);
			
			int http_code = dao.insertIntoPC_PARTS(itemEntry.DEPT_NO, 
													itemEntry.DEPT_NAME, 
													itemEntry.LOCATION);
			
			if( http_code == 200 ) {
				//returnString = jsonArray.toString();
				returnString = "Item inserted";
			} else {
				return Response.status(500).entity("Unable to process Item").build();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(500).entity("Server was not able to process your request").build();
		}
		
		return Response.ok(returnString).build();
	}
}
class ItemEntry{
	public String DEPT_NO;
	public String DEPT_NAME;
	public String LOCATION;
}
