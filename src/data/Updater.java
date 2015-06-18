package data;

import graphics.MainFrame;

public class Updater {

	public void calculateFPS() { 
	long beginTime; //Time when the cycle begins
        long timeDiff = 0;  // Time it took for the cycle to execute
        int sleepTime = 0; // ms to sleep (<0 if we're behind)
        int framesSkipped; //Number of frames being skipped
		
		int FPS = 30; // Add in constants, or make variable depending on the systems capacity
		boolean isRunning = true;
		while(isRunning) {
			try {
				beginTime = System.currentTimeMillis();
				framesSkipped = 0;
				
				//RUN UPDATE ON TOP CLASS
					MainFrame.update(timeDiff);
				
				//RUN DRAW ON TOP CLASS
				MainFrame.draw(MainFrame.getmainFrame().getGraphics());
				timeDiff = System.currentTimeMillis() - beginTime;
				
				sleepTime = (int)(1000/FPS - timeDiff); 
				
				
                    if(sleepTime > 0){
                        //If sleepTime > 0 then no problem
                        try{
                            //Send the thread to sleep for a short period
                            //Good for battery 
                            Thread.sleep(sleepTime);
                        }catch (InterruptedException e){}
                    }
					while (sleepTime < 0 && framesSkipped < 1000/FPS){
                        //catching up
                        //updates without rendering
                        //INSERT UPDATE ON TOP CLASS
						timeDiff = System.currentTimeMillis() - beginTime;
						MainFrame.update(timeDiff);
				//		MainFrame.update(timeDiff);
                        //add frame period to check if in next frame
                        sleepTime += 1000/FPS;
                        framesSkipped++;
					}
			} finally {
				
			}
		}
	}
	
	
		
}