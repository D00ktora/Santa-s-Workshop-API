package Santas_Workshop_API.controller;

import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.deliveries.StatusChangeDTO;
import Santas_Workshop_API.service.DeliveryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/deliveries")
public class DeliveryController {
	private final DeliveryService deliveryService;

	@PostMapping()
	public ResponseEntity<DeliveryDTO> deliver(@RequestBody @Valid DeliveryDTO deliveryDTO, BindingResult bindingResult) throws BadRequestException {
		if (bindingResult.hasErrors()) {
			throw new BadRequestException(getErrors(bindingResult));
		}
		DeliveryDTO deliveryPlan = deliveryService.createDeliveryPlan(deliveryDTO);
		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPlan);
	}

	@GetMapping()
	public ResponseEntity<List<DeliveryDTO>> getDeliveryPlan(
			@RequestParam(required = false) String deliveryStatus,
			@RequestParam(required = false) String recipientName
	) {
		List<DeliveryDTO> allDeliveries = deliveryService.getAllDeliveries(deliveryStatus, recipientName);
		return ResponseEntity.ok(allDeliveries);
	}

	@PatchMapping("/{id}/status")
	public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable Long id, @RequestBody @Valid StatusChangeDTO deliveryStatus, BindingResult bindingResult) throws BadRequestException {
		if (bindingResult.hasErrors()) {
			throw new BadRequestException(getErrors(bindingResult));
		}

		if (deliveryStatus == null) {
			throw new BadRequestException("Delivery Status must be : IN_TRANSIT, DELIVERED or FAILED");
		}
		return ResponseEntity.ok(deliveryService.changeStatus(deliveryStatus.getStatus(), id));
	}



	private static String getErrors(BindingResult bindingResult) {
		StringBuilder errors = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.append(fieldError.getField())
					.append(" - ")
					.append(fieldError.getDefaultMessage())
					.append(" / ");
		}
		return errors.toString();
	}
}
