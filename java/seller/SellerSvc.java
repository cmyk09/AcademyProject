package com.ezen.seller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SellerSvc
{

	@Autowired
	@Qualifier("selldao")
	private SellerDAO dao;
	
	public SellerVO getSeller(int sellernum) {
		
		SellerVO seller = dao.getSeller(sellernum);
		return seller;
	}

	public boolean withdrawal(SellerVO seller) { 
		return dao.withdrawal(seller);
	}

	public boolean update(SellerVO seller) {
		return dao.update(seller);
	}

	public boolean pwdChange(SellerVO seller)
	{
		return dao.pwdChange(seller);
	}


	 public boolean lastRequest(withdrawalform wdf)
	 {
		 return dao.lastRequest(wdf);
	 }
	 
	 public List<Map> orderList(int sellernum) 
	 {
		 return dao.orderList(sellernum);
	 }

	 public Map shipping(Map shipInfo)
	 {
		 return dao.shipping(shipInfo);
	 }

	public List<Map> goodsList(int parseInt) {
		// TODO Auto-generated method stub
		return null;
	}

}
