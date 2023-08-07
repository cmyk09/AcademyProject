package com.ezen.seller;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

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

	public Map shipping(Map shipInfo);
}
