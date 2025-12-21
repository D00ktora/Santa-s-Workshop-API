package Santas_Workshop_API.repository;

import Santas_Workshop_API.entity.Elf;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElfRepository extends JpaRepository<Elf, Long> {
}
