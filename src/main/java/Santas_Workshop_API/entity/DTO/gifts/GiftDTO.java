package Santas_Workshop_API.entity.DTO.gifts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

public class GiftDTO {
	@Size(min = 2, max = 50)
	@NotBlank
	private String name;
	@NotBlank
	private String category;
	@Min(value = 0)
	@Max(value = 99)
	private Integer targetAge;
	private Boolean isWrapped = false;
	private String status;
	private LocalDateTime createdAt;
}
