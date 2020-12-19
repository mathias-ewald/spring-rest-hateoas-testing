package training.edit.testsuite;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import training.edit.testsuite.data.Product;
import training.edit.testsuite.data.ProductRepo;
import training.edit.testsuite.data.Training;
import training.edit.testsuite.data.TrainingRepo;

@Configuration
@SpringBootApplication
@EnableHypermediaSupport(type = HypermediaType.HAL_FORMS)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SpringRestHateoasApplication {

	private final @NonNull TrainingRepo trainingRepo;

	private final @NonNull ProductRepo productRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringRestHateoasApplication.class, args);
	}

	@Transactional
	@EventListener
	public void loadData(ContextRefreshedEvent event) {
		Product p1 = productRepo.save(Product.builder().name("Product 1").build());
		Product p2 = productRepo.save(Product.builder().name("Product 2").build());
		trainingRepo.save(Training.builder().title("Training 1").description("Foo Bar").product(p1).build());
		trainingRepo.save(Training.builder().title("Training 2").description("Bar Foo").product(p2).build());
		trainingRepo.save(Training.builder().title("Training 3").description("Foo Foo").product(p2).build());
	}

}
