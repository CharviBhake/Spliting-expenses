package com.dailycodebuffer.Repository;

import com.dailycodebuffer.Entity.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group,String> {
}
