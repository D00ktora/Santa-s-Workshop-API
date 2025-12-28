package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.entity.DTO.general.GeneralInformationDTO;
import Santas_Workshop_API.entity.DTO.general.StatsDTO;
import Santas_Workshop_API.entity.DTO.general.StatsWrapperDTO;
import Santas_Workshop_API.entity.enums.gift.Status;
import Santas_Workshop_API.repository.ArchivedDeliveriesRepository;
import Santas_Workshop_API.repository.ArchivedGiftsRepository;
import Santas_Workshop_API.repository.DeliveryRepository;
import Santas_Workshop_API.repository.GiftsRepository;
import Santas_Workshop_API.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {
	@Value("${spring.application.name}")
	private String appName;
	@Value("${spring.application.version}")
	private String appVersion;
	private final GiftsRepository giftsRepository;
	private final DeliveryRepository deliveryRepository;
	private final ArchivedDeliveriesRepository archivedDeliveriesRepository;
	private final ArchivedGiftsRepository archivedGiftsRepository;

	@Override
	public GeneralInformationDTO showGeneralInformation() {
		GeneralInformationDTO generalInformationDTO = new GeneralInformationDTO();
		generalInformationDTO.setAppName(appName);
		generalInformationDTO.setVersion(appVersion);
		generalInformationDTO.setCurrentServerTime(LocalDateTime.now().toString());
		Map<String, Set<String>> endpoints = new LinkedHashMap<>();
		endpoints.computeIfAbsent("home", k -> new LinkedHashSet<>()).add("GET: /api - General information for the app.");
		endpoints.computeIfAbsent("home", k -> new LinkedHashSet<>()).add("GET: /api/stats - Get gift and deliveries stats.");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("POST: /api/gifts - Create Gift - Accept JSON as follow: {name: (String) ,category: (String),targetAge: (Integer),isWrapped: (Boolean)} - Category can be one of the following: TOY, BOOK, GADGET, CLOTHES, OTHER");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("GET: /api/gifts - Get all gift with pagination. - Optional parameters are : status, category, wrapper, page(default = 0), pageSize(default = 100), sort(default = createdAt)");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("GET: /api/gifts/{id} - Ger gift by ID");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("PUT: /api/gifts/{id} - Full update of gift - Accept JSON as follow: {name: (String) ,category: (String),targetAge: (Integer),isWrapped: (Boolean)}");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("PATCH: /api/gifts/{id}/wrap - If this endpoints is reached, the gift will be wrap and State will be ready for shipment. - ID must be valid.");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("DELETE: /api/gifts/{id} - Delete gift.");
		endpoints.computeIfAbsent("gifts", k -> new LinkedHashSet<>()).add("POST: /api/search - Search for gifts by part of the name. - Accept parameter with name query");
		endpoints.computeIfAbsent("elves", k -> new LinkedHashSet<>()).add("POST: /api/elves - Create Elf - Accept JSON as follow: {name: (String),skillLevel: (String)} - skillLevel can be one of the following: JUNIOR, MID, SENIOR");
		endpoints.computeIfAbsent("elves", k -> new LinkedHashSet<>()).add("GET: /api/elves - Get List of Elves.");
		endpoints.computeIfAbsent("elves", k -> new LinkedHashSet<>()).add("GET: /api/elves/{id} - Get Elf by Id.");
		endpoints.computeIfAbsent("elves", k -> new LinkedHashSet<>()).add("DELETE: /api/elves/{id} - Delete Elf by Id.");
		endpoints.computeIfAbsent("elves", k -> new LinkedHashSet<>()).add("POST: /api/elves/{elfId}/assign/{giftId} - Assign gift to Elf - !Gifts that do not have Elf cannot be shipped.");
		endpoints.computeIfAbsent("deliveries", k -> new LinkedHashSet<>()).add("POST: /api/deliveries - Create Delivery - Accept JSON as follow: {address: (String),recipientName: (String),deliveryStatus: (String),gifts: (Set of Gifts Id`s)} - deliveryStatus can be one of the following: PLANNED, IN_TRANSIT, DELIVERED, FAILED.");
		endpoints.computeIfAbsent("deliveries", k -> new LinkedHashSet<>()).add("GET: /api/deliveries - Get All delivery plans with optional parameters (deliveryStatus, recipientName");
		endpoints.computeIfAbsent("deliveries", k -> new LinkedHashSet<>()).add("GET: /api/deliveries/{id}/status - Change delivery status. - Accept JSON as follow: {status: (String)} - Status can be one of the following: PLANNED, IN_TRANSIT, DELIVERED, FAILED");
		generalInformationDTO.setEndPoints(endpoints);
		return generalInformationDTO;
	}

	@Override
	public StatsWrapperDTO showStats() {
		Long countedPendingGifts = giftsRepository.countByStatus(Status.PENDING);
		Long countedReadyGifts = giftsRepository.countByStatus(Status.READY);
		Long countedLoadedGifts = giftsRepository.countByStatus(Status.LOADED);
		Long countedDeliveredGifts = archivedGiftsRepository.countByStatus("DELIVERED");
		Long countPlannedDeliveries = deliveryRepository.countByDeliveryStatus(Santas_Workshop_API.entity.enums.delivery.Status.PLANNED);
		Long countInTransitDeliveries = deliveryRepository.countByDeliveryStatus(Santas_Workshop_API.entity.enums.delivery.Status.IN_TRANSIT);
		Long countDeliveredDeliveries = archivedDeliveriesRepository.countByDeliveryStatus("DELIVERED");
		Long countFailedDeliveries = archivedDeliveriesRepository.countByDeliveryStatus("FAILED");
		StatsDTO statsDTO = new StatsDTO();
		statsDTO.setPendingGifts(countedPendingGifts);
		statsDTO.setReadyGifts(countedReadyGifts);
		statsDTO.setLoadedGifts(countedLoadedGifts);
		statsDTO.setDeliveredGifts(countedDeliveredGifts);
		statsDTO.setPlannedDeliveries(countPlannedDeliveries);
		statsDTO.setInTransitDeliveries(countInTransitDeliveries);
		statsDTO.setDeliveredDeliveries(countDeliveredDeliveries);
		statsDTO.setFailedDeliveries(countFailedDeliveries);
		StatsWrapperDTO statsWrapperDTO  = new StatsWrapperDTO();
		statsWrapperDTO.setStatsDTO(statsDTO);
		return statsWrapperDTO;
	}
}
