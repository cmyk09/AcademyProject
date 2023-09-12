package com.ezen.seller;

import java.util.*;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ezen.goods.GoodsMgtMapper;
import com.ezen.goods.OrderVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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

	public String getOrderStatus(int orderNo) {
		return sellermapper.getOrderStatus(orderNo);
	}

	public List<Map> incomelist(int sellernum) {
		return sellermapper.incomelist(sellernum);
	}

	public PageInfo<Map> incomelistSearch(int sellerno, String category, String startdate, String enddate, String keyword,
			int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Map> pageInfo = null;
		
		Map isMap = new HashMap<>();
		isMap.put("sellerno", sellerno);
		isMap.put("startdate", startdate);
		isMap.put("enddate", enddate);
		isMap.put("keyword", keyword);
		System.out.println("DAO까지는 옴.");
		switch(category)
			{
			case "orderNo":
				pageInfo = new PageInfo<>(sellermapper.incomelistSearchOrderNo(isMap));
				System.out.println("orderNo mapper 들어감.");
				System.out.println("category: " + category);
				System.out.println("isMap: " + isMap);
				return pageInfo;
			case "GoodsName":
				pageInfo = new PageInfo<>(sellermapper.incomelistSearchGoodsName(isMap));
				System.out.println("GoodsName mapper 들어감.");
				return pageInfo;
			case "mid":
				pageInfo = new PageInfo<>(sellermapper.incomelistSearchMid(isMap));
				System.out.println("mid mapper 들어감.");
				return pageInfo;
			default:
				System.out.println("값 출력 안 됨.");
				return pageInfo;
			}

	}
}
