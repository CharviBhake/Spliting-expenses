package com.dailycodebuffer.Controller;

import com.dailycodebuffer.Entity.*;
import com.dailycodebuffer.Repository.GroupRepository;
import com.dailycodebuffer.Service.BalanceService;
import com.dailycodebuffer.Service.BalanceService;
import com.dailycodebuffer.Service.ExpenseService;
import com.dailycodebuffer.Service.GroupService;
import com.dailycodebuffer.Service.UserService;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("group")
public class GroupController {
    @Autowired
    private GroupService groupService;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private ExpenseService expenseService;
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceService balanceService;

    @PostMapping
    public ResponseEntity<?> addGroup(@RequestBody Group grp){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        if (grp.getUsers() == null) {
            grp.setUsers(new ArrayList<>());
        }
        grp.getUsers().add(user);
        groupService.saveGroup(grp);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PutMapping("grpId/{grpId}/expense")
    public ResponseEntity<?> addExpense(@PathVariable String grpId, @RequestBody Expense expenseRequest){
        System.out.println("==== Inside getBalance method ====");
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String username=authentication.getName();
        User user=userService.findByUserName(username);
      /*  List<User> userList=new ArrayList<>();
        for(String name: expenseRequest.getUsers()){
            User user1=userService.findByUserName(name);
            System.out.println("Trying to fetch user: " + name + ", Got: " + user1);
            if(user1!=null){
                userList.add(user1);
            } else {
                System.out.println("User not found: " + name);
            }
        } */
        System.out.println("Incoming users: " + expenseRequest.getUsers());
        System.out.println("Class of first user: " + expenseRequest.getUsers().get(0).getClass().getName());

        Expense expense = Expense.builder()
                .description(expenseRequest.getDescription())
                .amount(expenseRequest.getAmount())
                .createdBy(username)
                .users(expenseRequest.getUsers())
                .build();
        expenseService.SaveNewExpense(expense);
        System.out.println("ExpenseRequest object: " + expenseRequest);
        System.out.println("Is users null? " + (expenseRequest.getUsers() == null));

        if(grp2!=null){

            if(grp2.getExpenseList()==null) grp2.setExpenseList(new ArrayList<>());
            grp2.getExpenseList().add(expense);
            for(User user1:grp2.getUsers()){
                balanceService.updateExpense(grp2,user1.getUsername());
            }
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("grpId/{grpId}/addUser")
    public ResponseEntity<List<User>> addUser(@PathVariable String grpId){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        User user=userService.findByUserName(userName);
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        if(grp2!=null){
            if(grp2.getUsers()==null) grp2.setUsers(new ArrayList<>());
            grp2.getUsers().add(user);
            return new ResponseEntity<>(grp2.getUsers(),HttpStatus.ACCEPTED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("grpId/{grpId}/update")
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
    public ResponseEntity<Map<String,Integer>> balanceList(@PathVariable String grpId){
        Authentication authentication=SecurityContextHolder.getContext().getAuthentication();
        String userName=authentication.getName();
        Map<String,Integer> balance=balanceService.getBalance(grpId,userName);
        if(balance==null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Map<String, Integer> result = new HashMap<>();
        for (Map.Entry<String, Integer> entry : balance.entrySet()) {
            result.put(entry.getKey(), entry.getValue());  // or getEmail(), or getId()
        }
        return new ResponseEntity<>(result,HttpStatus.OK);
    }
    @PutMapping("grpId/{grpId}/payment")
    public ResponseEntity<?> payment(@PathVariable String grpId, @RequestBody Payment payment){
        Optional<Group> grp1=groupService.getGroup(grpId);
        Group grp2=grp1.orElse(null);
        String from=payment.getFrom();
        String to=payment.getTo();
        int amount=payment.getAmount();
        if(grp2!=null && grp2.getUsers().contains(from) &&grp2.getUsers().contains(to)) {
            List<String> users=new ArrayList<>();
            Expense expense=Expense.builder()
                    .title("Payment")
                    .amount(amount)
                    .createdBy(from)
                    .users(users)
                    .build();
            expenseService.SaveNewExpense(expense);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
