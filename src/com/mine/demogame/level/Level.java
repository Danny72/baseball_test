package com.mine.demogame.level;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mine.demogame.graphics.Sprite;
import com.mine.demogame.entity.Entity;

public class Level {

	public Block[] blocks;
	public final int width, height;
	final Random random = new Random();
	private List<Entity> entities = new ArrayList<Entity>();

	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		blocks = new Block[width * height];
		generate_level();
	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).tick();
		}
	}
	
	public void add_entity(Entity e) {
		entities.add(e);
	}

	public void generate_level() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Block block = null;
				if (random.nextInt(15) == 0) {
					block = new SolidBlock();
				} else {
					block = new Block();
					if (random.nextInt(15) == 0) {
						block.add_sprite(new Sprite(0, 0, 0));
					}

				}
				blocks[x + y * width] = block;
			}
		}
	}

	public Block create(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height) {
			return Block.solid_wall;
		}
		return blocks[x + y * width];
	}

}
