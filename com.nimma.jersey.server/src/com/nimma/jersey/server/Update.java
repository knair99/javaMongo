package com.nimma.jersey.server;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.nimma.jersey.server.clientdatum.ClientDatum;
import com.nimma.jersey.server.iotdatum.IOTDatum;

import java.util.ArrayList;
import java.util.HashMap;


@Path("/update") //handles delete as well
public class Update {

	
	public static HashMap<Integer, String> iots = new HashMap<Integer, String>();
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response postData(ClientDatum data) {
				
		String result = "Updated";
		
		MongoServerSide mss = new MongoServerSide();
		
		ArrayList<String> dept_data = new ArrayList<String>();
		
		dept_data = mss.UpdateServerData(data.getitemname(), data.getdiscount());
		
		//For alerting IOTs
		iots.put(data.getid(), dept_data.toString());
		System.out.println(iots);
		System.out.println(this);

		
		return Response.status(201).entity(result).build();
	}
	
	@POST
	@Path("delete")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response deleteData(ClientDatum data) {
				
		String result = "Updated";
		
		MongoServerSide mss = new MongoServerSide();
		
		ArrayList<String> dept_data = new ArrayList<String>();
		
		dept_data = mss.DeleteServerData(data.getitemname());
		
		//For alerting IOTs
		iots.put(data.getid(), dept_data.toString() + "<--- has been deleted");
		System.out.println(iots);
		System.out.println(this);

		
		return Response.status(201).entity(result).build();
	}
	
	@POST
	@Path("iot")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateIOTs(IOTDatum data) {
				
		String result = "";
		System.out.println(this);

						
		int iot_dept_id = data.getid(); //this is the dept id of the iot
		String new_data;
		System.out.println(iots);
		if(iots.isEmpty()){
			new_data = "No new updates for you.";
		}
		else{
			new_data = iots.get(iot_dept_id);
			if(new_data == null){
				new_data = "none";
			}
			System.out.println("dept id " + iot_dept_id+ " -> " + new_data);
			iots.remove(iot_dept_id);
		}
		
		result = "Hello IOT: " + data.getdeviceid() + " : Here are your recent dept updates: " + new_data;
		return Response.status(201).entity(result).build();
	}

}
