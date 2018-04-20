package com.ca4006.property;

import java.io.InputStream;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Property {

	private int id;
	private String area;
	private String type;
	private int bedroomCount;
	private int bathroomCount;

	private int startingPrice;
	private int currentBid;

	@XmlTransient
	private Lock biddingLock;

	private static int propertyCount = 0;

	public Property() {
		super();
	}

	public Property(String area, String type, int bedroomCount, int bathroomCount, int startingPrice) {
		super();
		this.id = propertyCount++;
		this.area = area;
		this.type = type;
		this.bedroomCount = bedroomCount;
		this.bathroomCount = bathroomCount;
		this.startingPrice = startingPrice;

		this.biddingLock = new ReentrantLock();
	}

	public String toString() {
		return String.format("%2d: Area(%s), Type(%s), %d Bedroom(s), %d Bathroom(s)", id, area, type, bedroomCount,
				bathroomCount);
	}

	public int getID() {
		return id;
	}

	public String getArea() {
		return area;
	}

	public String getType() {
		return type;
	}

	public int getBedrooms() {
		return bedroomCount;
	}

	public int getBathrooms() {
		return bathroomCount;
	}

	public static Property readProperty(InputStream is) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(is);
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();

			// Property Fields
			String area = "UNKNOWN", type = "UNKNOWN";
			int bedrooms = 0, bathrooms = 0, startingPrice = 0;

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				if (element.getTagName().equals("area")) {
					area = element.getTextContent();
				} else if (element.getTagName().equals("type")) {
					type = element.getTextContent();
				} else if (element.getTagName().equals("bedrooms")) {
					bedrooms = Integer.parseInt(element.getTextContent());
				} else if (element.getTagName().equals("bathrooms")) {
					bathrooms = Integer.parseInt(element.getTextContent());
				} else if (element.getTagName().equals("startingPrice")) {
					startingPrice = Integer.parseInt(element.getTextContent());
				}
			}

			return new Property(area, type, bedrooms, bathrooms, startingPrice);
		} catch (Exception e) {
			System.err.println(e.getStackTrace());
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}

	public boolean bid(Bid b) {
		try {
			biddingLock.lock();
			if (b.getValue() > currentBid && b.getValue() >= startingPrice) {
				currentBid = b.getValue();
				return true;
			}
		} finally {
			biddingLock.unlock();
		}
		return false;
	}
}
