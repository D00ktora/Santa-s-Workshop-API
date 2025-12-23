package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DeliveryService {
	DeliveryDTO createDeliveryPlan (DeliveryDTO deliveryDTO);
	Page<DeliveryDTO> getAllDeliveries (Pageable pageable);
	DeliveryDTO changeStatus(String status, Long id);
}
