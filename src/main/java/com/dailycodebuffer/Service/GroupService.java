package com.dailycodebuffer.Service;

import com.dailycodebuffer.Entity.Group;
import com.dailycodebuffer.Repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GroupService {
    @Autowired
    private GroupRepository groupRepository;

    public Optional<Group> getGroup(String grpId){
        return groupRepository.findById(grpId);
    }
    public void saveGroup(Group grp){
        groupRepository.save(grp);
    }

}
