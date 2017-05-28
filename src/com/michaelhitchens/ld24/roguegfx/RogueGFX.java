package com.michaelhitchens.ld24.roguegfx;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.michaelhitchens.ld24.Game;
import com.michaelhitchens.ld24.KeyStates;
import com.michaelhitchens.ld24.Resources;
import com.michaelhitchens.ld24.Stage;

public class RogueGFX extends Stage {

	byte[ ][ ] level;
	
	boolean hasGun = false;
	boolean doorOpen = false;
	
	String status = "";
	int turnsPassed = 0;
	
	int viewX = 0 , viewY = 0;
	
	private byte x = 0 , y = 0;
	byte enemyX = 0 , enemyY = 0;
	
	byte[ ] wallList = { 0 , 1 , 4 , 5 , 6 };
	
	int transitionTimer = -1;
	float hue = 0;
	
	String[ ] story = { "a''GIVEaMEaBACKaMYaKEY.''a" , "a''YOUaDON'TaKNOWaHOWaTOaUSEaIT,aBUTaIaDO.''a" , "a''WITHOUTaITaYOUaCANNOTaCOMPLETEaTHEaLASTaEVOLUTION.''a" , "a''WEaWILLaALLaDIE.''a" , "a''STUCKaINaTHISaMAZEaFOREVER.''a" , "a''IaWILLaEVOLVEaANDaHURTaYOUaIFaNECESSARY.''a" , "a''FINE.aBEaTHATaWAY.''a" };
	
	public RogueGFX( Game game ) {
		super( game );
		
		loadLevel( );
	}

	private void loadLevel( ) {
		try {
			BufferedReader in = new BufferedReader( new FileReader( "data/roguegfx-room.txt" ) );
			StringTokenizer header = new StringTokenizer( in.readLine( ) );
			level = new byte[ Integer.parseInt( header.nextToken( ) ) ][ Integer.parseInt( header.nextToken( ) ) ];
			for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
				for ( int i = 0 ; i < level.length ; i++ ) {
					char newChar = ( char )in.read( );
					
					if ( newChar == '#' ) {
						level[ i ][ ii ] = 1;
					} else if ( newChar == '.' ) {
						level[ i ][ ii ] = 2;
					} else if ( newChar == ',' ) {
						level[ i ][ ii ] = 3;
					} else if ( newChar == '@' ) {
						x = ( byte )i;
						y = ( byte )ii;
						level[ i ][ ii ] = 2;
						viewX = 320 - i * 24;
						viewY = 240 - ii * 24;
					} else if ( newChar == '8' ) {
						level[ i ][ ii ] = 6;
					} else if ( newChar == '^' ) {
						level[ i ][ ii ] = 4;
					} else if ( newChar == 'v' ) {
						level[ i ][ ii ] = 5;
					} else if ( newChar == 'P' ) {
						level[ i ][ ii ] = 7;
					} else if ( newChar == '*' ) {
						level[ i ][ ii ] = 8;
					} else if ( newChar == '$' ) {
						level[ i ][ ii ] = 9;
					} else if ( newChar == '%' ) {
						enemyX = ( byte )i;
						enemyY = ( byte )ii;
						level[ i ][ ii ] = 2;
					} else {
						level[ i ][ ii ] = 0;
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
	

	public void render( ) {
		
		if ( transitionTimer == -1 ) {
		
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontNoOutline.getTexture( ).getTextureName( ) );
			
			Resources.fontNoOutline.drawString( status , 4 , 4 );
			
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.rogueGFX.getTextureName( ) );
			
			for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
				for ( int i = 0 ; i < level.length ; i++ ) {
					int draw = level[ i ][ ii ];
					
					if ( draw == 7 ) {
						Color col = Color.getHSBColor( hue , 1 , 1 );
						GL11.glColor4f( ( float )col.getRed( ) / 255f , ( float )col.getGreen( ) / 255f , ( float )col.getBlue( ) / 255f , 1f );
					}
					
					if ( draw != 0 ) {
						Resources.rogueGFX.drawNoTexture( viewX + 24 * i , viewY + 24 * ii , draw - 1 );
					}
					
					if ( draw == 7 ) GL11.glColor4f( 1f , 1f , 1f , 1f );
				}
			}
			
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.rogueLiving.getTextureName( ) );
			Resources.rogueLiving.drawNoTexture( viewX + 24 * x , viewY + 24 * y , 0 );
			Resources.rogueLiving.drawNoTexture( viewX + 24 * enemyX , viewY + 24 * enemyY , 1 );
			
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontBackground.getTexture( ).getTextureName( ) );
			Resources.fontBackground.drawString( status , 4 , 4 );
		} else {
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.blank.getTextureName( ) );
			GL11.glBegin( GL11.GL_QUADS );
			
			for ( int ii = 0 ; ii < getMaster( ).getDisplayHeight( ) / 16 + 1 ; ii++ ) {
				for ( int i = 0 ; i < getMaster( ).getDisplayWidth( ) / 16 ; i++ ) {
					float randNum = ( float )Game.random.nextDouble( );
					GL11.glColor4f( randNum , randNum , randNum , 1f );
			        
			        GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex2i( i * 16 , ii * 16 );
			        GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex2i( i * 16 , ii * 16 + 16 );
			        GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex2i( i * 16 + 16 , ii * 16 + 16 );
			        GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex2i( i * 16 + 16 , ii * 16 );
				}
			}
			
	        GL11.glEnd( );
			
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
		
		if ( transitionTimer != -1 ) {
			transitionTimer --;
			if ( transitionTimer == 0 ) getMaster( ).nextStage( );
		}
		
		hue = ( hue + 0.0005f ) % 360f;
		
	}
	
	private void tryToMove( int x , int y ) {
		boolean foundWall = false;
		for ( int i = 0 ; i < wallList.length ; i++ ) {
			if ( level[ x ][ y ] == wallList[ i ] ) {
				foundWall = true;
				break;
			} else if ( level[ x ][ y ] == 9 ) {
				if ( hasGun ) {
					level[ x ][ y ] = 3;
					status = "aYOUaBLASTaTHEaLOCKaTOaBITSaANDaOPENaTHEaDOOR.a";
					doorOpen = true;
					foundWall = true;
					break;
				} else {
					status = "aYOURaKEYaDOESN'TaWORK.aIFaONLYaYOUaHADaSOMEaFIREPOWER...a";
					foundWall = true;
					break;
				}
			}
		}
		
		if ( !foundWall ) {
			this.x = ( byte )x;
			this.y = ( byte )y;
			
			viewX = 320 - x * 24;
			viewY = 240 - y * 24;
		}
		
		passTurn( );
	}
	
	private void passTurn( ) {
		turnsPassed += 1;
		
		if ( turnsPassed < story.length * 2 ) status = story[ ( int )( ( float )turnsPassed / 2 ) ];
		
		if ( level[ x ][ y ] == 7 ) {
			transitionTimer = 1500;
			Resources.pureNoise2.start( );
		} else if ( level[ x ][ y ] == 8 ) {
			hasGun = true;
			status = "aYOUaPICKUPaTHEaFIREARM.aYOUaWANTaTOaTESTaITaTOaSEEaIFaITaFUNCTIONS.a";
			level[ x ][ y ] = 3;
		}
		
		if ( doorOpen && enemyX != -1 && enemyY != -1) {
			enemyY++;
			if ( level[ enemyX ][ enemyY ] == 7 ) enemyX = enemyY = -1;
		}
	}

	public void switchTo( ) {
		Resources.pureNoise.stop( );
	}

	
	
}
