package net.blackforest.ld24.textadventure;

public class Room {

	final public static byte NORTH = 0;
	final public static byte SOUTH = 1;
	
	String description;
	boolean[ ] exits = new boolean[ 2 ];
	
	public Room( String description , boolean exitNorth , boolean exitSouth ) {
		this.description = description;
		exits[ NORTH ] = exitNorth;
		exits[ SOUTH ] = exitSouth;
	}
	
	/*
	public static Room getRoomByID( int id ) {
		switch( id ) {
			default:
			case 0: return new Room( "You are alone in a small, sparsely furnished, dimly lit room. The walls are \nmade of big, rough blocks, while the floor and ceiling are made from smooth, \nmedium-sized tiles. You cannot seem to determine where the light is coming \nfrom. Everything is very sterile and gloomy, especially without color. \n\nWait. Color? What's that? You think you just made up a new word. Everything \nis made up of values. \n\nThe floor is of a dark value, the walls are of a medium value, and the ceiling \nis lighter than both. \n\nThere is a door to the north which is darker than the walls but lighter than \nthe floor." , true , false , false , false );
			case 1: return new Room( "You are in a long, straight hallway with a low ceiling. The decor is the same \nas the room to the south. It looks about ten times longer though, but you think \nyou can traverse the space pretty quickly. \n\nThere is something shimmering and glowing brightly at the end of the hallway, \nbut you can't make out any of its fine details. It seems to be casting more of \nan ambient light rather than one with a direction; the hall is as dim as the \nroom where you woke up yet the object is very brilliant. \n\nThe hall continues to the north. There is an open door to the south." , true , false , true , false );
			case 2: return new Room( "You walk down the hallway. You get half way before realizing that there is no \nfloor in front of you, creating a large gap leading to blackness. It's as if \nthe floor never existed. You wonder why you didn't notice it at the start of \nthe hallway. You can see something down below, in the darkness. Your only \noption seems to be trying to jump the gap. \n\nFrom this distance you notice that there is an archway in the wall at the end \nof the hall. It is roughly the same height as the surrounding walls. The bright \nglow seems to be contained within the middle. \n\nThe hallway continues past the missing floor to the north. There is more \nhallway to the south." , false , false , true , false );
		}
	}
	*/
	
}
