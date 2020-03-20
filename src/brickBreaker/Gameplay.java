package brickBreaker;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Gameplay extends JPanel implements KeyListener, ActionListener{

	private boolean play = false;
	private int score = 0;
	
	private int totalBricks = 21;
	
	private Timer timer;
	//speed
	private int delay = 8;
	
	//starting pos of slider
	private int playerX = 310;
	
	//starting ball pos
	private int ballPosX = 120;
	private int ballPosY = 350;
	
	//starting movement speed of ball
	private int ballXdir = -1;
	private int ballYdir = -2;
	
	private MapGenerator map;
	
	public Gameplay() {
		
		//create map object
		map = new MapGenerator(3, 7);
		
		//add the keyListener 
		addKeyListener(this);
		setFocusable(true);
		//remove scroll bars
		setFocusTraversalKeysEnabled(false);
		//create and start timer
		timer = new Timer(delay, this);
		timer.start();
		
	}
	
	public void paint(Graphics g) {
		
		//background
		g.setColor(Color.black);
		g.fillRect(1, 1, 692, 592);
		
		//draw map
		map.draw((Graphics2D) g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif", Font.BOLD, 25));
		g.drawString("Score: "+score, 590, 30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 8);
		
		//ball
		g.setColor(Color.yellow);
		g.fillOval(ballPosX, ballPosY, 20, 20);
		
		//Win
		if(totalBricks <= 0) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("You Won! Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart ", 230, 350);
		}
		
		//Gameover
		if(ballPosY > 570) {
			play = false;
			ballXdir = 0;
			ballYdir = 0;
			g.setColor(Color.red);
			g.setFont(new Font("serif", Font.BOLD, 30));
			g.drawString("Game Over, Score: " + score, 190, 300);
			
			g.setFont(new Font("serif", Font.BOLD, 20));
			g.drawString("Press Enter to Restart ", 230, 350);
		}
		
		//clear panel
		g.dispose();
		
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		timer.start();
		
		//this is run continuosly
		if(play) {
			
			//ball collision with paddle
			//Have to create rectangle around ball to check collision
			//Have to also create rectangle around paddle
			//This is because Rectangle class, intersect method can only detect rectangle objects
			if(new Rectangle(ballPosX, ballPosY, 20, 20).intersects(new Rectangle(playerX, 550, 100, 8))) {
				
				ballYdir =- ballYdir;
				
			}
			
			//ball collision with bricks
			for(int i = 0; i < map.map.length; i++) {
				
				for(int j = 0; j < map.map[0].length; j++) {
					//if there is a brick
					if(map.map[i][j] > 0) {
						
						int brickX = j * map.brickWidth + 80;
						int brickY = i * map.brickHeight + 50;
						int brickWidth = map.brickWidth;
						int brickHeight = map.brickHeight;
						
						//create rectangle objects around each brick
						Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
						Rectangle ballRect = new Rectangle(ballPosX, ballPosY, 20, 20);
						Rectangle brickRect = rect;
						
						//label this whole section
						A : if(ballRect.intersects(brickRect)) {
							//if the ball hits a brick
							//set that brick to 0, removing the brick
							map.setBrickValue(0, i, j);
							//decrement totalBricks and add score
							totalBricks--;
							score+= 5;
							
							if(ballPosX + 19 <= brickRect.x || ballPosX + 1 >= brickRect.x + brickRect.width) {
								ballXdir = - ballXdir;
							} else {
								ballYdir = -ballYdir;
							}
							//stop the compiler from running this section
							break A;
						}
						
					}
					
				}
				
			}
			
			//move ball
			ballPosX += ballXdir;
			ballPosY += ballYdir;
			
			//if ball hits left side of border, change direction of ball
			if(ballPosX < 0) {
				ballXdir = -ballXdir;
			}
			//if ball hits top
			if(ballPosY < 0) {
				ballYdir = -ballYdir;
			}
			//if ball hits right
			if(ballPosX > 670) {
				ballXdir = -ballXdir;
			}
		}
		
		//calls paint method continuously
		repaint();
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//if right key is pressed
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			//if paddle hits right side of border, keep it in bounds
			if(playerX >= 600) {
				playerX = 600;
			} else {
				moveRight();
			}
			
		}
		
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			//if paddle hits left side of border, keep it in bounds
			if(playerX < 10) {
				playerX = 10;
			} else {
				moveLeft();
			}
		}
		
		//Restart game
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				play = true;
				ballPosX = 120;
				ballPosY = 350;
				ballXdir = -1;
				ballYdir = -2;
				playerX = 310;
				score = 0;
				totalBricks = 21;
				map = new MapGenerator(3,7);
				//restart paint method
				repaint();
			}
		}
		
	}
	
	public void moveRight() {
		
		play = true;
		playerX += 20;
		
	}
	
	public void moveLeft() {
		
		play = true;
		playerX -= 20;
		
	}
	
}
