package com.michaelhitchens.ld24.topdownshooter;

public class GhostBullet {
	static float speed = 0.3f;
	private double tickSpeedX, tickSpeedY;
	int life = 2000;
	float x , y;
	
	public GhostBullet( float x , float y , double angle ) {
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
