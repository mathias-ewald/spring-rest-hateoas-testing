package training.edit.testsuite.rest.training;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.LinkRelationProvider;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import training.edit.testsuite.data.Product;
import training.edit.testsuite.data.Training;

@Component
public class TrainingModelAssembler extends RepresentationModelAssemblerSupport<Training, TrainingModel> {

	private LinkRelationProvider relProvider;

	public TrainingModelAssembler(LinkRelationProvider relProvider) {
		super(TrainingController.class, TrainingModel.class);
		this.relProvider = relProvider;
	}

	public TrainingModel toModel(Optional<Training> entity) {
		return toModel(entity.orElse(null));
	}

	@Override
	public TrainingModel toModel(Training entity) {
		if (entity == null)
			return null;
		TrainingModel model = createModel(entity);
		model.add(linkTo(methodOn(TrainingController.class).getTrainingProduct(entity.getId()))
				.withRel(this.relProvider.getItemResourceRelFor(Product.class)));
		return model;
	}

	@Override
	public CollectionModel<TrainingModel> toCollectionModel(Iterable<? extends Training> entities) {
		CollectionModel<TrainingModel> collection = super.toCollectionModel(entities);
		collection.add(linkTo(TrainingController.class).withRel("self"));
		return collection;
	}

	private TrainingModel createModel(Training entity) {
		TrainingModel model = super.createModelWithId(entity.getId(), entity);
		model.setTitle(entity.getTitle());
		model.setDescription(entity.getDescription());
		return model;
	}

}
