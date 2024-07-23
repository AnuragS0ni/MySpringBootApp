package com.anurag.MySpringBootApp.repositries;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.anurag.MySpringBootApp.Entity.JournalEntity;


@Repository
public interface JournalRepository extends MongoRepository<JournalEntity, ObjectId>{

}
