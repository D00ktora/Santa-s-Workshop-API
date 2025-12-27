package Santas_Workshop_API.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class ArchivedDelivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long deliveryId;
	private String address;
	private String recipientName;
	@ElementCollection
	private Set<Long> gifts = new HashSet<>();
	private String deliveryStatus;
	private String estimatedArrival;
}
