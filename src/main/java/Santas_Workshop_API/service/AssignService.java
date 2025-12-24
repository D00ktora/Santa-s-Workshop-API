package Santas_Workshop_API.service;

import Santas_Workshop_API.entity.DTO.elves.ElfDTO;

public interface AssignService {
	ElfDTO assignGiftToElf(Long elfId, Long giftId);
}
