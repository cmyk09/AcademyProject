package com.ezen.seller;

import java.util.*;
import java.io.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.SessionAttribute;
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
	@GetMapping("/{sellernum}")
	public String index(@PathVariable int sellernum) //판매자 매니저 페이지(가장 기본 index) 페이지로 이동
	{
		return "ezen/seller/sellerManager";
	}

	@PostMapping("logout")
	@ResponseBody
	public Map logoutPost(@RequestParam String logout, SessionStatus status)
	{	
		status.setComplete();
		Map map = new HashMap<>();
		map.put("logout", true);
		return map;
	}
	
	//상품 관리-------------------------------------------------------------

	//주문 관리-------------------------------------------------------------
	@GetMapping("/order/orderList/{sellernum}")
	public String orderList(@SessionAttribute(value = "sellernum", required = false) int logininfo)//주문 목록/수정
	{
	
		return "ezen/order/orderList";
		
	}
	@GetMapping("/order/shipping/{sellernum}")
	public String shipping(@SessionAttribute(value = "sellernum", required = false) int logininfo)//발송 관리
	{

		return "ezen/order/shipping";
		
	}
	//정산 관리-------------------------------------------------------------
	@GetMapping("/calculate/incomechart/{sellernum}")
	public String incomechart(@SessionAttribute(value = "sellernum", required = false) int logininfo)//수익 통계
	{
	
		return "ezen/calculate/incomechart";
		
	}
	@GetMapping("/calculate/incomelist/{sellernum}")
	public String incomelist(@SessionAttribute(value = "sellernum", required = false) int logininfo)//입금 내역
	{
		
		return "ezen/calculate/incomelist";
	
	}
	//고객 관리-------------------------------------------------------------
	@GetMapping("/customer/QnAList/{sellernum}")
	public String QnAList(@SessionAttribute(value = "sellernum", required = false) int logininfo)//상품문의 목록
	{
		
		return "ezen/customer/QnAList";
		
	}
	@GetMapping("/customer/reviewlist/{sellernum}")
	public String reviewlist(@SessionAttribute(value = "sellernum", required = false) int logininfo)//리뷰 관리
	{
		
		return "ezen/customer/reviewlist";
		
	}
	//판매자 관리------------------------------------------------------------
    @GetMapping("/sellerinfo/selinfo/{sellernum}")
    public String selinfo(@PathVariable int sellernum, Model model)
    {
            Seller seller = svc.getSeller(sellernum);
            model.addAttribute("seller", seller);
            return "ezen/seller/sellerinfo/selinfo";
    }

    @PostMapping("/sellerinfo/selinfo")
    @ResponseBody
    public Map<String, Object> updateInfo(@ModelAttribute Seller seller)
    {
    	System.out.println(seller.getSellernum());
    	boolean updated = svc.update(seller);
        Map<String, Object> map = new HashMap<>();
        map.put("updated", updated);
        return map;
    }
    
    @PostMapping("/sellerinfo/pwdChange")
    @ResponseBody
    public Map pwdChange(@ModelAttribute Seller seller)
    {
    	boolean changing = svc.pwdChange(seller);
        Map<String, Object> map = new HashMap<>();
        map.put("changing", changing);
        return map;
    }
	
    @GetMapping("/sellerinfo/withdrawal/{sellernum}")
	public String withdrawal(@PathVariable int sellernum, Model model) {
	    Seller seller = svc.getSeller(sellernum);
	    model.addAttribute("seller", seller);
	    return "ezen/seller/sellerinfo/withdrawal";
	}

	 @PostMapping("/sellerinfo/lastRequest")
	 @ResponseBody
	 public Map lastRequest(withdrawalform wdf) {
		 boolean lastRequesting = svc.lastRequest(wdf);
		 Map map = new HashMap<>();
		 map.put("lastRequesting", lastRequesting);
		 return map;
	 }
	
	@PostMapping("/sellerinfo/withdrawal/")
	@ResponseBody
	public Map withdrawalPost(Seller seller)
	{
		boolean withdrawaled = svc.withdrawal(seller);
		Map map = new HashMap<>();
		map.put("withdrawaled", withdrawaled);
		return map;
	}
}
