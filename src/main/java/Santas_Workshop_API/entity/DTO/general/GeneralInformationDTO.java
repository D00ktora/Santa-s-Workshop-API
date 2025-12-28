package Santas_Workshop_API.entity.DTO.general;

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
	private String appName;
	private String version;
	private String currentServerTime;
	Map<String, Set<String>> endPoints;
}
