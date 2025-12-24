package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.elves.ElfDTO;

import java.util.List;

public interface ElfService {
	ElfDTO createElf(ElfDTO elfDTO);
	List<ElfDTO> getAllElves();
	ElfDTO getElfById(Long id);
	Boolean deleteElfById(Long id);
	ElfDTO assignGift(Long id, Long giftId);
}
