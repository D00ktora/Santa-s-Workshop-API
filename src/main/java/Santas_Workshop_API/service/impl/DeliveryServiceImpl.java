package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.DeliveryMapping;
import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.config.errorHandling.exceptions.ConflictException;
import Santas_Workshop_API.config.errorHandling.exceptions.NotFoundException;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.Delivery;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.delivery.Status;
import Santas_Workshop_API.repository.DeliveryRepository;
import Santas_Workshop_API.service.DeliveryService;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DeliveryServiceImpl implements DeliveryService {
	private final DeliveryRepository deliveryRepository;
	private final GiftService giftService;

	@Override
	public DeliveryDTO createDeliveryPlan(DeliveryDTO deliveryDTO) throws BadRequestException {
		giftValidation(deliveryDTO.getGifts());
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
	public DeliveryDTO changeStatus(String status, Long id) throws BadRequestException {
		Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException(String.format("Delivery with id %s not found", id)));
		delivery.setDeliveryStatus(checkAndGetDeliveryStatus(status, delivery));
		deliveryRepository.save(delivery);
		Set<Long> giftIds = new HashSet<>();
		for (Gift gift : delivery.getGifts()) {
			giftIds.add(gift.getId());
		}
		giftService.setGiftStatusToDelivered(delivery.getGifts());
		DeliveryDTO deliveryDTO = DeliveryMapping.INSTANCE.mapToDeliveryDto(delivery);
		deliveryDTO.setGifts(giftIds);
		return deliveryDTO;
	}

	@Override
	public DeliveryDTO getDeliveryById(Long id) {
		Delivery delivery = deliveryRepository.findById(id).orElseThrow(() -> new NotFoundException("Delivery with id " + id + " not found"));
		Set<Long> giftsById = delivery.getGifts().stream().map(Gift::getId).collect(Collectors.toSet());
		DeliveryDTO deliveryDTO = DeliveryMapping.INSTANCE.mapToDeliveryDto(delivery);
		deliveryDTO.setGifts(giftsById);
		return deliveryDTO;
	}

	private void giftValidation(Set<Long> giftIds) throws BadRequestException {
		if (giftIds.isEmpty()) {
			throw new BadRequestException("Gifts cannot be empty");
		}
		for (Long id : giftIds) {
			GiftDTO giftById = giftService.getGiftById(id);
			if (giftById == null) {
				throw new NotFoundException("Gift with id " + id + " not found");
			}
			if (giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.PENDING.toString())) {
				throw new ConflictException("Gift with id " + id + " is already pending");
			}
			if (giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.DELIVERED.toString())) {
				throw new ConflictException("Gift with id " + id + " is already delivered");
			}
			if (giftById.getStatus().equals(Santas_Workshop_API.entity.enums.gift.Status.LOADED.toString())) {
				throw new ConflictException("Gift with id " + id + " is already loaded");
			}
		}
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

	private Status checkAndGetDeliveryStatus(String deliveryStatus, Delivery delivery) throws BadRequestException {
		if (delivery.getDeliveryStatus().equals(Status.PLANNED) && (deliveryStatus.equals("DELIVERED") || deliveryStatus.equals("IN_TRANSIT") || deliveryStatus.equals("FAILED"))) {
			switch (deliveryStatus){
				case "DELIVERED": return Status.DELIVERED;
				case "IN_TRANSIT": return Status.IN_TRANSIT;
				case "FAILED": return Status.FAILED;
			}
		}
		if (delivery.getDeliveryStatus().equals(Status.IN_TRANSIT) && (deliveryStatus.equals("DELIVERED") || deliveryStatus.equals("FAILED"))) {
			switch (deliveryStatus) {
				case "DELIVERED": return Status.DELIVERED;
				case "FAILED": return Status.FAILED;
			}
		}
		if (delivery.getDeliveryStatus().equals(Status.DELIVERED) && deliveryStatus.equals("FAILED")) {
			return Status.FAILED;
		}
		throw new BadRequestException("Delivery Status cannot be " + deliveryStatus);
	}
}
