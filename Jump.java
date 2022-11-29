import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class Jump extends Thread{
	private int upTime=1, downTime=1, boostTime;
	Timer timer;
	
	public void run(){		
		timer = new Timer(20, new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(Start.player.isAlive){
					if(Start.player.flapping && Start.player.yPos>=0){
						downTime=1;
						if(boostTime!=0){
							Start.player.yPos=Start.player.yPos-2*boostTime;
							boostTime--;
						}
						Start.player.yPos=(int)(Start.player.yPos-0.04*(upTime*upTime));
						upTime++;
						if(Start.player.yPos<=0){
							Start.player.yPos=-4;
						}
					}
					else if (!Start.player.flapping && Start.player.yPos<=720){
						upTime=1;
						boostTime=6;
						Start.player.yPos=(int)(Start.player.yPos+downTime/2);
						downTime++;
						if(Start.player.yPos>=1275) {
							Start.player.yPos=1280;
						}
					}
				}
				
			}});
		timer.start();
	}
	
	public void reset(){
		//upTime=1; 
		//downTime=1;
		//boostTime=6;
		timer.stop();
	}
}
