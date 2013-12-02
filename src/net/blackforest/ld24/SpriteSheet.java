package net.blackforest.ld24;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

public class SpriteSheet extends SpriteStrip {

	private int cellHeight;
	private float texOffsetV;
	public String directory;
	
	public SpriteSheet( String file , int cellWidth , int cellHeight ) throws IOException {
		super( file , cellWidth );
		directory = file;
		this.cellHeight = cellHeight;
		texOffsetV = 1f / ( float )( getHeight( ) / cellHeight );
	}
	
	public void draw( int x , int y , int column , int row ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , getTextureName( ) );
		
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( getTextureOffsetU( ) * column , texOffsetV * row );
        GL11.glVertex2i( x , y );
        GL11.glTexCoord2f( getTextureOffsetU( ) * column , texOffsetV * ( row + 1 ) );
        GL11.glVertex2i( x , y + cellHeight * 2 );
        GL11.glTexCoord2f( getTextureOffsetU( ) * ( column + 1 ) , texOffsetV * ( row + 1 ) );
        GL11.glVertex2i( x + getCellWidth( ) * 2 , y + cellHeight * 2 );
        GL11.glTexCoord2f( getTextureOffsetU( ) * ( column + 1 ) , texOffsetV * row );
        GL11.glVertex2i( x + getCellWidth( ) * 2 , y );
        
        GL11.glEnd( );
	}
	
	public void drawNoTexture( int x , int y , int column , int row ) {
		GL11.glBegin( GL11.GL_QUADS );
        
        GL11.glTexCoord2f( getTextureOffsetU( ) * column , texOffsetV * row );
        GL11.glVertex2i( x , y );
        GL11.glTexCoord2f( getTextureOffsetU( ) * column , texOffsetV * ( row + 1 ) );
        GL11.glVertex2i( x , y + cellHeight * 2 );
        GL11.glTexCoord2f( getTextureOffsetU( ) * ( column + 1 ) , texOffsetV * ( row + 1 ) );
        GL11.glVertex2i( x + getCellWidth( ) * 2 , y + cellHeight * 2 );
        GL11.glTexCoord2f( getTextureOffsetU( ) * ( column + 1 ) , texOffsetV * row );
        GL11.glVertex2i( x + getCellWidth( ) * 2 , y );
        
        GL11.glEnd( );
	}
	
	public void replace( String file ) throws IOException {
		super.replace( file );
		directory = file;
	}
	
}
