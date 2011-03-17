package gr.uit.mthreads;

/**
 * Implement this interface in order to be used from any MThread descent  
 * @author mike mountrakis mountrakis@uit.gr
 *
 */
public interface MWork {
	public void initializeWork();
	public void performWork();
	public void finilizeWork();
}
