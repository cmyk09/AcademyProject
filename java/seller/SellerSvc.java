package com.ezen.seller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ezen.account.Seller;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SellerSvc
{

	@Autowired
	@Qualifier("selldao")
	private SellerDAO dao;
	
	public Seller getSeller(int sellernum) {
		
		Seller seller = dao.getSeller(sellernum);
		return seller;
	}

	public boolean withdrawal(Seller seller) { 
		return dao.withdrawal(seller);
	}

	public boolean update(Seller seller) {
		return dao.update(seller);
	}

	public boolean pwdChange(Seller seller)
	{
		return dao.pwdChange(seller);
	}


	 public boolean lastRequest(withdrawalform wdf)
	 {
		 return dao.lastRequest(wdf);
	 }

}
