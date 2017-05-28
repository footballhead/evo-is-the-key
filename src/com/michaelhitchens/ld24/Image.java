package com.michaelhitchens.ld24;

//import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

public class Image { // rename to Image when moved to OpenGL

	static String baseDirectory = "";
	private int textureName;
	private int width;
	private int height;
	private ByteBuffer data;
	
	public Image( String file ) throws IOException {
		loadImage( file );
	}
	
	public void draw( int x , int y , float xscale , float yscale ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , textureName );
		
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex2i( x , y ); //0,0
        GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex2i( x , ( int )( y + height * yscale ) ); //16,0
        GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex2i( ( int )( x + width * xscale ) , ( int )( y + height * yscale ) ); //16,16
        GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex2i( ( int )( x + width * xscale ) , y ); //0,16
        
        GL11.glEnd( );
	}
	
	public void drawNoTexture( int x , int y , float xscale , float yscale ) {
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex2i( x , y ); //0,0
        GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex2i( x , ( int )( y + height * yscale ) ); //16,0
        GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex2i( ( int )( x + width * xscale ) , ( int )( y + height * yscale ) ); //16,16
        GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex2i( ( int )( x + width * xscale ) , y ); //0,16
        
        GL11.glEnd( );
	}
	
	public void draw( int x , int y ) {
		draw( x , y , 1f , 1f );
	}
	
	public void draw( int x , int y , float xscale ) {
		draw( x , y , xscale , 1f );
	}
	
	/*public void draw( int x , int y , Color col ) {
		GL11.glColor4ub( ( byte )col.getRed( ) , ( byte )col.getGreen( ) , ( byte )col.getBlue( ) , ( byte )col.getAlpha( ) );
		draw( x , y , 1f , 1f );
	}
	
	public void draw( int x , int y , Color col , float xscale ) {
		GL11.glColor4ub( ( byte )col.getRed( ) , ( byte )col.getGreen( ) , ( byte )col.getBlue( ) , ( byte )col.getAlpha( ) );
		draw( x , y , xscale , 1f );
	}
	
	public void draw( int x , int y , Color col , float xscale , float yscale ) {
		GL11.glColor4ub( ( byte )col.getRed( ) , ( byte )col.getGreen( ) , ( byte )col.getBlue( ) , ( byte )col.getAlpha( ) );
		draw( x , y , xscale , yscale );
	}*/
	
	private byte[ ] formatData( int[ ] intImageData ) {
		byte[ ] byteImageData = new byte[ intImageData.length * 4 ];
		
		for ( int i = 0 ; i < intImageData.length ; i++ ) {
	    	int color = intImageData[ i ];
	    	
	    	int alpha = ( color & 0xFF000000 ) >> 24;
	    	int red = ( color & 0x00FF0000 ) >> 16;
	    	int green = ( color & 0x0000FF00 ) >> 8;
	    	int blue = ( color & 0x000000FF );
	    	
	    	byteImageData[ i * 4 ] = ( byte )red;
	    	byteImageData[ i * 4 + 1 ] = ( byte )green;
	    	byteImageData[ i * 4 + 2 ] = ( byte )blue;
	    	byteImageData[ i * 4 + 3 ] = ( byte )alpha;
	    }
		
		return byteImageData;
	}
	
	/*public Color getPixel( int x , int y ) {
		int pix = ( y * width + x ) * 4;
		return new Color( data.get( pix ) , data.get( pix + 1 ) , data.get( pix + 2 ) , data.get( pix + 3 ) );
	}*/
	
	/**
	 * Loads a different image with the same variable references.
	 * WARNING: Dimensions and colorspace MUST match original image.
	 */
	public void replace( String file ) throws IOException {
		BufferedImage bufimage = loadBufferedImage( file );
		
	    //width = bufimage.getWidth( );
	    //height = bufimage.getHeight( );
	    int imageArea = width * height;
	    	
	    int[ ] intImageData = new int[ imageArea ];
	    bufimage.getRGB( 0 , 0 , width , height , intImageData , 0 , width ); // still have no idea what scansize is for
	    
	    byte[ ] byteImageData = formatData( intImageData );
	    
	    data.put( byteImageData );
	    data.flip( );
	    
	    GL11.glBindTexture( GL11.GL_TEXTURE_2D , textureName );
	    GL11.glTexImage2D( GL11.GL_TEXTURE_2D , 0 , GL11.GL_RGBA , width , height , 0 , GL11.GL_RGBA , GL11.GL_UNSIGNED_BYTE , data );
	}
	
	private BufferedImage loadBufferedImage( String file ) throws IOException {
		InputStream input = getClass( ).getClassLoader( ).getResourceAsStream( baseDirectory + file );
		
		if ( input == null ) {
			System.out.println( "Using system file" );
			return ImageIO.read( new File( baseDirectory + file ) );
		} else {
			System.out.println( "Using JAR file" );
			return ImageIO.read( input );
		}
	}
	
	/** My lovely texture loading code which will haunt me for the rest of my life */
	private void loadImage( String file ) throws IOException {
		BufferedImage bufimage = loadBufferedImage( file );
		
	    width = bufimage.getWidth( );
	    height = bufimage.getHeight( );
	    int imageArea = width * height;
	    	
	    int[ ] intImageData = new int[ imageArea ];
	    bufimage.getRGB( 0 , 0 , width , height , intImageData , 0 , width ); // still have no idea what scansize is for
	    
	    byte[ ] byteImageData = formatData( intImageData );
	    
	    data = ByteBuffer.allocateDirect( byteImageData.length );
	    	
	    data.put( byteImageData );
	    data.flip( );
	    	
	    IntBuffer buf = ByteBuffer.allocateDirect( 4 ).order( ByteOrder.nativeOrder( ) ).asIntBuffer( );
	    GL11.glGenTextures( buf ); // Create Texture In OpenGL
	        
	    GL11.glBindTexture( GL11.GL_TEXTURE_2D , buf.get( 0 ) );
	        
	    GL11.glTexParameteri( GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MIN_FILTER , GL11.GL_NEAREST );
	    GL11.glTexParameteri( GL11.GL_TEXTURE_2D , GL11.GL_TEXTURE_MAG_FILTER , GL11.GL_NEAREST );
	    GL11.glTexImage2D( GL11.GL_TEXTURE_2D , 0 , GL11.GL_RGBA , width , height , 0 , GL11.GL_RGBA , GL11.GL_UNSIGNED_BYTE , data );
	    
	    textureName = buf.get( 0 ); // because I don't know where to put this
	}
	
	public static void setBaseDirectory( String dir ) {
		baseDirectory = dir;
	}
	
	public static String getBaseDirectory( ) { return baseDirectory; }
	public int getTextureName( ) { return textureName; }
	public int getWidth( ) { return width; }
	public int getHeight( ) { return height; }
	public ByteBuffer getData( ) { return data; }
	//public int getCenterX( ) { return width
	
}
