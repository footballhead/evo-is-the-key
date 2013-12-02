package net.blackforest.ld24;

import java.io.IOException;
import java.util.Random;

import net.blackforest.ld24.fps.FirstPersonShooter;
import net.blackforest.ld24.rogueascii.AsciiRogue;
import net.blackforest.ld24.roguegfx.RogueGFX;
import net.blackforest.ld24.textadventure.TextAdventure;
import net.blackforest.ld24.topdownshooter.TopDownShooter;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Game {

	public static Random random = new Random( );
	
	private boolean running = true;
	private String displayTitle = "Ludum Dare 24 Game";
	private DisplayMode displayMode = new DisplayMode( 640 , 480 );
	private int FPS = 0;
	private int delta = 0;
	
	private Stage[ ] stage = new Stage[ 6 ];
	private int currentStage = 0;
	
	public static byte health = 100;
	
	public Game( ) {
		initDisplay( );
		initGL( );
		Resources.initialize( );
		createStages( );
		
		stage[ currentStage ].switchTo( );
	}
	
	/** init **/
	
	private void initGL( ) {
		GL11.glEnable( GL11.GL_TEXTURE_2D );
		GL11.glClearColor( 0f , 0f , 0f , 1f );
		
		GL11.glEnable( GL11.GL_BLEND );
		GL11.glBlendFunc( GL11.GL_SRC_ALPHA , GL11.GL_ONE_MINUS_SRC_ALPHA );
		
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glLoadIdentity( );
		
		GL11.glOrtho( 0f , displayMode.getWidth( ) , displayMode.getHeight( ) , 0f , -1f , 1f );
		
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
		GL11.glLoadIdentity( );
	}
	
	private void initDisplay( ) {
		try {
			Display.setFullscreen( false );
			Display.setTitle( displayTitle );
			Display.setDisplayMode( displayMode );
			Display.create( );
		} catch ( LWJGLException lwjgle ) {
			lwjgle.printStackTrace( );
			System.exit( 1 );
		}
	}
	
	private void createStages( ) {
		stage[ 0 ] = new TerminalMenu( this );
		stage[ 1 ] = new TextAdventure( this );
		stage[ 2 ] = new AsciiRogue( this );
		stage[ 3 ] = new RogueGFX( this );
		stage[ 4 ] = new TopDownShooter( this );
		stage[ 5 ] = new FirstPersonShooter( this );
	}
	
	private void reset( ) {
		stage[ 1 ] = new TextAdventure( this );
		stage[ 2 ] = new AsciiRogue( this );
		stage[ 3 ] = new RogueGFX( this );
		stage[ 4 ] = new TopDownShooter( this );
		stage[ 5 ] = new FirstPersonShooter( this );
	}
	
	/** run **/
	
	public void run( ) {
		long time = System.currentTimeMillis( );
		int frames = 0;
		
		while ( running ) {
			long tickStart = System.currentTimeMillis( );
			
			update( );
			render( );
			Display.update( );
			
			frames++;
			if ( System.currentTimeMillis( ) > time + 1000 ) {
				FPS = frames;
				frames = 0;
				time += 1000;
				
				System.out.println( "FPS: " + FPS ); // DEBUG
			}
			
			delta = ( int )( System.currentTimeMillis( ) - tickStart );
		}
		
		cleanUp( );
		
	}
	
	private void cleanUp( ) {
		Display.destroy( );
		//Resources.fadeInNoise.stop( );
		//Resources.fadeInNoise.stop( );
	}
	
	private void update( ) {
		KeyboardStringHandler.handleInput( );
		
		stage[ currentStage ].update( );
		
		KeyStates.reset( );
		
		if ( Display.isCloseRequested( ) ) running = false;
		if ( Keyboard.isKeyDown( Keyboard.KEY_ESCAPE ) ) running = false;
	}
	
	private void render( ) {
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
		
		stage[ currentStage ].render( );
	}
	
	/** other **/
	
	public void nextStage( ) {
		currentStage++;
		stage[ currentStage ].switchTo( );
	}
	
	public void gameOver( ) {
		changeStage( 0 );
		( ( TerminalMenu )stage[ 0 ] ).gameOver( );
		reset( );
		Resources.pureNoise.setFramePosition( 0 );
		Resources.pureNoise2.setFramePosition( 0 );
		Resources.fadeInNoise.setFramePosition( 0 );
	}
	
	public void win( ) {
		changeStage( 0 );
		( ( TerminalMenu )stage[ 0 ] ).win( );
		reset( );
	}
	
	public void changeStage( int newStage ) {
		currentStage = newStage;
		stage[ currentStage ].switchTo( );
	}
	
	public void stopRunning( ) {
		running = false;
	}
	
	
	/** entry **/
	
	public static void main( String[ ] args ) {
		Game game = new Game( );
		game.run( );
	}
	
	
	
	/** get **/
	 
	public int getFPS( ) {
		return FPS;
	}
	
	public int getDisplayWidth( ) {
		return displayMode.getWidth( );
	}
	
	public int getDisplayHeight( ) {
		return displayMode.getHeight( );
	}
	
	public int getDelta( ) {
		return delta;
	}
	
}
