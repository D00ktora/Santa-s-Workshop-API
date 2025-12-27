package Santas_Workshop_API.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
public class DeliveredGift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long giftId;
	private String name;
	private String category;
	private String targetAge;
	private String isWrapped;
	private String status;
	private String createdAt;
	private Long elfId;
	private Long deliveryId;
}
