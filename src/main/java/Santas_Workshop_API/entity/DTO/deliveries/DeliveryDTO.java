package Santas_Workshop_API.entity.DTO.deliveries;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

	private String deliveryStatus;
}
