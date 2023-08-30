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

	//login 파트. get 부분은 Member 폴더의 MemberLogin과 공유합니다.
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
	
	//회원가입 Get 파트
	@GetMapping("/sellerSignUp")
	public String sellerSignUpForm()
	{
		return "ezen/account/seller/sellerSignUp";
	}
	
	//회원가입 Post 파트
	@PostMapping("/sellerSignup")
	@ResponseBody
	public Map sellerSignup(SellerVO seller)
	{
		Map map = new HashMap<>();
		boolean signedUp = accsvc.sellerSignup(seller);
		map.put("signedUp", signedUp);
		return map;
	}
	
	//아이디 중복 체크 파트
	@PostMapping("/signup/sellerIdDuplicate")
	@ResponseBody
	public Map sellerIdDuplicate(@RequestParam("adminId") String adminId)
	{
		Map map = new HashMap<>();
		boolean sellerIdDuplicate = accsvc.sellerIdDuplicate(adminId);
		map.put("sellerIdDuplicate", sellerIdDuplicate);
		return map;
	}
	
	//아이디 찾기 Get 파트
	@GetMapping("/sellerIdFrom")
	public String sellerIdFrom()
	{
		return "ezen/account/seller/sellerIdFinder";
	}
	
	//아이디 찾기 Post 파트
	@PostMapping("/sellerIdFrom")
	@ResponseBody
	public Map sellerIdFrom(SellerVO seller)
	{
		Map map = new HashMap<>();
		String sellerIdFrom = accsvc.sellerIdFinder(seller);
		map.put("sellerIdFrom", sellerIdFrom);
		return map;
	}
	
	//비밀번호 찾기 Get 파트
	@GetMapping("/sellerPwdFrom")
	public String sellerPwdFrom()
	{
		return "ezen/account/seller/sellerPwdFinder";
	}
	
	//비밀번호 찾기 Post 파트
	@PostMapping("/sellerPwdFrom")
	@ResponseBody
	public Map sellerPwdFrom(SellerVO seller)
	{
		Map map = new HashMap<>();
		String sellerPwdFrom = accsvc.sellerPwdFinder(seller);
		map.put("sellerPwdFrom", sellerPwdFrom);
		return map;
	}
	
}
