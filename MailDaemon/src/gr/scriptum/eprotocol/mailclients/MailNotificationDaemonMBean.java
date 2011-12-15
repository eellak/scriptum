package gr.scriptum.eprotocol.mailclients;


public interface MailNotificationDaemonMBean {
	/**
	 * Starts the service by creating a new thread
	 * @throws Exception
	 */
    public void start() throws Exception;
    /**
     * Stops the service interrupting the running thread
     * @throws Exception
     */
    public void stop() throws Exception;	
	/**
	 * Checks if the service thread is running or not
	 * @return
	 */
	public String isRunning();    
    /**
     * Sets debugging state
     * @param d
     */
	public void setDebug(boolean d );
	public boolean getDebug();
	/**
	 * Gets mail fetching timeout
	 * @return
	 */
	public int getTimeout();
	public void setTimeout(int timeout);
    /**
     * Returns the JMX - friendly object Name
     * @return
     */
	/**
	 * Daemon thread sleep period. Defines how much time in msec debore daemon check for new incomings again.
	 * @return
	 */
	public  int getSleepPeriod() ;
	/**
	 * Sets the sleep period time in msces
	 * @param sleepPeriod
	 */
	public void setSleepPeriod(int sleepPeriod);

}
