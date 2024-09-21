package dev.argjerryl.Exchange;

public class InsufficientFundsException extends RuntimeException {

    // Constructor that accepts a message
    public InsufficientFundsException(String message) {
        super(message);  // Call the constructor of the RuntimeException with the message
    }

    // Default no-argument constructor
    public InsufficientFundsException() {
        super();  // Call the no-argument constructor of RuntimeException
    }
}
