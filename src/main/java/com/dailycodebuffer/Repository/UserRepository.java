package com.dailycodebuffer.Repository;

import com.dailycodebuffer.Entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
        User findByUsername(String userName);
        void deleteByUsername(String userName);
}
