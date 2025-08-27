package jimmy.exception;

/**
 * Custom exception class for the Jimmy task management system.
 * Used to represent application-specific errors that occur during task operations.
 * Extends RuntimeException to provide unchecked exception behavior.
 */
public class JimmyException extends RuntimeException {
    
    /**
     * Constructs a new JimmyException with the specified error message.
     * The message describes what went wrong in the application.
     *
     * @param message The error message describing the exception
     */
    public JimmyException(String message) {
        super(message);
    }
}
