import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Pipe {
	private Image pipeImage;
	
	private int xPos, mapOffset;
	private boolean isTop;
	
	static final int pipeWidth = 52;
	static final int pipeHeight = 425;
	
	public Pipe(int xPos, int length, boolean isTop){
		this.xPos = xPos;
		this.mapOffset = length;
		this.isTop = isTop;
		
		try {
			if (this.isTop)
				pipeImage = ImageIO.read(Start.class.getResource("topPipe.png"));		
			else
				pipeImage = ImageIO.read(Start.class.getResource("botPipe.png"));
			
		} catch (IOException e) {e.printStackTrace();}		
	}
	
	public int getX(){
		return this.xPos;
	}
	
	public void setX(int x){
		this.xPos = x;
	}
	
	public int getOffset(){
		return this.mapOffset;
	}
	
	public void setOffset(int y){
		this.mapOffset = y;
	}
	
	public Image getImg(){
		return pipeImage;
	}
	
	public void move(){
		xPos-=3;
	}
	
}
