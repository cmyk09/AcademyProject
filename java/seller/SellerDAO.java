package com.ezen.seller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ezen.account.Seller;
import com.ezen.goods.GoodsMgtMapper;

@Repository("selldao")
public class SellerDAO
{
	
	@Autowired
	@Qualifier("selmapper")
	private SellerMapper sellermapper;
	
	public Seller getSeller(int sellernum) {
		return sellermapper.getSeller(sellernum);
	}

	public boolean update(Seller seller) {
		return sellermapper.update(seller)>0;
	}

	public boolean withdrawal(Seller seller) {
		
		return sellermapper.withdrawal(seller)>0;
	}

	public boolean pwdChange(Seller seller) {
		return sellermapper.pwdChange(seller)>0;
	}


	 public boolean lastRequest(withdrawalform wdf)
	 {
		 return sellermapper.lastRequest(wdf)>0;
	 }
	 
}
