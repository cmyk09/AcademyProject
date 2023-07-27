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
	public Seller getSeller(int sellernum);

	public int update(Seller seller);

	public int withdrawal(Seller seller);

	public int pwdChange(Seller seller);

	public int lastRequest(withdrawalform wdf);

}
