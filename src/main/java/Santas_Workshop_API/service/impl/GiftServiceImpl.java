package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.GiftMapper;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftsDTO;
import Santas_Workshop_API.entity.DTO.gifts.InputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.OutputGiftDTO;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.repository.GiftsRepository;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftServiceImpl implements GiftService {

	private final GiftsRepository giftsRepository;

	@Override
	public ResponseEntity<OutputGiftDTO> createGift(InputGiftDTO inputGiftDTO, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return validation(inputGiftDTO, bindingResult);
		}
		//todo: To check if i can implement StateMachine in order to change automatically the status and not do it by default.
		return correctlySavedGift(inputGiftDTO);
	}

	@Override
	public Page<GiftsDTO> getAllGifts(String status, String category, String wrapped, int page, int pageSize, String sort) throws BadRequestException {
		Pageable pageable = createPageable(page, pageSize, sort);
		Specification<Gift> specifications = createSortSpecifications(status, category, wrapped);
		Page<Gift> all = giftsRepository.findAll(specifications, pageable);
		return all.map(GiftMapper.INSTANCE::fromGiftToGiftsDTO);
	}

	@Override
	public GiftDTO getGiftById(Long id) {
		Optional<Gift> gift = giftsRepository.findById(id);
		return gift.map(GiftMapper.INSTANCE::fromGiftToGiftDTO).orElse(null);
	}

	@Override
	public InputGiftDTO updateGift(Long id, InputGiftDTO inputGiftDTO) {
		return null;
	}

	@Override
	public InputGiftDTO wrapGift(Long id) {
		return null;
	}

	@Override
	public void deleteGift(Long id) {

	}

	@Override
	public List<InputGiftDTO> searchGifts(String searchWord) {
		return List.of();
	}

	private ResponseEntity<OutputGiftDTO> correctlySavedGift(InputGiftDTO inputGiftDTO) {
		Gift createdGift = GiftMapper.INSTANCE.fromInputGiftDtoToGift(inputGiftDTO);
		Gift savedGift = giftsRepository.save(createdGift);
		OutputGiftDTO outputGiftDTO = GiftMapper.INSTANCE.fromGiftToOutputGiftDTO(savedGift);
		return ResponseEntity.ok().body(outputGiftDTO);
	}

	private static ResponseEntity<OutputGiftDTO> validation(InputGiftDTO inputGiftDTO, BindingResult bindingResult) {
		OutputGiftDTO outputGiftDTO = GiftMapper.INSTANCE.fromInputGiftDtoToOutputGiftDTO(inputGiftDTO);
		List<String> errors = new ArrayList<>();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
		}
		outputGiftDTO.setErrors(errors);
		return ResponseEntity.badRequest().body(outputGiftDTO);
	}

	private static Pageable createPageable(int page, int size, String sort) throws BadRequestException {
		if (page < 0) {
			throw new BadRequestException();
		}
		if (size <= 0) {
			throw new BadRequestException();
		}
		if (!sort.equals("createdAt")) {
			if (!sort.equals("name")) {
				throw new BadRequestException();
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
}
