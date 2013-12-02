package net.blackforest.ld24;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class TerminalMenu extends Stage { // max 35 lines!

	String[ ] loadingStuff = { "Booting system..." , " Done." , "\nThank you for choosing Ludum Dare 24 OS!" , "\nCopyright (C) Michael Hitchens 2012" , "\n\nuser: michael" , "\npass:" , "\n\nWelcome user michael!" , "\nType 'HELP' if you are stuck." , "\nmichael@ludum-dare-os:~$ " };
	
	String terminalString = "";
	
	int timer = 0;
	int loadingStage = 0;
	
	public TerminalMenu( Game game ) {
		super( game );
		KeyboardStringHandler.setInputEnabled( false ); // just in case
		KeyboardStringHandler.clearKeyboardString( ); // just in case
		
		terminalString += loadingStuff[ 0 ];
		timer = Game.random.nextInt( 80 );
	}
	
	public void switchTo( ) {
		GL11.glClearColor( 0f , 0f , 0f , 1f );
		System.out.println( "YAY!" );
		GL11.glColor4f( 1f , 1f , 1f , 1f );
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontNoOutline.getTexture( ).getTextureName( ) );
			
			GL11.glMatrixMode( GL11.GL_PROJECTION );
			GL11.glLoadIdentity( );
			
			GL11.glOrtho( 0f , getMaster( ).getDisplayWidth( ) , getMaster( ).getDisplayHeight( ) , 0f , -1f , 1f );
			
			GL11.glMatrixMode( GL11.GL_MODELVIEW );
			GL11.glLoadIdentity( );
	}
	
	public void update( ) {
		if ( Keyboard.isKeyDown( Keyboard.KEY_RETURN ) && !KeyStates.enterPressed ) {
			KeyStates.enterPressed = true;
			String result = parseKeyboardInput( );
			terminalString += KeyboardStringHandler.getKeyboardString( ) + '\n' + result + "\nmichael@ludum-dare-os:~$ ";
			KeyboardStringHandler.clearKeyboardString( );
		}
		
		if ( timer == 0 && loadingStage != loadingStuff.length - 1 ) {
			loadingStage++;
			terminalString += loadingStuff[ loadingStage ];
			timer = Game.random.nextInt( 20 );
		} else if ( timer != 0 ) {
			timer--;
		}
	}
	
	private String parseKeyboardInput( ) {
		String input = KeyboardStringHandler.getKeyboardString( );
		if ( input.equalsIgnoreCase( "orly" ) ) {
			return "Yarly";
		} else if ( input.equalsIgnoreCase( "SHUTDOWN" ) ) {
			getMaster( ).stopRunning( );
			return "SHUTTING DOWN";
		} else if ( input.equalsIgnoreCase( "help" ) ) {
			return "\n***LUDUM DARE 24 OS HELP DOCUMENTS***\n    'HELP' SHOWS THIS HELP DOCUMENT.\n    'LS' DISPLAYS THE CONTENTS OF THE CURRENT DIRECTORY\n    './<filename>' EXECUTES THE SPECIFIED FILE\n        eg. './EVOLUTION-IS-THE-KEY' WILL START THE GAME.\n    'CREDITS' SHOWS THE LIST OF CREDITS FOR THE LUDUM DARE 24 OS\n    'SHUTDOWN' TERMINATES THE LUDUM DARE 24 OS.\n* * * * * * * * * * * * * * * * * *\n    'W', 'A', 'S', 'D' (IN GAME) MOVE PLAYER\n    'LEFT MOUSE' SHOOTS WEAPON (IF AVAILABLE)\n* * * * * * * * * * * * * * * * * *";
		} else if ( input.equalsIgnoreCase( "ls" ) ) {
			return "EVOLUTION-IS-THE-KEY";
		} else if ( input.equalsIgnoreCase( "./evolution-is-the-key" ) ) {
			getMaster( ).nextStage( );
			return "Starting the game...";
		} else if ( input.equalsIgnoreCase( "credits" ) ) {
			return "\n***LUDUM DARE 24 OS CREDITS***\n    ALL ASSETS CREATED BY MICHAEL HITCHENS USING\n    JAVA\n    GIMP\n    LIGHTWEIGHT JAVA GAME LIBRARY\n    AUDACITY\n* * * * * * * * * * * * * * * * * *\n";
		} else if ( input.equalsIgnoreCase( "game" ) ) {
			return "TO EXECUTE 'GAME' USE './GAME'";
		}
		
		return "NO COMMAND '" + input + "' FOUND. TYPE 'HELP' IF YOU ARE STUCK.";
	}
	
	public void render( ) {
		//GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontNoOutline.getTexture( ).getTextureName( ) );
		Resources.fontNoOutline.drawString( terminalString.toUpperCase() + KeyboardStringHandler.getKeyboardString( ).toUpperCase( ) + "_" , 4 , 4 );
	}
	
	public void gameOver( ) {
		terminalString = "* * * * * * * * * *\n*                 *\n*    GAME OVER    *\n*                 *\n* * * * * * * * * *\nmichael@ludum-dare-os:~$ ";
		KeyboardStringHandler.clearKeyboardString( ); // just in case
		KeyboardStringHandler.setInputEnabled( true ); // just in case
	}
	
	public void win( ) {
		terminalString = "* * * * * * * * * * * * * * * * *\n*                               *\n*            YOU WIN!           *\n* YOU EVOLVED ENOUGH TO ESCAPE! *\n*                               *\n* * * * * * * * * * * * * * * * *\nmichael@ludum-dare-os:~$ ";
		KeyboardStringHandler.clearKeyboardString( ); // just in case
		KeyboardStringHandler.setInputEnabled( true ); // just in case
	}
	
}
