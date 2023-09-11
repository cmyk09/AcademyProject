package com.ezen.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ezen.goods.OrderVO;
import com.github.pagehelper.PageInfo;

@Component("selmapper")
@Mapper
public interface SellerMapper
{
	public SellerVO getSeller(int sellernum);

	public int update(SellerVO seller);

	public int withdrawal(SellerVO seller);

	public int pwdChange(SellerVO seller);

	public int lastRequest(withdrawalform wdf);

	public List<Map> orderList(int sellernum);

	public int shipinfo(OrderVO ovo);

	public int changeStatus(OrderVO ovo);

	public List<Map> beforeStlmn(int sellernum);

	public int purchaseCompl(OrderVO ovo);

	public String getOrderStatus(int orderNo);

	public List<Map> incomelist(int sellernum);

	public PageInfo<Map> incomelistSearchGoodsNo(Map isMap);

	public PageInfo<Map> incomelistSearchGoodsName(Map isMap);

	public PageInfo<Map> incomelistSearchMid(Map isMap);

}
