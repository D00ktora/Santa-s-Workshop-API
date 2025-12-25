package Santas_Workshop_API.config;

import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.Delivery;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface DeliveryMapping {
	DeliveryMapping INSTANCE = Mappers.getMapper(DeliveryMapping.class);
	@Mapping(target = "gifts", ignore = true)
	DeliveryDTO mapToDeliveryDto(Delivery delivery);
	@Mapping(target = "gifts", ignore = true)
	@Mapping(target = "estimatedArrival", ignore = true)
	@Mapping(target = "deliveryStatus", ignore = true)
	@Mapping(target = "id", ignore = true)
	Delivery mapToDelivery(DeliveryDTO deliveryDTO);
}
