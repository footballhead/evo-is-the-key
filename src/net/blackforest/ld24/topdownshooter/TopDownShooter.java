package net.blackforest.ld24.topdownshooter;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.blackforest.ld24.Game;
import net.blackforest.ld24.KeyStates;
import net.blackforest.ld24.Resources;
import net.blackforest.ld24.Stage;

public class TopDownShooter extends Stage {

	boolean intro = true;
	
	boolean transition = false;
	int transitionTimer = 0;
	
	byte[ ][ ] level = new byte[ 22 ][ 22 ];
	
	int viewX = 320 - ( int )( 22f * 32f / 2f );
	int viewY = 240 - ( int )( 22f * 32f / 2f );
	
	int boundsX = 48 , boundsY = 48;
	int boundsEndX = 22 * 32 - 48, boundsEndY = 22 * 32 - 48;
	
	float x = 22f * 32f / 2f , y = 22f * 32f / 2f + 160;
	float radius = 16f;
	
	int gunTimer = 0;
	int damage = 10;
	
	boolean firstSoundPlaying = false;
	boolean firstGhostShoot = false;
	
	Ghost ghost = new Ghost( 352 , 352 );
	ArrayList< Bullet > bulletList = new ArrayList< Bullet >( );
	ArrayList< GhostBullet > ghostBulletList = new ArrayList< GhostBullet >( );
	ArrayList< Minion > minionList = new ArrayList< Minion >( );
	ArrayList< Particle > particleList = new ArrayList< Particle >( );
	
	String introChat = "aaaaaaGIVEaMEaTHEaKEYaaaaaa\n\naORaYOUaWILLaNEVERaEVOLVE!a"; // the font has no 'a' but will try to draw it, creating a filled space
	String endChat = "aNO!a";
	
	public TopDownShooter( Game game ) {
		super( game );
		for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
			for ( int i = 0 ; i < level.length ; i++ ) {
				if ( i == 0 || ii == 0 || i == level.length - 1 || ii == level[ 0 ].length - 1 ) {
					level[ i ][ ii ] = ( byte )( Game.random.nextInt( 2 ) + 2 );
				} else {
					level[ i ][ ii ] = ( byte )Game.random.nextInt( 2 );
				}
			}
		}
		
		ghost.stage = 0;
	}

	public void render() {
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.topdownTiles.getTextureName( ) );
		
		for ( int ii = 0 ; ii < level[ 0 ].length ; ii++ ) {
			for ( int i = 0 ; i < level.length ; i++ ) {
				Resources.topdownTiles.drawNoTexture( i * 32 + viewX , ii * 32 + viewY , level[ i ][ ii ] );
			}
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.shadow.getTextureName( ) );
		
		for ( int i = 1 ; i < level.length - 1 ; i++ ) {
			Resources.shadow.drawNoTexture( i * 32 + viewX , 32 + viewY , 1 );
		}
		
		for ( int i = 1 ; i < level[ 0 ].length - 1 ; i++ ) {
			Resources.shadow.drawNoTexture( 32 + viewX , i * 32 + viewY , 0 );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.bullet.getTextureName( ) );
		
		for ( int i = 0 ; i < bulletList.size( ) ; i++ ) {
			Bullet bul = bulletList.get( i );
			Resources.bullet.drawNoTexture( ( int )bul.x + viewX - 4 , ( int )bul.y + viewY - 4 , 1 , 1 );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.minion.getTextureName( ) );
		
		for ( int i = 0 ; i < minionList.size( ) ; i++ ) {
			Minion min = minionList.get( i );
			Resources.minion.drawNoTexture( ( int )min.x + viewX - 10 , ( int )min.y + viewY - 10 , 1 , 1);
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.ghostBullet.getTextureName( ) );
		
		for ( int i = 0 ; i < ghostBulletList.size( ) ; i++ ) {
			GhostBullet bul = ghostBulletList.get( i );
			Resources.ghostBullet.drawNoTexture( ( int )bul.x + viewX - 10 , ( int )bul.y + viewY - 10 , 2 , 2 );
		}
		
		Resources.testPlayer.draw( ( int )( x + viewX - 16 ) , ( int )( y + viewY - 16 ) );
		if ( ghost.getHealthPercent( ) < 0.3f ) {
			Resources.ghostAngry.draw( ( int )( ghost.x + viewX - ghost.radius ) , ( int )( ghost.y + viewY - ghost.radius ) , ghost.radius / 32f , ghost.radius / 32f );
		} else {
			Resources.ghost.draw( ( int )( ghost.x + viewX - ghost.radius ) , ( int )( ghost.y + viewY - ghost.radius ) , ghost.radius / 32f , ghost.radius / 32f );
		}
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.particle.getTextureName( ) );
		
		for ( int i = 0 ; i < particleList.size( ) ; i++ ) {
			Particle par = particleList.get( i );
			
			GL11.glColor4ub( ( byte )par.col.getRed( ) , ( byte )par.col.getGreen( ) , ( byte )par.col.getBlue( ) , ( byte )( 192 * par.getLifePercent( ) ) );
			
			Resources.particle.drawNoTexture( ( int )par.x + viewX - 8 , ( int )par.y + viewY - 8 , 1 , 1 );
		}
		
		GL11.glColor4f( 1f , 1f , 1f , 1f );
		
		Resources.bossHealthBack.draw( 0 , 0 );
		Resources.enemyHealthBar.draw( 0 , 0 , getMaster( ).getDisplayWidth( ) * ghost.getHealthPercent( ) );
		
		GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.fontBackground.getTexture( ).getTextureName( ) );
		Resources.fontBackground.drawString( "HEALTH: " + Game.health , 4 , getMaster( ).getDisplayHeight( ) - 18 );
		
		if ( intro ) {
			Resources.fontBackground.drawString( introChat , 112 , 64 , 2 );
		} else if ( transition ) {
			Resources.fontBackground.drawString( endChat , 288 , 64 , 2 );
			
			GL11.glBindTexture( GL11.GL_TEXTURE_2D , Resources.blank.getTextureName( ) );
			GL11.glBegin( GL11.GL_QUADS );
			
			for ( int ii = 0 ; ii < getMaster( ).getDisplayHeight( ) / 16 + 1 ; ii++ ) {
				for ( int i = 0 ; i < getMaster( ).getDisplayWidth( ) / 16 ; i++ ) {
					GL11.glColor4f( ( float )Game.random.nextDouble( ) , ( float )Game.random.nextDouble( ) , ( float )Game.random.nextDouble( ) , ( float )transitionTimer / 10000f );
			        
			        GL11.glTexCoord2f( 0f , 0f ); GL11.glVertex2i( i * 16 , ii * 16 );
			        GL11.glTexCoord2f( 0f , 1f ); GL11.glVertex2i( i * 16 , ii * 16 + 16 );
			        GL11.glTexCoord2f( 1f , 1f ); GL11.glVertex2i( i * 16 + 16 , ii * 16 + 16 );
			        GL11.glTexCoord2f( 1f , 0f ); GL11.glVertex2i( i * 16 + 16 , ii * 16 );
				}
			}
			
	        GL11.glEnd( );
	        
	        GL11.glColor4f( 1f , 1f , 1f , 1f );
		}
	}

	public void update( ) {
		if ( intro ) {
			ghost.health += getMaster( ).getDelta( );
			ghost.x = 352 + Game.random.nextInt( 16 ) - 8;
			ghost.y = 352 + Game.random.nextInt( 16 ) - 8;
			if ( ghost.health > ghost.maxHealth ) {
				ghost.health = ghost.maxHealth;
				intro = false;
				centerView( );
				ghost.x = ghost.y = 352;
			}
		} else if ( !transition ) {
			if ( Keyboard.isKeyDown( Keyboard.KEY_W ) && !KeyStates.wPressed ) {
				KeyStates.wPressed = true;
			}
			if ( Keyboard.isKeyDown( Keyboard.KEY_A ) && !KeyStates.aPressed ) {
				KeyStates.aPressed = true;
			}
			if ( Keyboard.isKeyDown( Keyboard.KEY_S ) && !KeyStates.sPressed ) {
				KeyStates.sPressed = true;
			}
			if ( Keyboard.isKeyDown( Keyboard.KEY_D ) && !KeyStates.dPressed ) {
				KeyStates.dPressed = true;
			}
			if ( Mouse.isButtonDown( 0 ) && !KeyStates.leftClickPressed ) {
				KeyStates.leftClickPressed = true;
			}
			
			if ( KeyStates.wPressed ) {
				float add = 0.1f * getMaster( ).getDelta( );
				float nextY = y - add;
				
				if ( nextY > boundsY ) {
					y = nextY;
					centerView( );
				}
			}
			if ( KeyStates.aPressed ) {
				float add = 0.1f * getMaster( ).getDelta( );
				float nextX = x - add;
				
				if ( nextX > boundsX ) {
					x = nextX;
					centerView( );
				}
			}
			if ( KeyStates.sPressed ) {
				float add = 0.1f * getMaster( ).getDelta( );
				float nextY = y + add;
				
				if ( nextY < boundsEndY ) {
					y = nextY;
					centerView( );
				}
			}
			if ( KeyStates.dPressed ) {
				float add = 0.1f * getMaster( ).getDelta( );
				float nextX = x + add;
				
				if ( nextX < boundsEndX ) {
					x = nextX;
					centerView( );
				}
			}
			
			if ( KeyStates.leftClickPressed ) {
				if ( gunTimer == 0 ) {
					int mouseY = getMaster( ).getDisplayHeight( ) - Mouse.getY( );
					double variation = getMaster( ).random.nextDouble( ) / 4.0 - 0.125;
					double angle = Math.atan2( mouseY - y - viewY , Mouse.getX( ) - x - viewX );
					bulletList.add( new Bullet( x , y , angle + variation ) );
					gunTimer = 250;
					
					particleList.add( new Particle( x , y , 1000 , new Color( 224 , 224 , 224 ) ) );
					particleList.add( new Particle( x , y , 1000 , new Color( 224 , 224 , 224 ) ) );
					particleList.add( new Particle( x , y , 1000 , new Color( 224 , 224 , 224 ) ) );
					particleList.add( new Particle( x , y , 1000 , new Color( 224 , 224 , 224 ) ) );
					
					if ( firstSoundPlaying ) {
						Resources.shoot2.loop( 1 );
						firstSoundPlaying = false;
					} else {
						Resources.shoot.loop( 1 );
						firstSoundPlaying = true;
					}
				}
			}
			
			
			if ( gunTimer > 0 ) {
				gunTimer -= getMaster( ).getDelta( );
				if ( gunTimer < 0 ) {
					gunTimer = 0;
				}
			}
			
			ghost.update( getMaster( ).getDelta( ) );
			
			doGhostAction( );
			
			for ( int i = 0 ; i < minionList.size( ) ; i ++ ) {
				minionList.get( i ).update( getMaster( ).getDelta( ) );
			}
			
			for ( int i = 0 ; i < minionList.size( ) ; i ++ ) {
				Minion min = minionList.get( i );
				
				double distToPlayer = Math.sqrt( Math.pow( min.x - x , 2 ) + Math.pow( min.y - y , 2 ) );
				if ( distToPlayer < Minion.radius + 16 ) {
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 255 , 255 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 255 , 255 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 255 , 255 ) ) );
					
					minionList.remove( i );
					
					Game.health -= 2;
				}
			}
			
			for ( int i = 0 ; i < bulletList.size( ) ; i++ ) {
				bulletList.get( i ).update( getMaster( ).getDelta( ) );
			}
			
			for ( int i = 0 ; i < bulletList.size( ) ; i++ ) {
				if ( bulletList.get( i ).life < 0 ) {
					bulletList.remove( i );
				} else {
					Bullet bul = bulletList.get( i );
					
					double distToGhost = Math.sqrt( Math.pow( bul.x - ghost.x , 2 ) + Math.pow( bul.y - ghost.y , 2 ) );
					if ( distToGhost < ghost.radius ) {
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 0 , 255 ) ) );
						
						bulletList.remove( i );
						ghost.health -= damage;
						
						if ( ghost.health <= 0 ) {
							ghostDead( );
						}
					} else {
						boolean hitEnemy = false;
						
						for ( int ii = 0 ; ii < minionList.size( ) ; ii++ ) {
							Minion min = minionList.get( ii );
							
							double dist = Math.sqrt( Math.pow( bul.x - min.x , 2 ) + Math.pow( bul.y - min.y , 2 ) );
							if ( dist < Minion.radius + 4 ) {
								hitEnemy = true;
								min.health -= 1;
								particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
								particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
								particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
								particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
								particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
								if ( min.health <= 0 ) minionList.remove( ii );
								break;
							}
						}
						
						if ( hitEnemy ) {
							bulletList.remove( i );
						} else {				
							int levX = ( int )( bul.x / 32f );
							int levY = ( int )( bul.y / 32f );
							
							if ( levX < 0 || levY < 0 || levX > 22 || levY > 22 ) {
								bulletList.remove( i );
							} else if ( level[ levX ][ levY ] == 2 || level[ levX ][ levY ] == 3 ) {
								particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 190 , 190 , 190 ) ) );
								particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 190 , 190 , 190 ) ) );
								particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 190 , 190 , 190 ) ) );
								bulletList.remove( i );
							}
						}
					}
				}
			}
			
			for ( int i = 0 ; i < ghostBulletList.size( ) ; i++ ) {
				GhostBullet bul = ghostBulletList.get( i );
				bul.update( getMaster( ).getDelta( ) );
				particleList.add( new Particle( bul.x , bul.y , 100 , new Color( 0 , 255 , 255 ) ) );
			}
			
			for ( int i = 0 ; i < minionList.size( ) ; i++ ) {
				Minion min = minionList.get( i );
				
				if ( min.x < boundsX || min.y < boundsY || min.x > boundsEndX || min.y > boundsEndY ) {
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
					particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 ) ) );
					minionList.remove( i );
				}
			}
			
			for ( int i = 0 ; i < ghostBulletList.size( ) ; i++ ) {
				if ( ghostBulletList.get( i ).life < 0 ) {
					ghostBulletList.remove( i );
				} else {
					GhostBullet bul = ghostBulletList.get( i );
					
					double distToGhost = Math.sqrt( Math.pow( bul.x - x , 2 ) + Math.pow( bul.y - y , 2 ) );
					if ( distToGhost < radius + 4f ) {
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 255 , 255 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 255 , 255 , 255 ) ) );
						particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 255 , 255 , 255 ) ) );
						ghostBulletList.remove( i );
						Game.health -= 5;
					} else {
						int levX = ( int )( bul.x / 32f );
						int levY = ( int )( bul.y / 32f );
						
						if ( levX < 0 || levY < 0 || levX > 22 || levY > 22 ) {
							ghostBulletList.remove( i );
						} else if ( level[ levX ][ levY ] == 2 || level[ levX ][ levY ] == 3 ) {
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 ) ) );
							ghostBulletList.remove( i );
						}
					}
				}
			}
	
			if ( Game.health <= 0 ) {
				getMaster( ).gameOver( );
			}
		} else {
			viewX = 320 - 352 + Game.random.nextInt( 16 ) - 8;
			viewY = 240 - 352 + Game.random.nextInt( 16 ) - 8;
			
			if ( transitionTimer < 10000 ) {
				transitionTimer += getMaster( ).getDelta( );
			} else {
				getMaster( ).nextStage( );
			}
		}
		
		for ( int i = 0 ; i < particleList.size( ) ; i++ ) {
			particleList.get( i ).update( getMaster( ).getDelta( ) );
			
			if ( particleList.get( i ).life < 0 ) {
				particleList.remove( i );
			}
		}
	}
	
	private void doGhostAction( ) {
		if ( ghost.actionTimer < 0 ) {
			if ( ghost.stage == 0 ) {
				if ( ghost.getHealthPercent( ) < 0.3f ) ghost.actionTimer += 750; else ghost.actionTimer += 1200; 
				
				if ( firstGhostShoot ) Resources.ghostShoot.loop( 1 ); else Resources.ghostShoot2.loop( 1 );
				firstGhostShoot = !firstGhostShoot;
				
				double angle = 0.0;
				angle = Math.atan2( y - ghost.y , x - ghost.x );
				
				if ( ghost.getHealthPercent( ) < 0.3f ) angle += Game.random.nextDouble( ) / 2.0 - 0.25;
				
				ghostBulletList.add( new GhostBullet( ghost.x , ghost.y , angle - 0.1 ) );
				ghostBulletList.add( new GhostBullet( ghost.x , ghost.y , angle - 0.2 ) );
				ghostBulletList.add( new GhostBullet( ghost.x , ghost.y , angle ) );
				ghostBulletList.add( new GhostBullet( ghost.x , ghost.y , angle + 0.2 ) );
				ghostBulletList.add( new GhostBullet( ghost.x , ghost.y , angle + 0.1 ) );
			} else if ( ghost.stage == 1 ) {
				ghost.x = 352 + Game.random.nextInt( 8 ) - 4;
				ghost.y = 352 + Game.random.nextInt( 8 ) - 4;
				if ( ghost.getHealthPercent( ) < 0.3f ) ghost.actionTimer += 150; else ghost.actionTimer += 300; 
				
				minionList.add( new Minion( ghost.x , ghost.y ) );
			}
		}
	}
	
	private void ghostDead( ) {
		transition = true;
		Resources.endNoise.start( );
		
		for ( int i = 0 ; i < minionList.size( ) ; i++ ) {
			Minion min = minionList.get( i );
			particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 , 255 ) ) );
			particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 , 255 ) ) );
			particleList.add( new Particle( min.x , min.y , 1000 , new Color( 255 , 0 , 0 , 255 ) ) );
			minionList.remove( i );
			i -= 1;
		}
		
		for ( int i = 0 ; i < ghostBulletList.size( ) ; i++ ) {
			GhostBullet bul = ghostBulletList.get( i );
			particleList.add( new Particle( bul.x , bul.y , 1000 , new Color( 0 , 255 , 255 , 255 ) ) );
			ghostBulletList.remove( i );
			i -= 1;
		}
	}
	
	private void centerView( ) {
		viewX = ( int )( 320 - x );
		viewY = ( int )( 240 + 12 - y );
	}
	
	public void switchTo() {
		GL11.glColor4f( 1f , 1f , 1f , 1f );
		Resources.pureNoise2.stop( );
		Resources.shoot.setLoopPoints( 0 , -1 );
		Resources.shoot2.setLoopPoints( 0 , -1 );
		Resources.ghostShoot.setLoopPoints( 0 , Resources.ghostShoot.getFrameLength( ) - 1 );
	}

}
