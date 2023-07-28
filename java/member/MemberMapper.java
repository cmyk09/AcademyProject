package com.ezen.member;

import java.util.*;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface MemberMapper 
{
	public List<MemberVO> idDuplicate(String mid);
	
	public int memberSignUp(MemberVO member);
	
	public MemberVO memberLogin(MemberVO member);

	public MemberVO getMember(int mnum);

	public int updateMember(MemberVO member);

	public int pwdUpdate(MemberVO member);

	public int getMnumByID(String mid);

	public int updateStatus(MemberVO member);

	public String findId(String mname, String mphone);

	public String findPwd(String mid, String mname);

}
