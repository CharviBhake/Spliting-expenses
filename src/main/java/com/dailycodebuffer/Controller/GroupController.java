package com.dailycodebuffer.Controller;

import com.dailycodebuffer.Entity.Expense;
import com.dailycodebuffer.Entity.Group;
import com.dailycodebuffer.Entity.User;
import com.dailycodebuffer.Repository.GroupRepository;
import com.dailycodebuffer.Service.ExpenseService;
import com.dailycodebuffer.Service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpenseService expenseService;

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody Group grp){
        groupService.saveGroup(grp);
        String id=grp.getId();
        Optional<Group> grp1=groupService.getGroup(id);
        Group grp2=grp1.orElse(null);
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        if(grp2!=null){
            grp2.getUsers().add(userName);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("grpId/{grpId}/expense")
    public ResponseEntity<?> addExpense(@PathVariable String grpId, @RequestBody Expense expenseRequest){
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        Expense expense = Expense.builder()
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .createdBy(username)
                .build();
        expenseService.SaveNewExpense(expense);
        if(grp2!=null){
            grp2.getExpenseList().add(expense);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("grpId/{grpId}")
    public ResponseEntity<?> addUser(@PathVariable String grpId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        if(grp2!=null){
            grp2.getUsers().add(userName);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("grpId/{grpId}")
    public ResponseEntity<?> updateGroup(@RequestBody Group group,@PathVariable String grpId){
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        if(grp2!=null){
            grp2.setName(group.getName());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @GetMapping("grpId/{grpId}/balance")
    public ResponseEntity<?> balanceList(@PathVariable String grpId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        if(grp2!=null){
            Map<String,Integer> map=new HashMap<>();
            List<Expense> expenseList=grp2.getExpenseList();
            if(expenseList!=null){
                for(int i=0;i<expenseList.size();i++){
                    Expense expense=expenseList.get(i);
                    if(expense.getUsers().contains(userName)){
                        int amount=expense.getAmount();
                        int n=expense.getUsers().size();
                        int split=(amount/n);
                        map.put(expense.getCreatedBy(),split);
                    }
                }
            }
            return  new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
