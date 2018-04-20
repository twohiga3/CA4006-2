package com.ca4006.property;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("property")
@Singleton
public class PropertyManager {

	private ConcurrentHashMap<Integer, Property> properties = new ConcurrentHashMap<>();

	@POST
	@Path("create")
	@Produces("text/xml")
	public Property createProperty(InputStream in) {
		Property p = Property.readProperty(in);
		properties.put(p.getID(), p);
		return p;
	}

	@GET
	@Path("get/{id: [0-9]*}")
	@Produces("text/xml")
	public Property getPropertyByID(@PathParam("id") int id) {
		System.out.println("ID Requested: " + id);
		if (properties.containsKey(id)) {
			return properties.get(id);
		}
		return null;
	}

	@GET
	@Path("list")
	@Produces("text/xml")
	// TODO: Add a count + offset to this?
	// TODO: Create getSortedList or getFilteredList that filter/sort data before
	// returning?
	public Property[] getPropertyList() {
		Property[] list = new Property[properties.size()];
		properties.values().toArray(list);
		return list;
	}

}
