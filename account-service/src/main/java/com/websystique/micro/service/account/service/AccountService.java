package com.websystique.micro.service.account.service;

import com.websystique.micro.service.account.domain.Account;
import com.websystique.micro.service.account.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepo accRepo;

    public List<Account> findAll() throws Exception {
        List<Account> l = new ArrayList<>();
        accRepo.findAll().forEach(l::add);
        for (Account account : accRepo.findAll()) {
            System.out.println(account.getFullName());
        }
        return l;
    }

    public Account findOne(long id){
        return new Account();
    }

    public void delete(long id){
        accRepo.delete(id);
    }

    public void save(Account acc){
        accRepo.save(acc);
    }
}