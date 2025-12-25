package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveredGiftsRepository extends JpaRepository<Delivery, Long> {
}
