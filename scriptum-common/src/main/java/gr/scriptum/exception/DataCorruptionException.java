/**
 * 
 */
package gr.scriptum.exception;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos</a>
 *
 */
public class DataCorruptionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6306061778844721162L;

	public DataCorruptionException(String message) {
		super(message);
	}
}
