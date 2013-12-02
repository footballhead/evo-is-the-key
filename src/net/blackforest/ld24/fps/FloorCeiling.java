package net.blackforest.ld24.fps;

import org.lwjgl.opengl.GL11;

public class FloorCeiling {

	int x , y , z;
	boolean sky;
	
	public FloorCeiling( int x , int y , int z , boolean isSky ) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.sky = isSky;
	}
	
	public void drawFloor( ) {
		GL11.glBegin( GL11.GL_QUADS );
		
		GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f( -1f + x , -1f + y , -1f + z );
		GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f( -1f + x , -1f + y ,  1f + z );
		GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f(  1f + x , -1f + y ,  1f + z );
		GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f(  1f + x , -1f + y , -1f + z );
		
		GL11.glEnd( );
	}
	
	public void drawCeiling( ) {
		GL11.glBegin( GL11.GL_QUADS );
		
		GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f( -1f + x , 1f + y , -1f + z );
		GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f(  1f + x , 1f + y , -1f + z );
		GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f(  1f + x , 1f + y ,  1f + z );
		GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f( -1f + x , 1f + y ,  1f + z );
		
		GL11.glEnd( );
	}
}
