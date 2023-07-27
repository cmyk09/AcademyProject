package com.ezen.member;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

@Repository
@Slf4j
public class MemberDAO 
{
	@Autowired
	private MemberMapper memberMapper;
	
	public boolean idDuplicate(String id) 
	{
		List<MemberVO> memlist = memberMapper.idDuplicate(id);
		
		if(memlist.isEmpty()) return true;
		else return false;
	}
	
	public boolean signUp(MemberVO member) 
	{
		int n = memberMapper.memberSignUp(member);
		return n>0;
	}

	public MemberVO login(MemberVO member) 
	{
		MemberVO mem = memberMapper.memberLogin(member);
		return mem;
	}

	public MemberVO getMember(int mnum) 
	{
		MemberVO mem = memberMapper.getMember(mnum);
		return mem;
	}

	public boolean update(MemberVO member) 
	{
		return memberMapper.updateMember(member) > 0;
	}

	public boolean pwdUpdate(MemberVO member) 
	{
		return memberMapper.pwdUpdate(member) > 0;
	}

	public int getMnumByID(String mid) 
	{
		return memberMapper.getMnumByID(mid);
	}

	public boolean updateStatus(MemberVO member) 
	{
		return memberMapper.updateStatus(member) > 0;
	}

	
}
