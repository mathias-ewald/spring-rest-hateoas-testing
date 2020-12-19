package training.edit.testsuite.rest;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import training.edit.testsuite.rest.product.ProductController;
import training.edit.testsuite.rest.training.TrainingController;

@RequestMapping(path = "/")
@RestController
public class RootController {

	@GetMapping
	public RepresentationModel<?> get() {
		RepresentationModel<?> m = new RepresentationModel<>();
		m.add(linkTo(TrainingController.class).withRel("trainings"));
		m.add(linkTo(ProductController.class).withRel("products"));
		return m;
	}

}
