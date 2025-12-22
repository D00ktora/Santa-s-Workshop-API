package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.Gift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface GiftsRepository extends JpaRepository<Gift, Long>, JpaSpecificationExecutor<Gift> {
}
