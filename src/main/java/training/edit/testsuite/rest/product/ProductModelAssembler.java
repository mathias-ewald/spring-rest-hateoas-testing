package training.edit.testsuite.rest.product;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import training.edit.testsuite.data.Product;

@Component
public class ProductModelAssembler extends RepresentationModelAssemblerSupport<Product, ProductModel> {

	private LinkRelationProvider relProvider;

	public ProductModelAssembler(LinkRelationProvider relProvider) {
		super(ProductController.class, ProductModel.class);
		this.relProvider = relProvider;
	}

	public ProductModel toModel(Optional<Product> entity) {
		return toModel(entity.orElse(null));
	}

	@Override
	public ProductModel toModel(Product entity) {
		if (entity == null)
			return null;
		ProductModel model = createModel(entity);
		return model;
	}

	@Override
	public CollectionModel<ProductModel> toCollectionModel(Iterable<? extends Product> entities) {
		CollectionModel<ProductModel> collection = super.toCollectionModel(entities);
		collection.add(linkTo(ProductController.class).withRel("self"));
		return collection;
	}

	private ProductModel createModel(Product entity) {
		ProductModel model = super.createModelWithId(entity.getId(), entity);
		model.setId(entity.getId());
		model.setName(entity.getName());
		return model;
	}

}