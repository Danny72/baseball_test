package com.mine.demogame.graphics;

import com.mine.demogame.Game;
import com.mine.demogame.entity.Player;
import com.mine.demogame.input.InputHandler;
import com.mine.demogame.level.Block;
import com.mine.demogame.level.Level;

public class Render3D extends Render {

	public double[] zbuffer;
	private double render_distance = 30000;
	private double forward, right, up, cosine, sine;
	double head_movement;
	private int sprite_sheet_w = 256;
	private int anim = 0;
	private int spr_x = 0;
	private int spr_y = 32;
	private boolean swinging = false;

	public Render3D(int width, int height) {
		super(width, height);
		zbuffer = new double[width * height];
	}

	public void floor(Game game) {
		// Math.sin(game.time % 1000.0 / 50);
		double rotation = 0;//game.player.rotation;
		cosine = Math.cos(rotation);
		sine = Math.sin(rotation);

		int floor_pos = 8;
		int ceiling_pos = 150;

		forward = game.player.z / 5.0;
		right = game.player.x / 5.0;
		up = game.player.y;

		anim = (++anim) % 7500;
		
		if (InputHandler.mouse_button == 1) {
			anim = 0;
			swinging = true;
		}
		
		int num = 25;
		int divisor = num / 8;
		int temp = 64;
		
		if (!swinging) {
			spr_x = 0;
			spr_y = 32;
		}
		
		if (swinging) {
			if (anim % num >= divisor * 1 && anim % num < divisor * 2) {
				spr_x = 192-temp;
				spr_y = 96;
			}
			if (anim % num >= divisor * 2 && anim % num < divisor * 3) {
				spr_x = 64-temp;
				spr_y = 160;
			}
			if (anim % num >= divisor * 3 && anim % num < divisor * 4) {
				spr_x = 192-temp;
				spr_y = 160;
			}
			if (anim % num >= divisor * 4 && anim % num < divisor * 5) {
				spr_x = 64-temp;
				spr_y = 224;
			}
			if (anim % num >= divisor * 5 && anim % num < divisor * 6) {
				spr_x = 192-temp;
				spr_y = 224;
			}
			if (anim % num >= divisor * 6 && anim % num < divisor * 7) {
				spr_x = 64-temp;
				spr_y = 288;
			}
			if (anim % num >= divisor * 7 && anim % num < divisor * 8) {
				spr_x = 192-temp;
				spr_y = 288;
			}
			if (anim > num && anim < num + 10) {
				spr_x = 192-temp;
				spr_y = 288;
			}
			if (anim > num + 10) {
				spr_x = 0;
				spr_y = 32;
				swinging = false;
			}
			
		}
		

		head_movement = 0;

		double ceiling;
		double z;
		double depth;

		for (int y = 0; y < height; y++) {

			ceiling = (y - height / 2.0) / height;

			// defines players head bobbing movement, dependent on moving style
			if (Player.player_crouched && Player.walking) {
				head_movement = Math.sin(game.time / 6.0) * 0.1;
				z = (floor_pos * up + head_movement / ceiling);
			}
			if (Player.player_sprinting) {
				head_movement = Math.sin(game.time / 6.0) * 0.6;
				z = (floor_pos * up + head_movement / ceiling);
			}
			if (Player.walking) {
				head_movement = Math.sin(game.time / 6.0) * 0.3;
				z = (floor_pos * up + head_movement / ceiling);
			}

			// if moving, add in head bobbing movement
			if (Player.walking) {
				z = (floor_pos + up + head_movement) / ceiling;
			} else {
				z = (floor_pos + up) / ceiling;
			}

			if (ceiling < 0) {
				if (Player.walking) {
					z = (ceiling_pos - up - head_movement) / -ceiling;
				} else {
					z = (ceiling_pos - up) / -ceiling;
				}
			}

			for (int x = 0; x < width; x++) {

				depth = (x - width / 2.0) / height;
				depth *= z;
				double xx = depth * cosine + z * sine;
				double yy = z * cosine - depth * sine;
				int xpix = (int) ((xx + right) * 4);
				int ypix = (int) ((yy + forward) * 4);
				zbuffer[x + y * width] = z;
				//if less than equator, use sky texture, else use floor
				if (x + y * width < (width * height / 2)) {
					pixels[x + y * width] = Texture.floor.pixels[((xpix & 31) + 96) + (ypix & 31) * sprite_sheet_w];
				} else {
					pixels[x + y * width] = Texture.floor.pixels[(xpix & 31) + (ypix & 31) * sprite_sheet_w];
				}
			}
		}

		Level level = game.level;
		int size = 25;
		double gap = 1.0;

		int z_point = 35;
		int space = 2;
		int multiple = 2;

		//back
		for (int i = -6; i < 5; i += 2) {
			render_wall(i, i + 2, z_point, z_point, 0);
			render_wall(i, i + 2, z_point, z_point, 1);
			render_wall(i, i + 2, z_point, z_point, 2);
		}
		//left
		space = 6;
		for (int i = -2; i > -10; i -= 2) {
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 0);
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 1);
			render_wall(-space + i, -space + i + 2, z_point + i, z_point + i + 2, 2);
		}
		//right
		for (int i = 2; i < 10; i += 2) {
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 0);
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 1);
			render_wall(space + i - 2, space + i, z_point - i + 2, z_point - i, 2);
		}
		//left vert
		multiple = -8;
		for (int i = -8; i > -16; i -= 2) {
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 0);
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 1);
			render_wall(-space + multiple, -space + multiple, z_point + i - 2, z_point + i, 2);
		}
		//right vert
		multiple = 8;
		for (int i = 8; i < 16; i += 2) {
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 0);
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 1);
			render_wall(space + multiple, space + multiple, z_point - i, z_point - i - 2, 2);
		}

		render_sprite(-0.5, 0.0, 2.5, 0.5);

	}

	public void render_sprite(double x, double y, double z, double h_off) {

		double up_correct = -0.062;
		double right_correct = 0.062;
		double forward_correct = 0.062;
		double walk_correct = 0.062;

		//adjusts the x,y,z for various movements
		double xc = ((x / 2) - (right * right_correct)) * 2 + h_off;
		double yc = ((y / 2) - (up * up_correct) + (head_movement * walk_correct)) * 2 + h_off;
		double zc = ((z / 2) - (forward * forward_correct)) * 2;

		//Calculates the rotation on the sprite for x,y,z
		double rot_x = xc * cosine - zc * sine;
		double rot_y = yc;
		double rot_z = zc * cosine + xc * sine;

		//works out the centre point
		double x_centre = width / 2;
		double y_centre = height / 2;

		//fix: height instead of width
		double xpixel = rot_x / rot_z * height + x_centre;
		double ypixel = rot_y / rot_z * height + y_centre;

		double x_pixell = xpixel - (height) / rot_z;
		double x_pixelr = xpixel + (height) / rot_z;
		double y_pixell = ypixel - (height / 2) / rot_z;
		double y_pixelr = ypixel + (height / 2) / rot_z;
		
		int xpl = (int) x_pixell;
		int xpr = (int) x_pixelr;
		int ypl = (int) y_pixell;
		int ypr = (int) y_pixelr;
		
		if (xpl < 0) {
			xpl = 0;
		}
		if (xpr > width) {
			xpr = width;
		}
		if (ypl < 0) {
			ypl = 0;
		}
		if (ypr > height) {
			ypr = height;
		}

		rot_z *= 8;

		for (int yp = ypl; yp < ypr; yp++) {
			// formats a variable for accepting a texture
			double pixel_rotationy = (yp - y_pixelr) / (y_pixell - y_pixelr);
			int ytexture = (int) (pixel_rotationy * 64);
			for (int xp = xpl; xp < xpr; xp++) {
				double pixel_rotationx = (xp - x_pixelr) / (x_pixell - x_pixelr);
				int xtexture = (int) (pixel_rotationx * 128);
				
				if (zbuffer[xp + yp * width] > rot_z) {
					//System.out.println(spr_x + ":" + spr_y);
					if (((xtexture & 127) + spr_x) + ((ytexture & 63) + spr_y) * sprite_sheet_w >= 90112) continue;
					int colour = Texture.floor.pixels[((xtexture & 127) + spr_x) + ((ytexture & 63) + spr_y) * sprite_sheet_w];
					//transparency using pink and ARGB
					if (colour != 0xFFFF00FF) {
						pixels[xp + yp * width] = colour;
						zbuffer[xp + yp * width] = rot_z;
					}

				}
			}
		}

	}

	public void render_wall(double xleft, double xright, double zdistance_left, double zdistance_right, double yheight) {

		double up_correct = 0.062;
		double right_correct = 0.062;
		double forward_correct = 0.062;
		double walk_correct = 0.062;

		// as each block is 0.5, need to half wall co-ordinates
		//xleft /= 2; xright /= 2; zdistance_left /= 2; zdistance_right /= 2;
		yheight /= 2;

		double xc_left = ((xleft) - (right * right_correct)) * 2;
		double zc_left = ((zdistance_left) - (forward * forward_correct)) * 2;

		double rot_left_side_x = xc_left * cosine - zc_left * sine;
		double ycorner_tl = ((-yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double ycorner_bl = ((+0.5 - yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double rot_left_side_z = zc_left * cosine + xc_left * sine;

		double xc_right = ((xright) - (right * right_correct)) * 2;
		double zc_right = ((zdistance_right) - (forward * forward_correct)) * 2;

		double rot_right_side_x = xc_right * cosine - zc_right * sine;
		double ycorner_tr = ((-yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double ycorner_br = ((+0.5 - yheight) - (-up * up_correct) + (head_movement * walk_correct)) * 2;
		double rot_right_side_z = zc_right * cosine + xc_right * sine;

		// Clipping

		double tex30 = 0;
		double tex40 = 32;
		double clip = 0.5;

		if (rot_left_side_z < clip && rot_right_side_z < clip) {
			return;
		}

		if (rot_left_side_z < clip) {
			double clip0 = (clip - rot_left_side_z) / (rot_right_side_z - rot_left_side_z);
			rot_left_side_z = rot_left_side_z + (rot_right_side_z - rot_left_side_z) * clip0;
			rot_left_side_x = rot_left_side_x + (rot_right_side_x - rot_left_side_x) * clip0;
			tex30 = tex30 + (tex40 - tex30) * clip0;
		}

		if (rot_right_side_z < clip) {
			double clip0 = (clip - rot_left_side_z) / (rot_right_side_z - rot_left_side_z);
			rot_right_side_z = rot_left_side_z + (rot_right_side_z - rot_left_side_z) * clip0;
			rot_right_side_x = rot_left_side_x + (rot_right_side_x - rot_left_side_x) * clip0;
			tex40 = tex30 + (tex40 - tex30) * clip0;
		}

		double xpixel_left = (rot_left_side_x / rot_left_side_z * height + width / 2);
		double xpixel_right = (rot_right_side_x / rot_right_side_z * height + width / 2);

		if (xpixel_left >= xpixel_right) {
			return;
		}

		int xpixel_left_int = (int) (xpixel_left);
		int xpixel_right_int = (int) (xpixel_right);

		if (xpixel_left_int < 0) {
			xpixel_left_int = 0;
		}

		if (xpixel_right_int > width) {
			xpixel_right_int = width;
		}

		double ypixel_left_top = ycorner_tl / rot_left_side_z * height + height / 2.0;
		double ypixel_left_bot = ycorner_bl / rot_left_side_z * height + height / 2.0;
		double ypixel_right_top = ycorner_tr / rot_right_side_z * height + height / 2.0;
		double ypixel_right_bot = ycorner_br / rot_right_side_z * height + height / 2.0;

		double tex1 = 1 / rot_left_side_z;
		double tex2 = 1 / rot_right_side_z;
		double tex3 = tex30 / rot_left_side_z;
		double tex4 = tex40 / rot_right_side_z - tex3;

		// keep rendering wall until the x is inverted
		for (int x = xpixel_left_int; x < xpixel_right_int; x++) {

			double pixel_rotationx = (x - xpixel_left) / (xpixel_right - xpixel_left);
			int xtexture = (int) ((tex3 + tex4 * pixel_rotationx) / (tex1 + (tex2 - tex1) * pixel_rotationx));

			double ypixel_top = ypixel_left_top + (ypixel_right_top - ypixel_left_top) * pixel_rotationx;
			double ypixel_bot = ypixel_left_bot + (ypixel_right_bot - ypixel_left_bot) * pixel_rotationx;

			int ypixel_top_int = (int) (ypixel_top);
			int ypixel_bot_int = (int) (ypixel_bot);

			// ?
			if (ypixel_top_int < 0) {
				ypixel_top_int = 0;
			}

			if (ypixel_bot_int > height) {
				ypixel_bot_int = height;
			}

			for (int y = ypixel_top_int; y < ypixel_bot_int; y++) {
				double pixel_rotationy = (y - ypixel_top) / (ypixel_bot - ypixel_top);
				int ytexture = (int) (64 * pixel_rotationy);

				double brightness = render_distance / (1 / (tex1 + (tex2 - tex1) * pixel_rotationx) * 8);
				if (brightness > (render_distance / zbuffer[x + y * width])) {
					pixels[x + y * width] = Texture.floor.pixels[((xtexture & 2) + 64) + ((ytexture & 63) + 96) * sprite_sheet_w];
					zbuffer[x + y * width] = 1 / (tex1 + (tex2 - tex1) * pixel_rotationx) * 8;
				} else {
					continue;
				}

			}
		}
	}

	public void render_distance_limiter() {
		for (int i = 0; i < width * height; i++) {
			int colour = pixels[i];
			int brightness = (int) (render_distance / zbuffer[i]);

			if (brightness < 0) {
				brightness = 0;
			}

			if (brightness > 255) {
				brightness = 255;
			}

			int r = (colour >> 16) & 0xff;
			int g = (colour >> 8) & 0xff;
			int b = (colour) & 0xff;

			r = r * brightness / 255;
			g = g * brightness / 255;
			b = b * brightness / 255;

			pixels[i] = r << 16 | g << 8 | b;
		}
	}
}
