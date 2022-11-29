
public class Start {
	static Player player;
	static Map map;
	static GameLoop mainLoop = new GameLoop();
	
	public static void main(String[] args){
		initialize();
	}
	
	public static void initialize(){
		player = new Player();
		map = new Map();
		mainLoop.initialize();
		mainLoop.run(map, player, mainLoop);
	}
	
	public static void reset(){
		//mainLoop.initialize();
		player.respawn();
		mainLoop.runs = true;
		player.flapping=true;
	}
}
