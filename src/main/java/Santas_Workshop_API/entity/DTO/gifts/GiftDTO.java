package Santas_Workshop_API.entity.DTO.gifts;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
	private String name;
	private String category;
	private Integer targetAge;
	private String isWrapped;
	private String status;
	private LocalDateTime createdAt;
}
