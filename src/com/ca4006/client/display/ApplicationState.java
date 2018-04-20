package com.ca4006.client.display;

import com.ca4006.property.Property;

public class ApplicationState {

	public enum PageType {
		MENU, LIST, NOTFOUND, PROPERTY_INFO
	}

	private PageType currentPage;
	int currentlyViewing;
	private Property[] propertyList;
	private Property lastProperty;
	private String userID = "this-is-not-yet-setup";

	public ApplicationState() {
		currentPage = PageType.PROPERTY_INFO;
		currentlyViewing = 0;
	}

	public Property[] getPropertyList(boolean refresh) {
		if (propertyList == null || refresh) {
			propertyList = PropertyHandler.getPropertyList();
		}
		return propertyList;
	}

	public Property getProperty(int id, boolean refresh) {
		if (!refresh && lastProperty != null && lastProperty.getID() == id) {
			return lastProperty;
		}
		Property p = PropertyHandler.getProperty(id);
		lastProperty = p;
		return p;
	}

	public PageType getPage() {
		return currentPage;
	}
}
