package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GiftService {
	GiftDTO createGift(GiftDTO giftDTO);
	Page<GiftDTO> getAllGifts(GiftDTO giftDTO, Pageable pageable);
	GiftDTO getGiftById(Long id);
	GiftDTO updateGift(Long id, GiftDTO giftDTO);
	GiftDTO wrapGift(Long id);
	void deleteGift(Long id);
	List<GiftDTO> searchGifts(String searchWord);
}
