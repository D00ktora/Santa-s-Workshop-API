package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.DeliveryMapping;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.Delivery;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.gift.Status;
import Santas_Workshop_API.repository.DeliveredGiftsRepository;
import Santas_Workshop_API.repository.DeliveryRepository;
import Santas_Workshop_API.service.DeliveryService;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
	public Page<DeliveryDTO> getAllDeliveries(Pageable pageable) {
		return null;
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
			if (giftById.getStatus().equals(Status.PENDING.toString()) || giftById.getStatus().equals(Status.DELIVERED.toString()) || giftById.getStatus().equals(Status.LOADED.toString())) {
				return false;
			}
		}
		return true;
	}
}
