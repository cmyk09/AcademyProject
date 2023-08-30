package com.ezen.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezen.seller.SellerVO;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Repository("accountdao")
@Slf4j
public class AccountDAO 
{
	@Autowired
	private AccountMapper amap;

	/*
	 * public SellerVO logindao(SellerVO seller) { SellerVO sel =
	 * amap.loginMap(seller); return sel; }
	 * 
	 * public boolean signindao(SellerVO seller) {
	 * 
	 * int count = amap.signinMap(seller); return count > 0; }
	 * 
	 * public boolean idDuplicate(String adminId) { List<SellerVO> list =
	 * amap.idDuplicate(adminId);
	 * 
	 * if(list.isEmpty()) return true; else return false; }
	 * 
	 * public int getsellernum(SellerVO seller) {
	 * 
	 * return amap.getsellernum(seller); }
	 * 
	 * public String getadminId(SellerVO seller) { return amap.getadminId(seller); }
	 */
	public boolean sellerIdDuplicate(String adminId)
	{
		List<SellerVO> sellerList = amap.sellerIdDuplicate(adminId);
		
		if(sellerList.isEmpty()) return true;
		else return false;
	}

	public SellerVO sellerLogin(SellerVO seller) {
		SellerVO sel = amap.sellerLogin(seller);
		return sel;
	}

	public int getsellernumByID(String adminId) {
		return amap.getsellernumByID(adminId);
	}

	public boolean sellerSignup(SellerVO seller) {
		int n = amap.sellerSignup(seller);
		return n>0;
	}

	public int sellernumGet(SellerVO seller) {
		return amap.sellernumGet(seller);
	}

	public String sellerIdFinder(String officeEmail, String officePhone) {
		return amap.sellerIdFinder(officeEmail, officePhone);
	}

	public String sellerPwdFinder(String adminId, String officeEmail, String officePhone) {
		return amap.sellerPwdFinder(adminId, officeEmail, officePhone);
	}

}
