package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.GiftMapper;
import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.config.errorHandling.exceptions.NotFoundException;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DeliveredGift;
import Santas_Workshop_API.entity.Delivery;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.gift.Category;
import Santas_Workshop_API.entity.enums.gift.Status;
import Santas_Workshop_API.repository.ArchivedGiftsRepository;
import Santas_Workshop_API.repository.GiftsRepository;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

	private final GiftsRepository giftsRepository;
	private final ArchivedGiftsRepository archivedGiftsRepository;

	@Override
	public GiftDTO createGift(GiftDTO inputGiftDTO) {
		Gift createdGift = GiftMapper.INSTANCE.fromGiftDtoToGift(inputGiftDTO);
		if (inputGiftDTO.getIsWrapped().equals("true")) {
			createdGift.setStatus(Status.READY);
		}
		Gift savedGift = giftsRepository.save(createdGift);
		return GiftMapper.INSTANCE.fromGiftToGiftDTO(savedGift);
	}

	@Override
	public Page<GiftDTO> getAllGifts(String status, String category, String wrapped, int page, int pageSize, String sort) throws BadRequestException {
		Pageable pageable = createPageable(page, pageSize, sort);
		Specification<Gift> specifications = createSortSpecifications(status, category, wrapped);
		Page<Gift> all = giftsRepository.findAll(specifications, pageable);
		return all.map(GiftMapper.INSTANCE::fromGiftToGiftDTO);
	}

	@Override
	public GiftDTO getGiftById(Long id) {
		Gift gift = giftsRepository.findById(id).orElseThrow(() -> new NotFoundException("Gift with id " + id + " is not found"));
		return GiftMapper.INSTANCE.fromGiftToGiftDTO(gift);
	}

	@Override
	public GiftDTO updateGift(Long id, GiftDTO updateInputDTO) {
		Gift gift = giftsRepository.findById(id).orElseThrow(() -> new NotFoundException("Gift with id " + id + " is not found"));
		gift.setName(updateInputDTO.getName());
		updateGiftCategory(updateInputDTO, gift);
		updateWrap(updateInputDTO, gift);
		gift.setTargetAge(updateInputDTO.getTargetAge());
		Gift save = giftsRepository.save(gift);
		return GiftMapper.INSTANCE.fromGiftToGiftDTO(save);
	}

	@Override
	public GiftDTO wrapGift(Long id) {
		Gift gift = giftsRepository.findById(id).orElseThrow(() -> new NotFoundException("Gift with id " + id + " is not found"));
		gift.setIsWrapped(true);
		gift.setStatus(Status.READY);
		Gift saved = giftsRepository.save(gift);
		return GiftMapper.INSTANCE.fromGiftToGiftDTO(saved);
	}

	@Override
	public void deleteGift(Long id) {
		Gift gift = giftsRepository.findById(id).orElseThrow(() -> new NotFoundException("Gift with id " + id + " is not found"));
		giftsRepository.delete(gift);
	}

	@Override
	public List<GiftDTO> searchGifts(String searchWord) throws BadRequestException {
		if (searchWord == null || searchWord.isBlank() || searchWord.isEmpty()) {
			throw new BadRequestException("Search word is not full");
		}
		List<Gift> giftsByName = giftsRepository.findByNameContainingIgnoreCase(searchWord);
		List<GiftDTO> giftDTOS = new ArrayList<>();
		for (Gift gift : giftsByName) {
			giftDTOS.add(GiftMapper.INSTANCE.fromGiftToGiftDTO(gift));
		}
		return giftDTOS;
	}

	@Override
	public Set<Gift> setGiftStatusToLoaded(Set<Long> giftIds, Delivery delivery) {
		List<Gift> allById = giftsRepository.findAllById(giftIds);
		for (Gift gift : allById) {
			gift.setStatus(Status.LOADED);
			gift.setDelivery(delivery);
		}
		List<Gift> gifts = giftsRepository.saveAll(allById);
		return new HashSet<>(gifts);
	}

	@Override
	public void setGiftStatus(Set<Gift> gifts, String status) {
		switch (status) {
			case "DELIVERED": {
				archiveGifts(gifts);
			}
			break;
			case "FAILED": {
				resetGifts(gifts);
			}
			break;
		}
	}

	private static Pageable createPageable(int page, int size, String sort) throws BadRequestException {
		if (page < 0) {
			throw new BadRequestException("Page number cannot be less than zero");
		}
		if (size <= 0) {
			throw new BadRequestException("Size cannot be less than zero");
		}
		if (!sort.equals("createdAt")) {
			if (!sort.equals("name")) {
				throw new BadRequestException("Sort is invalid");
			}
		}
		Sort sortingClass = Sort.by(Sort.Direction.DESC, sort);
		return PageRequest.of(page, size, sortingClass);
	}

	private static Specification<Gift> createSortSpecifications(String status, String category, String wrapped) {
		Specification<Gift> specification = Specification.where((root, query, cb) -> cb.conjunction());
		if (status != null && !status.isBlank()) {
			specification = specification.and((root, query, cb) ->
					cb.equal(root.get("status"), status));
		}
		if (category != null && !category.isBlank()) {
			specification = specification.and((root, query, cb) ->
					cb.equal(root.get("category"), category));
		}
		if (wrapped != null) {
			specification = specification.and((root, query, cb) ->
					cb.equal(root.get("wrapped"), wrapped));
		}
		return specification;
	}

	private static void updateGiftCategory(GiftDTO updateInputDTO, Gift gift) {
		switch (updateInputDTO.getCategory()){
			case "TOY":
				gift.setCategory(Category.TOY);
				break;
			case "BOOK":
				gift.setCategory(Category.BOOK);
				break;
			case "GADGET":
				gift.setCategory(Category.GADGET);
				break;
			case "CLOTHES":
				gift.setCategory(Category.CLOTHES);
				break;
			case "OTHER":
				gift.setCategory(Category.OTHER);
				break;
		}
	}

	private static void updateWrap(GiftDTO updateInputDTO, Gift gift) {
		if (updateInputDTO.getIsWrapped().equals("true")) {
			gift.setIsWrapped(true);
			gift.setStatus(Status.READY);
		} else if (updateInputDTO.getIsWrapped().equals("false")) {
			gift.setIsWrapped(false);
			gift.setStatus(Status.PENDING);
		}
	}

	private void resetGifts(Set<Gift> gifts) {
		for (Gift gift : gifts) {
			gift.setStatus(Status.READY);
			gift.setDelivery(null);
		}
		giftsRepository.saveAll(gifts);
	}

	private void archiveGifts(Set<Gift> gifts) {
		for (Gift gift : gifts) {
			gift.setStatus(Status.DELIVERED);
			DeliveredGift deliveredGift = mapGiftToDelivered(gift);
			archivedGiftsRepository.save(deliveredGift);
			giftsRepository.delete(gift);
		}
	}

	private DeliveredGift mapGiftToDelivered(Gift gift) {
		DeliveredGift deliveredGift = new DeliveredGift();
		deliveredGift.setGiftId(gift.getId());
		deliveredGift.setName(gift.getName());
		deliveredGift.setCategory(gift.getCategory().toString());
		deliveredGift.setTargetAge(gift.getTargetAge().toString());
		deliveredGift.setIsWrapped(gift.getIsWrapped().toString());
		deliveredGift.setStatus(gift.getStatus().toString());
		deliveredGift.setCreatedAt(gift.getCreatedAt().toString());
		deliveredGift.setElfId(gift.getElf().getId());
		deliveredGift.setDeliveryId(gift.getDelivery().getId());
		return deliveredGift;
	}
}
