package Santas_Workshop_API.entity;

import Santas_Workshop_API.entity.enums.delivery.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@NotBlank
	@Size(min = 5, max = 120)
	private String address;
	@Column(nullable = false)
	@NotBlank
	private String recipientName;
	@OneToMany(mappedBy = "delivery")
	private Set<Gift> gifts = new HashSet<>();

	@Enumerated(EnumType.STRING)
	private Status deliveryStatus = Status.PLANNED;

	private LocalDateTime estimatedArrival = LocalDateTime.now().plusDays(3);
}
