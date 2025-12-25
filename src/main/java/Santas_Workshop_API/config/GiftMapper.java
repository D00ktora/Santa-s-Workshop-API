package Santas_Workshop_API.config;

import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.Gift;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface GiftMapper {
	GiftMapper INSTANCE = Mappers.getMapper(GiftMapper.class);
	GiftDTO fromGiftToGiftDTO(Gift gift);
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "elf", ignore = true)
	@Mapping(target = "isWrapped", expression = "java(giftDTO.getIsWrapped() == null || giftDTO.getIsWrapped().equals(\"false\") ? false : true)")
	@Mapping(target = "createdAt", ignore = true)
	Gift fromGiftDtoToGift(GiftDTO giftDTO);
}
