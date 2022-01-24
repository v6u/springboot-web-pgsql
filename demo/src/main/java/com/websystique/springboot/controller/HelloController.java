package com.websystique.springboot.controller;

import com.websystique.micro.service.account.domain.Account;
import com.websystique.micro.service.account.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.compare;

@Controller
public class HelloController {

	@Autowired
	AccountService accService;

	@Value("${appz.image_version:0.0.0}")
	private String version;
	
	@Value("${appz.env:0}")
	private String env;
	
	@Value("${title:0}")
	private String title;

	@Value("${nano:0}")
	private String nano;

	@Value("${spring.datasource.url:notSet}")
	private String crudConnectionString;

	@Value("${spring.datasource.slave.url:notSet}")
	private String readOnlyConnectionString;
	
	@RequestMapping(value = "/")
	public String index(Model modal) {
		modal.addAttribute("title", title + " " + version + "/" + env);
		modal.addAttribute("message", "NANO is " + nano);
		try{
			accService.testMaster();
			modal.addAttribute("masterstatus","onLine");
		}catch (Exception ex){
			modal.addAttribute("masterstatus","offLine");
		}
		try{
			accService.testSlave();
			modal.addAttribute("slavestatus","onLine");
		}catch (Exception ex){
			modal.addAttribute("slavestatus","offLine");
		}
		if (crudConnectionString.contains("?")){
			crudConnectionString=crudConnectionString.substring(0,crudConnectionString.indexOf("?"));
		}
		if (readOnlyConnectionString.contains("?")){
			readOnlyConnectionString=readOnlyConnectionString.substring(0,readOnlyConnectionString.indexOf("?"));
		}
		modal.addAttribute("rwconnection", "Read-Write: " + crudConnectionString);
		modal.addAttribute("readconnection", "Read-Only: " + readOnlyConnectionString);
		modal.addAttribute("account", new Account());
		try {
			List<Account> l = accService.findAll();
			l.sort((a,b)->compare(a.getId(),b.getId()));
			modal.addAttribute("accounts", l);
		} catch (Exception ex){
			modal.addAttribute("accounts", new ArrayList<Account>());
			return "index";
		}
		return "index";
	}

	@PostMapping(value={"account/delete"})
	public String delete(Model model, @RequestParam("accountid") String accid){
		long accountid = Long.parseLong(accid);
		accService.delete(accountid);
		return "redirect:/";
	}
	@PostMapping(value={"account/add"})
	public String add(Model model, @ModelAttribute("account") Account acc){
		accService.save(acc);
		return "redirect:/";
	}
	@PostMapping(value={"account/edit"})
	public String edit(Model model, @ModelAttribute("account") Account acc){
		accService.save(acc);
		return "redirect:/";
	}

}
