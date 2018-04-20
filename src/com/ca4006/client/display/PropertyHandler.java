package com.ca4006.client.display;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;

import com.ca4006.property.Property;

public class PropertyHandler {

	private static final String DOMAIN_PATH = "http://localhost:8080/CA4006";
	static Client client = ClientBuilder.newClient();

	public static void createProperty(String area, String type, int bedrooms, int bathrooms) {
		String xml = "<property>" + "<area>" + area + "</area>" + "<type>" + type + "</type>" + "<bedrooms>" + bedrooms
				+ "</bedrooms>" + "<bathrooms>" + bathrooms + "</bathrooms>" + "</property>";
		Response response = client.target(DOMAIN_PATH + "/property/create").request().post(Entity.xml(xml));
		System.out.println(response.getStatus());
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed to create property");
		}
	}

	public static Property getProperty(int id) {
		Property response = client.target(DOMAIN_PATH + "/property/get/" + id).request().get(Property.class);
		System.out.println("\t* " + response);
		return response;
	}

	public static Property[] getPropertyList() {
		Property[] response = client.target(DOMAIN_PATH + "/property/list").request().get(Property[].class);
		System.out.println("Properties retrieved: " + response.length);
		for (Property p : response) {
			System.out.println("\t* " + p);
		}
		return response;
	}
}
