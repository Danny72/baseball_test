package com.mine.demogame;

import com.mine.demogame.entity.Player;
import com.mine.demogame.input.InputHandler;
import com.mine.demogame.level.Level;

public class Game {
	
	public int time;
	public Player player;
	public Level level;
	
	public Game(InputHandler input) {
		player = new Player(input);
		level = new Level(50,50);
		level.add_entity(player);
	}
	
	public void tick() {
		
		time++;
		level.update();
	}

}
