package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.DeliveryMapping;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.Delivery;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.delivery.Status;
import Santas_Workshop_API.repository.DeliveredGiftsRepository;
import Santas_Workshop_API.repository.DeliveryRepository;
import Santas_Workshop_API.service.DeliveryService;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
	private final DeliveryRepository deliveryRepository;
	private final DeliveredGiftsRepository deliveredGiftsRepository;
	private final GiftService giftService;

	@Override
	public DeliveryDTO createDeliveryPlan(DeliveryDTO deliveryDTO) {
		if (!giftValidation(deliveryDTO.getGifts())) {
			return null;
		}
		Delivery delivery = DeliveryMapping.INSTANCE.mapToDelivery(deliveryDTO);
		Delivery savedDelivery = deliveryRepository.save(delivery);
		Set<Gift> gifts = giftService.setGiftStatusToLoaded(deliveryDTO.getGifts(), savedDelivery);
		delivery.setGifts(gifts);
		Delivery saveDeliveryWithGifts = deliveryRepository.save(delivery);
		DeliveryDTO newDeliveries = DeliveryMapping.INSTANCE.mapToDeliveryDto(saveDeliveryWithGifts);
		newDeliveries.setGifts(deliveryDTO.getGifts());
		return newDeliveries;
	}

	@Override
	public List<DeliveryDTO> getAllDeliveries(String deliveryStatus, String recipientName) {
		Specification<Delivery> specification = createSortSpecifications(deliveryStatus, recipientName);
		List<Delivery> all = deliveryRepository.findAll(specification);
		List<DeliveryDTO> deliveriesDTOS = new ArrayList<>();
		for (Delivery delivery : all) {
			Set<Gift> gifts = delivery.getGifts();
			Set<Long> giftIds = new HashSet<>();
			for (Gift gift : gifts) {
				giftIds.add(gift.getId());
			}
			DeliveryDTO deliveryDTO = DeliveryMapping.INSTANCE.mapToDeliveryDto(delivery);
			deliveryDTO.setGifts(giftIds);
			deliveriesDTOS.add(deliveryDTO);
		}
		return deliveriesDTOS;
	}

	@Override
	public DeliveryDTO changeStatus(String status, Long id) {
		return null;
	}

	private Boolean giftValidation(Set<Long> giftIds) {
		if (giftIds.isEmpty()) {
			return false;
		}
		for (Long id : giftIds) {
			GiftDTO giftById = giftService.getGiftById(id);
			if (giftById == null) {
				return false;
			}
			if (
					giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.PENDING.toString()) ||
					giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.DELIVERED.toString()) ||
					giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.LOADED.toString())) {
				return false;
			}
		}
		return true;
	}

	private static Specification<Delivery> createSortSpecifications(String deliveryStatus, String recipientName) {
		Specification<Delivery> specification = Specification.where((root, query, cb) -> cb.conjunction());
		if (deliveryStatus != null && !deliveryStatus.isBlank()) {
			specification = specification.and((root, query, cb) ->
					cb.equal(root.get("deliveryStatus"), deliveryStatus));
		}
		if (recipientName != null && !recipientName.isBlank()) {
			specification = specification.and((root, query, cb) ->
					cb.like(cb.lower(root.get("recipientName")), "%" + recipientName.toLowerCase() + "%"));

		}
		return specification;
	}
}
