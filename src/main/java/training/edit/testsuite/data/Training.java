package training.edit.testsuite.data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Training {

	@Id
	@Type(type = "uuid-char")
	private UUID id;

	private String title;

	private String description;

	@ManyToOne
	private Product product;

	@PrePersist
	private void generateId() {
		if (id == null)
			id = UUID.randomUUID();
	}

}
