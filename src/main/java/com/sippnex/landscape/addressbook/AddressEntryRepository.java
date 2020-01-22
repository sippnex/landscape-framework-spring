package com.sippnex.landscape.addressbook;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressEntryRepository extends MongoRepository<AddressEntry, String> {

}
