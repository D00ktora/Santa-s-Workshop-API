package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.general.GeneralInformationDTO;
import Santas_Workshop_API.entity.DTO.general.StatsDTO;
import Santas_Workshop_API.entity.DTO.general.StatsWrapperDTO;

public interface HomeService {
	GeneralInformationDTO showGeneralInformation();
	StatsWrapperDTO showStats();
}
