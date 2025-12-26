package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.ElfMapper;
import Santas_Workshop_API.config.errorHandling.exceptions.ConflictException;
import Santas_Workshop_API.config.errorHandling.exceptions.NotFoundException;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.Elf;
import Santas_Workshop_API.entity.Gift;
import Santas_Workshop_API.entity.enums.gift.Status;
import Santas_Workshop_API.repository.ElfRepository;
import Santas_Workshop_API.repository.GiftsRepository;
import Santas_Workshop_API.service.AssignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class AssignServiceImpl implements AssignService {

	private final ElfRepository elfRepository;
	private final GiftsRepository giftsRepository;

	@Override
	public ElfDTO assignGiftToElf(Long elfId, Long giftId) {
		Gift gift = giftsRepository.findById(giftId).orElseThrow(() -> new NotFoundException("Gift not found"));
		if (gift.getStatus().equals(Status.DELIVERED)) {
			throw new ConflictException("Gift is already delivered");
		}
		Elf elf = elfRepository.findById(elfId).orElseThrow(() -> new NotFoundException("Elf not found"));
		if (elf.getAssignedGiftIds().contains(gift)) {
			return ElfMapper.INSTANCE.fromElfToElfDTO(elf);
		}
		gift.setElf(elf);
		giftsRepository.save(gift);
		elf.getAssignedGiftIds().add(gift);
		elf.setAssignedGiftIds(elf.getAssignedGiftIds());
		Elf save = elfRepository.save(elf);
		return ElfMapper.INSTANCE.fromElfToElfDTO(save);
	}
}
