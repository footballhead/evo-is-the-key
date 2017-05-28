package com.michaelhitchens.ld24;

public abstract class Stage {

	private Game master;
	
	public Stage( Game game ) {
		master = game;
	}
	
	public abstract void render( );
	public abstract void update( );
	public abstract void switchTo( );
	
	public Game getMaster( ) {
		return master;
	}
	
}
