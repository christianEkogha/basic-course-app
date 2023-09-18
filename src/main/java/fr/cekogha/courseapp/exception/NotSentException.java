package fr.cekogha.courseapp.exception;


public class NotSentException extends RuntimeException {

    public NotSentException() {
        super();
    }

    public NotSentException(final String message) {
        super(message);
    }

}
