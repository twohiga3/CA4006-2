package com.ca4006.client.display;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.ca4006.client.display.ApplicationState.PageType;
import com.ca4006.property.Property;

public class Display extends JPanel implements Runnable {
	ApplicationState applicationState;

	public Display() {
		applicationState = new ApplicationState();

		repaint();
	}

	@Override
	public void run() {
		while (true) {
			repaint();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(new Color(150, 150, 150));

		if (applicationState.getPage() == PageType.LIST) {
			renderPropertyList(g);
		} else if (applicationState.getPage() == PageType.PROPERTY_INFO) {
			renderProperty(g);
		} else {
			render404(g);
		}

	}

	public void renderProperty(Graphics g) {
		g.setColor(Color.RED);

		// TODO: When switching to a property_view page make it set the ID
		Property p = applicationState.getProperty(applicationState.currentlyViewing, false);

		int x = 100;
		int y = 32;

		g.setColor(Color.black);
		g.drawRect(x, y, Frame.WIDTH - (100 * 2), 380);

		g.setColor(Color.WHITE);
		g.fillRect(x + 5, y + 5, 190, 190);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 200, 200);
		g.drawRect(x + 5, y + 5, 190, 190);

		g.drawString(String.format("Area: %s", p.getArea()), x + 210, y + 20);
		g.drawString(String.format("Type: %s", p.getType()), x + 210, y + 40);
		g.drawString(String.format("Bedrooms: %d", p.getBedrooms()), x + 210, y + 60);
		g.drawString(String.format("Bathrooms: %d", p.getBathrooms()), x + 210, y + 80);
	}

	public void renderPropertyList(Graphics g) {
		int height = 124;
		g.setColor(Color.BLUE);

		Property[] list = applicationState.getPropertyList(false);
		for (int i = 0; i < list.length; i++) {
			renderPropertyListElement(g, list[i], 100, 32 + height * (i));
		}
	}

	public void renderPropertyListElement(Graphics g, Property p, int x, int y) {
		g.setColor(Color.black);
		g.drawRect(x, y, Frame.WIDTH - (100 * 2), 100);

		g.setColor(Color.WHITE);
		g.fillRect(x + 5, y + 5, 90, 90);
		g.setColor(Color.BLACK);
		g.drawRect(x, y, 100, 100);
		g.drawRect(x + 5, y + 5, 90, 90);

		// Draw image of the house here.
		// g.drawImage(...);

		// Describe the Property
		g.drawString(String.format("Area: %s", p.getArea()), x + 110, y + 20);
		g.drawString(String.format("Type: %s", p.getType()), x + 110, y + 40);
		g.drawString(String.format("Bedrooms: %d", p.getBedrooms()), x + 110, y + 60);
		g.drawString(String.format("Bathrooms: %d", p.getBathrooms()), x + 110, y + 80);
	}

	public void render404(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString("404 - Page not found", 32, 32);
	}

}
