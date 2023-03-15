package ca.tetervak.petsitterservice.repos;

import ca.tetervak.petsitterservice.ents.Response;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by iuliana.cosmina on 7/23/16.
 */
public interface ResponseRepo extends JpaRepository<Response, Long> {
}
