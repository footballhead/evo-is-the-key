package net.blackforest.ld24;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class SpriteStrip extends Image {

	private int cellWidth; // not used?
	private int numOfImages;
	private float texOffsetU; 
	
	public SpriteStrip( String file , int cellWidth ) throws IOException {
		super( file );
		this.cellWidth = cellWidth;
		numOfImages = getWidth( ) / cellWidth;
		texOffsetU = 1f / ( float )numOfImages;
	}
	
	public void draw( int x , int y , int column ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , getTextureName( ) );
		
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( texOffsetU * column , 0 );
        GL11.glVertex2i( x , y );
        GL11.glTexCoord2f( texOffsetU * column , 1f );
        GL11.glVertex2i( x , y + getHeight( ) );
        GL11.glTexCoord2f( texOffsetU * ( column + 1 ) , 1f );
        GL11.glVertex2i( x + cellWidth , y + getHeight( ) );
        GL11.glTexCoord2f( texOffsetU * ( column + 1 ) , 0f );
        GL11.glVertex2i( x + cellWidth , y );
        
        GL11.glEnd( );
	}
	
	public void drawNoTexture( int x , int y , int column ) {
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( texOffsetU * column , 0 );
        GL11.glVertex2i( x , y );
        GL11.glTexCoord2f( texOffsetU * column , 1f );
        GL11.glVertex2i( x , y + getHeight( )  );
        GL11.glTexCoord2f( texOffsetU * ( column + 1 ) , 1f );
        GL11.glVertex2i( x + cellWidth , y + getHeight( ) );
        GL11.glTexCoord2f( texOffsetU * ( column + 1 ) , 0f );
        GL11.glVertex2i( x + cellWidth , y );
        
        GL11.glEnd( );
	}
	
	float getTextureOffsetU( ) { return texOffsetU; }
	
	public int getCellWidth( ) { return cellWidth; }
	public int getNumberOfSubImages( ) { return numOfImages; }
}
