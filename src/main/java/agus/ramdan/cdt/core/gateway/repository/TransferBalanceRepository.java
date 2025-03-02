package agus.ramdan.cdt.core.gateway.repository;

import agus.ramdan.cdt.core.gateway.domain.TransferBalanceEntity;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferBalanceRepository extends ReactiveMongoRepository<TransferBalanceEntity, String> {
}
