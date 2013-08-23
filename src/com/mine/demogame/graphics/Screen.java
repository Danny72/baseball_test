package com.mine.demogame.graphics;

import java.util.Random;

import com.mine.demogame.Game;

public class Screen extends Render {

	private Render test;
	private Render3D render3D;
	
	public Screen(int width, int height) {
		super(width, height);
		Random random = new Random();
		render3D = new Render3D(width, height);
		test = new Render(256, 256);
		for (int i = 0; i < 256 * 256; i++) {
			test.pixels[i] = random.nextInt() * (random.nextInt(10) / 4);
		}
	}

	public void render(Game game) {

		for (int i = 0; i < width * height; i++) {
			pixels[i] = 0;
		}

		render3D.floor(game);
		
		//xleft, xright, zdistance_left, zdistance_right, yheight
		/*
		render3D.render_wall(0, 0.5, 1.5, 1.5, 0);
		render3D.render_wall(0.5, 0, 1.5, 1.5, 0);
		
		render3D.render_wall(0, 0.5, 2, 2, 0);
		render3D.render_wall(0.5, 0, 2, 2, 0);
		
		render3D.render_wall(0, 0, 1.5, 2, 0);
		render3D.render_wall(0, 0, 2, 1.5, 0);
		
		render3D.render_wall(0.5, 0.5, 1.5, 2, 0);
		render3D.render_wall(0.5, 0.5, 2, 1.5, 0);
		
		
		render3D.render_wall(0, 1, 1.5, 1.5, 0);
		render3D.render_wall(1, 0, 1.5, 1.5, 0);
		
		render3D.render_wall(0, 1, 2.5, 2.5, 0);
		render3D.render_wall(1, 0, 2.5, 2.5, 0);
		
		render3D.render_wall(0, 0, 1.5, 2.5, 0);
		render3D.render_wall(0, 0, 2.5, 1.5, 0);
		
		render3D.render_wall(1, 1, 1.5, 2.5, 0);
		render3D.render_wall(1, 1, 2.5, 1.5, 0);
		*/
		
		render3D.render_distance_limiter();
		
		
		draw(render3D,0,0);
	}
}
