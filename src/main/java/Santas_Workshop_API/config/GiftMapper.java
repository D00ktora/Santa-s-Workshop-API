package Santas_Workshop_API.config;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftsDTO;
import Santas_Workshop_API.entity.DTO.gifts.InputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.OutputGiftDTO;
import Santas_Workshop_API.entity.Gift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GiftMapper {
	GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);
	OutputGiftDTO fromInputGiftDtoToOutputGiftDTO(InputGiftDTO inputGiftDTO);
	@Mapping(target = "isWrapped", expression = "java(inputGiftDTO.getIsWrapped() == null || inputGiftDTO.getIsWrapped().equals(\"false\") ? false : true)")
	Gift fromInputGiftDtoToGift(InputGiftDTO inputGiftDTO);
	OutputGiftDTO fromGiftToOutputGiftDTO(Gift gift);
	GiftsDTO fromGiftToGiftsDTO(Gift gift);
	GiftDTO fromGiftToGiftDTO(Gift gift);
	@Mapping(target ="id", ignore = true)
	Gift fromGiftToGift(Gift gift);
}
