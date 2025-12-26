package Santas_Workshop_API.config.errorHandling.exceptions;

public class ConflictException extends RuntimeException {
	public ConflictException(String message) {
		super(message);
	}
}
