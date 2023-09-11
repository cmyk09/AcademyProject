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

	public PageInfo<Map> incomelistSearch(int sellernum, String category, String startdate, String enddate, String keyword,
			int pageNum, int pageSize)
	{
		PageHelper.startPage(pageNum, pageSize);
		
		Map isMap = new HashMap<>();
		isMap.put("sellernum", sellernum);
		isMap.put("startdate", startdate);
		isMap.put("enddate", enddate);
		isMap.put("keyword", keyword);
		if(category=="goodsNo")
		{
			return sellermapper.incomelistSearchGoodsNo(isMap);
		}
		else if(category=="GoodsName")
		{
			return sellermapper.incomelistSearchGoodsName(isMap);
		}
		else if(category=="mid")
		{
			return sellermapper.incomelistSearchMid(isMap);
		}
		return null;
	}
}
