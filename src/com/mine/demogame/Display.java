package com.mine.demogame;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.mine.demogame.entity.Player;
import com.mine.demogame.graphics.Screen;
import com.mine.demogame.gui.Launcher;
import com.mine.demogame.input.InputHandler;

public class Display extends Canvas implements Runnable {

	private static final long serialVersionUID = 1L;

	public static int width = 800;
	public static int height = 600;

	private Thread thread;
	private Screen screen;
	private Game game;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private InputHandler input;
	private int old_x, new_x = 0;
	private int fps;
	public static int mouse_speed;
	public static int res_selection = 1;

	static Launcher launcher;

	public Display() {
		Dimension size = new Dimension(width, height);
		this.setPreferredSize(size);
		this.setMinimumSize(size);
		this.setMaximumSize(size);

		screen = new Screen(width, height);
		input = new InputHandler();
		game = new Game(input);
		img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		
		addKeyListener(input);
		addFocusListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
	}

	public static Launcher get_launcher_instance() {
		if (launcher == null) {
			launcher = new Launcher(0);
		}
		return launcher;
	}

	public synchronized void start() {
		if (running)
			return;

		running = true;
		thread = new Thread(this, "game");
		thread.start();

		System.out.println("Running...");
	}

	public synchronized void stop() {
		if (!running)
			return;

		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}

	public void run() {
		this.requestFocus();
		int frames = 0;
		long previous_time = System.nanoTime();
		double ns = 1000000000.0 / 60.0;
		double delta = 0.0;
		int updates = 0;
		long timer = System.currentTimeMillis();
		long current_time;
		
		/*updates is executed at a steady tick as it's internal game logic
		 *frames is calculated every second as it's how many frames the PC can generate
		 *in that elapsed second
		 */
		while (running) {
			current_time = System.nanoTime();
			delta += (current_time - previous_time) / ns;
			previous_time = current_time;
			if (delta >= 1) {
				updates++;
				tick();
				delta--;
			}
			
			frames++;
			render();
			
			//executes once a second
			while (System.currentTimeMillis() - timer > 1000) {
				//System.out.println("ups " + updates + "  fps " + frames);
				timer+=1000;
				fps = frames;
				frames = 0;
				updates = 0;
			}
			

			new_x = InputHandler.mouse_x;

			if (new_x > old_x) {
				Player.turn_right = true;
			}
			if (new_x < old_x) {
				Player.turn_left = true;
			}
			if (new_x == old_x) {
				Player.turn_left = false;
				Player.turn_right = false;
			}
			mouse_speed = Math.abs(new_x - old_x);
			old_x = new_x;
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}

		screen.render(game);

		for (int i = 0; i < width * height; i++) {
			pixels[i] = screen.pixels[i];
		}

		Graphics g = bs.getDrawGraphics();
		g.drawImage(img, 0, 0, width, height, null);
		
		g.setFont(new Font("Verdana", 0, 30));
		g.setColor(Color.white);
		g.drawString(Integer.toString(fps), 5, 25);
		
		g.setColor(Color.white);
		g.drawRect(420, 360, 80, 80);
		
		g.dispose();
		bs.show();
	}

	private void tick() {
		input.tick();
		game.tick();
	}

	public static void main(String[] args) {

		get_launcher_instance();

	}

}
