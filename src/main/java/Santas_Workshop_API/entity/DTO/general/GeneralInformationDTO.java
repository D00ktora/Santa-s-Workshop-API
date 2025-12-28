package Santas_Workshop_API.entity.DTO.general;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

import java.util.Map;
import java.util.Set;
@JsonPropertyOrder({
		"appName",
		"version",
		"currentServerTime",
		"endPoints"
})
@Data
public class GeneralInformationDTO {
	@JsonProperty("Application Name")
	private String appName;
	@JsonProperty("Application Version")
	private String version;
	@JsonProperty("Current Server Time")
	private String currentServerTime;
	@JsonProperty("End Points")
	Map<String, Set<String>> endPoints;
}
