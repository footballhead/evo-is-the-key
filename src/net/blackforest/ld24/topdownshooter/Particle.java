package net.blackforest.ld24.topdownshooter;

import java.awt.Color;

import net.blackforest.ld24.Game;

public class Particle {

	float x , y;
	float moveX , moveY;
	int life;
	int initialLife;
	
	Color col;
	
	public Particle( float x , float y , int life , Color col ) {
		this.x = x;
		this.y = y;
		this.life = initialLife = life;
		this.col = col;
		
		double direction = Game.random.nextDouble( ) * Math.PI * 2;
		moveX = ( float )( Math.cos( direction ) ) / 50;
		moveY = ( float )( Math.sin( direction ) ) / 50;
	}
	
	public void update( int delta ) {
		x += moveX * delta;
		y += moveY * delta;
		life -= delta;
	}
	
	public float getLifePercent( ) {
		return ( float )life / ( float )initialLife;
	}
}
