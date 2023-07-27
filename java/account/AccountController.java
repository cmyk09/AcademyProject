package com.ezen.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/account")
@SessionAttributes("sellernum")
public class AccountController
{
	@Autowired
	private AccountSvc accsvc;
	
	@GetMapping("/")
	public String index() //판매자 매니저 페이지(가장 기본 index) 페이지로 이동
	{
		return "ezen/account/index";
	}
	
	@GetMapping("/login")
	public String sellerLogin() //판매자 정보 수정(seller 정보에 대한 edit(Update) 기능)
	{
		return "ezen/account/seller/sellerlogin";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Map<String, Integer> sellerLoginPost(Seller seller, HttpSession session)
	{
		int logined = accsvc.loginSvc(seller);
		if(logined==2)
		{
			session.setAttribute("adminId", seller.getAdminId());
		}
		Map<String, Integer> map = new HashMap<>();
		map.put("logined", logined);
		
		return map;
	}
	
	@GetMapping("/signin")
	public String sellerSignin()
	{
		return "ezen/account/seller/sellersignin";
	}
	
	@PostMapping("/signin")
	@ResponseBody
	public Map sellerSigninPost(Seller seller)
	{
		Map map = new HashMap<>();
		boolean signin = accsvc.signinsvc(seller);
		map.put("signin", signin);
		return map;
	}
	
	@PostMapping("/signin/idDuplicate")
	@ResponseBody
	public Map idDuplicate(@RequestParam("adminId") String adminId)
	{
		System.out.println("id: "+adminId);
		Map map = new HashMap<>();
		boolean isDuplicate = accsvc.idDuplicate(adminId);
		map.put("isDuplicate", isDuplicate);
		return map;
	}
}
