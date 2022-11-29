import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Map extends JPanel{
	JFrame frame;
	Image mapBackground;
	
	static final int pipeGap = 180;
	
	//Whether game is in the gameState
	boolean play = false;
	
	public Map(){
		try {
			mapBackground=ImageIO.read(Start.class.getResource("background.png"));
		} catch (IOException e1) {}
		frame=new JFrame("Flappy Bird");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	    frame.add(this);
		frame.pack();
		frame.setSize(1280, 720);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.addKeyListener(new KeyListener(){
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					if(play){
						//Flap once
						Start.player.flapping=true;
					}
					else{
						//Reset game
						Start.reset();
						play=true;
					}
		        }
			}
			@Override
			public void keyReleased(KeyEvent e) {	
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if(play){
					Start.player.flapping=false;
				}
				}
			}
			@Override
			public void keyTyped(KeyEvent e) {
		    }
				
			});
		
		updateUI();
		}

	public void paintComponent(Graphics g){
	    super.paintComponent(g);
	    Graphics2D graph =(Graphics2D)g;

	    graph.drawImage(mapBackground,0,0,null);
	    if(play){
	    	for (int i =0;i<Start.mainLoop.topPipe.size();i++){
			    graph.drawImage(Start.mainLoop.topPipe.get(i).getImg(), Start.mainLoop.topPipe.get(i).getX(), (Start.mainLoop.topPipe.get(i).getOffset()-Pipe.pipeHeight), null); 
			    graph.drawImage(Start.mainLoop.botPipe.get(i).getImg(), Start.mainLoop.botPipe.get(i).getX(), (pipeGap + (Start.mainLoop.botPipe.get(i).getOffset())), null);
			    graph.setFont(new Font("ComicSans", Font.BOLD,55));
			    graph.setColor(Color.BLACK);
			    graph.drawString(Integer.toString(Start.mainLoop.score), 1280/2-50, 50);
			    graph.setFont(new Font("ComicSans", Font.PLAIN,50));
			    graph.setColor(Color.WHITE);
			    graph.drawString(Integer.toString(Start.mainLoop.score), 1280/2-50, 50);
	    	}
	    }
	    graph.drawImage(Start.player.birdFlap,Player.birdXPos,Start.player.getY(),null); 
	    
	}
}
