package training.edit.testsuite.data;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;

import org.hibernate.annotations.Type;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Data
@Builder
public class Product {

	@Id
	@Type(type = "uuid-char")
	private UUID id;

	private String name;

	@PrePersist
	private void generateId() {
		if (id == null)
			id = UUID.randomUUID();
	}

}
