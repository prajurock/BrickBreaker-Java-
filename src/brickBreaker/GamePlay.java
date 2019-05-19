package brickBreaker;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePlay extends JPanel implements KeyListener,ActionListener {
	
	private boolean play=false;
	private int score=0;
	
	private int totalBricks=21;
	
	private Timer timer;
	private int delay=8;
	
	private int playerX=310;
	
	private int ballposX=120;
	private int ballposY=350;
	private int balldirX=-1;
	private int balldirY=-1;
	
	private MapGenerator map;
	
	public GamePlay() {
		map=new MapGenerator(3,7);
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(true);
		timer=new Timer(delay,this);
		timer.start();
		
		
	}
	
	public void paint(Graphics g) {
		//background(postionX,postionY,size,height)
		g.setColor(Color.BLACK);
		g.fillRect(1, 1, 692, 592);
		
		//draw map
		map.draw((Graphics2D)g);
		
		//borders
		g.setColor(Color.yellow);
		g.fillRect(0, 0, 3, 592);
		g.fillRect(0, 0, 692, 3);
		g.fillRect(691, 0, 3, 592);
		
		//score
		g.setColor(Color.white);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString(""+score, 590, 30);
		
		//paddle
		g.setColor(Color.green);
		g.fillRect(playerX, 550, 100, 3);
		
		if(totalBricks<=0) {
			play=false;
			balldirX=0;
			balldirY=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,35));
			g.drawString("You Won:- "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Press Enter to restart", 190, 400);
		}
		
		//Ball
		g.setColor(Color.yellow);
		g.fillOval(ballposX, ballposY,20, 20);
		if(ballposY>570) {
			play=false;
			balldirX=0;
			balldirY=0;
			g.setColor(Color.red);
			g.setFont(new Font("serif",Font.BOLD,35));
			g.drawString("Game Over:- "+score, 190, 300);
			
			g.setFont(new Font("serif",Font.BOLD,25));
			g.drawString("Press Enter to restart", 190, 400);


		}
		
		g.dispose();
 
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
			if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,3))) {
				balldirY=-balldirY;
			}
			A:for(int i=0;i<map.map.length;i++) {
				for(int j=0;j<map.map[0].length;j++) {
					if(map.map[i][j]>0) {
						int brickX=j*map.brickWidth+80;
						int brickY=i*map.brickHeight+50;
						int brickwidth=map.brickWidth;
						int brickHeight=map.brickHeight;
						
						Rectangle rect=new Rectangle(brickX,brickY,brickwidth,brickHeight);
						Rectangle ballrect=new Rectangle(ballposX,ballposY,20,20);
						Rectangle brickRect=rect;
						if(ballrect.intersects(brickRect)) {
							map.setBrickValue(0, i, j);
							totalBricks--;
							score+=5;
							
							if(ballposX+19<=brickRect.x || ballposX+1>=brickRect.x+brickRect.width) {
								balldirX=-balldirX;
								
							}else {
								balldirY=-balldirY;
							}
							break A;
						}
					}
				}
			}
			ballposX+=balldirX;
			ballposY+=balldirY;
			if(ballposX < 0) {
				balldirX=-balldirX;
				
			}
			if(ballposY < 0) {
				balldirY=-balldirY;
				
			}
			if(ballposX>670) {
				balldirX=-balldirX;
				
			}
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if(playerX>=600)
				playerX=600;
			else
				moveRight();
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			if(playerX<10)
				playerX=10;
			else
				moveLeft();
		}
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(!play) {
				ballposX=120;
				ballposY=350;
				balldirX=-1;
				balldirY=-1;
				playerX=310;
				score=0;
				totalBricks=21;
				map=new MapGenerator(3,7);
				
				repaint();
				
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
	
	}

	public void moveRight() {
		play=true;
		playerX+=20;
		
	}
	public void moveLeft() {
		play=true;
		playerX-=20;
		
	}

}
