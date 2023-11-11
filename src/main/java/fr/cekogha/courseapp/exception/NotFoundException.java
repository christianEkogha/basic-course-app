package fr.cekogha.courseapp.exception;

/** The type Not found exception. */
public class NotFoundException extends RuntimeException {

  /** Instantiates a new Not found exception. */
  public NotFoundException() {
    super();
  }

  /**
   * Instantiates a new Not found exception.
   *
   * @param message the message
   */
  public NotFoundException(final String message) {
    super(message);
  }
}
