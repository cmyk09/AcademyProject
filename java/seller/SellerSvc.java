package com.ezen.seller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ezen.goods.GoodsVO;
import com.ezen.goods.OrderVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	public boolean shipinfo(OrderVO ovo) {

		return dao.shipinfo(ovo);
	}

	public boolean changeStatus(OrderVO ovo) {
		return dao.changeStatus(ovo);
	}

	public List<Map> beforeStlmn(int sellernum) {
		return dao.beforeStlmn(sellernum);
	}

	public boolean purchaseCompl(OrderVO ovo) {
		return dao.purchaseCompl(ovo);
	}

	public String getOrderStatus(int orderNo) {
		return dao.getOrderStatus(orderNo);
	}

	public List<Map> incomelist(int sellernum) {
		return dao.incomelist(sellernum);
	}

	public PageInfo<Map> incomelistSearch(int sellerno, String category, String startdate, String enddate, String keyword,
			int pageNum, int pageSize) {
		return dao.incomelistSearch(sellerno, category, startdate, enddate, keyword, pageNum, pageSize);
	}

}
