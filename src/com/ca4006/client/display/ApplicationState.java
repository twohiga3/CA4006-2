package com.ca4006.client.display;

import com.ca4006.property.Property;

public class ApplicationState {

	public enum PageType {
		MENU, LIST, NOTFOUND,
	}

	private PageType currentPage;
	private Property[] propertyList;
	private Property lastProperty;
	private String userID = "this-is-not-yet-setup";

	public ApplicationState() {
		currentPage = PageType.LIST;
	}

	public void changePage(PageType page) {
		currentPage = page;
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
