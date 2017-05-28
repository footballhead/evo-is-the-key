package com.michaelhitchens.ld24.textadventure;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.michaelhitchens.ld24.Game;
import com.michaelhitchens.ld24.KeyStates;
import com.michaelhitchens.ld24.KeyboardStringHandler;
import com.michaelhitchens.ld24.Resources;
import com.michaelhitchens.ld24.Stage;

public class TextAdventure extends Stage {

	Room[ ] room = new Room[ 5 ];
	byte y = 2;
	String result = "";
	int timer = -1;
	String transition = "";
	
	int descriptionLength = 0;
	int actualLength = 0;
	
	int drawActionLength = 0;
	int actualActionLength = 0;
	
	boolean isKeyDiscovered = false;
	boolean isLockedDoorOpen = false;
	boolean isLockedDoorUnlocked = false;
	boolean hasKey = false;
	
	public TextAdventure( Game game ) {
		super( game );
		
		room[ 0 ] = new Room( "YOU WALK DOWN THE HALLWAY. YOU GET HALF WAY BEFORE REALIZING THAT THERE IS NO \nFLOOR IN FRONT OF YOU, CREATING A LARGE GAP LEADING TO BLACKNESS. IT'S AS IF    \nTHE FLOOR NEVER EXISTED. YOU WONDER WHY YOU DIDN'T NOTICE IT AT THE START OF \nTHE HALLWAY. YOU CAN SEE SOMETHING DOWN BELOW, IN THE DARKNESS. \n\nYOU MIGHT BE ABLE TO JUMP THE GAP. \n\nFROM THIS DISTANCE YOU NOTICE THAT THERE IS AN ARCHWAY IN THE WALL AT THE END \nOF THE HALL. IT IS ROUGHLY THE SAME HEIGHT AS THE SURROUNDING WALLS. THE BRIGHT \nGLOW SEEMS TO BE CONTAINED WITH THE MIDDLE. \n\nTHE HALLWAY CONTINUES PAST THE MISSING FLOOR TO THE NORTH. THERE IS MORE \nHALLWAY TO THE SOUTH." , false , true );
		room[ 1 ] = new Room( "YOU ARE IN A LONG, STRAIGHT HALLWAY WITH A LOW CEILING. THE DECOR IS THE SAME \nAS THE ROOM TO THE SOUTH. IT LOOKS ABOUT TENS TIME LONGER THOUGH, BUT YOU THINK \nYOU CAN TRAVERSE THE SPACE PRETTY QUICKLY. \n\nTHERE IS SOMETHING SHIMMERING AND GLOWING BRIGHTLY AT THE END OF THE HALLWAY, \nBUT YOU CAN'T MAKE OUT ANY OF ITS FINE DETAILS. IT SEEMS TO BE CASTING MORE OF \nAN AMBIENT LIGHT RATHER THAN ONE WITH A DIRECTION; THE HALL IS AS DIM AS THE \nROOM WHERE YOU WOKE UP YET THE OBJECT IS VERY BRILLIANT. \n\nTHE HALL CONTINUES TO THE NORTH. THERE IS AN OPEN DOOR TO THE SOUTH." , true , true );
		room[ 2 ] = new Room( "YOU ARE ALONE IN A SMALL, BARE, DIMLY LIT ROOM. THE WALLS ARE MADE OF BIG,    \nROUGH BLOCKS, WHILE THE FLOOR AND CEILING ARE MADE FROM SMOOTH, MEDIUM-SIZED   \nTILES. YOU CANNOT SEEM TO DETERMINE WHERE THE LIGHT IS COMING FROM. EVERYTHING \nIS VERY STERILE AND GLOOMY, ESPECIALLY WITHOUT COLOR. \n\nWAIT. COLOR? WHAT'S THAT? YOU THINK YOU JUST MADE UP A NEW WORD. EVERYTHING \nIS MADE UP OF VALUES. \n\nTHE FLOOR IS OF A DARK VALUE, THE WALLS ARE OF A MEDIUM VALUE, AND THE CEILING \nIS LIGHTER THAN BOTH. \n\nTHERE IS AN OPEN DOOR TO THE NORTH. THERE IS A CLOSED DOOR TO THE SOUTH." , true , true );
		room[ 3 ] = new Room( "YOU ENTER A SMALL HALLWAY, MUCH SMALLER THAN THE OTHER. EVERYTHING IS MUCH    \nMORE RUN DOWN THAN THE REST OF THE ROOMS; TILES ARE CRACKING AND RIPPED UP, THE \nWALLS HAVE HUGE CHUNKS CARVED OUT OF THEM, AND CEILING TILES ARE SCATTERED \nEVERYWHERE. YOU HEAR THE OCCASIONAL CREAK AND GROAN COMING FROM THE SPACE. \nTHE PLACE LOOKS LIKE IT MIGHT FALL APART ANY MINUTE. \n\nTHERE IS A WHITE DOOR AT THE END OF THE HALLWAY. \n\nAN OPEN DOOR LIES TO THE NORTH. THE HALL CONTINUES SOUTH." , true , true );
		room[ 4 ] = new Room( "YOU CONTINUE DOWN THE HALLWAY. IT SEEMS TO BECOME MORE RUN DOWN THE CLOSER YOU \nGET TO THE END OF THE HALL. THE CREAKS AND GROANS GET LOUDER AS YOU PROCEED.   \n\nTHERE IS A WHITE DOOOR TO THE SOUTH, IN PERFECTLY GOOD CONDITION. THE HALL \nCONTINUES TO THE NORTH." , true , false );
		
		
		KeyboardStringHandler.setInputEnabled( true );
		KeyboardStringHandler.clearKeyboardString( );
	}
	
	public void render( ) {
		if ( transition.isEmpty( ) ) {
			Resources.fontNoOutline.drawString( room[ y ].description.substring( 0 , descriptionLength ) , 4 , 4 );
		} else {
			Resources.fontNoOutline.drawString( transition , 4 , 4 );
		}
		
		if ( transition.length( ) < 2000 ) { 
			Resources.fontNoOutline.drawString( ">>>" + result.substring( 0 , drawActionLength / 2 ) , 4 , getMaster( ).getDisplayHeight( ) - 100 );
			
			Resources.fontNoOutline.drawString( ">" + KeyboardStringHandler.getKeyboardString( ).toUpperCase( ) + "_" , 4 , getMaster( ).getDisplayHeight( ) - 20 );
		}
	}
	
	public void update( ) {
		if ( Keyboard.isKeyDown( Keyboard.KEY_RETURN ) && !KeyStates.enterPressed && KeyboardStringHandler.isInputEnabled( ) ) {
			KeyStates.enterPressed = true;
			result = parseKeyboardInput( );
			actualActionLength = result.length( );
			drawActionLength = 0;
			KeyboardStringHandler.clearKeyboardString( );
		}
		
		if ( timer != -1 ) {
			timer--;
			transition += "#";
			if ( ( 3000 - timer ) % 78 == 0 ) transition += "\n";
			
			if ( timer == 0 ) {
				getMaster( ).nextStage( );
			}
		}
		
		if ( descriptionLength < actualLength ) {
			descriptionLength ++;
		}
		
		if ( drawActionLength < actualActionLength * 2 ) {
			drawActionLength ++;
		}
	}
	
	private String parseKeyboardInput( ) {
		String input = KeyboardStringHandler.getKeyboardString( ).toLowerCase( );
		
		if ( input.equalsIgnoreCase( "north" ) ) {
			if ( y == 0 ) {
				return "THERE IS A GAP IN YOUR WAY. YOU NEED TO JUMP TO CROSS IT.";
			}
			if ( room[ y ].exits[ Room.NORTH ] == true ) {
				y -= 1;
				descriptionLength = 0;
				actualLength = room[ y ].description.length( );
				return "YOU GO NORTH.";
			} else {
				return "YOU BANG FACE FIRST INTO THE WALL. THERE IS NO EXIT THAT WAY.";
			}
		} else if ( input.equalsIgnoreCase( "south" ) ) {
			if ( y == 2 && !isLockedDoorOpen ) { 
				return "THERE IS A DOOR IN YOUR WAY.";
			}
			if ( y == 4 ) {
				return "THERE IS A DOOR IN YOUR WAY.";
			}
			
			if ( room[ y ].exits[ Room.SOUTH ] == true ) {
				y += 1;
				descriptionLength = 0;
				actualLength = room[ y ].description.length( );
				return "YOU GO SOUTH.";
			} else {
				return "YOU BANG FACE FIRST INTO THE WALL. THERE IS NO EXIT THAT WAY.";
			}
		} else if ( input.equalsIgnoreCase( "east" ) || input.equalsIgnoreCase( "west" ) ) {
			return "YOU DON'T COMPREHEND THAT DIRECTION.";
		} else if ( input.toLowerCase( ).contains( "examine" ) ) {
			return "YOU ONLY SEE WHAT YOU CAN SEE.";
		} else if ( input.startsWith( "jump" ) || input.startsWith( "HOP" ) ) {
			if ( input.toLowerCase( ).contains( "gap" ) || input.toLowerCase( ).contains( "pit" ) || input.toLowerCase( ).contains( "chasm" ) ) {
				if ( y == 0 ) {
					if ( !hasKey ) {
						isKeyDiscovered = true;
						return "AS YOU GO TO JUMP SOMETHING SPARKLY CATCHES YOUR EYE. \n>>>THERE IS A KEY ON THE FLOOR BEFORE THE GAP, IN FRONT OF YOU.";
					} else {
						return "YOU ARE TOO SCARED TO JUMP NOW. CHICKEN.";
					}
				} else {
					return "WHAT GAP?";
				}
			}
			return "YOU HOP ON THE SPOT.";
		} else if ( input.startsWith( "open" ) || input.startsWith( "push" ) || input.startsWith( "pull" ) ) {
			if ( input.contains( "door" ) ) {
				if ( y == 4 ) {
					timer = 3000;
					KeyboardStringHandler.setInputEnabled( false );
					Resources.fadeInNoise.start( );
					return "THE FLOOR GIVES OUT FROM UNDER YOU.\n>>>YOU FALL AT AN ALARMING SPEED.";
				} else if ( y == 3 ) {
					return "THE DOOR IS ALREADY OPEN.";
				} else if ( y == 2 ) {
					if ( isLockedDoorUnlocked ) {
						if ( !isLockedDoorOpen ) {
							isLockedDoorOpen = true;
							return "YOU OPEN THE DOOR. THE DOOR KNOB SNAPS OFF IN YOUR HAND. \n>>>YOU DECIDE TO KEEP IT, JUST IN CASE.";
						} else {
							return "BOTH DOORS ARE OPEN.";
						}
					} else {
						return "THE DOOR IS LOCKED.";
					}
				} else if ( y == 1 ) {
					return "THE DOOR IS ALREADY OPEN.";
				} else if ( y == 0 ) {
					return "HUH? DOOR? WHERE?";
				}
			}
		} else if ( input.startsWith( "get" ) || input.startsWith( "take" ) || input.startsWith( "pickup" ) ) {
			if ( input.contains( "key" ) ) {
				if ( y == 0 && isKeyDiscovered && !hasKey ) {
					hasKey = true;
					return "YOU PICK UP THE KEY.";
				}
			}
		} else if ( input.startsWith( "unlock" ) ) {
			if ( input.contains( "door" ) ) {
				if ( y == 2 ) {
					if ( hasKey ) {
						if ( isLockedDoorUnlocked ) {
							return "IT'S UNLOCKED.";
						} else {
							isLockedDoorUnlocked = true;
							return "YOU UNLOCK THE DOOR.";
						}
					} else {
						return "WITH WHAT?";
					}
				}
			}
			return "UNLOCK? THERE IS NOTHING LOCKED AROUND YOU.";
		}
		
		return "YOU HAVE NO IDEA WHAT THAT MEANS.";
	}
	
	public void switchTo( ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontNoOutline.getTexture( ).getTextureName( ) );
		
		descriptionLength = 0;
		actualLength = room[ 2 ].description.length( );
	}
	
	
}
