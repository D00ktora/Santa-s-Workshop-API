package Santas_Workshop_API.controller;

import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.CreateValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.UpdateValidation;
import Santas_Workshop_API.entity.enums.gift.Status;
import Santas_Workshop_API.service.DeliveryService;
import Santas_Workshop_API.service.ElfService;
import Santas_Workshop_API.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
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
	public ResponseEntity<GiftDTO> createGift(@RequestBody @Validated(CreateValidation.class) GiftDTO inputGiftDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errors = getErrors(bindingResult);
			return ResponseEntity.badRequest().header("Errors", errors).body(null);
		}
		GiftDTO gift = giftService.createGift(inputGiftDTO);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gift.getId()).toUri();
		return ResponseEntity.created(location).body(gift);
	}

	@GetMapping("/")
	public Page<GiftDTO> getGifts(
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
	public ResponseEntity<GiftDTO> updateGift(@PathVariable Long id, @RequestBody @Validated(UpdateValidation.class) GiftDTO updateInputDTO, BindingResult bindingResult) {
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

	@DeleteMapping("/elves/{id}")
	public ResponseEntity<?> deleteElf(@PathVariable Long id) {
		Boolean result = elfService.deleteElfById(id);
		if (result) {
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping("/elves/{elfId}/assign/{giftId}")
	public ResponseEntity<ElfDTO> assignGift(@PathVariable Long elfId, @PathVariable Long giftId) {
		if (elfService.getElfById(elfId) == null || giftService.getGiftById(giftId) == null) {
			return ResponseEntity.notFound().build();
		}
		if (giftService.getGiftById(giftId).getStatus().equals(Status.DELIVERED.toString())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
		ElfDTO elfDTO = elfService.assignGift(elfId, giftId);
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


	/*
	Here start DeliveryController
	 */

	private final DeliveryService deliveryService;

	@PostMapping("/delivery")
	public ResponseEntity<DeliveryDTO> deliver(@RequestBody @Valid DeliveryDTO deliveryDTO, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			String errors = getErrors(bindingResult);
			return ResponseEntity.badRequest().header("Errors", errors).body(null);
		}
		DeliveryDTO deliveryPlan = deliveryService.createDeliveryPlan(deliveryDTO);
		if (deliveryPlan == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPlan);
	}
}
