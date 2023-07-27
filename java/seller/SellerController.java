package com.ezen.seller;

import java.util.*;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.ezen.account.Seller;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/sellerManager")
@SessionAttributes("sellernum")
public class SellerController
{
	@Autowired
	private SellerSvc svc;
	
	@Autowired
	@Qualifier("selldao")
	private SellerDAO dao;
	
	//메인 인덱스 화면--------------------------------------------------------
	@GetMapping("/")
	public String index() //판매자 매니저 페이지(가장 기본 index) 페이지로 이동
	{
		return "ezen/sellerManager/sellerManager";
	}

	@PostMapping("logout")
	@ResponseBody
	public Map logoutPost(SessionStatus status)
	{	
		status.setComplete();
		Map map = new HashMap<>();
		map.put("logout", true);
		return map;
	}
	
	//상품 관리-------------------------------------------------------------

	//주문 관리-------------------------------------------------------------
	@GetMapping("/order/orderList")
	public String orderList()//주문 목록/수정
	{
		return "ezen/order/orderList";
	}
	@GetMapping("/order/shipping")
	public String shipping()//발송 관리
	{
		return "ezen/order/shipping";
	}
	//정산 관리-------------------------------------------------------------
	@GetMapping("/calculate/incomechart")
	public String incomechart()//수익 통계
	{
		return "ezen/calculate/incomechart";
	}
	@GetMapping("/calculate/incomelist")
	public String incomelist()//입금 내역
	{
		return "ezen/calculate/incomelist";
	}
	//고객 관리-------------------------------------------------------------
	@GetMapping("/customer/QnAList")
	public String QnAList()//상품문의 목록
	{
		return "ezen/customer/QnAList";
	}
	@GetMapping("/customer/reviewlist")
	public String reviewlist()//리뷰 관리
	{
		return "ezen/customer/reviewlist";
	}
	@GetMapping("/customer/blackConsumer")
	public String blackConsumer()//판매 방해ID 관리
	{
		return "ezen/customer/blackConsumer";
	}
	//판매자 관리------------------------------------------------------------
	@GetMapping("/sellerinfo/selinfo")
	public String selinfo(@PathVariable int sellernum, Model model) //판매자 정보 수정(seller 정보에 대한 edit(Update) 기능)
	{
		Seller seller = svc.getSeller(sellernum);
		model.addAttribute("seller", seller);
		return "ezen/sellerManager/selinfo";
	}
	
	@PostMapping("/sellerinfo/selinfo")
	@ResponseBody
	public Map<String,Object> updateInfo(Seller seller)
	{
		boolean updated = dao.update(seller);
		Map<String,Object> map = new HashMap<>();
		map.put("updated", updated);
		return map;
	}
	
	@GetMapping("/sellerinfo/wirhdrawal")
	public String wirhdrawal()//판매자 탈퇴
	{
		return "ezen/sellerManager/wirhdrawal";
	}
	
	/*
	@PostMapping("/sellerinfo/wirhdrawal")
	public Map withdrawalPost(Seller seller)
	{
		boolean withdrawaled = svc.withdrawal(seller);
		Map map = new HashMap<>();
		map.put("withdrawaled", withdrawaled);
		return map;
	}
	*/
}
