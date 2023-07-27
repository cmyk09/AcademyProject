package com.ezen.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ezen.member.MemberSVC;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountSvc
{
	@Autowired
	@Qualifier("accountdao") 
	private AccountDAO accdao;
	
	public int loginSvc(Seller seller)
	{
		String adid = seller.getAdminId();
		String adpw = seller.getAdminpass();
		
		Seller sel = accdao.logindao(seller);
		
		if(sel==null)
		{
			return 1;
		}else if(sel.getAdminpass().equals(adpw))
		{
			return 2;
		}else
		{
			return 3;
		}
	}

	public boolean signinsvc(Seller seller) {
		
		boolean signined = accdao.signindao(seller);

		return signined;
	}

	public boolean idDuplicate(String adminId) {
		boolean idDuplicate = accdao.idDuplicate(adminId);
		return idDuplicate;
	}
}
