package Santas_Workshop_API.entity;

import Santas_Workshop_API.entity.enums.gift.Category;
import Santas_Workshop_API.entity.enums.gift.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Gift {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@Size(min = 2, max = 50)
	private String name;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Category category;
	@Min(value = 0)
	@Max(value = 99)
	private Integer targetAge;
	private Boolean isWrapped = false;
	@Enumerated(EnumType.STRING)
	private Status status;
	@Column(nullable = false)
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "elf_id")
	private Elf elf;
	@ManyToOne
	@JoinColumn(name = "delivery_id")
	private Delivery delivery;
}
