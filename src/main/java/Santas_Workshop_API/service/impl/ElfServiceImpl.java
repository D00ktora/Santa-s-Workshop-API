package Santas_Workshop_API.service.impl;

import Santas_Workshop_API.config.ElfMapper;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.Elf;
import Santas_Workshop_API.repository.ElfRepository;
import Santas_Workshop_API.service.ElfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ElfServiceImpl implements ElfService {

	private final ElfRepository elfRepository;

	@Override
	public ElfDTO createElf(ElfDTO elfDTO) {
		Elf elf = ElfMapper.INSTANCE.fromElfDTOToElf(elfDTO);
		Elf save = elfRepository.save(elf);
		return ElfMapper.INSTANCE.fromElfToElfDTO(save);
	}

	@Override
	public List<ElfDTO> getAllElves() {
		List<ElfDTO> elfDtos = new ArrayList<>();
		for (Elf elf : elfRepository.findAll()) {
			elfDtos.add(ElfMapper.INSTANCE.fromElfToElfDTO(elf));
		}
		return elfDtos;
	}

	@Override
	public ElfDTO getElfById(Long id) {
		Elf elf = elfRepository.findById(id).orElse(null);
		if (elf == null) {
			return null;
		}
		return ElfMapper.INSTANCE.fromElfToElfDTO(elf);
	}

	@Override
	public void deleteElfById(Long id) {

	}

	@Override
	public ElfDTO assignGift(ElfDTO elfDTO) {
		return null;
	}
}
