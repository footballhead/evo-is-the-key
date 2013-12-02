package net.blackforest.ld24.rogueascii;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.blackforest.ld24.Game;
import net.blackforest.ld24.KeyStates;
import net.blackforest.ld24.Resources;
import net.blackforest.ld24.Stage;

public class AsciiRogue extends Stage {

	char[ ][ ] level = new char[ 32 ][ 16 ];
	
	char[ ] portal = { '-' , '~' , '+' , '*' , '$' , '0' , 'O' };
	
	String status = "THUD!";
	int turnsPassed = 0;
	
	byte x = 0 , y = 0;
	byte portalX = 0 , portalY = 0;
	byte enemyX = 0 , enemyY = 0;
	byte knobX = -1 , knobY = -1;
	byte itemX = -1 , itemY = -1;
	byte boulderX = -1 , boulderY = -1;
	byte foodX = -1 , foodY = -1;
	byte keyX = -1 , keyY = -1;
	byte plateX = -1 , plateY = -1;
	byte doorX = -1 , doorY = -1;
	byte activateX = -1 , activateY = -1;
	
	//byte health = 80;
	
	boolean hasKnob = false;
	boolean hasKey = false;
	boolean hasActivated = false;
	boolean canMove = true;
	
	int timer = -1;
	
	public AsciiRogue( Game game ) {
		super( game );
		
		loadLevel( );
	}

	private void loadLevel( ) {
		try {
			BufferedReader in = new BufferedReader( new FileReader( "data/ascii-room-new.txt" ) );
			StringTokenizer header = new StringTokenizer( in.readLine( ) );
			level = new char[ Integer.parseInt( header.nextToken( ) ) ][ Integer.parseInt( header.nextToken( ) ) ];
			for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
				for ( int i = 0 ; i < level.length ; i++ ) {
					char newChar = ( char )in.read( );
					if ( newChar == '@' ) {
						x = ( byte )i;
						y = ( byte )ii;
						level[ i ][ ii ] = ',';
					} else if ( newChar == 'P' ) {
						portalX = ( byte )i;
						portalY = ( byte )ii;
						level[ i ][ ii ] = portal[ 0 ];
					} else if ( newChar == '%' ) {
						enemyX = ( byte )i;
						enemyY = ( byte )ii;
						level[ i ][ ii ] = '.';
					} else if ( newChar == '*' ) {
						knobX = ( byte )i;
						knobY = ( byte )ii;
						level[ i ][ ii ] = ',';
					} else if ( newChar == 'O' ) {
						boulderX = ( byte )i;
						boulderY = ( byte )ii;
						level[ i ][ ii ] = ',';
					} else if ( newChar == '+' ) {
						foodX = ( byte )i;
						foodY = ( byte )ii;
						level[ i ][ ii ] = '.';
					} else if ( newChar == '=' ) {
						keyX = ( byte )i;
						keyY = ( byte )ii;
						level[ i ][ ii ] = '.';
					} else if ( newChar == '0' ) {
						plateX = ( byte )i;
						plateY = ( byte )ii;
						level[ i ][ ii ] = '0';
					} else if ( newChar == 'S' ) {
						doorX = ( byte )i;
						doorY = ( byte )ii;
						level[ i ][ ii ] = 'S';
					} else if ( newChar == 'x' ) {
						activateX = ( byte )i;
						activateY = ( byte )ii;
						level[ i ][ ii ] = '.';
					} else {
						level[ i ][ ii ] = newChar;
					}
				}
				in.readLine( );
			}
			
			in.close( );
		} catch ( FileNotFoundException fnfe ) {
			fnfe.printStackTrace( );
			System.exit( 1 );
		} catch ( IOException ioe ) {
			ioe.printStackTrace( );
			System.exit( 1 );
		}
	}
	

	public void render() {
		if ( timer == -1 ) {
			Resources.fontNoOutline.drawString( status , 4 , 4 );
			
			for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
				for ( int i = 0 ; i < level.length ; i++ ) {
					char draw;
					if ( i == x && ii == y ) {
						draw = '@';
					} else if ( i == enemyX && ii == enemyY ) {
						draw = '%';
					} else if ( i == itemX && ii == itemY ) {
						draw = '!';
					} else if ( i == knobX && ii == knobY ) {
						draw = '*';
					} else if ( i == foodX && ii == foodY ) {
						draw = '+';
					} else if ( i == keyX && ii == keyY ) {
						draw = '=';
					} else if ( i == boulderX && ii == boulderY ) {
						draw = 'O';
					} else {
						draw = level[ i ][ ii ];
					}
					Resources.fontNoOutline.drawString( "" + draw , 192 + 8 * i , 40 + 13 * ii );
					
				}
			}
			
			Resources.fontNoOutline.drawString( "HEALTH: " + Game.health , 4 , getMaster( ).getDisplayHeight( ) - 20 );
		} else {
			for ( int ii = 0 ; ii < 36 ; ii++ ) {
				for ( int i = 0 ; i < 78 ; i++ ) {
					Resources.fontNoOutline.drawString( "" + ( char )( Game.random.nextInt( 48 ) + 32 ) , 4 + 8 * i , 4 + 13 * ii );
				}
			}
		}
		
	}

	public void update() {
		if ( Keyboard.isKeyDown( Keyboard.KEY_W ) && !KeyStates.wPressed ) {
			KeyStates.wPressed = true;
			tryToMove( x , y - 1 );
		}
		if ( Keyboard.isKeyDown( Keyboard.KEY_A ) && !KeyStates.aPressed ) {
			KeyStates.aPressed = true;
			tryToMove( x - 1 , y );
		}
		if ( Keyboard.isKeyDown( Keyboard.KEY_S ) && !KeyStates.sPressed ) {
			KeyStates.sPressed = true;
			tryToMove( x , y + 1 );
		}
		if ( Keyboard.isKeyDown( Keyboard.KEY_D ) && !KeyStates.dPressed ) {
			KeyStates.dPressed = true;
			tryToMove( x + 1 , y );
		}
		
		if ( timer != -1 ) {
			timer--;
			
			if ( timer == 0 ) getMaster( ).nextStage( );
		}
	}
	
	private void tryToMove( int x , int y ) {
		if ( canMove ) {
			if ( x > 0 && y > 0 && x < level.length - 1 && y < level[ 0 ].length - 1 ) {
				if ( level[ x ][ y ] != '#' && level[ x ][ y ] != '^' && level[ x ][ y ] != 'v' ) {
					if ( level[ x ][ y ] == 's' ) {
						if ( !hasKnob ) {
							status = "THIS DOOR HAS NO KNOB AND YOURS IS GONE. ODD.";
						} else {
							level[ x ][ y ] = '.';
							this.x = ( byte )x;
							this.y = ( byte )y;
							status = "THIS DOOR HAS NO KNOB. YOU SLOT IN YOURS AND THE DOOR OPENS.";
						}
					} else if ( level[ x ][ y ] == 'X' ) {
						status = "SPIKEY! YOU GET HURT RUNNING INTO THE SHARP SPIKES.";
						Game.health -= 15;
						if ( Game.health <= 0 ) getMaster( ).gameOver( );
					} else if ( level[ x ][ y ] == '$' ) {
						if ( hasKey ) {
							level[ x ][ y ] = '.';
							this.x = ( byte )x;
							this.y = ( byte )y;
							status = "THE KEY FITS. YOU UNLOCK AND OPEN THE DOOR. THE KEY SNAPS IN HALF.";
						} else {
							status = "THE DOOR IS LOCKED. YOU GO TO USE THE KEY THAT... YOU NO LONGER HAVE. STRANGE.";
						}
					} else if ( x == boulderX && y == boulderY ) {
						int dirX = x - this.x;
						int dirY = y - this.y;
						
						if ( level[ boulderX + dirX ][ boulderY + dirY ] != '#' ) {
							boulderX += dirX;
							boulderY += dirY;
							status = "YOU MOVE THE BOULDER.";
						} else {
							status = "THE BOULDER IS STUCK.";
						}
						
					} else if ( level[ x ][ y ] == 'S' ) {
						status = "THERE IS A DOOR IN YOUR WAY, BUT NO APPARENT WAY TO OPEN IT.";
					} else {
						this.x = ( byte )x;
						this.y = ( byte )y;
					}
					
					if ( x == itemX && y == itemY ) {
						status = "HEY! IT'S THAT KEY! YOU RECLAIM IT.";
						itemX = itemY = -1;
					} else if ( x == knobX && y == knobY ) {
						status = "IT'S THE DOORKNOB. MUST HAVE FALLEN OUT OF YOUR POCKET. YOU TAKE IT.";
						knobX = knobY = -1;
						hasKnob = true;
					} else if ( x == foodX && y == foodY ) {
						status = "IT'S FOOD. WITHOUT THINKING, YOU SCARF IT DOWN. IT MAKES YOU FEEL BETTER.";
						Game.health = 100;
						foodX = -1;
						foodY = -1;
					} else if ( x == keyX && y == keyY ) {
						keyX = keyY = -1;
						hasKey = true;
						status = "IT'S A KEY. NOT THE ONE THAT YOU USED TO HAVE THOUGH.";
					} else if ( x == plateX && y == plateY ) {
						status = "YOU STEP ONTO THE PRESSURE PLATE. NOTHING HAPPENS; YOU ARE TOO LIGHT.";
					}
				}
			}
			
			if ( x == portalX && y == portalY ) {
				timer = 1200;
				Resources.pureNoise.start( );
				canMove = false;
			}
			
			passTurn( );
		}
	}
	
	private void passTurn( ) {
		turnsPassed += 1;
		
		level[ portalX ][ portalY ] = portal[ turnsPassed % portal.length ];
		
		if ( boulderX == plateX && boulderY == plateY ) {
			status = "YOU PUSH THE BOULDER ON TO THE PRESSURE PLATE. THE DOOR OPENS.";
			level[ doorX ][ doorY ] = '.';
			plateX = plateY = -1;
		}
		
		if ( enemyX != -1 && enemyY != -1 && hasActivated ) {
			if ( enemyY != portalY ) {
				enemyY++;
			} else {
				enemyX++;
			}
			
			if ( enemyX == portalX - 1 ) {
				status = "YOU HEAR A CLINK AND SEE SOMETHING DROP FROM THE ENTITY.";
				itemX = enemyX;
				itemY = enemyY;
			}
			
			if ( enemyX == portalX && enemyY == portalY ) {
				enemyX = enemyY = -1;
				status = "YOU SEE A FLASH OF LIGHT. THE ENTITY VANISHES.";
			}
		}
		
		if ( x == activateX && y == activateY ) {
			hasActivated = true;
			status = "YOU SEE AND HEAR SOMETHING MOVING IN THE ROOM.";
		}
	}

	public void switchTo( ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontNoOutline.getTexture( ).getTextureName( ) );
		Resources.fadeInNoise.stop( );
		Game.health = 80;
	}

	
	
}
