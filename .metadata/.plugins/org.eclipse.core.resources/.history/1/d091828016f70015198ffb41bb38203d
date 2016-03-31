package com.nimma.jersey.server;


import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nimma.jersey.server.clientdatum.*;

import java.util.ArrayList;
import java.util.HashMap;



@Path("/posts")
public class Post {
	
	public static HashMap<Integer, String> clients = new HashMap<Integer, String>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postData(ClientDatum data) {
				
		String previous_data = clients.get(data.getid());
		if(previous_data == null){
			previous_data = "";
			clients.put(data.getid(), ""); //just initialize if no previous data
		}
		
		String new_data = previous_data + " | " + data.getitemname();
		clients.put(data.getid(), new_data);
		
		String result = "Hello client: " + data.getid() + " : Your data so far: " + new_data;
		result = result + " Your discount: " + data.getdiscount();
		
		//Save data into db
		MongoServerSide mss = new MongoServerSide();
		mss.CreateServerData(data.getid(),data.getdeptname(), data.getdiscount(), data.getitemname());

		
		
		return Response.status(201).entity(result).build();
		
	}

}




