package com.ca4006.client.display;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

public class Main {

	public static void main(String[] args) {
		Display display = new Display();

		JScrollPane scrollPane = new JScrollPane(display);
		scrollPane.setPreferredSize(new Dimension(Frame.WIDTH, Frame.HEIGHT));
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setBounds(50, 30, 300, 50);

		JFrame frame = Frame.create(display);
		frame.add(scrollPane);
		frame.setVisible(true);
		new Thread(display).start();
	}

}
