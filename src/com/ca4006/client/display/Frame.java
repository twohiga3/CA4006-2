package com.ca4006.client.display;

import javax.swing.JFrame;

public class Frame {

	public static final int WIDTH = 640;
	public static final int HEIGHT = 480;

	public static JFrame create(Display display) {
		JFrame frame = new JFrame();
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		return frame;
	}
}
