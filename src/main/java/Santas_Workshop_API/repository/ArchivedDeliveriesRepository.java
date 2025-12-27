package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.ArchivedDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArchivedDeliveriesRepository extends JpaRepository<ArchivedDelivery, Long> {
}
