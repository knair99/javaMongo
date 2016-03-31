package com.nimma.jersey.client;


import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import java.util.ArrayList;


public class ClientSide {
	
	//Main method - init
	public static void main(String[] args) {
		
		MongoClientSide mcs = new MongoClientSide();
		
		//CRUD - create
		//mcs.CreateClientData(3, "asian food", "40% off", "mahatma rice");
		
		//CRUD - read
		ArrayList<String> items = mcs.ReadClientData();
		//Only push if there is data already
		if(items.size() > 0){
		
			pushToServer(items);
		}

	}
	
//Push data to server
public static void pushToServer(ArrayList<String> items_collections){
	try {
		
		String[]  items = new String [items_collections.size()];
		items_collections.toArray(items);
		
		//Setup client
		Client client = ClientBuilder.newClient();
		WebTarget target = client.target("http://localhost:8080/com.nimma.jersey.server/rest");
		
				
		for( String single_item: items){
			
			//Handle response
			System.out.println("JSON from client : " + single_item);
			Response r2 = target.path("/posts").request(MediaType.APPLICATION_JSON_TYPE).post(
					Entity.entity(single_item, MediaType.APPLICATION_JSON_TYPE));
	
			//Output
			System.out.println("Server response:");
			System.out.println(r2.readEntity(String.class));
			System.out.println("---------------------------");
		}

		
	} catch (Exception e) { //Worst case sanity check
			e.printStackTrace();
		} 
	}

}



