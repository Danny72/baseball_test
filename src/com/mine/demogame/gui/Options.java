package com.mine.demogame.gui;

import java.awt.Choice;
import java.awt.Dimension;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import com.mine.demogame.Configuration;
import com.mine.demogame.Display;

public class Options extends JFrame {

	private static final long serialVersionUID = 1L;

	private int width = 500;
	private int height = 500;
	private JButton ok;
	private Rectangle rok;
	private Choice resolution = new Choice();
	private Rectangle rresolution;
	private JTextField twidth, theight;
	private JLabel lwidth, lheight;
	protected int button_width = 80;
	protected int button_height = 40;
	private JPanel window = new JPanel();
	Configuration config = new Configuration();

	public Options() {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.setTitle("Options");
		this.setSize(new Dimension(width, height));
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		this.getContentPane().add(window);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		window.setLayout(null);
		draw_buttons();
		repaint();
	}

	private void draw_buttons() {
		ok = new JButton("OK");
		ok.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				config.save_config("width", parse_width());
				config.save_config("height", parse_height());
				dispose();
			}
		});
		
		lwidth = new JLabel("Width");
		lwidth.setBounds(50, 150, 80, 20);
		window.add(lwidth);
		
		twidth = new JTextField();
		twidth.setBounds(90, 150, 60, 20);
		window.add(twidth);
		
		lheight = new JLabel("Height");
		lheight.setBounds(50, 180, 80, 20);
		window.add(lheight);
		
		theight = new JTextField();
		theight.setBounds(90, 180, 60, 20);
		window.add(theight);

		rok = new Rectangle(width - 100, height - 70, button_width, button_height - 10);
		ok.setBounds(rok);
		window.add(ok);

		rresolution = new Rectangle(50, 50, 80, 25);
		resolution.setBounds(rresolution);
		resolution.add("600, 450");
		resolution.add("800, 600");
		resolution.add("1024, 768");
		resolution.select(1);
		window.add(resolution);

	}
	
	private void drop() {
		int res_selection = resolution.getSelectedIndex();
		if (res_selection == 0) {
			width = 640;
			height = 480;
		}
		if (res_selection == 1 || res_selection == -1) {
			width = 800;
			height = 600;
		}
		if (res_selection == 2) {
			width = 1024;
			height = 768;
		}
		
	}
	
	private int parse_width() {
		try {
			int w = Integer.parseInt(twidth.getText());
			return w;
		} catch (NumberFormatException e) {
			drop();
			return width;
		}
	}
	
	private int parse_height() {
		try {
			int h = Integer.parseInt(theight.getText());
			return h;
		} catch (NumberFormatException e) {
			drop();
			return height;
		}
	}
	
	

}
