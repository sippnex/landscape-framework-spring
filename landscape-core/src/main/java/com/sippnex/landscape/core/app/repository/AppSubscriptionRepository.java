package com.sippnex.landscape.core.app.repository;

import com.sippnex.landscape.core.app.domain.AppSubscription;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AppSubscriptionRepository extends MongoRepository<AppSubscription, String> {

    List<AppSubscription> findByUserId(String userId);

    Optional<AppSubscription> findByAppIdAndUserId(String appId, String userId);

    void deleteByAppIdAndUserId(String appId, String userId);

}
