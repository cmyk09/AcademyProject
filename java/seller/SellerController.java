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

import com.ezen.goods.GoodsMgtSVC;
import com.ezen.goods.OrderVO;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/ezen/sellerManager")
@SessionAttributes("sellernum")
public class SellerController
{
	@Autowired
	private SellerSvc svc;
	
	@Autowired
	private GoodsMgtSVC goodsSvc;
	
	@Autowired
	private OrderVO ovo;
	
	@Autowired
	@Qualifier("selldao")
	private SellerDAO dao;
	
	
	//메인 인덱스 화면--------------------------------------------------------
	@GetMapping("/")
	public String index(Model model, HttpSession session) //판매자 매니저 페이지(가장 기본 index) 페이지로 이동
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("seller", seller);
	    
		return "ezen/seller/sellerManager";
	}

	@PostMapping("logout")
	@ResponseBody
	public Map<String, Object> logoutPost(HttpSession session, SessionStatus sessionStatus) {
	    Map<String, Object> response = new HashMap<>();
	    try {
	        sessionStatus.setComplete(); // 현재 컨트롤러에서 사용하는 세션 속성 삭제
	        session.invalidate(); // 세션 무효화
	        response.put("success", true);
	    } catch (Exception e) {
	        response.put("success", false);
	    }
	    return response;
	}
	
	//상품 관리-------------------------------------------------------------
	@PostMapping("/addGoodsForm")
	public String addGoodsFormLink(Model model, HttpSession session)
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
		
	    model.addAttribute("seller", seller);
		
		List<Map> list1 = goodsSvc.getGoodsCategoryList1();
		System.out.println("goods: " + list1);
		
		model.addAttribute("list1", list1);
		return "ezen/goods/addGoodsForm";
	}
	
	@PostMapping("/goodsList")
	public String goodsListLink(Model model, HttpSession session)
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("seller", seller);
		
	    
		List<Map> goodsList = goodsSvc.goodsList(Integer.parseInt(session.getAttribute("sellernum").toString()));
		
		model.addAttribute("goodsList", goodsList);
		return "ezen/goods/goodsList";
	}
	//주문 관리-------------------------------------------------------------
	@PostMapping("/orderList")
	public String orderListLink(Model model, HttpSession session)
	{
		List<Map> order = svc.orderList(Integer.parseInt(session.getAttribute("sellernum").toString()));
		System.out.println(order);
		model.addAttribute("order", order);
		
		return "ezen/seller/order/orderlist";
	}
	
	@PostMapping("/getOrderStatus")
	@ResponseBody
	public Map<String, Object> getOrderStatus(@ModelAttribute OrderVO ovo)
	{
		String getOrderStatus = svc.getOrderStatus(ovo.getOrderNo());
        Map<String, Object> map = new HashMap<>();
        map.put("getOrderStatus", getOrderStatus);
        return map;
	}
	
	@PostMapping("/changeStatus")
	@ResponseBody
	public Map<String, Object> changeStatus(@ModelAttribute OrderVO ovo)
	{
		boolean statup = svc.changeStatus(ovo);
        Map<String, Object> map = new HashMap<>();
        map.put("statup", statup);
        return map;
	}

	//송장 정보 갱신용 맵핑
	@PostMapping("/shipinfo")
	@ResponseBody
	public Map<String, Object> shipinfo(@ModelAttribute OrderVO ovo)
	{
		boolean sInfoupdated = svc.shipinfo(ovo);
        Map<String, Object> map = new HashMap<>();
        map.put("sInfoupdated", sInfoupdated);
        return map;
	}
	//정산 관리-------------------------------------------------------------
	@PostMapping("/incomelist")
	public String incomelist(Model model, HttpSession session)
	{
		List<Map> incomelist = svc.incomelist(Integer.parseInt(session.getAttribute("sellernum").toString()));
		model.addAttribute("incomelist", incomelist);
		return "ezen/seller/calculate/incomelist";
	
	}
	//정산 관리내 검색 기능
	@PostMapping("/incomelistSearch/{page}/{category}/{keyword}/{startdate}/{enddate}")
	public String incomelistSearch(Model model, HttpSession session, @PathVariable("page") int page, @PathVariable("category") String category,
			@PathVariable("keyword") String keyword, @PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate)
	{
		System.out.println("page : " + page);
		int sellerno = Integer.parseInt(session.getAttribute("sellernum").toString());
		
		int pageNum = page;
		int pageSize = 10;
		
		System.out.println("category : " + category);
		System.out.println("startdate : " + startdate);
		System.out.println("enddate : " + enddate);
		System.out.println("keyword : " + keyword);
		System.out.println("pageNum : " + pageNum);
		System.out.println("pageSize : " + pageSize);
		
		PageInfo<Map> incomelistSearch = svc.incomelistSearch(sellerno, category, startdate, enddate, keyword, pageNum, pageSize);
		model.addAttribute("incomelistSearch", incomelistSearch);
		
		model.addAttribute("pageSize", pageSize);
		model.addAttribute("pageNum",pageNum);
		model.addAttribute("category", category);
		model.addAttribute("startdate", startdate);
		model.addAttribute("enddate", enddate);
		model.addAttribute("keyword", keyword);
		
		System.out.println("incomelistSearch : " + incomelistSearch);
		 
		return "ezen/seller/calculate/incomelistSearch";
	}
	
	//고객 관리-------------------------------------------------------------
	@PostMapping("/QnAList")
	public String QnAList(Model model, HttpSession session)//상품문의 목록
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
        model.addAttribute("seller", seller);
        
		return "ezen/seller/custom/QnAList";
		
	}
	@PostMapping("/reviewlist")
	public String reviewlist(Model model, HttpSession session)//리뷰 관리
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
        model.addAttribute("seller", seller);
		return "ezen/seller/custom/reviewlist";
		
	}
	//판매자 관리------------------------------------------------------------
    @PostMapping("/selinfo")
    public String selinfo(Model model, HttpSession session)
    {
            SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
            model.addAttribute("seller", seller);
            return "ezen/seller/sellerinfo/selinfo";
    }

    @PostMapping("/sellerinfo/selinfo")
    @ResponseBody
    public Map<String, Object> updateInfo(@ModelAttribute SellerVO seller)
    {
    	System.out.println(seller.getSellernum());
    	boolean updated = svc.update(seller);
        Map<String, Object> map = new HashMap<>();
        map.put("updated", updated);
        return map;
    }
    
    @PostMapping("/sellerinfo/pwdChange")
    @ResponseBody
    public Map pwdChange(@ModelAttribute SellerVO seller)
    {
    	boolean changing = svc.pwdChange(seller);
        Map<String, Object> map = new HashMap<>();
        map.put("changing", changing);
        return map;
    }
	
    @PostMapping("/withdrawal")
	public String withdrawal(HttpSession session, Model model) {
	    SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
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
	
	@PostMapping("/sellerinfo/withdrawal")
	@ResponseBody
	public Map withdrawalPost(SellerVO seller)
	{
		boolean withdrawaled = svc.withdrawal(seller);
		Map map = new HashMap<>();
		map.put("withdrawaled", withdrawaled);
		return map;
	}
}
