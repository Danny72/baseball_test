package com.mine.demogame.gui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.mine.demogame.Configuration;
import com.mine.demogame.RunGame;
import com.mine.demogame.input.InputHandler;

public class Launcher extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	private JButton play, options, help, about, quit;
	private Rectangle rplay, roptions, rhelp, rabout, rquit;
	Configuration config = new Configuration();

	private int width = 800;
	private int height = 400;
	protected int button_width = 80;
	protected int button_height = 40;
	Thread thread;
	JFrame frame;
	boolean running = false;

	public Launcher(int id) {

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		frame = new JFrame();

		frame.setUndecorated(true);
		frame.setTitle("Demogame launcher");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(3);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setResizable(false);
		window.setLayout(null);
		
		if (id == 0) {
			//draw_buttons();
		}

		InputHandler input = new InputHandler();
		frame.addKeyListener(input);
		frame.addFocusListener(input);
		frame.addMouseListener(input);
		frame.addMouseMotionListener(input);
		start_menu();
		repaint();
	}

	public void update_frame() {

		int x = frame.getX();
		int y = frame.getY();

		if (InputHandler.dragged) {
			frame.setLocation(x + InputHandler.drag_mousex - InputHandler.press_mousex, y + InputHandler.drag_mousey - InputHandler.press_mousey);
		}
	}

	public void start_menu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
	}

	public void stop_menu() {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		while (running) {
			render_menu();
			update_frame();
		}
	}

	private void render_menu() {
		BufferStrategy bs = frame.getBufferStrategy();
		if (bs == null) {
			frame.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 800, 400);
		try {
			g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/menu_background.png")), 0, 0, 800, 400, null);
			if (InputHandler.mouse_x >= 680 && InputHandler.mouse_x <= 800) {
				if (InputHandler.mouse_y >= 155 && InputHandler.mouse_y <= 185) {
					g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 155, 25, 25, null);
					if (InputHandler.mouse_button == 1) {
						System.out.println("play");
						config.load_config("res/settings/config.xml");
						frame.dispose();
						new RunGame();
						InputHandler.mouse_button = 0;
					}
				}
				if (InputHandler.mouse_y >= 185 && InputHandler.mouse_y <= 205) {
					g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 185, 25, 25, null);
					if (InputHandler.mouse_button == 1) {
						System.out.println("options");
						new Options();
						InputHandler.mouse_button = 0;	
					}
				}
				if (InputHandler.mouse_y >= 205 && InputHandler.mouse_y <= 235) {
					g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 215, 25, 25, null);
					if (InputHandler.mouse_button == 1) {
						System.out.println("help");
					}
				}
				if (InputHandler.mouse_y >= 235 && InputHandler.mouse_y <= 270) {
					g.drawImage(ImageIO.read(Launcher.class.getResource("/menu/arrow.png")), 770, 245, 25, 25, null);
					if (InputHandler.mouse_button == 1) {
						System.exit(0);
					}
				}
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		g.setFont(new Font("Verdana", 0, 16));
		g.setColor(Color.black);
		g.drawString("Play", 800 - 100, 175);
		g.drawString("Options", 800 - 100, 205);
		g.drawString("Help", 800 - 100, 235);
		g.drawString("Quit", 800 - 100, 265);
		g.dispose();
		bs.show();
	}

	private void draw_buttons() {

		play = new JButton("Play");
		play.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				config.load_config("res/settings/config.xml");
				//new RunGame(this);
				frame.dispose();
			}
		});
		rplay = new Rectangle((width / 2) - (button_width / 2), 50, button_width, button_height);
		play.setBounds(rplay);
		window.add(play);

		options = new JButton("Options");
		options.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new Options();
				frame.dispose();
			}
		});
		roptions = new Rectangle((width / 2) - (button_width / 2), 100, button_width, button_height);
		options.setBounds(roptions);
		window.add(options);

		help = new JButton("Help");
		help.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello help");
			}
		});
		rhelp = new Rectangle((width / 2) - (button_width / 2), 150, button_width, button_height);
		help.setBounds(rhelp);
		window.add(help);

		about = new JButton("About");
		about.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Hello about");
			}
		});
		rabout = new Rectangle((width / 2) - (button_width / 2), 200, button_width, button_height);
		about.setBounds(rabout);
		window.add(about);

		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		rquit = new Rectangle((width / 2) - (button_width / 2), 250, button_width, button_height);
		quit.setBounds(rquit);
		window.add(quit);
	}
}
