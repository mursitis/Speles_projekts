import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Player {
	Image birdFlap;
	Image birdIdle;
	
	int yPos;
	boolean flapping = false;
	boolean isAlive;
	Jump jump;
	
	static final int birdWidth = 92;
	static final int birdHeight = 64;
	static final int birdXPos = 400;

	
	public Player() {
		//Initial position (bottom of screen is y=0)
		this.yPos = 100;
		
		isAlive = true;
		//Initialize and thread responsible for jumping
		
		
		//Set bird graphics
		try{
		this.birdFlap = ImageIO.read(Start.class.getResource("birdFlapping.png"));
		this.birdIdle = ImageIO.read(Start.class.getResource("birdIdle.png"));
		} catch (IOException e) {e.printStackTrace();}


	}
	
	public int getY(){
		return this.yPos;
	}
	
	public void kill(){
		this.isAlive = false;
		jump.reset();
	}
	
	public void respawn(){
		this.isAlive = true;
		this.yPos = 100;
		jump = new Jump();
		jump.run();
	}
	
	public boolean checkCollision(Pipe topPipe, Pipe botPipe){
		//Assume colliding for simplicity
		boolean isColliding = false;
		//System.out.println("Flappy Bird Ypos: " + this.yPos + " -- " + (720 - botPipe.getLength()+50));// + " " topPipe.xPos + " -- " + topPipe.length + "	Bottom Pipe: " + botPipe.xPos + " -- " + botPipe.length);
		//If player is above the top or below bottom pipes
		if((topPipe.getX() <= birdXPos+birdWidth && topPipe.getX()>= birdXPos) || ((topPipe.getX() + Pipe.pipeWidth) < (birdXPos+birdWidth) && (topPipe.getX()+Pipe.pipeWidth) > birdXPos)){
			if (this.yPos < topPipe.getOffset() || this.yPos >= (Map.pipeGap + botPipe.getOffset() - birdHeight)) {
				isColliding = true;
			}
		}
		
		return isColliding;
	}
	
	public Image getImg(){
		return birdFlap;
	}
}
