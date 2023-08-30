package com.ezen.account;

import java.util.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import com.ezen.seller.SellerVO;

@Component
@Mapper
public interface AccountMapper 
{

	public List<SellerVO> sellerIdDuplicate(String adminId);

	public SellerVO sellerLogin(SellerVO seller);

	public int getsellernumByID(String adminId);

	public int sellerSignup(SellerVO seller);

	public int sellernumGet(SellerVO seller);

	public String sellerIdFinder(String officeEmail, String officePhone);

	public String sellerPwdFinder(String adminId, String officeEmail, String officePhone);

}
