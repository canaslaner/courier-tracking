package tr.com.migros.tracking.repository;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tr.com.migros.tracking.model.Courier;
import tr.com.migros.tracking.model.Entrance;
import tr.com.migros.tracking.model.Store;
import tr.com.migros.tracking.util.Constants;

@Repository
public interface EntranceRepository extends JpaRepository<Entrance, Long> {

    @CachePut(cacheNames = Constants.Cache.CACHE_NAME_ENTRANCE, key = "#entity.courier.id +'_'+ #entity.store.id")
    // this is default behaviour, however, added this annotation to show it is used
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    <S extends Entrance> S save(S entity);

}
