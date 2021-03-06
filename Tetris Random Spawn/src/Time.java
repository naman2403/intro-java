import java.text.SimpleDateFormat;
import java.util.Date;


//Documentation of the methods are in the method

public class Time implements Runnable {
	//each level time is decreased as such:
	//0, 50, 75, 100, 125, 150, 175, 200, 225, 250
	
	//how much time the game should wait between levels
	private static int[] levWait = {1700, 1450, 1375, 1275, 1150, 1000, 825, 625, 325, 125, 1, 0}; 
	
	//thread time repaints and calls methods after periods of time
	Thread time;
	
	static long printTime = 0;
	static long startTime = 0;
	static long currentTime = 0;
	
	//starts the thread time
	public static void threadTimeStart() {
		Thread time = new Thread(new Time());
		time.start();
	}
	
	//calls timeKeeping when the thread time starts
	public void run() {
		timeKeeping();
	}
	
	//keeps track of time, and calls methods that need to be called every drop
	public static void timeKeeping() {
		//random place stuff
		int rantime = 0;
		//the startTime helps keep track of how much time has passed since the loop started, which keeps the computer's performance from effecting game speed
		startTime = System.currentTimeMillis();
		Main.display.repaint();
		sleep();
		
		//a loop that calls methods after every drop
		while (Main.gameOn) {
			startTime = System.currentTimeMillis();
			
			Physics.dropCall();
			Square.neighborFindCall();
						
			//random place stuff
			rantime++;
			if(rantime > 2) {
				Physics.randomPlace();
				rantime = 0;
			}
			
			Main.display.repaint();
			Physics.clearLineCheck();
			
			sleep();
		}
	}
	
	private static void sleep() {
		currentTime = System.currentTimeMillis();
		
		//sleeps for the amount of time alloted, removing how much time has passed since the beginning of the loop
		if ((levWait[Levels.level -1]-(currentTime-startTime)) >= 0) {
			try {
				Thread.sleep(levWait[Levels.level -1]-(currentTime-startTime));
			} catch (InterruptedException e) {
				System.out.println("Time thread died");
				e.printStackTrace();
			}
		}
	
		printStatus();
	}
	
	private static void printStatus() {
		if ((currentTime - printTime) > 1000) {
			if ((levWait[Levels.level -1]-(currentTime-startTime)) >= 0) {
				System.out.println("[System @ " + timeStamp() + "] Normal: Game is on time");
			} else {
				System.err.println("[System @ " + timeStamp() + "] Warning: Did the time change, or is the game falling behind? Time behind is " + (levWait[Levels.level -1]-(startTime-System.currentTimeMillis())));
			}
			printTime = currentTime;
		}
	}
	
	private static String timeStamp() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy h:mm:ss a");
		String formattedDate = sdf.format(date);
		return formattedDate; // 12/01/2011 4:48:16 PM
	}
}