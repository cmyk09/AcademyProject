package com.ezen.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Repository("accountdao")
@Slf4j
public class AccountDAO
{
	@Autowired
	private AccountMapper amap;

	public Seller logindao(Seller seller)
	{
		Seller sel = amap.loginMap(seller);
	    return sel;
	}

	public boolean signindao(Seller seller) {

		int count = amap.signinMap(seller);
		return count > 0;
	}

	public boolean idDuplicate(String adminId) {
		List<Seller> list = amap.idDuplicate(adminId);
		
		if(list.isEmpty()) return true;
		else return false;
	}
}
