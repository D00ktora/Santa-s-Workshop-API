package Santas_Workshop_API.entity.DTO.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StatsWrapperDTO {
	@JsonProperty("Stats")
	private StatsDTO statsDTO;
}
