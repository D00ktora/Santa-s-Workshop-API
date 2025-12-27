package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.DeliveredGift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedGiftsRepository extends JpaRepository<DeliveredGift, Long> {
}
