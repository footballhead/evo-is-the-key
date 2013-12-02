package net.blackforest.ld24;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Resources {
	public static Image wall;
	public static Image crosshair;
	public static Image arch;
	public static Image ceiling;
	public static Image floor;
	public static Image grass;
	public static Image sky;
	public static Image door;
	
	public static Image enemyHealthBar;
	public static Image bossHealthBack;
	public static Image ghost;
	public static Image ghostAngry;
	public static Image blank;
	public static Image testPlayer;
	public static Image bullet;
	public static Image ghostBullet;
	public static Image minion;
	public static Image particle;
	
	public static SpriteStrip rogueGFX;
	public static SpriteStrip shadow;
	public static SpriteStrip rogueLiving;
	public static SpriteStrip topdownTiles;
	
	public static MonospaceFont fontBackground;
	public static MonospaceFont fontNoOutline;
	
	public static Clip fadeInNoise;
	public static Clip endNoise;
	public static Clip pureNoise;
	public static Clip pureNoise2;
	public static Clip shoot;
	public static Clip shoot2;
	public static Clip ghostShoot;
	public static Clip ghostShoot2;
	public static Clip unlock;
	
	public static void initialize( ) {
		try {
			wall = new Image( "data/wall.png" );
			crosshair = new Image( "data/crosshair.png" );
			arch = new Image( "data/arch.png" );
			ceiling = new Image( "data/ceiling.png" );
			floor = new Image( "data/floor.png" );
			grass = new Image( "data/grass.png" );
			sky = new Image( "data/sky.png" );
			door = new Image( "data/door.png" );
			
			particle = new Image( "data/particle2.png" );
			minion = new Image( "data/minion.png" );
			enemyHealthBar = new Image( "data/boss-health.png" );
			bossHealthBack = new Image( "data/boss-health-back.png" );
			ghost = new Image( "data/ghost.png" );
			ghostAngry = new Image( "data/ghost-angry.png" );
			testPlayer = new Image( "data/test-player.png" );
			bullet = new Image( "data/bullet.png" );
			ghostBullet = new Image( "data/bullet2.png" );
			
			shadow = new SpriteStrip( "data/shadow.png" , 32 );
			topdownTiles = new SpriteStrip( "data/topdown-gfx2.png" , 32 );
			blank = new Image( "data/blank.png" );
			rogueGFX = new SpriteStrip( "data/rogue-gfx.png" , 24 );
			rogueLiving = new SpriteStrip( "data/rogue-gfx-living.png" , 24 );
			fontBackground = new MonospaceFont( new Image( "data/font-background.png" ) , 8 , 13 );
			fontNoOutline = new MonospaceFont( new Image( "data/font-no-outline.png" ) , 8 , 13 );
			
			// I don't know what this does
			// http://ubuntuforums.org/showthread.php?t=1469572
			AudioInputStream audIn = AudioSystem.getAudioInputStream( new File( "data/noise.wav" ) );
			AudioFormat format = audIn.getFormat( );
			DataLine.Info info = new DataLine.Info( Clip.class , format );
			fadeInNoise = ( Clip )AudioSystem.getLine( info );
			fadeInNoise.open( audIn );
			
			//loadSoundToClip( "data/pure-static.wav" , pureNoise );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/pure-static.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			pureNoise = ( Clip )AudioSystem.getLine( info );
			pureNoise.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/pure-static2.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			pureNoise2 = ( Clip )AudioSystem.getLine( info );
			pureNoise2.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/shoot.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			shoot = ( Clip )AudioSystem.getLine( info );
			shoot.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/shoot.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			shoot2 = ( Clip )AudioSystem.getLine( info );
			shoot2.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/ghost-shoot.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			ghostShoot = ( Clip )AudioSystem.getLine( info );
			ghostShoot.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/ghost-shoot.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			ghostShoot2 = ( Clip )AudioSystem.getLine( info );
			ghostShoot2.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/end-static.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			endNoise = ( Clip )AudioSystem.getLine( info );
			endNoise.open( audIn );
			
			audIn = AudioSystem.getAudioInputStream( new File( "data/unlock.wav" ) );
			format = audIn.getFormat( );
			info = new DataLine.Info( Clip.class , format );
			unlock = ( Clip )AudioSystem.getLine( info );
			unlock.open( audIn );
			
		} catch ( IOException ioe ) {
			ioe.printStackTrace( );
			System.exit( 1 );
		} catch ( LineUnavailableException lue ) {
			lue.printStackTrace( );
			System.exit( 1 );
		} catch ( UnsupportedAudioFileException uafe ) {
			uafe.printStackTrace( );
			System.exit( 1 );
		}
	}
	
	private static void loadSoundToClip( String filePath , Clip destination ) throws LineUnavailableException , IOException , UnsupportedAudioFileException {
		InputStream input = Resources.class.getClassLoader( ).getResourceAsStream( filePath );
		AudioInputStream audIn = null;
		
		if ( input == null ) {
			System.out.println( "Using system sound file" );
			audIn = AudioSystem.getAudioInputStream( new File( filePath ) );
		} else {
			System.out.println( "Using JAR sound file" );
			audIn = AudioSystem.getAudioInputStream( input );
		}
		
		AudioFormat format = audIn.getFormat( );
		DataLine.Info info = new DataLine.Info( Clip.class , format );
		destination = ( Clip )AudioSystem.getLine( info );
		destination.open( audIn );
	}
}
