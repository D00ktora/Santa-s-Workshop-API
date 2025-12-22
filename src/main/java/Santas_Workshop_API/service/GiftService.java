package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftsDTO;
import Santas_Workshop_API.entity.DTO.gifts.InputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.OutputGiftDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import java.util.List;

public interface GiftService {
	ResponseEntity<OutputGiftDTO> createGift(InputGiftDTO inputGiftDTO, BindingResult bindingResult);
	Page<GiftsDTO> getAllGifts(
			String status,
			String category,
			String wrapped,
			int page,
			int pageSize,
			String sort) throws BadRequestException;
	GiftDTO getGiftById(Long id);
	InputGiftDTO updateGift(Long id, InputGiftDTO inputGiftDTO);
	InputGiftDTO wrapGift(Long id);
	void deleteGift(Long id);
	List<InputGiftDTO> searchGifts(String searchWord);
}
