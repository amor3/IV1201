/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.integration;

/**
 *
 * @author work
 */
public class NullArgumentException extends Exception {

    /**
     * Creates a new instance of <code>NullArgumentException</code> without
     * detail message.
     */
    public NullArgumentException() {
    }

    /**
     * Constructs an instance of <code>NullArgumentException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NullArgumentException(String msg) {
        super(msg);
    }
}
