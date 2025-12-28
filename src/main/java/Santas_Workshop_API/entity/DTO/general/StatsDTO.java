package Santas_Workshop_API.entity.DTO.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@Data
@JsonPropertyOrder({
		"pendingGifts",
		"readyGifts",
		"loadedGifts",
		"deliveredGifts",
		"plannedDeliveries",
		"inTransitDeliveries",
		"deliveredDeliveries",
		"failedDeliveries"
})
public class StatsDTO {
	@JsonProperty("Gifts with Pending status")
	private Long pendingGifts;
	@JsonProperty("Gifts with Ready status")
	private Long readyGifts;
	@JsonProperty("Gifts with Loaded status")
	private Long loadedGifts;
	@JsonProperty("Gifts with Delivered status")
	private Long deliveredGifts;
	@JsonProperty("Deliveries with Planned status")
	private Long plannedDeliveries;
	@JsonProperty("Deliveries with In Transit status")
	private Long inTransitDeliveries;
	@JsonProperty("Deliveries with Delivered status")
	private Long deliveredDeliveries;
	@JsonProperty("Deliveries with Failed status")
	private Long failedDeliveries;
}
