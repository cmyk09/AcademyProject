package com.ezen.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import com.ezen.account.Seller;

@Component("selmapper")
@Mapper
public interface SellerMapper
{
	List<Map> getGoodsCategoryList1();

	List<Map> getGoodsCategoryList2(String code);

	List<Map> getGoodsCategoryList3(String code);
	
	public List<Map> getSeller(int sellernum);

	public int update(Seller seller);

}
