package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface GiftService {
	GiftDTO createGift(GiftDTO inputGiftDTO);
	Page<GiftDTO> getAllGifts(
			String status,
			String category,
			String wrapped,
			int page,
			int pageSize,
			String sort) throws BadRequestException;
	GiftDTO getGiftById(Long id);
	GiftDTO updateGift(Long id, GiftDTO updateInputDTO);
	GiftDTO wrapGift(Long id);
	Boolean deleteGift(Long id);
	List<GiftDTO> searchGifts(String searchWord);
}
