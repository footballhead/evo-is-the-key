package net.blackforest.ld24.fps;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import net.blackforest.ld24.Game;
import net.blackforest.ld24.KeyStates;
import net.blackforest.ld24.Resources;
import net.blackforest.ld24.Stage;

public class FirstPersonShooter extends Stage {

	int lastMouseX = 0 , lastMouseY = 0;
	
	int pitch = 0 , yaw = 0;
	float x = 6 , y = 0 , z = 6;
	float lastX = x , lastY = y , lastZ = z;
	float radius = 1.5f;
	
	ArrayList< Block > blockList = new ArrayList< Block >( );
	ArrayList< FloorCeiling > floorCeilingList = new ArrayList< FloorCeiling >( );
	Block portal = null;
	Block door = null;
	
	int levelWidth;
	int levelHeight;
	
	boolean transition = false;
	int transitionTimer = 0;
	
	String status = "";
	
	public FirstPersonShooter( Game game ) {
		super( game );
		
		loadLevel( );
	}
	
	private void loadLevel( ) {
		try {
			BufferedReader in = new BufferedReader( new FileReader( "data/fps-end.txt" ) );
			StringTokenizer header = new StringTokenizer( in.readLine( ) );
			levelWidth = Integer.parseInt( header.nextToken( ) );
			levelHeight = Integer.parseInt( header.nextToken( ) );

			for ( int ii = 0 ; ii < levelHeight ; ii++ ) {
				for ( int i = 0 ; i < levelWidth ; i++ ) {
					int rawChar = in.read( );
					char newChar = ( char )rawChar;

					switch ( newChar ) {
						case '^':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , true , false , false , false ) );
							break;
						case '>':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , true , false , false ) );
							break;
						case 'V':
						case 'v':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , false , true , false ) );
							break;
						case '<':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , false , false , true ) );
							break;
						case 'L':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , false , true , true ) );
							break;
						case '1':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , true , true , false ) );
							break;
						case '2':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , true , false , false , true ) );
							break;
						case '3':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , true , true , false , false ) );
							break;
						case '@':
							x = i * 2;
							z = ii * 2;
							floorCeilingList.add( new FloorCeiling( i * 2 , 0 , ii * 2 , false ) );
							break;
						case '.':
							floorCeilingList.add( new FloorCeiling( i * 2 , 0 , ii * 2 , false ) );
							break;
						case 'P':
							portal = new Block( i * 2 , 0 , ii * 2 , false , false , true , false );
							break;
						case ' ':
							blockList.add( new Block( i * 2 , 0 , ii * 2 , false , false , false , false ) );
							break;
						case '*':
							floorCeilingList.add( new FloorCeiling( i * 2 , 0 , ii * 2 , true ) );
							break;
						case '$':
							door = new Block( i * 2 , 0 , ii * 2 , true , false , false , false );
							floorCeilingList.add( new FloorCeiling( i * 2 , 0 , ii * 2 , false ) );
							break;
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
	
	public void switchTo() {
		changeGL( );
		Mouse.setCursorPosition( getMaster( ).getDisplayWidth( ) / 2 , getMaster( ).getDisplayHeight( ) / 2 );
		
		lastMouseX = getMaster( ).getDisplayWidth( ) / 2;
		lastMouseY = getMaster( ).getDisplayHeight( ) / 2;
	}
	
	private void changeGL( ) { // call when switching stages
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glShadeModel( GL11.GL_SMOOTH );
		
		GL11.glEnable( GL11.GL_DEPTH_TEST );
		GL11.glDepthFunc( GL11.GL_LEQUAL );
		GL11.glClearDepth( 1.0 );
		
		GL11.glEnable( GL11.GL_CULL_FACE );
		GL11.glCullFace( GL11.GL_BACK );
		
		GL11.glClearColor( 0f , 0f , 0f , 1f );
		
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA , GL11.GL_ONE_MINUS_SRC_ALPHA );
		
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glLoadIdentity( );
		
		GLU.gluPerspective( 45f , ( float )getMaster( ).getDisplayWidth( ) / ( float )getMaster( ).getDisplayHeight( ) , 0.1f , 100f );
		
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glLoadIdentity( );
	}
	
	public void update( ) {
		
		if ( Keyboard.isKeyDown( Keyboard.KEY_W ) && !KeyStates.wPressed ) KeyStates.wPressed = true;
		if ( Keyboard.isKeyDown( Keyboard.KEY_A ) && !KeyStates.aPressed ) KeyStates.aPressed = true;
		if ( Keyboard.isKeyDown( Keyboard.KEY_S ) && !KeyStates.sPressed ) KeyStates.sPressed = true;
		if ( Keyboard.isKeyDown( Keyboard.KEY_D ) && !KeyStates.dPressed ) KeyStates.dPressed = true;
		
		if ( KeyStates.wPressed ) {
			z -= Math.sin( Math.toRadians( yaw + 90 ) ) / 200f * getMaster( ).getDelta( );
			x -= Math.cos( Math.toRadians( yaw + 90 ) ) / 200f * getMaster( ).getDelta( );
		}
		if ( KeyStates.sPressed ) {
			z += Math.sin( Math.toRadians( yaw + 90 ) ) / 200f * getMaster( ).getDelta( );
			x += Math.cos( Math.toRadians( yaw + 90 ) ) / 200f * getMaster( ).getDelta( );
		}
		if ( KeyStates.aPressed ) {
			z -= Math.sin( Math.toRadians( yaw ) ) / 200f * getMaster( ).getDelta( );
			x -= Math.cos( Math.toRadians( yaw ) ) / 200f * getMaster( ).getDelta( );
		}
		if ( KeyStates.dPressed ) {
			z += Math.sin( Math.toRadians( yaw ) ) / 200f * getMaster( ).getDelta( );
			x += Math.cos( Math.toRadians( yaw ) ) / 200f * getMaster( ).getDelta( );
		}
		
		if ( Mouse.isButtonDown( 0 ) && !KeyStates.leftClickPressed ) {
			KeyStates.leftClickPressed = true;
		}
		
		updateMouseLook( );
		
		for ( int i = 0 ; i < blockList.size( ) ; i++ ) {
			Block blk = blockList.get( i );
			double distance = Math.sqrt( Math.pow( blk.x - x , 2 ) + Math.pow( blk.z - z , 2 ) );
			if ( distance < radius ) {
				
				x = lastX;
				z = lastZ;
				
			}
		}
		
		if ( door != null ) {
			double distance = Math.sqrt( Math.pow( door.x - x , 2 ) + Math.pow( door.z - z , 2 ) );
			if ( distance < radius ) {
				Resources.unlock.start( );
				status = "aYOUaUSEaTHEaKEYaTOaOPENaTHEaDOORa";
				GL11.glClearColor( 66f / 255f , 182f / 255f, 219f / 255f , 1f );
				transition = true;
				door = null;
			}
		}
		
		if ( transition ) {
			transitionTimer += getMaster( ).getDelta( );
			
			if ( transitionTimer > 5000 ) {
				getMaster( ).win( );
			}
		}
		
		lastZ = z;
		lastX = x;
	}
	
	private void updateMouseLook( ) {
		int mouseX = Mouse.getX( );
		int mouseY = Mouse.getY( );
		
		int xSpeed = lastMouseX - mouseX;
		int ySpeed = lastMouseY - mouseY;
				
		pitch += ySpeed;
		yaw -= xSpeed;
		
		lastMouseX = mouseX;
		lastMouseY = mouseY;
	}
	
	// NOTE NEGATIVE Z VALUES ARE AWAY FROM CAMERA!!!!!
	
	public void render( ) {
		GL11.glLoadIdentity( );
		
		GL11.glRotatef( pitch , 1f , 0f , 0f );
		GL11.glRotatef( yaw , 0f , 1f , 0f );
		
		GL11.glTranslatef( -x , -y , -z );
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.wall.getTextureName( ) );
		
		for ( int i = 0 ; i < blockList.size( ) ; i++ ) {
			blockList.get( i ).draw( );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.floor.getTextureName( ) );
		
		int iHolder = 0;
		
		for ( int i = 0 ; i < floorCeilingList.size( ) ; i++ ) {
			if ( floorCeilingList.get( i ).sky ) {
				iHolder = i;
				break;
			}
			floorCeilingList.get( i ).drawFloor( );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.grass.getTextureName( ) );
		
		for ( int i = iHolder ; i < floorCeilingList.size( ) ; i++ ) {
			floorCeilingList.get( i ).drawFloor( );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.ceiling.getTextureName( ) );
		
		for ( int i = 0 ; i < floorCeilingList.size( ) ; i++ ) {
			floorCeilingList.get( i ).drawCeiling( );
			if ( i == iHolder ) break;
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.sky.getTextureName( ) );
		
		for ( int i = iHolder ; i < floorCeilingList.size( ) ; i++ ) {
			floorCeilingList.get( i ).drawCeiling( );
		}
		
		if ( portal != null ) {
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.arch.getTextureName( ) );
			portal.draw( );
		}
		
		if ( door != null ) {
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.door.getTextureName( ) );
			door.draw( );
		}
		
		drawHUD( );
	}
	
	private void drawHUD( ) {
		setUpOrthoHUD( );
		
		Resources.crosshair.draw( getMaster( ).getDisplayWidth( ) / 2 - 16 , getMaster( ).getDisplayHeight( ) / 2 - 16 ); // I know tex dimentions are 16x16
		
		if ( !status.isEmpty( ) ) {
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontBackground.getTexture( ).getTextureName( ) );
			Resources.fontBackground.drawString( status , 192 , 32 );
		}
		
		if ( transition ) {
			GL11.glColor4f( 0f , 0f , 0f , 1f * ( ( float )transitionTimer / 5000f ) );
			Resources.blank.draw( 0 , 0 , 40 , 40 );
			GL11.glColor4f( 1f , 1f , 1f , 1f );
		}
		
		restorePerspective( );
	}
	
	private void setUpOrthoHUD( ) {
		GL11.glDisable( GL11.GL_DEPTH_TEST );
		
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glPushMatrix( );
		
		GL11.glLoadIdentity( );
		GL11.glOrtho( 0f , getMaster( ).getDisplayWidth( ) , getMaster( ).getDisplayHeight( ) , 0f , -1f , 1f );
		
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glPushMatrix( );
		
		GL11.glLoadIdentity( );
	}
	
	private void restorePerspective( ) {
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glPopMatrix( );
		
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glPopMatrix( );
		
		GL11.glEnable( GL11.GL_DEPTH_TEST );
	}

	
	
}
