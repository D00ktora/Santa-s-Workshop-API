package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.gift.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GiftsRepository extends JpaRepository<Gift, Long>, JpaSpecificationExecutor<Gift> {
	List<Gift> findByNameContainingIgnoreCase(String query);
	Long countByStatus(Status status);
}
