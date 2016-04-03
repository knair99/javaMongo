package com.nimma.jersey.server;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nimma.jersey.server.iotdatum.IOTDatum;

import java.util.ArrayList;
import java.util.HashMap;


@Path("/register")
public class Register {
	
	public static HashMap<Integer, String> clients = new HashMap<Integer, String>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postData(IOTDatum data) {
				
		String result = "";
		
		MongoServerSide mss = new MongoServerSide();
		
		ArrayList<String> dept_data = new ArrayList<String>();
		dept_data = mss.FindByDeptId(data.getid());
		
		//Get each item detail
		String[]  items = new String [dept_data.size()];
		dept_data.toArray(items);
		
		//Make one big string to send back to the client
		for( String single_item: items){
			result = result + "\n" + single_item;
		}
		result = "Hello IOT: " + data.getdeviceid() + " : Your discounts: " + result;

		
		return Response.status(201).entity(result).build();
	}

}
