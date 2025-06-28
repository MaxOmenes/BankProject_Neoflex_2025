package neoflex.deal.store.repository;

import neoflex.deal.store.entity.StatementEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface StatementRepository extends CrudRepository<StatementEntity, UUID> {
}
