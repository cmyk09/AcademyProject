package com.ezen.seller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ezen.goods.GoodsMgtMapper;
import com.ezen.goods.OrderVO;

@Repository("selldao")
public class SellerDAO
{
	
	@Autowired
	@Qualifier("selmapper")
	private SellerMapper sellermapper;
	
	public SellerVO getSeller(int sellernum) {
		return sellermapper.getSeller(sellernum);
	}

	public boolean update(SellerVO seller) {
		return sellermapper.update(seller)>0;
	}

	public boolean withdrawal(SellerVO seller) {
		
		return sellermapper.withdrawal(seller)>0;
	}

	public boolean pwdChange(SellerVO seller) {
		return sellermapper.pwdChange(seller)>0;
	}


	 public boolean lastRequest(withdrawalform wdf)
	 {
		 return sellermapper.lastRequest(wdf)>0;
	 }
		
	 public List<Map> orderList(int sellernum) 
	 {
		 return sellermapper.orderList(sellernum);
	 }

	public boolean shipinfo(OrderVO ovo) {
		return sellermapper.shipinfo(ovo)>0;
	}

	public boolean changeStatus(OrderVO ovo) {
		return sellermapper.changeStatus(ovo)>0;
	}

	public List<Map> beforeStlmn(int sellernum) {
		return sellermapper.beforeStlmn(sellernum);
	}

	public boolean purchaseCompl(OrderVO ovo) {
		return sellermapper.purchaseCompl(ovo)>0;
	}

	public List<Map> getOrderStatus(int orderNo) {
		return sellermapper.getOrderStatus(orderNo);
	}

	public List<Map> incomelist(int sellernum) {
		return sellermapper.incomelist(sellernum);
	}
	 
}
