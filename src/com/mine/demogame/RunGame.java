package com.mine.demogame;

import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class RunGame {

	public RunGame() {
		// create custom blank cursor for the frame
		BufferedImage cursor = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0, 0), "blank");

		Display game = new Display();

		JFrame frame = new JFrame();
		frame.add(game);
		//frame.getContentPane().setCursor(blank);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setTitle("Demogame 0.05");
		frame.setSize(Display.width, Display.height);
		
		game.start();
		stop_menu_thread();
	}
	
	private void stop_menu_thread() {
		Display.get_launcher_instance().stop_menu();
	}

}
