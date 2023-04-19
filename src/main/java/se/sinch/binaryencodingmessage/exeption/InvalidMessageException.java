package se.sinch.binaryencodingmessage.exeption;

/*
    Custom exception class to handle checked exceptions while validating message details.
     * @author Parasuram
 */
public class InvalidMessageException extends Exception {

    public InvalidMessageException(String message) {
        super(message);
    }
}
