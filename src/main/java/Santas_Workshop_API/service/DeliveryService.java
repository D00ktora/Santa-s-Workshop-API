package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;

import java.util.List;

public interface DeliveryService {
	DeliveryDTO createDeliveryPlan (DeliveryDTO deliveryDTO);
	List<DeliveryDTO> getAllDeliveries (String status, String recipientName);
	DeliveryDTO changeStatus(String status, Long id);
	DeliveryDTO getDeliveryById(Long id);
}
