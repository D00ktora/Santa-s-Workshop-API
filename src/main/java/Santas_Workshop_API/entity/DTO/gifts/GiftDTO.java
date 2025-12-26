package Santas_Workshop_API.entity.DTO.gifts;

import Santas_Workshop_API.entity.DTO.gifts.customValidation.CategoryValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.CreateValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.IsWrappedValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.UpdateValidation;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@JsonPropertyOrder({
		"id",
		"name",
		"category",
		"targetAge",
		"isWrapped",
		"status",
		"createdAt",
})
@Data
public class GiftDTO {
	private Long id;

	@Size(min = 2, max = 50, groups = {CreateValidation.class, UpdateValidation.class})
	@NotBlank(groups = {CreateValidation.class, UpdateValidation.class})
	private String name;

	@NotBlank(groups = {CreateValidation.class, UpdateValidation.class})
	@CategoryValidation(groups = {CreateValidation.class, UpdateValidation.class})
	private String category;

	@Min(value = 0, groups = {CreateValidation.class, UpdateValidation.class})
	@Max(value = 99, groups = {CreateValidation.class, UpdateValidation.class})
	private Integer targetAge;

	@IsWrappedValidation (groups = {CreateValidation.class, UpdateValidation.class})
	private String isWrapped;

	private String status;

	private LocalDateTime createdAt;
}
