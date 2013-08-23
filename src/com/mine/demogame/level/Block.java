package com.mine.demogame.level;

import java.util.ArrayList;
import java.util.List;

import com.mine.demogame.graphics.Sprite;

public class Block {
	
	public boolean solid = false;
	
	public static Block solid_wall = new SolidBlock();
	
	public List<Sprite> sprites = new ArrayList<Sprite>();
	
	public void add_sprite(Sprite sprite) {
		sprites.add(sprite);
	}

}
