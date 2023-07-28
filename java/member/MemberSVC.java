package com.ezen.member;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MemberSVC 
{
	@Autowired
	private MemberDAO memberDao;

	public boolean idDuplicate(String id) 
	{
		boolean idDuplicate = memberDao.idDuplicate(id);
		return idDuplicate;
	}
	
	public boolean signUp(MemberVO member) 
	{
		java.util.Date mrdate = new java.util.Date(new Date().getTime());
		member.setMrdate(mrdate);
		
		String active = "active";
		member.setMstatus(active);
		
		boolean signedUp = memberDao.signUp(member);
		return signedUp;
	}

	public int login(MemberVO member) 
	{
		String mid = member.getMid();
		String mpwd = member.getMpwd();
		
		MemberVO mem = memberDao.login(member);
		
		if(mem==null) {
			return 1;	// 아이디 없음
		}else {
			String mstatus = mem.getMstatus();
			boolean isActive = mstatus.equals("inactive");
			
			if(isActive) {
				return 1;
			}else {
				if(mem.getMpwd().equals(mpwd)) {
					return 3;	// 아이디 비밀번호 일치
				}else {
					return 2;	// 비밀번호 불일치
				}
			}	
		}
	}
	
	public MemberVO getMember(int mnum) 
	{
		MemberVO mem = memberDao.getMember(mnum);
		return mem;
	}

	public boolean update(MemberVO member) 
	{
		return memberDao.update(member);
	}
	
	public boolean pwdCheck(MemberVO member, int mnum) 
	{
		//int mnum = memberDao.getMnumByID(member.getMid());
		MemberVO mem = memberDao.getMember(mnum);
		if(member.getMpwd().equals(mem.getMpwd())) {
			return true;
		}else {
			return false;
		}
	}

	public boolean pwdUpdate(MemberVO member) 
	{
		return memberDao.pwdUpdate(member);
	}

	public int getMnumByID(String mid) 
	{
		return memberDao.getMnumByID(mid);
	}

	public boolean mWithdraw(MemberVO member) 
	{
		String inactive = "inactive";
		member.setMstatus(inactive);
		return memberDao.updateStatus(member);
	}

	public String findId(MemberVO member) 
	{
		String mname = member.getMname();
		String mphone = member.getMphone();
		
		String mid = memberDao.findId(mname, mphone);
		if(mid==null) {
			mid = "검색된 아이디 없음";
		}
		return mid;
	}

	public String findPwd(MemberVO member) 
	{
		String mid = member.getMid();
		String mname = member.getMname();
		String mpwd = memberDao.findPwd(mid, mname);
		return mpwd;
	}

	
}
