package Santas_Workshop_API.entity;

import Santas_Workshop_API.entity.enums.elves.SkillLevel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Elf {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false)
	@Size(min = 2, max = 40)
	private String name;
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SkillLevel skillLevel;
	@OneToMany(mappedBy = "elf")
	private Set<Gift> assignedGiftIds = new HashSet<>();
}
