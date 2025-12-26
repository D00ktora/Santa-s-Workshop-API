package Santas_Workshop_API.service;

import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;

import java.util.List;

public interface DeliveryService {
	DeliveryDTO createDeliveryPlan (DeliveryDTO deliveryDTO) throws BadRequestException;
	List<DeliveryDTO> getAllDeliveries (String status, String recipientName);
	DeliveryDTO changeStatus(String status, Long id) throws BadRequestException;
	DeliveryDTO getDeliveryById(Long id);
}
