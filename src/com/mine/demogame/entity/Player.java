package com.mine.demogame.entity;

import com.mine.demogame.Display;
import com.mine.demogame.input.InputHandler;

public class Player extends Mob {

	public double y, rotation, xa, za, rotationa;
	public static boolean turn_left, turn_right = false;
	public static boolean walking = false;
	public static boolean player_crouched = false;
	public static boolean player_sprinting = false;
	private InputHandler input;
	
	public Player(InputHandler input) {
		this.input = input;
	}

	public void tick() {
		double rotation_speed = 0.01 * Display.mouse_speed;

		double jump_height = 0.5;
		double crouch_height = 0.2;
		int xa = 0;
		int za = 0;

		if (input.forward) {
			za++;
			walking = true;
		}
		if (input.back) {
			za--;
			walking = true;
		}
		if (input.right) {
			xa++;
			walking = true;
		}
		if (input.left) {
			xa--;
			walking = true;
		}

		if (xa != 0 || za != 0) {
			move(xa, za, rotation);
		}

		if (turn_left) {
			rotationa -= rotation_speed;
			// System.out.println(Display.mouse_speed);
		}
		if (turn_right) {
			rotationa += rotation_speed;
			// System.out.println(Display.mouse_speed);
		}
		if (input.jump) {
			y += jump_height;
			input.crouch = false;
			input.sprint = false;
		}
		if (input.crouch) {
			y -= crouch_height;
			walk_speed = 0.5;
			input.sprint = false;
			player_crouched = true;
		}
		if (input.sprint & (input.forward || input.back || input.left || input.right)) {
			walk_speed = 3;
			walking = true;
			player_sprinting = true;
		}

		if (!input.forward && !input.back && !input.left && !input.right && !input.sprint) {
			walking = false;
		}

		if (!input.crouch) {
			player_crouched = false;
		}

		if (!input.sprint) {
			player_sprinting = false;
		}

		y *= 0.9;

		rotation += rotationa;
		rotationa *= 0.2;
	}

	public void tick(boolean forward, boolean back, boolean left, boolean right, boolean jump, boolean crouch, boolean sprint) {

	}

}
