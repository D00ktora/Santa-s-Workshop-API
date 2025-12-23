package Santas_Workshop_API.entity.DTO.gifts;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
		"id",
		"name",
		"category",
		"targetAge",
		"isWrapped",
		"status",
		"createdAt"
})
public class GiftsDTO {
	private Long id;
	private String name;
	private String category;
	private String targetAge;
	private String isWrapped;
	private String status;
	private String createdAt;
}
