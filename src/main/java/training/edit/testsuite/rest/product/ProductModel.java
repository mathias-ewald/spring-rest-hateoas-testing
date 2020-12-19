package training.edit.testsuite.rest.product;

import java.util.UUID;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(itemRelation = "product", collectionRelation = "products")
public class ProductModel extends RepresentationModel<ProductModel> {

	@JsonIgnore
	private UUID id;

	private String name;

}
