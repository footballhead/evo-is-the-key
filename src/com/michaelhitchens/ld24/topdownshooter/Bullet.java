package com.michaelhitchens.ld24.topdownshooter;

public class Bullet {
	static float speed = 1f;
	private double tickSpeedX, tickSpeedY;
	int life = 1000;
	float x , y;
	
	public Bullet( float x , float y , double angle ) {
		this.x = x;
		this.y = y;
		tickSpeedX = speed * Math.cos( angle );
		tickSpeedY = speed * Math.sin( angle );
	}
	
	public void update( int delta ) {
		x += tickSpeedX * delta;
		y += tickSpeedY * delta;
		
		life -= delta;
	}
	
}
