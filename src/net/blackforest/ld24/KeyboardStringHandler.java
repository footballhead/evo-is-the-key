package net.blackforest.ld24;

import org.lwjgl.input.Keyboard;

public class KeyboardStringHandler {

	private static String keyboardString = "";
	private static boolean inputEnabled = false;
	
	public static void clearKeyboardString( ) {
		keyboardString = "";
	}
	
	public static boolean isInputEnabled( ) {
		return inputEnabled;
	}
	
	public static String getKeyboardString( ) {
		return keyboardString;
	}
	
	public static void handleInput( ) {
		// Thank you Google http://www.java-gaming.org/index.php?topic=25864.0
		if ( inputEnabled ) {
			while ( Keyboard.next( ) ) {
				if ( Keyboard.getEventKeyState( ) ) { // apparently this is wrong, but good enough for my uses
					char c = Keyboard.getEventCharacter( );
					int ci = ( int )c;
					
					if ( ci != 0 && ci != 13 && ci != 9 ) { // 0 = shift, 13 = enter, 9 = tab
						if ( ci == 8 || ci == 127 ) { // 8 = backspace , 127 = delete
							if ( keyboardString.length( ) > 0 ) keyboardString = keyboardString.substring( 0 , keyboardString.length( ) - 1 );
						} else {
							keyboardString += Keyboard.getEventCharacter( );
						}
					}
				}
			}
		} else {
			while ( Keyboard.next( ) ) {
				// TODO DO SOMETHING HERE
			}
		}
	}
	
	public static void setInputEnabled( boolean enabled ) {
		inputEnabled = enabled;
	}
	
	public static void toggleInputEnabled( ) {
		inputEnabled = !inputEnabled;
	}
	
}
