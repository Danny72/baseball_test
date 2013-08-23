package com.mine.demogame.graphics;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Texture {
	public static Render floor = load_bitmap("/textures/tex3.png");
	
	public static Render load_bitmap(String filename) {
		try { 
			BufferedImage image= ImageIO.read(Texture.class.getResource(filename));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0,0,width,height, result.pixels, 0, width);
			return result;
		}
		catch(Exception e) {
			System.out.println("Texture not loaded");
			throw new RuntimeException(e);
			
		}
		
	}
}
