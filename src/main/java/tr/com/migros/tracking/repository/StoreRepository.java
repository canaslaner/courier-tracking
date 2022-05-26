package tr.com.migros.tracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tr.com.migros.tracking.model.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {
}
