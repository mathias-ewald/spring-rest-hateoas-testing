package training.edit.testsuite.data;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TrainingRepo extends CrudRepository<Training, UUID> {

}
