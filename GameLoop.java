import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GameLoop extends Thread{
	int nP = 6;	//# of pipes
	ArrayList<Pipe> topPipe = new ArrayList<Pipe>(nP);
	ArrayList<Pipe> botPipe = new ArrayList<Pipe>(nP);
	//Pipe[] topPipe = new Pipe[nP];  //Make an array of pipes
	//Pipe[] botPipe = new Pipe[nP];
	boolean runs = false;
	//int inPipe = 0;
	Timer tick;
	int distance = 0; //distance from first pipe... increases by 200
	int score;
	boolean[] hasPassed = new boolean[nP];
	
//	READ AND WRITE PATH
	File pathFile = new File ("data\\flappy_HS.txt");
	String path = pathFile.getAbsolutePath(); //path to write and read from
	
	//high scores to be read from text file 
	int highScore[] = new int[10];
	
	public void initialize(){
		distance = 1280/nP;
		topPipe.clear();
		botPipe.clear();
		score = 0;
		for(int i = 0; i<nP;i++){
			double rand = Math.random();
			topPipe.add(new Pipe(1280+i*distance,(int)(100+rand*250),true));
			botPipe.add(new Pipe(1280+i*distance,(int)(100+rand*250),false));
			hasPassed[i] = false;
		}
		try {
			readFromFile();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(int i =0;i<highScore.length;i++)
			System.out.println(highScore[i]);
	}
	
	public void run(Map map, Player player, GameLoop mainLoop){
		Timer tick = new Timer(20, new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {				
				if(runs){
					map.updateUI();
					for (int i = 0; i<nP;i++){
						double rand = Math.random();
						topPipe.get(i).move();
						botPipe.get(i).move();
						
						if(topPipe.get(i).getX() < Player.birdXPos){
							if(!hasPassed[i]){
								score++;
								hasPassed[i] = true;
								//System.out.println(score);
							}
						}
						
						if(topPipe.get(i).getX() < 0){
							int spawnRand = (int)(100+rand*250);
							topPipe.set(i, new Pipe(1280, spawnRand, true));
							botPipe.set(i, new Pipe(1280, spawnRand, false));
							hasPassed[i] = false;
						}
						
						
						//Check collision
						if (player.checkCollision(topPipe.get(i), botPipe.get(i))){
							player.kill();
							map.updateUI();
							mainLoop.runs = false;
							map.play  = false;
							runs = false;
							checkHighScore();
							initialize();
							return;
						}
					}
				}}});
		tick.start();
	}
	
	private void checkHighScore(){
		for(int i=0;i<highScore.length;i++){
			if(score>highScore[i]){
				//Update high scores by inserting new score and pushing everything below the rest down by 1 index
				int oldScore1 = highScore[i];
				highScore[i] = score;
				int oldScore2 = oldScore1;
				
				for (int j = i+1;j<highScore.length;j++){
					oldScore1 = highScore[j];
					highScore[j] = oldScore2;
					oldScore2 = oldScore1;
				}
				try {
					writeToFile();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			}
		}
	}
	
	private void writeToFile() throws IOException{

		PrintWriter print_line = new PrintWriter(path, "UTF-8");
		
		for (int i = 0; i < highScore.length; i++){
		    print_line.println(highScore[i]);
		    //System.out.println(line[i]);
		}
		
		print_line.close();
		
		System.out.println("Done Writing");
	}

	//Reads from text file and fill cellContents char array and sets piece to current turn.
	private void readFromFile() throws IOException{
		FileReader fr = new FileReader(path);
		BufferedReader textReader = new BufferedReader(fr);
		
		for(int i = 0;i<highScore.length;i++)
			highScore[i] = Integer.parseInt(textReader.readLine());
		textReader.close();
	}
}
