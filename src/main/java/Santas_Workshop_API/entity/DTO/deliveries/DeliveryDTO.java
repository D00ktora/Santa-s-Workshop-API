package Santas_Workshop_API.entity.DTO.deliveries;

import Santas_Workshop_API.entity.Gift;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class DeliveryDTO {

	private Long id;

	private String address;

	private String recipientName;

	private String deliveryStatus;

	private LocalDateTime estimatedArrival;

	private Set<Gift> gifts;
}
