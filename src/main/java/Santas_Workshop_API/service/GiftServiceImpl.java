package Santas_Workshop_API.service;

import Santas_Workshop_API.config.GiftMapper;
import Santas_Workshop_API.entity.DTO.gifts.InputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.OutputGiftDTO;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.repository.GiftsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

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
	public Page<InputGiftDTO> getAllGifts(InputGiftDTO inputGiftDTO, Pageable pageable) {
		return null;
	}

	@Override
	public InputGiftDTO getGiftById(Long id) {
		return null;
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
}
