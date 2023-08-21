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
	public Map logoutPost(@RequestParam String logout, SessionStatus status)
	{	
		status.setComplete();
		Map map = new HashMap<>();
		map.put("logout", true);
		return map;
	}
	
	//상품 관리-------------------------------------------------------------
	@PostMapping("/addGoodsForm")
	public String addGoodsFormLink(Model model, HttpSession session)
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("seller", seller);
		
		List<Map> addGoodsForm = goodsSvc.getGoodsCategoryList1();
		
		model.addAttribute("addGoodsForm", addGoodsForm);
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
		model.addAttribute("order", order);
		return "ezen/seller/order/orderlist";
	}

	@PostMapping("/shipping")
	public String shippingLink(Model model, HttpSession session)
	{
	    List<Map> order = svc.orderList(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("order", order);
		return "ezen/seller/order/shipping";
	}
	
	/*
	 * @GetMapping("/order/shipping/{sellernum}") public String shipping(Model
	 * model, @PathVariable int sellernum)//발송 관리 { List<Map> goods =
	 * svc.orderList(sellernum);
	 * 
	 * System.out.println("Ctl_goodsList_goods" +goods); model.addAttribute("goods",
	 * goods); return "ezen/seller/order/shipping"; }
	 */
	/*
	 * @PostMapping("/order/shipping")
	 * 
	 * @ResponseBody public Map shippingPost(@RequestParam Map shipInfo) { Map map =
	 * svc.shipping(shipInfo); return map; }
	 */
	//정산 관리-------------------------------------------------------------
	@PostMapping("/incomechart")
	public String incomechart(Model model, HttpSession session)
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("seller", seller);
		return "ezen/seller/calculate/incomechart";
	}

	@PostMapping("/incomelist")
	public String incomelist(Model model, HttpSession session)
	{
		SellerVO seller = svc.getSeller(Integer.parseInt(session.getAttribute("sellernum").toString()));
	    model.addAttribute("seller", seller);
		return "ezen/seller/calculate/incomelist";
	
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
