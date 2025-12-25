package Santas_Workshop_API.config;

import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryMapping {
	DeliveryMapping INSTANCE = Mappers.getMapper(DeliveryMapping.class);
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "gifts", ignore = true)
	DeliveryDTO mapToDeliveryDto(Delivery delivery);
	Delivery mapToDelivery(DeliveryDTO deliveryDTO);
}
