package Santas_Workshop_API.controller;

import Santas_Workshop_API.config.errorHandling.exceptions.BadRequestException;
import Santas_Workshop_API.entity.DTO.deliveries.DeliveryDTO;
import Santas_Workshop_API.entity.DTO.elves.ElfDTO;
import Santas_Workshop_API.entity.DTO.gifts.GiftDTO;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.CreateValidation;
import Santas_Workshop_API.entity.DTO.gifts.customValidation.UpdateValidation;
import Santas_Workshop_API.service.DeliveryService;
import Santas_Workshop_API.service.ElfService;
import Santas_Workshop_API.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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

//	@PostMapping("/")
//	public ResponseEntity<GiftDTO> createGift(@RequestBody @Validated(CreateValidation.class) GiftDTO inputGiftDTO, BindingResult bindingResult) throws BadRequestException {
//		if (bindingResult.hasErrors()) {
//			throw new BadRequestException(getErrors(bindingResult));}
//		GiftDTO gift = giftService.createGift(inputGiftDTO);
//		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(gift.getId()).toUri();
//		return ResponseEntity.created(location).body(gift);
//	}

//	@GetMapping("/")
//	public Page<GiftDTO> getGifts(
//			@RequestParam(required = false) String status,
//			@RequestParam(required = false) String category,
//			@RequestParam(required = false) String wrapped,
//			@RequestParam(required = false, defaultValue = "0") int page,
//			@RequestParam(required = false, defaultValue = "100") int pageSize,
//			@RequestParam(required = false, defaultValue = "createdAt") String sort
//	) throws BadRequestException {
//		return giftService.getAllGifts(status, category, wrapped, page, pageSize, sort);
//	}

//	@GetMapping("/{id}")
//	public ResponseEntity<GiftDTO> getGift(@PathVariable Long id) {
//		GiftDTO giftById = giftService.getGiftById(id);
//		return ResponseEntity.ok(giftById);
//	}

//	@PostMapping("/{id}")
//	public ResponseEntity<GiftDTO> updateGift(@PathVariable Long id, @RequestBody @Validated(UpdateValidation.class) GiftDTO updateInputDTO, BindingResult bindingResult) throws BadRequestException {
//		if (bindingResult.hasErrors()) {
//			throw new BadRequestException(getErrors(bindingResult));
//		}
//		GiftDTO giftDTO = giftService.updateGift(id, updateInputDTO);
//		return ResponseEntity.ok(giftDTO);
//	}

//	@PutMapping("/{id}/wrap")
//	public ResponseEntity<GiftDTO> wrapGift(@PathVariable Long id) {
//		GiftDTO giftDTO = giftService.wrapGift(id);
//		return ResponseEntity.ok(giftDTO);
//	}

//	@DeleteMapping("/{id}")
//	public ResponseEntity<GiftDTO> deleteGift(@PathVariable Long id) {
//		giftService.deleteGift(id);
//		return ResponseEntity.noContent().build();
//	}

//	@GetMapping("/search")
//	public ResponseEntity<List<GiftDTO>> searchGifts(@RequestParam String query) throws BadRequestException {
//		return ResponseEntity.ok(giftService.searchGifts(query));
//
//	}

	/*
	For Gift Controller End
	 */



	/*
	For Elf Controller Start
	 */

	private final ElfService elfService;

//	@PostMapping("/elves")
//	public ResponseEntity<ElfDTO> createElf(@RequestBody @Valid ElfDTO elfDTO, BindingResult bindingResult) throws BadRequestException {
//		if (bindingResult.hasErrors()) {
//			throw new BadRequestException(getErrors(bindingResult));
//		}
//		return ResponseEntity.ok(elfService.createElf(elfDTO));
//	}


//	@GetMapping("/elves")
//	public ResponseEntity<List<ElfDTO>> getElves() {
//		List<ElfDTO> allElves = elfService.getAllElves();
//		return ResponseEntity.ok(allElves);
//	}

//	@GetMapping("/elves/{id}")
//	public ResponseEntity<ElfDTO> getElf(@PathVariable Long id) {
//		ElfDTO elfDTO = elfService.getElfById(id);
//		return ResponseEntity.ok(elfDTO);
//	}

//	@DeleteMapping("/elves/{id}")
//	public ResponseEntity<ElfDTO> deleteElf(@PathVariable Long id) {
//		elfService.deleteElfById(id);
//		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//	}

//	@PostMapping("/elves/{elfId}/assign/{giftId}")
//	public ResponseEntity<ElfDTO> assignGift(@PathVariable Long elfId, @PathVariable Long giftId) {
//		ElfDTO elfDTO = elfService.assignGift(elfId, giftId);
//		return ResponseEntity.ok(elfDTO);
//	}

	/*
	For Gift and Elves Method, but it will be split after finishing
	 */
//	private static String getErrors(BindingResult bindingResult) {
//		StringBuilder errors = new StringBuilder();
//		for (FieldError fieldError : bindingResult.getFieldErrors()) {
//			errors.append(fieldError.getField())
//					.append(" - ")
//					.append(fieldError.getDefaultMessage())
//					.append(" / ");
//		}
//		return errors.toString();
//	}


	/*
	Here start DeliveryController
	 */

	private final DeliveryService deliveryService;

//	@PostMapping("/delivery")
//	public ResponseEntity<DeliveryDTO> deliver(@RequestBody @Valid DeliveryDTO deliveryDTO, BindingResult bindingResult) throws BadRequestException {
//		if (bindingResult.hasErrors()) {
//			throw new BadRequestException(getErrors(bindingResult));
//		}
//		DeliveryDTO deliveryPlan = deliveryService.createDeliveryPlan(deliveryDTO);
//		return ResponseEntity.status(HttpStatus.CREATED).body(deliveryPlan);
//	}

//	@GetMapping("/deliveries")
//	public ResponseEntity<List<DeliveryDTO>> getDeliveryPlan(
//			@RequestParam(required = false) String deliveryStatus,
//			@RequestParam(required = false) String recipientName
//	) {
//		List<DeliveryDTO> allDeliveries = deliveryService.getAllDeliveries(deliveryStatus, recipientName);
//		return ResponseEntity.ok(allDeliveries);
//	}

	@PatchMapping("/deliveries/{id}/")
	public ResponseEntity<DeliveryDTO> updateDelivery(@PathVariable Long id, @RequestParam String deliveryStatus) throws BadRequestException {
		if (deliveryStatus == null || deliveryStatus.isEmpty()) {
			throw new BadRequestException("Delivery Status must be : PLANNED, IN_TRANSIT, DELIVERED or FAILED");
		}
		return ResponseEntity.ok(deliveryService.changeStatus(deliveryStatus, id));
	}
}
