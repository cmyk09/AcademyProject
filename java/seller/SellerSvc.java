package com.ezen.seller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ezen.account.Seller;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SellerSvc {

	@Autowired
	@Qualifier("selldao")
	private SellerDAO dao;
	
	public Seller getSeller(int sellernum) {
		List<Map> list = dao.getSeller(sellernum);
		Seller sel = getSellerIn(list.get(0));
		return sel;
	}

	private Seller getSellerIn(Map map) {
		int sellernum = Integer.parseInt(map.get("sellernum").toString());
		String officeName = map.get("officeName").toString();
		String sellerName = map.get("sellerName").toString();
		String offRegiNum = map.get("offRegiNum").toString();
		String officeAdd = map.get("officeAdd").toString();
		String busnessNum = map.get("busnessNum").toString();
		String busnessOcc = map.get("busnessOcc").toString();
		String busnessCate = map.get("busnessCate").toString();
		
		String officeEmail = map.get("officeEmail").toString();
		String officePhone = map.get("officePhone").toString();
		String officeFax = map.get("officeFax").toString();
		
		Seller sel = new Seller();
		sel.setSellernum(sellernum);
		sel.setOfficeName(officeName);
		sel.setSellerName(sellerName);
		sel.setOffRegiNum(offRegiNum);
		sel.setOfficeAdd(officeAdd);
		sel.setBusnessNum(busnessNum);
		sel.setBusnessOcc(busnessOcc);
		sel.setBusnessCate(busnessCate);
		
		sel.setOfficeEmail(officeEmail);
		sel.setOfficePhone(officePhone);
		sel.setOfficeFax(officeFax);
		
		return sel;
	}

}
