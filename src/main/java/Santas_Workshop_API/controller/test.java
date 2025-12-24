package Santas_Workshop_API.controller;

import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftsDTO;
import Santas_Workshop_API.entity.DTO.gifts.InputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.OutputGiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.UpdateInputDTO;
import Santas_Workshop_API.service.ElfService;
import Santas_Workshop_API.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class test {
	/*
	For Gift Controller start
	 */
	private final GiftService giftService;

	@PostMapping("/")
	public ResponseEntity<OutputGiftDTO> forTest(@RequestBody @Valid InputGiftDTO inputGiftDTO, BindingResult bindingResult) {
		ResponseEntity<OutputGiftDTO> gift = giftService.createGift(inputGiftDTO, bindingResult);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gift.getBody().getId()).toUri();
		return ResponseEntity.created(location).body(gift.getBody());
	}

	@GetMapping("/")
	public Page<GiftsDTO> getGifts(
			@RequestParam(required = false) String status,
			@RequestParam(required = false) String category,
			@RequestParam(required = false) String wrapped,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "100") int pageSize,
			@RequestParam(required = false, defaultValue = "createdAt") String sort
	) throws BadRequestException {
		return giftService.getAllGifts(status, category, wrapped, page, pageSize, sort);
	}

	@GetMapping("/{id}")
	public ResponseEntity<GiftDTO> getGift(@PathVariable Long id) {
		GiftDTO giftById = giftService.getGiftById(id);
		if (giftById == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(giftById);
	}

	@PostMapping("/{id}")
	public ResponseEntity<GiftDTO> updateGift(@PathVariable Long id, @RequestBody @Valid UpdateInputDTO updateInputDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errors = getErrors(bindingResult);
			return ResponseEntity.badRequest().header("Errors", errors).body(null);
		}

		GiftDTO giftDTO = giftService.updateGift(id, updateInputDTO);
		if (giftDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(giftDTO);
	}

	@PutMapping("/{id}/wrap")
	public ResponseEntity<GiftDTO> wrapGift(@PathVariable Long id) {
		GiftDTO giftDTO = giftService.wrapGift(id);
		if (giftDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(giftDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteGift(@PathVariable Long id) {
		Boolean deleted = giftService.deleteGift(id);
		if (deleted) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/search{query}")
	public ResponseEntity<List<GiftDTO>> searchGifts(@RequestParam String query) {
		return ResponseEntity.ok(giftService.searchGifts(query));

	}

	/*
	For Gift Controller End
	 */



	/*
	For Elf Controller Start
	 */

	private final ElfService elfService;

	@PostMapping("/elves")
	public ResponseEntity<ElfDTO> createElf(@RequestBody @Valid ElfDTO elfDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errors = getErrors(bindingResult);
			return ResponseEntity.badRequest().header("Errors", errors).body(null);
		}
		return ResponseEntity.ok(elfService.createElf(elfDTO));
	}


	@GetMapping("/elves")
	public ResponseEntity<List<ElfDTO>> getElfs() {
		List<ElfDTO> allElves = elfService.getAllElves();
		if (allElves.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(allElves);
	}

	@GetMapping("/elves/{id}")
	public ResponseEntity<ElfDTO> getElf(@PathVariable Long id) {
		ElfDTO elfDTO = elfService.getElfById(id);
		if (elfDTO == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(elfDTO);
	}

	/*
	For Gift and Elves Method, but it will be split after finishing
	 */
	private static String getErrors(BindingResult bindingResult) {
		StringBuilder errors = new StringBuilder();
		for (FieldError fieldError : bindingResult.getFieldErrors()) {
			errors.append(fieldError.getField())
					.append(" - ")
					.append(fieldError.getDefaultMessage())
					.append(" / ");
		}
		return errors.toString();
	}
}
