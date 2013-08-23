package com.mine.demogame.entity;

public class Mob extends Entity {
	
	double walk_speed = 1;
	
	public void move(int xa, int za, double rot) {
		if (xa != 0 && za != 0) {
			move(xa, 0, rot);
			move(0, za, rot);
			return;
		}
		
		double nx = (xa * Math.cos(rot) + za * Math.sin(rot)) * walk_speed;
		double nz = (za * Math.cos(rot) - xa * Math.sin(rot)) * walk_speed;
		x += nx;
		z += nz;
		nx *= 0.1;
		nz *= 0.1;
	}

}
