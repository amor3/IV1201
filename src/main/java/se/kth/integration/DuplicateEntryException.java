/**
 * 
 */
package se.kth.integration;

/**
 *
 * @author work
 */
public class DuplicateEntryException extends Exception {

    /**
     * Creates a new instance of <code>DuplicateEntryException</code> without
     * detail message.
     */
    public DuplicateEntryException() {
    }

    /**
     * Constructs an instance of <code>DuplicateEntryException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public DuplicateEntryException(String msg) {
        super(msg);
    }
}
