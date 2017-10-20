/**
 * 
 */
package gr.scriptum.exception;

/**
 * @author <a href="mailto:angelosanagnostopoulos@runbox.com">aanagnostopoulos
 *         </a>
 *
 */
public class OpenKMException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7441614299394660979L;

	public OpenKMException(String message) {
		super(message);
	}

	public OpenKMException(String message, Throwable cause) {
		super(message, cause);
	}

	public OpenKMException(Throwable cause) {
		super(cause);
	}
	
}
