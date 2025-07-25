package com.dailycodebuffer.Service;

import com.dailycodebuffer.Entity.Expense;
import com.dailycodebuffer.Entity.Group;
import com.dailycodebuffer.Entity.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class BalanceService {

    @Autowired
    private GroupService groupService;
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;
    @Autowired
    private ObjectMapper objectMapper;

   public Map<String,Integer> getBalance(String grpId,String userName){
        String key="balance"+grpId+":"+userName;
        try{
            Object cached=redisTemplate.opsForValue().get(key);
            if(cached!=null) return objectMapper.readValue(cached.toString(), new TypeReference<Map<String, Integer>>() {});
        }catch(Exception e){
            e.printStackTrace();
        }
        Optional<Group> optionalGroup=groupService.getGroup(grpId);
        if(optionalGroup.isEmpty()) {
            return null;
        }

        Group group=optionalGroup.get();
        Map<String,Integer> map=computerBalance(group,userName);

        try{
            redisTemplate.opsForValue().set(key,objectMapper.writeValueAsString(map),1, TimeUnit.HOURS);
        }catch(Exception e){
            e.printStackTrace();
        }
        return map;
    }

    public Map<String,Integer> computerBalance(Group group,String userName){
        Map<String,Integer> map=new HashMap<>();
        List<Expense> expenseList=group.getExpenseList();
        System.out.println("Expenses in group: " + expenseList);
        if (expenseList != null) {
            for (Expense expense : expenseList) {
                List<String> users = expense.getUsers();
                String paidBy = expense.getCreatedBy();
                int totalAmount = expense.getAmount();

                if (users == null || users.isEmpty()) continue;
                int split = totalAmount / users.size();

                if (!paidBy.equals(userName) &&
                        users.stream().anyMatch(u -> u.equals(userName))) {
                    map.put(paidBy, map.getOrDefault(paidBy, 0) + split);
                } else if (paidBy.equals(userName)) {
                    for (String person : users) {
                        if (!person.equals(userName)) {
                            map.put(person, map.getOrDefault(person, 0) - split);
                        }
                    }
                }
            }
        }
        return map;
    }
    public void updateExpense(Group group,String userName){
     if(group==null || userName==null) return;
     String key="balance:"+group.getId()+":"+userName;
     Map<String,Integer> balanceMap=new HashMap<>();
     List<Expense> expenseList=group.getExpenseList();
     if (expenseList!=null){
         for(Expense expense:expenseList){
             List<String> users=expense.getUsers();
             String paidBy=expense.getCreatedBy();
             int amount=expense.getAmount();
             if(users==null || users.isEmpty()) continue;

             int split=amount/ users.size();
             if(!paidBy.equals(userName) && users.stream().anyMatch(u->u.equals(userName))){
                 balanceMap.put(paidBy,balanceMap.getOrDefault(paidBy,0)+split);
             }else if(paidBy.equals(userName)){
                 for(String person:users){
                     balanceMap.put(person,balanceMap.getOrDefault(person,0)-split);
                 }
             }
         }
     }
     redisTemplate.opsForValue().set(key,balanceMap);
    }


}
