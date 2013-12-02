package net.blackforest.ld24.topdownshooter;

import net.blackforest.ld24.Game;

public class Minion {
	static int radius = 12;
	
	float x , y;
	
	float moveX = 0;
	float moveY = 0;
	
	int hopTimer = 0;
	byte health = 2;
	
	public Minion( float x , float y ) {
		this.x = x;
		this.y = y;
		
		double angle = Game.random.nextDouble( ) * Math.PI * 2;
		moveX = ( float )Math.cos( angle );
		moveY = ( float )Math.sin( angle );
	}
	
	public void update( int delta ) {
		if ( hopTimer < 0 ) {
			double angle = Game.random.nextDouble( ) * Math.PI * 2;
			moveX = ( float )Math.cos( angle );
			moveY = ( float )Math.sin( angle );
			hopTimer += Game.random.nextInt( 100 ) + 900;
		}
		
		hopTimer -= delta;
		
		x += moveX * delta;
		y += moveY * delta;
		
		moveX /= 1.03f;
		moveY /= 1.03f;
	}
}
