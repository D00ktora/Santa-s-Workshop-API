package Santas_Workshop_API.entity.DTO.gifts;

import Santas_Workshop_API.entity.DTO.gifts.customValidation.CategoryValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.IsWrappedValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.StatusValidation;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateInputDTO {
	@NotBlank
	@Size(min = 2, max = 50)
	private String name;

	@NotBlank
	@CategoryValidation
	private String category;

	@Min(value = 0)
	@Max(value = 99)
	private Integer targetAge;

	@IsWrappedValidation
	private String isWrapped;

	@NotBlank
	@StatusValidation
	private String status;
}
