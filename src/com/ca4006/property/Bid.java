package com.ca4006.property;

import java.io.InputStream;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Bid {

	enum Status {
		ACCEPTED, REJECTED, PENDING
	}

	private int propertyID;
	private int value;
	private Status status;

	public Bid() {
		super();
	}

	public Bid(int propertyID, int value) {
		super();
		this.propertyID = propertyID;
		this.value = value;
		this.status = Status.PENDING;
	}

	public String toString() {
		return String.format("[P: %d, V: %d, S: %s]", propertyID, value, status);
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public int getPropertyID() {
		return propertyID;
	}

	public int getValue() {
		return value;
	}

	public static Bid readBid(InputStream in) {
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			Document doc = builder.parse(in);
			Element root = doc.getDocumentElement();
			NodeList nodes = root.getChildNodes();

			// Bid fields
			int propertyID = -1, value = 0;

			System.out.println(root.getTagName());

			for (int i = 0; i < nodes.getLength(); i++) {
				Element element = (Element) nodes.item(i);

				if (element.getTagName().equals("propertyID")) {
					propertyID = Integer.parseInt(element.getTextContent());
				} else if (element.getTagName().equals("value")) {
					value = Integer.parseInt(element.getTextContent());
				}
			}

			if (propertyID == -1) {
				throw new WebApplicationException("Invalid Property ID", Response.Status.BAD_REQUEST);
			}

			return new Bid(propertyID, value);
		} catch (Exception e) {
			throw new WebApplicationException(e, Response.Status.BAD_REQUEST);
		}
	}
}