package com.ccb.athena.security;


/**
 * A generic runtime exception that optionally contains Location information 
 *
 * @author pandong
 */
public class SecurityAgentException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3736630834268485152L;

	/**
     * Constructs a <code>AthenaException</code> with no detail message.
     */
    public SecurityAgentException() {
    }

    /**
     * Constructs a <code>AthenaException</code> with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SecurityAgentException(String s) {
        this(s, null, null);
    }
    
    /**
     * Constructs a <code>AthenaException</code> with the specified
     * detail message and target.
     *
     * @param s the detail message.
     * @param target the target of the exception.
     */
    public SecurityAgentException(String s, Object target) {
        this(s, (Throwable) null, target);
    }

    /**
     * Constructs a <code>AthenaException</code> with the root cause
     *
     * @param cause The wrapped exception
     */
    public SecurityAgentException(Throwable cause) {
        this(null, cause, null);
    }
    
    /**
     * Constructs a <code>AthenaException</code> with the root cause and target
     *
     * @param cause The wrapped exception
     * @param target The target of the exception
     */
    public SecurityAgentException(Throwable cause, Object target) {
        this(null, cause, target);
    }

    /**
     * Constructs a <code>XWorkException</code> with the specified
     * detail message and exception cause.
     *
     * @param s the detail message.
     * @param cause the wrapped exception
     */
    public SecurityAgentException(String s, Throwable cause) {
        this(s, cause, null);
    }
    
    
     /**
     * Constructs a <code>AthenaException</code> with the specified
     * detail message, cause, and target
     *
     * @param s the detail message.
     * @param cause The wrapped exception
     * @param target The target of the exception
     */
    public SecurityAgentException(String s, Throwable cause, Object target) {
        super(s, cause);
    }


    /**
     * Gets the underlying cause
     * 
     * @return the underlying cause, <tt>null</tt> if no cause
     * @deprecated Use {@link #getCause()} 
     */
    @Deprecated public Throwable getThrowable() {
        return getCause();
    }
      
    /**
     * Returns a short description of this throwable object, including the 
     * location. If no detailed message is available, it will use the message
     * of the underlying exception if available.
     *
     * @return a string representation of this <code>Throwable</code>.
     */
    @Override
    public String toString() {
        String msg = getMessage();
        if (msg == null && getCause() != null) {
            msg = getCause().getMessage();
        }

        return msg;
    }
}
