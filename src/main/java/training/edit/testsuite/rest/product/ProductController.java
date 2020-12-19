package training.edit.testsuite.rest.product;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import training.edit.testsuite.data.ProductRepo;

@ExposesResourceFor(ProductModel.class)
@RequestMapping(path = "/products")
@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

	private final @NonNull ProductRepo repo;

	private final @NonNull ProductModelAssembler assembler;

	@GetMapping
	public ResponseEntity<CollectionModel<ProductModel>> getAllProducts() {
		return ResponseEntity.ok(assembler.toCollectionModel(repo.findAll()));
	}

	@GetMapping("{id}")
	public ResponseEntity<ProductModel> getProduct(@PathVariable UUID id) {
		return this.repo.findById(id) //
				.map(this.assembler::toModel) //
				.map(ResponseEntity::ok) //
				.orElse(ResponseEntity.notFound().build());
	}

}
