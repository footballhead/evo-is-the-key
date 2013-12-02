package net.blackforest.ld24;

import org.lwjgl.opengl.GL11;

// ASSUMED VARIABLES
// Texture is 256 by 256
// There are 16 characters per line
public class MonospaceFont {

	int glyphWidth;
	int glyphHeight;
	private float texWidth;
	private float texHeight;
	Image texture;
	
	/**
	 * Creates a monospace bitmap font from an OpenGL texture.
	 * Several variables are assumed when creating a monospace font.
	 * The first is that the texture is 256 pixels squared.
	 * The second is that there are 16 characters horizontally.
	 * 
	 * @param texture OpenGL texture wrapped as an Image.
	 * @param charWidth The width of an individual character.
	 * @param charHeight The height of an individual character.
	 */
	public MonospaceFont( Image texture , int charWidth , int charHeight ) {
		this.texture = texture;
		glyphWidth = charWidth;
		glyphHeight = charHeight;
		
		texWidth = 1f / 256f * glyphWidth;
		texHeight = 1f / 256f * glyphHeight;
	}
	
	public void drawString( String string , int x , int y , int scale ) {
    	GL11.glBegin( GL11.GL_QUADS );
		
    	int yOffset = 0;
    	int indexOffset = 0;
    	
		for ( int i = 0 ; i < string.length( ) ; i++ ) {
        	char c = string.charAt( i );

        	if ( ( int )c != 32 && c != '\n' ) { // get rid of SPAAAACE character
        		c -= 33;
        		
        		int row = ( int )( ( float )c / 16f ); // get rid of remainder
        		int column = c % 16; // get the remainder
        		
                GL11.glTexCoord2f( texWidth * column , texHeight * row );
                GL11.glVertex2i( x + glyphWidth * scale * ( i - indexOffset ) , y + yOffset ); //0,0
                GL11.glTexCoord2f( texWidth * column , texHeight * ( row + 1 ) );
                GL11.glVertex2i( x + glyphWidth * scale * ( i - indexOffset ) , y + yOffset + glyphHeight * scale ); //0,1
                GL11.glTexCoord2f( texWidth * ( column + 1 ) , texHeight * ( row + 1 ) );
                GL11.glVertex2i( x + glyphWidth * scale * ( ( i - indexOffset ) + 1 ) , y + yOffset + glyphHeight * scale ); //1,1
                GL11.glTexCoord2f( texWidth * ( column + 1 ) , texHeight * row );
                GL11.glVertex2i( x + glyphWidth * scale * ( ( i - indexOffset ) + 1 ) , y + yOffset ); //1,0
        	} else if ( c == '\n' ) {
        		yOffset += getFontHeight( );
        		indexOffset = i + 1;
        	}
        }
		
		GL11.glEnd( );
	}
	
	public void drawString( String string , int x , int y ) {
		drawString( string , x , y , 1 );
	}
	
	/*public void drawString( String string , int x , int y , int scale , Color color ) {
		GL11.glColor4ub( color.getRed( ) , color.getGreen( ) , color.getBlue( ) , color.getAlpha( ) );
		drawString( string , x , y , scale );
	}*/
	
	
	public void drawString( String string , int x , int y , int scale , boolean changeTexture ) {
		if ( changeTexture ) GL11.glBindTexture( GL11.GL_TEXTURE_2D , texture.getTextureName( ) );
		drawString( string , x , y , scale );
	}
	
	public void drawStringCenteredTexture( String string , int x , int y ) {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , texture.getTextureName( ) );
		drawStringCentered( string , x , y );
	}
	
	public void drawStringCentered( String string , int x , int y ) {
		drawString( string , x - getStringWidth( string ) / 2 , y - glyphHeight / 2 , 1 );
	}
	
	/*public void drawString( String string , int x , int y , int scale , Color color , boolean changeTexture ) {
		GL11.glColor4ub( color.getRed( ) , color.getGreen( ) , color.getBlue( ) , color.getAlpha( ) );
		if ( changeTexture ) GL11.glBindTexture( GL11.GL_TEXTURE_2D , texture.getTextureName( ) );
		drawString( string , x , y , scale );
	}*/
	
	public int getStringWidth( String string ) {
		return string.length( ) * glyphWidth;
	}
	
	public int getStringWidth( int stringLength ) {
		return stringLength * glyphWidth;
	}
	
	public int getFontHeight( ) { return glyphHeight; }
	
	public Image getTexture( ) { return texture; }
}
