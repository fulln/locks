package me.fulln.lock.exception;

/**
 * @author fulln
 * @version 0.0.1
 * @program locks
 * @description 重试异常
 * @date 2021/6/4 21:21
 **/
public class RetryException extends RuntimeException {

    private static final long serialVersionUID = 4658349124548496297L;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public RetryException(String message) {
        super(message);
    }

    public static RetryException getInstance(String key) {
        return new RetryException(key);
    }

    ;
}
