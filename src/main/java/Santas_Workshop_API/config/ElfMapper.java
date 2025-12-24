package Santas_Workshop_API.config;


import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.Elf;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ElfMapper {
	ElfMapper INSTANCE = Mappers.getMapper(ElfMapper.class);
	ElfDTO fromElfToElfDTO(Elf elf);
	@Mapping(target = "id", ignore = true)
	Elf fromElfDTOToElf(ElfDTO elf);
}
