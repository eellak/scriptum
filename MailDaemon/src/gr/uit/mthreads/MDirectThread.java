package gr.uit.mthreads;

import java.util.Date;

/**
 * STANDARD UIT THREAD CLASS LIBRARY
 * @author Mike Mountrakis mountrakis@uit.gr
 */
public class MDirectThread extends MThread{
	
	public MDirectThread() {
		super(Type.DIRECT);
	}
	
	public MDirectThread( MWork w){
		super(Type.DIRECT, w);
	}
	

	public  void Stop(){
		while( this.isAlive() ){
			this.interrupt();
		}
		isRunning = false;
		dateStoped = new Date();
	}
	
	public void run(){
		try{
			isRunning = true;
			work.initializeWork();
			work.performWork();
		}catch(Exception e){
			isRunning = false;
		}finally{
			work.finilizeWork();
		}
	}
}
