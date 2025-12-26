package Santas_Workshop_API.config.errorHandling;

import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.config.errorHandling.exceptions.ConflictException;
import Santas_Workshop_API.config.errorHandling.exceptions.NotFoundException;
import Santas_Workshop_API.entity.DTO.exceptions.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<ApiError> handleBadRequest(
			BadRequestException ex,
			HttpServletRequest request
	) {
		return buildError(HttpStatus.BAD_REQUEST, ex.getMessage(), request);
	}

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ApiError> handleNotFound(
			NotFoundException ex,
			HttpServletRequest request
	) {
		return buildError(HttpStatus.NOT_FOUND, ex.getMessage(), request);
	}

	@ExceptionHandler(ConflictException.class)
	public ResponseEntity<ApiError> handleConflict(
			ConflictException ex,
			HttpServletRequest request
	) {
		return buildError(HttpStatus.CONFLICT, ex.getMessage(), request);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ApiError> handleValidation(
			MethodArgumentNotValidException ex,
			HttpServletRequest request
	) {
		String message = ex.getBindingResult()
				.getFieldErrors()
				.stream()
				.map(error -> error.getField() + ": " + error.getDefaultMessage())
				.findFirst()
				.orElse("Validation failed");

		return buildError(HttpStatus.BAD_REQUEST, message, request);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiError> handleGeneric(
			Exception ex,
			HttpServletRequest request
	) {
		return buildError(
				HttpStatus.INTERNAL_SERVER_ERROR,
				"Unexpected server error",
				request
		);
	}

	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseEntity<ApiError> handleTypeMismatch(
			MethodArgumentTypeMismatchException ex,
			HttpServletRequest request
	) {
		return buildError(
				HttpStatus.BAD_REQUEST,
				"Invalid value for parameter '" + ex.getName() + "'",
				request
		);
	}

	private ResponseEntity<ApiError> buildError(
			HttpStatus status,
			String message,
			HttpServletRequest request
	) {
		ApiError error = ApiError.builder()
				.timestamp(LocalDateTime.now())
				.status(status.value())
				.error(status.getReasonPhrase())
				.message(message)
				.path(request.getRequestURI())
				.build();

		return ResponseEntity.status(status).body(error);
	}
}
