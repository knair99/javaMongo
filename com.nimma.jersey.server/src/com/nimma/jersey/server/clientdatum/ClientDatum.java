package com.nimma.jersey.server.clientdatum;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/*
JSON for sales schema
{
   "id" : 1,
   "item_name" : "Every Man Jack",
   "dept_name" : "Personal Care",
   "discount" : "50% off"
}

*/

@XmlRootElement
public class ClientDatum {
	
	private int id; //Department ID
	private String item_name; //Name of item
	private String dept_name;
	private String discount;
	
	
	//Dummy c-tor 
	public ClientDatum(){
		
	}
	
	@JsonCreator
	public ClientDatum (@JsonProperty("id") int id, @JsonProperty("item_name") String s,
						@JsonProperty("dept_name") String d, @JsonProperty("discount") String dis){ 
		this.id = id;
		this.item_name = s;
		this.discount = dis;
		this.dept_name = d;
	}
	
	public void setid(int id){
		this.id = id;
	}
	
	public int getid() {
		return id;
	}
	
	public String getitemname(){
		return item_name;
	}
	
	public void setitemname(String s){
		this.item_name = s;
	}
	
	public String getdeptname(){
		return dept_name;
	}
	
	public void setdeptname(String s){
		this.dept_name = s;
	}
	
	public String getdiscount(){
		return discount;
	}
	
	public void setdiscount(String s){
		this.discount = s;
	}
	
	@Override
	public String toString(){
		return "{" 
				+"\"id\": " + id 
				+ "," + "\"item_name\": \"" + item_name + "\""
				+ "," + "\"dept_name\": \"" +  dept_name + "\""
				+ "," +  "\"discount\": \"" + discount + "\""
				+ "}";
	}
	

}



