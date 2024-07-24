package com.anurag.MySpringBootApp.repositries;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.anurag.MySpringBootApp.Entity.User;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId>{
Optional<User> findByUserName(String userName);
void deleteByUserName(String userName);
}
 