package Santas_Workshop_API.config;

import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.repository.ElfRepository;
import Santas_Workshop_API.repository.GiftsRepository;
import Santas_Workshop_API.service.ElfService;
import Santas_Workshop_API.service.GiftService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

	private final ElfRepository elfRepository;
	private final GiftsRepository giftsRepository;
	private final ElfService elfService;
	private final GiftService giftService;
	private final ObjectMapper objectMapper;

	@Override
	public void run(String... args) throws Exception {
		if (elfRepository.count() == 0) {
			loadElves();
		}

		if (giftsRepository.count() == 0) {
			loadGifts();
		}
	}

	private void loadElves() throws Exception {
		var resource = new ClassPathResource("data/elves.json");
		List<ElfDTO> elves = objectMapper.readValue(
				resource.getInputStream(),
				new TypeReference<>() {}
		);

		for (ElfDTO elf : elves) {
			elfService.createElf(elf);
		}
	}

	private void loadGifts() throws Exception {
		var resource = new ClassPathResource("data/gifts.json");
		List<GiftDTO> gifts = objectMapper.readValue(
				resource.getInputStream(),
				new TypeReference<>() {}
		);

		for (GiftDTO gift : gifts) {
			giftService.createGift(gift);
		}
	}
}
