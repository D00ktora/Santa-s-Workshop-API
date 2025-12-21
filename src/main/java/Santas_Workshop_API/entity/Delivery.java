package Santas_Workshop_API.entity;

import Santas_Workshop_API.entity.enums.delivery.Status;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Data
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String address;
	private String recipientName;
	@OneToMany(mappedBy = "delivery")
	private Set<Gift> gifts;
	private Status deliveryStatus;
	private LocalDateTime estimatedArrival;
}
