package com.ezen.account;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ezen.seller.SellerVO;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AccountSvc 
{
	@Autowired
	@Qualifier("accountdao") 
	private AccountDAO accdao;
	

	public boolean sellerIdDuplicate(String adminId)
	{
		boolean sellerIdDuplicate = accdao.sellerIdDuplicate(adminId);
		return sellerIdDuplicate;
	}

	public int sellerLogin(SellerVO seller) {
		String adminId = seller.getAdminId();
		String adminpass = seller.getAdminpass();
		
		SellerVO sel = accdao.sellerLogin(seller);
		
		if(sel==null) {
			return 1;	// 아이디 없음
		}else {
			String sstatus = sel.getSstatus();
			boolean isActive = sstatus.equals("inactive");
			
			if(isActive) {
				return 1;
			}else {
				if(sel.getAdminpass().equals(adminpass)) {
					return 3;	// 아이디 비밀번호 일치
				}else {
					return 2;	// 비밀번호 불일치
				}
			}	
		}
	}

	public int getsellernumByID(String adminId) {
		return accdao.getsellernumByID(adminId);
	}

	public boolean sellerSignup(SellerVO seller) {
		java.sql.Date signUpDate = new java.sql.Date(new Date().getTime());
		seller.setSignUpDate(signUpDate);
		
		String active = "active";
		seller.setSstatus(active);
		
		boolean signedUp = accdao.sellerSignup(seller);
		return signedUp;
	}

	public int sellernumGet(SellerVO seller) {
		return accdao.sellernumGet(seller);
	}

}
