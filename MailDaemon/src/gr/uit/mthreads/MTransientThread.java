package gr.uit.mthreads;


import java.util.Date;

/**
 * STANDARD UIT THREAD CLASS LIBRARY
 * @author Mike Mountrakis mountrakis@uit.gr
 */

public class MTransientThread extends MThread{

	public static final int DEFAULT_MSEC_SLEEP_PERIOD = 1000;
	public int sleepPeriod = DEFAULT_MSEC_SLEEP_PERIOD;	
	
	public MTransientThread(){
		super(Type.TRANSIENT);
	}
	
	public MTransientThread( MWork work) {
		super(Type.TRANSIENT, work);
	}


	public final int getSleepPeriod() {
		return sleepPeriod;
	}

	public final void setSleepPeriod(int sleepPeriod) {
		this.sleepPeriod = sleepPeriod;
	}


	public  void Stop(){
		isRunning = false;
		while( this.isAlive() ){
			this.interrupt();
		}
	}	
	

	public void run() {
		try {
			isRunning = true;
			work.initializeWork();
			while (isRunning ) {
				Thread.sleep(sleepPeriod);
				dateLastRun = new Date();
				work.performWork();
			}
		} catch (InterruptedException  e) {
			;
		}finally{
			work.finilizeWork();
			dateStoped = new Date();
		}
	}
}
