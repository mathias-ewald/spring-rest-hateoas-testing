package training.edit.testsuite.rest.training;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Relation(itemRelation = "training", collectionRelation = "trainings")
public class TrainingModel extends RepresentationModel<TrainingModel> {

	private String title;

	private String description;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Getter(onMethod = @__(@JsonIgnore))
	private String product;

}
