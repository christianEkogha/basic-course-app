package fr.cekogha.courseapp.exception;

/** The type Not sent exception. */
public class NotSentException extends RuntimeException {

  /** Instantiates a new Not sent exception. */
  public NotSentException() {
    super();
  }

  /**
   * Instantiates a new Not sent exception.
   *
   * @param message the message
   */
  public NotSentException(final String message) {
    super(message);
  }
}
