package training.edit.testsuite.rest.training;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import training.edit.testsuite.ParameterResolver;
import training.edit.testsuite.data.Product;
import training.edit.testsuite.data.ProductRepo;
import training.edit.testsuite.data.Training;
import training.edit.testsuite.data.TrainingRepo;
import training.edit.testsuite.rest.product.ProductController;
import training.edit.testsuite.rest.product.ProductModel;
import training.edit.testsuite.rest.product.ProductModelAssembler;

@RestController
@RequestMapping(path = "/trainings", produces = MediaTypes.HAL_FORMS_JSON_VALUE)
@ExposesResourceFor(TrainingModel.class)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TrainingController {

	private final @NonNull TrainingRepo repo;

	private final @NonNull ProductRepo productRepo;

	private final @NonNull ProductController productController;

	private final @NonNull TrainingModelAssembler assembler;

	private final @NonNull ProductModelAssembler productAssembler;

	private final @NonNull LinkRelationProvider relProvider;

	@Autowired
	private RequestMappingHandlerMapping handler;

	@GetMapping
	public ResponseEntity<CollectionModel<TrainingModel>> getAllTrainings() {
		return ResponseEntity.ok(assembler.toCollectionModel(repo.findAll()));
	}

	@GetMapping("{id}")
	public ResponseEntity<TrainingModel> getTraining(@PathVariable UUID id) {
		return this.repo.findById(id) //
				.map(this.assembler::toModel) //
				.map(ResponseEntity::ok) //
				.orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("{id}/product")
	public ProductModel getTrainingProduct(@PathVariable UUID id) {
		Optional<Training> training = repo.findById(id);
		if (training.isEmpty())
			return null;
		return productAssembler.toModel(training.get().getProduct());
	}

	@PostMapping
	public ResponseEntity<TrainingModel> newTraining(@RequestBody TrainingModel entity) {
		String productUri = entity.getProduct();
		String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
		String relativeProductUri = productUri.substring(baseUrl.length());

		HandlerMethod method = null;
		String pattern = null;
		Map<RequestMappingInfo, HandlerMethod> map = handler.getHandlerMethods();
		for (RequestMappingInfo info : map.keySet()) {
			List<String> matches = info.getPatternsCondition().getMatchingPatterns(relativeProductUri);
			if (matches.size() > 0) {
				method = map.get(info).createWithResolvedBean();
				pattern = matches.get(0);
				break;
			}
		}
		
		ParameterResolver parameterResolver = new ParameterResolver(pattern);
		Map<String, String> params = parameterResolver.parametersByName(relativeProductUri);

		ProductModel productModel = null;
		if (method.getBean().equals(productController) && params.containsKey("id")) {
			UUID id = UUID.fromString(params.get("id"));
			productModel = productController.getProduct(id).getBody();
		}
		Optional<Product> product = productRepo.findById(productModel.getId());

		Training t = new Training();
		t.setTitle(entity.getTitle());
		t.setDescription(entity.getDescription());
		if (product.isPresent())
			t.setProduct(product.get());
		t = repo.save(t);

		Link uri = linkTo(methodOn(TrainingController.class).getTraining(t.getId())).withSelfRel();
		return ResponseEntity.created(uri.toUri()).body(assembler.toModel(t));
	}

//	@PutMapping("{id}")
//	public EntityModel<TrainingModel> replaceTraining(@PathVariable UUID id, @RequestBody TrainingModel entity) {
//		System.out.println(entity);
//		// final URI uri =
//		// ServletUriComponentsBuilder.fromCurrentRequest().build().toUri();
//		return null;
//	}
//
//	@PatchMapping("{id}")
//	public EntityModel<Training> updateTraining(@RequestBody Training entity) {
//		System.out.println(entity);
//		return null;
//	}
//
//	@DeleteMapping("{id}")
//	public ResponseEntity<Void> deleteTraining(@PathVariable UUID id) {
//		this.repo.deleteById(id);
//		return ResponseEntity.nocontent().build();
//	}

}
