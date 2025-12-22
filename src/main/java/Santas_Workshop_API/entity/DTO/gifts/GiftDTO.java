package Santas_Workshop_API.entity.DTO.gifts;

import lombok.Data;

import java.time.LocalDateTime;

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
