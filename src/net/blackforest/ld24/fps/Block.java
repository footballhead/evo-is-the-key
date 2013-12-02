package net.blackforest.ld24.fps;

import org.lwjgl.opengl.GL11;

public class Block {
	final public static byte NORTH = 0;
	final public static byte EAST = 1;
	final public static byte SOUTH = 2;
	final public static byte WEST = 3;
	
	boolean[ ] direction = new boolean[ 4 ];
	
	int x , y , z;
	
	public Block( int x , int y , int z , boolean northWall , boolean eastWall , boolean southWall , boolean westWall ) {
		direction[ NORTH ] = northWall;
		direction[ EAST ] = eastWall;
		direction[ SOUTH ] = southWall;
		direction[ WEST ] = westWall;
		
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void draw( ) {
		GL11.glBegin( GL11.GL_QUADS );
		
		if ( direction[ NORTH ] ) {
			GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f(  1f + x , -1f + y , -1f + z );
			GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f( -1f + x , -1f + y , -1f + z );
			GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f( -1f + x ,  1f + y , -1f + z );
			GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f(  1f + x ,  1f + y , -1f + z );
		}
		
		if ( direction[ EAST ] ) {
			GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f(  1f + x , -1f + y ,  1f + z );
			GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f(  1f + x , -1f + y , -1f + z );
			GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f(  1f + x ,  1f + y , -1f + z );
			GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f(  1f + x ,  1f + y ,  1f + z );
		}
		
		if ( direction[ SOUTH ] ) {
			GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f( -1f + x , -1f + y , 1f + z );
			GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f(  1f + x , -1f + y , 1f + z );
			GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f(  1f + x ,  1f + y , 1f + z );
			GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f( -1f + x ,  1f + y , 1f + z );
		}
		
		if ( direction[ WEST ] ) {
			GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex3f( -1f + x , -1f + y , -1f + z );
			GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex3f( -1f + x , -1f + y ,  1f + z );
			GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex3f( -1f + x ,  1f + y ,  1f + z );
			GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex3f( -1f + x ,  1f + y , -1f + z );
		}
		
		GL11.glEnd( );
	}
	
	public boolean isPointInside( float testx , float testy , float testz ) {
		if ( testx > x - 1 && testx < x + 1 && testy > y - 1 && testy < y + 1 && testz > z - 1 && testz < z + 1 ) return true;
		return false;
	}
	
}
