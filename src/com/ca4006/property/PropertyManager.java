package com.ca4006.property;

import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.ejb.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.ca4006.property.Bid.Status;

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

	@POST
	@Path("bid")
	@Produces("text/xml")
	public Bid bid(InputStream in) {
		Bid bid = Bid.readBid(in);
		Property p = properties.get(bid.getPropertyID());
		if (p == null) {
			System.out.println("Property does not exist: " + bid.getPropertyID());
			return null;
		}

		System.out.println("Bid: " + bid);
		System.out.println("Bidding on Property: " + p);
		boolean success = p.bid(bid);
		bid.setStatus(success ? Status.ACCEPTED : Status.REJECTED);
		System.out.println("Bid: " + bid);
		return bid;
	}

}
