package com.ezen.account;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.ezen.seller.SellerVO;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@SessionAttributes("sellernum")
@RequestMapping("/ezen/account")
public class AccountController 
{
	@Autowired
	private AccountSvc accsvc;

	@PostMapping("/sellerLogin")
	@ResponseBody
	public Map sellerLogin(SellerVO seller, HttpSession session, Model model)
	{
		Map map = new HashMap<>();
		int sellernumAdress = accsvc.sellernumGet(seller);
		int isLogin = accsvc.sellerLogin(seller);
		map.put("isLogin", isLogin);
		if(isLogin==3) {
			int sellernum = accsvc.getsellernumByID(seller.getAdminId());
			session.setAttribute("sellernum", sellernum);
		}
		map.put("sellernum", sellernumAdress);
		return map;
	}
	
		
	@GetMapping("/sellerSignUp")
	public String sellerSignUpForm()
	{
		return "ezen/account/seller/sellerSignUp";
	}
	
	@PostMapping("/signup/sellerIdDuplicate")
	@ResponseBody
	public Map sellerIdDuplicate(@RequestParam("adminId") String adminId)
	{
		Map map = new HashMap<>();
		boolean sellerIdDuplicate = accsvc.sellerIdDuplicate(adminId);
		map.put("sellerIdDuplicate", sellerIdDuplicate);
		return map;
	}
	
	@PostMapping("/sellerSignup")
	@ResponseBody
	public Map sellerSignup(SellerVO seller)
	{
		Map map = new HashMap<>();
		boolean signedUp = accsvc.sellerSignup(seller);
		map.put("signedUp", signedUp);
		return map;
	}
}
