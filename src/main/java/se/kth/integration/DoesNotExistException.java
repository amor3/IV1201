/**
 * 
 */
package se.kth.integration;

/**
 *
 * @author work
 */
public class DoesNotExistException extends Exception {

    /**
     * Creates a new instance of <code>DoesNotExistException</code> without
     * detail message.
     */
    public DoesNotExistException() {
    }

    /**
     * Constructs an instance of <code>DoesNotExistException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DoesNotExistException(String msg) {
        super(msg);
    }
}
