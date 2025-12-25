package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.Delivery;
import Santas_Workshop_API.entity.Gift;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

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
	Set<Gift> setGiftStatusToLoaded(Set<Long> giftIds, Delivery delivery);
	void setGiftStatusToDelivered(Set<Gift> gifts);
}
