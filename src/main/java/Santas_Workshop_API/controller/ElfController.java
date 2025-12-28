package Santas_Workshop_API.controller;

import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.service.ElfService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/elves")
public class ElfController {
	private final ElfService elfService;

	@PostMapping()
	public ResponseEntity<ElfDTO> createElf(@RequestBody @Valid ElfDTO elfDTO, BindingResult bindingResult) throws BadRequestException {
		if (bindingResult.hasErrors()) {
			throw new BadRequestException(getErrors(bindingResult));
		}
		return ResponseEntity.ok(elfService.createElf(elfDTO));
	}

	@GetMapping()
	public ResponseEntity<List<ElfDTO>> getElves() {
		List<ElfDTO> allElves = elfService.getAllElves();
		return ResponseEntity.ok(allElves);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ElfDTO> getElf(@PathVariable Long id) {
		ElfDTO elfDTO = elfService.getElfById(id);
		return ResponseEntity.ok(elfDTO);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ElfDTO> deleteElf(@PathVariable Long id) {
		elfService.deleteElfById(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@PostMapping("/{elfId}/assign/{giftId}")
	public ResponseEntity<ElfDTO> assignGift(@PathVariable Long elfId, @PathVariable Long giftId) {
		ElfDTO elfDTO = elfService.assignGift(elfId, giftId);
		return ResponseEntity.ok(elfDTO);
	}

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
