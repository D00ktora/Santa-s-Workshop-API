package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.ElfMapper;
import Santas_Workshop_API.config.errorHandling.exceptions.NotFoundException;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.Elf;
import Santas_Workshop_API.repository.ElfRepository;
import Santas_Workshop_API.service.AssignService;
import Santas_Workshop_API.service.ElfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElfServiceImpl implements ElfService {

	private final ElfRepository elfRepository;
	private final AssignService assignService;

	@Override
	public ElfDTO createElf(ElfDTO elfDTO) {
		Elf elf = ElfMapper.INSTANCE.fromElfDTOToElf(elfDTO);
		Elf save = elfRepository.save(elf);
		return ElfMapper.INSTANCE.fromElfToElfDTO(save);
	}

	@Override
	public List<ElfDTO> getAllElves() {
		List<ElfDTO> elfDtos = new ArrayList<>();
		if (elfRepository.findAll().isEmpty()) {
			throw new NotFoundException("No elves found");
		}
		for (Elf elf : elfRepository.findAll()) {
			elfDtos.add(ElfMapper.INSTANCE.fromElfToElfDTO(elf));
		}
		return elfDtos;
	}

	@Override
	public ElfDTO getElfById(Long id) {
		Elf elf = elfRepository.findById(id).orElseThrow(() -> new NotFoundException("No elf found"));
		return ElfMapper.INSTANCE.fromElfToElfDTO(elf);
	}

	@Override
	public void deleteElfById(Long id) {
		elfRepository.findById(id).orElseThrow(() -> new NotFoundException("No elf found"));
		elfRepository.deleteById(id);
	}

	@Override
	public ElfDTO assignGift(Long elfId, Long giftId) {
		return assignService.assignGiftToElf(elfId, giftId);
	}
}
