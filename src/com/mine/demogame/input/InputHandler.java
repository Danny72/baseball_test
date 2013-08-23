package com.mine.demogame.input;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, FocusListener, MouseMotionListener {

	public boolean[] key = new boolean[68836];
	public static int mouse_x;
	public static int mouse_y;
	public static int mouse_button;
	public static int drag_mousex;
	public static int drag_mousey;
	public static int press_mousex;
	public static int press_mousey;
	public static boolean dragged = false;
	
	public boolean forward, back, left, right, jump, crouch, sprint;

	public void tick() {
		forward = key[KeyEvent.VK_W];
		back = key[KeyEvent.VK_S];
		left = key[KeyEvent.VK_A];
		right = key[KeyEvent.VK_D];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_CONTROL];
		sprint = key[KeyEvent.VK_SHIFT];
	}

	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycode > 0 && keycode < key.length) {
			key[keycode] = true;
		}
	}

	public void keyReleased(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycode > 0 && keycode < key.length) {
			key[keycode] = false;
		}
	}

	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseDragged(MouseEvent e) {
		dragged = true;
		drag_mousex = e.getX();
		drag_mousey = e.getY();
	}

	public void mouseMoved(MouseEvent e) {
		mouse_x = e.getX();
		mouse_y = e.getY();

	}

	public void mouseClicked(MouseEvent e) {
		e.getButton();
	}

	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent e) {
		mouse_button = e.getButton();
		press_mousex = e.getX();
		press_mousey = e.getY();

	}

	public void mouseReleased(MouseEvent e) {
		mouse_button = 0;
		dragged = false;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void focusLost(FocusEvent e) {
		for (int i = 0; i < key.length; i++) {
			key[i] = false;
		}

	}

}
