package neoflex.deal.store.repository;

import neoflex.deal.store.entity.ClientEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

public interface ClientRepository extends CrudRepository<ClientEntity, UUID> {
}
