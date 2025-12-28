package Santas_Workshop_API.controller;

import Santas_Workshop_API.entity.DTO.general.GeneralInformationDTO;
import Santas_Workshop_API.entity.DTO.general.StatsDTO;
import Santas_Workshop_API.entity.DTO.general.StatsWrapperDTO;
import Santas_Workshop_API.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HomeController {
	private final HomeService homeService;

	@GetMapping("/api")
	public GeneralInformationDTO info(){
		return homeService.showGeneralInformation();
	}

	@GetMapping("/api/stats")
	public StatsWrapperDTO stats(){
		return homeService.showStats();
	}

}
