package com.websystique.micro.service.account.service;

import com.websystique.micro.service.account.domain.Account;
import com.websystique.micro.service.account.repo.read.AccountRepoRead;
import com.websystique.micro.service.account.repo.write.AccountRepoWrite;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {

    @Autowired
    private AccountRepoRead accRead;

    @Autowired
    private AccountRepoWrite accWrite;

    public List<Account> findAll() throws Exception {
        List<Account> l = new ArrayList<>();
        accRead.findAll().forEach(l::add);
        return l;
    }

    public Account findOne(long id){
        return new Account();
    }

    public void delete(long id){
        accWrite.deleteById(id);
    }

    public void save(Account acc){
        accWrite.save(acc);
    }

    public void testMaster() throws Exception{
        accWrite.count();
    }
}