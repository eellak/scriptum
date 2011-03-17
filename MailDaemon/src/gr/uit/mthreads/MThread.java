package gr.uit.mthreads;


import java.util.Date;

/**
 * All MThreads parent.
 * @author mike mountrakis mountrakis@uit.gr
 */

public abstract class MThread extends Thread{
	public enum Type{
		DIRECT,
		TRANSIENT
	}
	Date dateStarted = null;
    Date dateStoped = null;
    Date dateLastRun = null;
	Type type = Type.DIRECT;
	MWork work = null;
	volatile boolean isRunning = false; 
	
	
	public MThread( Type  t){
		super();
		type = t;
	}
	
	public MThread(Type t, MWork work){
		this(t);
		this.work = work;
	}
	
	public final Date getDateStarted() {
		return dateStarted;
	}

	public final void setDateStarted(Date dateStarted) {
		this.dateStarted = dateStarted;
	}

	public final Date getDateStoped() {
		return dateStoped;
	}

	public final void setDateStoped(Date dateStoped) {
		this.dateStoped = dateStoped;
	}

	public final Date getDateLastRun() {
		return dateLastRun;
	}

	public final void setDateLastRun(Date datelastRun) {
		this.dateLastRun = datelastRun;
	}

	public final Type getType() {
		return type;
	}

	public final void setType(Type type) {
		this.type = type;
	}

	public final MWork getWork() {
		return work;
	}

	public final void setWork(MWork work) {
		this.work = work;
	}

	
	public final void Start(){
		if( isRunning == false ){
			if( dateStarted == null )
				dateLastRun = new Date();
			else
				dateLastRun = new Date(dateStarted.getTime());
			dateStarted = new Date();
			this.start();
		}
	}
	
	
    public final boolean IsRunning(){
    	return isRunning;
    }
    
    
	public abstract void Stop();

	@Override
	public String toString() {
		return "MThread [dateStarted=" + dateStarted + ", dateStoped="
				+ dateStoped + ", dateLastRun=" + dateLastRun + ", type="
				+ type + ", work=" + work + ", isRunning=" + isRunning + "]";
	} 
	
	
}

