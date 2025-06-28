package neoflex.deal.store.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface CreditRepository extends CrudRepository<CreditRepository, UUID> {
}
