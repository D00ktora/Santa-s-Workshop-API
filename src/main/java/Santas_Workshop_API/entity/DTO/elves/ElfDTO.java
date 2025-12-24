package Santas_Workshop_API.entity.DTO.elves;

import Santas_Workshop_API.entity.DTO.elves.customValidation.ElfLevelValidation;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@JsonPropertyOrder({
		"id",
		"name",
		"skillLevel",
		"assignedGiftIds"
})
@Data
public class ElfDTO {
	private Long id;
	@NotBlank
	@Size(min = 2, max = 40)
	private String name;
	@ElfLevelValidation
	private String skillLevel;
	private Set<GiftDTO> assignedGiftIds = new HashSet<>();
}
