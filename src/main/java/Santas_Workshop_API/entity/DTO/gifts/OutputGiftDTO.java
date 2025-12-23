package Santas_Workshop_API.entity.DTO.gifts;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonPropertyOrder({
		"id",
		"name",
		"category",
		"targetAge",
		"isWrapped",
		"status",
		"createdAt",
		"errors"
})
public class OutputGiftDTO {
	private Long id;
	private String name;
	private String category;
	private Integer targetAge;
	private String isWrapped;
	private String status;
	private LocalDateTime createdAt;
	private List<String> errors = new ArrayList<>();
}
