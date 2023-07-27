package com.ezen.goods;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RequestMapping("/ezen/goods")
@Controller
@SessionAttributes("uid")
public class GoodsController 
{
	@Autowired
	private GoodsMgtSVC SVC;
	
	@Autowired
	private GoodsVO goods;

	// 초기 goodsIndex Form 호출
	@GetMapping("/")
	public String index(Model model)
	{
		model.addAttribute("userid","smith");
		return "ezen/goods/goodsIndex";
	}
	
	// 상품추가 폼 호출
	@GetMapping("/addGoodsForm")
	public String addGoodsForm(Model model)
	{
		List<Map> list1 = SVC.getGoodsCategoryList1();
		
		System.out.println("userid" + model.getAttribute("userid"));
		model.addAttribute("list1", list1);
		return "ezen/goods/addGoodsForm";
	}
	
	// 상품추가 품의 카테고리 선택시(2차 카테고리)
	@PostMapping("/scd")
	@ResponseBody
	public Map selectScd(@RequestParam("key") String code)
	{		
		System.out.println("1차 code : "+code);
		List<Map> list2 = SVC.getGoodsCategoryList2(code); 	
		
		System.out.println("2차 리스트 : "+list2);
		
		Map map = new HashMap<>();
		map.put("selected", true);
		map.put("list2", list2);
		return map;
	}
	
	// 상품추가 품의 카테고리 선택시(3차 카테고리)
	@PostMapping("/thd")
	@ResponseBody
	public Map selectThd(@RequestParam("key") String code)
	{		
		System.out.println("3차 code : "+code);
		List<Map> list3 = SVC.getGoodsCategoryList3(code); 	
		Map map = new HashMap<>();
		map.put("selected", true);
		map.put("list3", list3);
		return map;
	}
	
	// 상품추가 (상품추가 폼의 상품추가 버튼 클릭 시)
	@PostMapping("/addGoods")
	@ResponseBody
	public Map<String, Boolean> addBoard(Model model,
			GoodsVO goods,
			@RequestParam( value ="files1", required=false) MultipartFile mfile1,
			@RequestParam( value ="files2", required=false) MultipartFile mfile2,
			@RequestParam( value ="files3", required=false) MultipartFile mfile3,
			@RequestParam( value ="files4", required=false) MultipartFile mfile4,
			@RequestParam( value ="files5", required=false) MultipartFile mfile5,
			@RequestParam( value ="files6", required=false) MultipartFile mfile6,
			HttpServletRequest request)
	{
		System.out.println("상품추가 시작 name : " + goods.toString());
		System.out.println("상품추가 시작 fname 1: " + mfile1.getOriginalFilename());
		System.out.println("상품추가 시작 fname 2: " + mfile2.getOriginalFilename());
		System.out.println("상품추가 시작 fname 3: " + mfile3.getOriginalFilename());
		System.out.println("상품추가 시작 fname 4: " + mfile4.getOriginalFilename());
		System.out.println("상품추가 시작 fname 5: " + mfile5.getOriginalFilename());
		System.out.println("상품추가 시작 fname 6: " + mfile6.getOriginalFilename());
		
		boolean added = SVC.addGoods(goods, mfile1, mfile2, mfile3, mfile4, mfile5, mfile6, request);
		
		Map<String, Boolean> map = new HashMap<>();
		map.put("added", added);
		return map;
	}
	
	// 상품상세 폼 호출
	@GetMapping("/detailGoodsForm/{goodsno}")
	public String detailGoodsForm(Model model, @PathVariable int goodsno)
	{
		//System.out.println("userid" + model.getAttribute("userid"));
		List<Map> list = SVC.getGoodsCategoryListAll();		
		model.addAttribute("list", list);
		System.out.println("Ctl_detailGoodsForm" +list);
		
		Map map = SVC.detailGoodsForm(goodsno);
		
		System.out.println(map);
		model.addAttribute("map", map);
		return "ezen/goods/detailGoodsForm";
	}
	

	// 상품상세 품의 카트에 담기 버튼 클릭시
	@PostMapping("/putInCart")
	@ResponseBody
	public Map putInCart(CartVO cart)
	{	
		boolean putInCart = false;
		System.out.println("cart : "+cart);
		
		putInCart = SVC.putInCart(cart);
		
		Map map = new HashMap<>();
		map.put("putInCart", putInCart);
		return map;
	}
	
	// 상품 업데이트 폼 호출
	@GetMapping("/updateGoodsForm/{goodsno}")
	public String updateGoodsForm(Model model, @PathVariable int goodsno)
	{
		//System.out.println("userid" + model.getAttribute("userid"));
		Map map = SVC.updateGoodsForm(goodsno);
		List<Map> list1 = SVC.getGoodsCategoryList1();	
			
		System.out.println("업데이트 폼 로드 : "+ map);
		model.addAttribute("map", map);
		model.addAttribute("list1", list1);
		return "ezen/goods/updateGoodsForm";
	}
	
	// 상품 업데이트 폼의 업데이트 버튼 클릭시
	@PostMapping("/updateGoods")
	@ResponseBody
	public Map<String, Boolean> updateGoods(Model model,
			GoodsVO goods, GoodsImgFileName imgFileName,
			@RequestParam( value ="files1", required=false) MultipartFile mfile1,
			@RequestParam( value ="files2", required=false) MultipartFile mfile2,
			@RequestParam( value ="files3", required=false) MultipartFile mfile3,
			@RequestParam( value ="files4", required=false) MultipartFile mfile4,
			@RequestParam( value ="files5", required=false) MultipartFile mfile5,
			@RequestParam( value ="files6", required=false) MultipartFile mfile6,
			HttpServletRequest request)
	{		
		boolean updateed = SVC.updateGoods(goods, mfile1, mfile2, mfile3, mfile4, mfile5, mfile6, 
				imgFileName, request);
		
		Map<String, Boolean> map = new HashMap<>();
		map.put("updateed", updateed);
		return map;
	}
	 
	// 상품 업데이트 폼의 업데이트 버튼 클릭시
	@PostMapping("/deleteGoodsImg")
	@ResponseBody
	public Map deleteGoodsImg(Model model,
			@RequestParam("goodsno") int goodsno,
			@RequestParam("fileno") int fileno,
			@RequestParam("name") String name,
			@RequestParam("fakeName") String fakeName,
			HttpServletRequest request)
	{		
		System.out.println("Ctl_deleteGoodsImg_변수 : " + goodsno +" / "+ fileno +" / "+ name +" / "+ fakeName );
		boolean deleteFile = SVC.deleteFile(goodsno, fileno, name, fakeName,  request);
		
		Map map = new HashMap<>();
		map.put("deleteFile", deleteFile);
		map.put("goodsno", goodsno);
		return map;
	}
	
	// 카트 폼 호출
	@GetMapping("/cartList/{mnum}")
	public String cartList(Model model, @PathVariable int mnum)
	{
		int userno = mnum;
		List<Map> cart = SVC.getCartList(userno);
		
		System.out.println("Ctl_cartList_cart" + cart);
		model.addAttribute("cart", cart);
		return "ezen/goods/cartList";
	}
	
	// 카트에서 구매하기 클릭
	@PostMapping("/purchase")
	@ResponseBody
	public Map purchase(Model model,@RequestParam("checked") int[] value, @SessionAttribute(value = "uid", required = false) Integer uid)
	{	
		/*  데이터 검증용
		System.out.println("value cnt : "+value.length);
		for(int i=0;i<value.length;i++)
		{
			System.out.println("Ctl_purchase_codes : " + value[i]  );
		}
		System.out.println("Ctl_purchase_uid : " + uid  );
		*/
		
		
		int userno = uid;
		boolean purchase = SVC.purchase(userno, value);
		
		Map map = new HashMap<>();
		map.put("purchase", purchase);
		return map;
	}
	
	// 오더 폼 호출
	@GetMapping("/orderList/{mnum}")
	public String orderListMember(Model model, @PathVariable int mnum)
	{
		int userno = mnum;
		List<Map> orderList = SVC.getOrderListMember(userno);
		
		System.out.println("Ctl_orderListMember_order" + orderList);
		model.addAttribute("orderList", orderList);
		return "ezen/goods/orderList";
	}
	
	
	// 메인 폼 호출(jsp)
	@GetMapping("/main")
	public String viewMain2(Model model)
	{
		List<Map> list = SVC.getGoodsCategoryListAll();
		List<Map> goods = SVC.getGoodsMain();
		
		System.out.println("Ctl_viewMain_list" +list);
		model.addAttribute("list", list);
		model.addAttribute("goods", goods);
		return "main";
	}
	
	// 서치 폼 호출
	@GetMapping("/search/{keyword}/{categoryLv}/{categoryCode}/{page}")
	public String searchList(Model model, @PathVariable("keyword") String keyword, @PathVariable("categoryLv") int lvl,
											@PathVariable("categoryCode") String code, @PathVariable("page") int page)
	{
		
		System.out.println("Ctl_searchList_parm : keyword-" + keyword +" / lvl-" + lvl + " / code-" + code + " / page-" + page);
		
		List<Map> list = SVC.getGoodsCategoryListAll();
		
		int pageNum = page;
		int pageSize = 8;
		
		PageInfo<Map> pageInfo = SVC.getGoodsSearch(pageNum, pageSize, keyword, lvl, code);
		
		model.addAttribute("list", list);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("keyword", keyword);
		model.addAttribute("lvl", lvl);
		model.addAttribute("code", code);
		return "search";
	}
}






