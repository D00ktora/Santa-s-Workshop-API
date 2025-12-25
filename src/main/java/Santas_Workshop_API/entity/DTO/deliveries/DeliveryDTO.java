package Santas_Workshop_API.entity.DTO.deliveries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DeliveryDTO {

	private Long id;

	@NotBlank
	@Size(min = 5, max = 120)
	private String address;

	@NotBlank
	private String recipientName;

	private LocalDateTime estimatedArrival;

	@Size(min = 1, message = "There must be at least one gift ID")
	private Set<Long> gifts;
}
