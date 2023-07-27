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
	
	public List<Map> getSeller(int sellernum) {
		List<Map> list = sellermapper.getSeller(sellernum);
		return list;
	}

	public boolean update(Seller seller) {
		return sellermapper.update(seller)>0;
	}


	
}
