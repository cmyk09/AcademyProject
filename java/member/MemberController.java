package com.ezen.member;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
@RequestMapping("/ezen/member")
@SessionAttributes("uid")
public class MemberController 
{
	@Autowired
	private MemberSVC memberSvc;
	
	@GetMapping("/")
	@ResponseBody
	public String index()
	{
		return "Member INDEX";
	}
	
	@GetMapping("/index")
	public String index2()
	{
		return "ezen/member/memberIndex";
	}
	
	@GetMapping("/signup")
	public String signUpForm()
	{
		return "ezen/member/memberSignUp";
	}
	
	@PostMapping("/signup/idDuplicate")
	@ResponseBody
	public Map idDuplicate(@RequestParam("id") String id)
	{
		Map map = new HashMap<>();
		boolean isDuplicate = memberSvc.idDuplicate(id);
		map.put("isDuplicate", isDuplicate);
		return map;
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public Map signUp(MemberVO member)
	{
		Map map = new HashMap<>();
		boolean signedUp = memberSvc.signUp(member);
		map.put("signedUp", signedUp);
		return map;
	}
	
	@GetMapping("/loginForm")
	public String loginForm()
	{
		return "ezen/member/memberLogin";
	}
	
	@PostMapping("/login")
	@ResponseBody
	public Map login(MemberVO member, HttpSession session)
	{
		Map map = new HashMap<>();
		
		int isLogin = memberSvc.login(member);
		map.put("isLogin", isLogin);
		
		if(isLogin==3) {
			int mnum = memberSvc.getMnumByID(member.getMid());
			session.setAttribute("uid", mnum);
		}
		return map;
	}
	
	@GetMapping("/logout")
	@ResponseBody
	public Map logout(SessionStatus status)
	{
		status.setComplete();
		Map map = new HashMap<>();
		map.put("isLogout", true);
		return map;
	}
	
	@GetMapping("/memberInfo")
	public String memberInfo(Model model, 
								@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		if(uid==null) {
			return "ezen/member/memberLogin";
		} else {
			MemberVO mem = memberSvc.getMember(uid);
			model.addAttribute("member", mem);
			return "ezen/member/memberInfo";
		}
	}
	
	@GetMapping("/updateForm")
	public String updateForm(Model model, 
								@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		if(uid==null) {
			return "ezen/member/memberLogin";
		} else {
			MemberVO mem = memberSvc.getMember(uid);
			model.addAttribute("member", mem);
			return "ezen/member/memberInfoUpdate";
		}
	}
	
	@PostMapping("/update")
	@ResponseBody
	public Map updateInfo(MemberVO member, 
							@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		Map map = new HashMap<>();
		boolean updated = memberSvc.update(member);
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/pwdCheckForm")
	public String pwdCheckForm(Model model, 
								@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		if(uid==null) {
			return "ezen/member/memberLogin";
		} else {
			MemberVO mem = memberSvc.getMember(uid);
			model.addAttribute("member", mem);
			return "ezen/member/memberPwdCheckForm";
		}
	}
	
	@PostMapping("/pwdCheck")
	@ResponseBody
	public Map pwdCheck(Model model, MemberVO member, 
							@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		Map map = new HashMap<>();
		boolean checked = false;
		checked = memberSvc.pwdCheck(member, uid);
		map.put("checked", checked);
		return map;
	}
	
	@GetMapping("/pwdUpdateForm")
	public String pwdUpdateForm(Model model, 
									@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		if(uid==null) {
			return "ezen/member/memberLogin";
		} else {
			MemberVO mem = memberSvc.getMember(uid);
			model.addAttribute("member", mem);
			return "ezen/member/memberPwdUpdateForm";
		}
	}
	
	@PostMapping("/pwdUpdate")
	@ResponseBody
	public Map pwdUpdate(MemberVO member, 
							@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		Map map = new HashMap<>();
		boolean pwdUpdated = memberSvc.pwdUpdate(member);
		map.put("pupdated", pwdUpdated);
		return map;
	}
	
	@GetMapping("/mWithdrawForm")
	public String mWithdrawForm(Model model, 
									@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		if(uid==null) {
			return "ezen/member/memberLogin";
		} else {
			MemberVO mem = memberSvc.getMember(uid);
			model.addAttribute("member", mem);
			return "ezen/member/memberWithdrawForm";
		}
	}
	
	@PostMapping("/mWithdraw")
	@ResponseBody
	public Map mWithdraw(MemberVO member, SessionStatus status,
							@SessionAttribute(value = "uid", required = false) Integer uid)
	{
		Map map = new HashMap<>();
		boolean pwdChecked = false;
		boolean withdraw = false;
		
		pwdChecked = memberSvc.pwdCheck(member, uid);
		
		if(pwdChecked)
		{
			withdraw = memberSvc.mWithdraw(member);
			status.setComplete();
		}
		map.put("withdraw", withdraw);
		return map;
	}
	
}
