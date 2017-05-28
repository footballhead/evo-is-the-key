package com.michaelhitchens.ld24.topdownshooter;

public class Ghost {

	float x , y;
	float radius = 64;
	float maxHealth = 2000;
	float health = 0;
	
	int stage = 0;
	int stageTimer = 10000;
	int actionTimer = 1000;

	public Ghost( float x , float y ) {
		this.x = x;
		this.y = y;
	}
	
	public float getHealthPercent( ) {
		return health / maxHealth;
	}
	
	public void update( int delta ) {
		stageTimer -= delta;
		if ( stageTimer < 0 ) {
			stage = ( stage + 1 ) % 2; // 3 is num of stages
			if ( getHealthPercent( ) < 0.3f ) stageTimer += 5000; else stageTimer += 10000;
		}
		
		actionTimer -= delta;
	}
}
