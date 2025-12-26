package Santas_Workshop_API.config.errorHandling.exceptions;

public class BadRequestException extends org.apache.coyote.BadRequestException {
	public BadRequestException(String message) {
		super(message);
	}
}
