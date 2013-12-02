package net.blackforest.ld24;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class KeyStates {

	public static boolean enterPressed = false;
	public static boolean wPressed = false;
	public static boolean aPressed = false;
	public static boolean sPressed = false;
	public static boolean dPressed = false;
	
	public static boolean leftClickPressed = false;
	
	public static void reset( ) {
		if ( !Keyboard.isKeyDown( Keyboard.KEY_RETURN ) && enterPressed ) enterPressed = false;
		if ( !Keyboard.isKeyDown( Keyboard.KEY_W ) && wPressed ) wPressed = false;
		if ( !Keyboard.isKeyDown( Keyboard.KEY_A ) && aPressed ) aPressed = false;
		if ( !Keyboard.isKeyDown( Keyboard.KEY_S ) && sPressed ) sPressed = false;
		if ( !Keyboard.isKeyDown( Keyboard.KEY_D ) && dPressed ) dPressed = false;
		if ( !Mouse.isButtonDown( 0 ) && leftClickPressed ) leftClickPressed = false; 
	}
	
}
