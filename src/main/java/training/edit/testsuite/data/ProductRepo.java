package training.edit.testsuite.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product, UUID> {

}
