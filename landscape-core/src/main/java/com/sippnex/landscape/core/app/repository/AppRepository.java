package com.sippnex.landscape.core.app.repository;

import com.sippnex.landscape.core.app.domain.App;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppRepository extends MongoRepository<App, String> {

    Optional<App> findByName(String name);

}
