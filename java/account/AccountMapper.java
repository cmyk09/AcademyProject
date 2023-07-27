package com.ezen.account;

import java.util.*;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Component
@Mapper
public interface AccountMapper 
{
	
	public Seller loginMap(Seller seller);

	public int signinMap(Seller seller);

	public List<Seller> idDuplicate(String adminId);

	public int getsellernum(Seller seller);

	public String getadminId(Seller seller);
}
