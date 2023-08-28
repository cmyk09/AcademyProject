package com.ezen.goods;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;

@Service
public class GoodsMgtSVC 
{
	
	@Autowired
	private GoodsMgtDAO DAO;
	
	// 1차 카테고리 리스트 호출용 쿼리 호출
	public List<Map> getGoodsCategoryList1() 
	{
		List<Map> map =DAO.getGoodsCategoryList1();
		return map;
	}

	// 2차 카테고리 리스트 호출용 쿼리 호출
	public List<Map> getGoodsCategoryList2(String code) 
	{
		List<Map> map =DAO.getGoodsCategoryList2(code);
		return map;
	}

	// 3차 카테고리 리스트 호출용 쿼리 호출
	public List<Map> getGoodsCategoryList3(String code) 
	{
		List<Map> map =DAO.getGoodsCategoryList3(code);
		return map;
	}

	// 상품 추가 루틴
	@Transactional
	public boolean addGoods(GoodsVO goods, MultipartFile mfile1, MultipartFile mfile2, MultipartFile mfile3,
			MultipartFile mfile4, MultipartFile mfile5, MultipartFile mfile6, HttpServletRequest request) 
	{
		boolean added = false;
		// 1.Img 저장
		List<GoodImage> imgList = addGoodsImgFile(mfile1, mfile2, mfile3, mfile4, mfile5, mfile6, request);
		
		// 2.DB 저장
		// 2-0. goodsVO 빈값 채우기
		goods.setGoodsStatus("I");   // 상품이 등록만 된 상태임을 나타냄
				
	 	Date d = new Date();
	 	long dt = d.getTime();
		java.sql.Date inDate = new java.sql.Date(dt);
		
		goods.setGoodsRegistryDay(inDate);
		
		System.out.println("SVC_addGoods_ellerCode : " + goods.getSellerCode());
		
		// 2-1. goods 테이블에 저장
		int r1 = DAO.addGoods(goods);
		
		// 2-2. goods_img_list 테이블에 저장
		Map imgMap = new HashMap<>();
		
		for (int i=0 ; i<imgList.size(); i++)
		{
			String imgName = imgList.get(i).getImgFileName();
			String imgFakeName = imgList.get(i).getImgFakeFileName();
			
			if (i == 0)
			{
				imgMap.put("Goodsno", goods.getGoodsno());
				imgMap.put("imgName1", imgName);
				imgMap.put("imgFakeName1", imgFakeName);
			}else if (i == imgList.size()-1)
			{
				imgMap.put("imgName6", imgName);
				imgMap.put("imgFakeName6", imgFakeName);
			}else
			{
				String name = "imgName" +(i+1);
				String fakeName = "imgFakeName" +(i+1);
				
				System.out.println("SVC_addGoods_name : " + name);
				System.out.println("SVC_addGoods_fakeName : " + fakeName);
				
				imgMap.put(name, imgName);
				imgMap.put(fakeName, imgFakeName);
			}
			
		}
		int r2 = DAO.saveGoodsImgFile(imgMap);
		
		System.out.println("SVC_addGoods_r 값 - r1 : "+r1 + " / r2 : "+r2);
		
		if ((r1 + r2) > 1) added = true;
		return added;
	}

	// 이미지 파일 저장용 루틴
	private List<GoodImage> addGoodsImgFile(MultipartFile mfile1, MultipartFile mfile2, MultipartFile mfile3,
			MultipartFile mfile4, MultipartFile mfile5, MultipartFile mfile6, HttpServletRequest request) 
	{   
	    List<MultipartFile> mfiles = new ArrayList<>();
	    mfiles.add(mfile1) ;
	    mfiles.add(mfile2) ;
	    mfiles.add(mfile3) ;
	    mfiles.add(mfile4) ;
	    mfiles.add(mfile5) ;	    
	    mfiles.add(mfile6) ;
	    
	    List<GoodImage> list = null;
	    	
	    	for (int i=0; i<mfiles.size(); i++)
	    	{
	    		if(mfiles.get(i).getSize()==0) continue;

	            if (list == null) list = new ArrayList<>();
	            
	            GoodImage img = saveImgFile(mfiles.get(i), request);
  
	            System.out.println("files : " + img.toString());
	            list.add(img);
	    	}
		return list;
	}

	// 상품 상세 호출용 쿼리 호출
	public Map detailGoodsForm(int goodsno) 
	{
		Map map = DAO.detailGoodsForm(goodsno);
		return map;
	}

	// 상품 업데이트 호출용 쿼리 호출
	public Map updateGoodsForm(int goodsno) 
	{
		Map map = DAO.updateGoodsForm(goodsno);
		return map;
	}

	// 상품 업데이트 루틴
	public boolean updateGoods(GoodsVO goods, MultipartFile mfile1, MultipartFile mfile2, MultipartFile mfile3,
			MultipartFile mfile4, MultipartFile mfile5, MultipartFile mfile6, GoodsImgFileName imgFileName, HttpServletRequest request) 
	{
		System.out.println("SVC_updateGoods_goodVO : "+goods);
		System.out.println("SVC_updateGoods_GoodsImgFileName : "+imgFileName);
		boolean updated = false;
		// 1. Img 파일 저장
		//    : 이 경우 4가지로 분류 되는데
		//      1) mfile 객체가 null 인 경우와 아닌 경우 만약 null 이 아니면 해당 mfile를 파일로 저장
		//      2) mfile 객체가 null 이면서 nameOfImg 이 'X' 가 아닌 경우 이경우는 기존 파일이 있으므로 새로 파일을 저장하지 않음.
	    //      3) 여기서 Goods_img_list 테이블에 넣을 값도 만든다.(SQL 구문)
		
		String strSet = "";
		List<GoodImage> imgList = new ArrayList<>();
	    for (int i=0 ; i <= 5; i++)
	    {
	    	MultipartFile mfile = null;
	    	String nameOfImg = "";
	    	String fakeNameOfImg = "";
	    	switch (i)
	    	{
	    		case 0 : mfile = mfile1; nameOfImg=imgFileName.getNameOfImg1(); fakeNameOfImg=imgFileName.getFakeNameOfImg1();  break;
	    		case 1 : mfile = mfile2; nameOfImg=imgFileName.getNameOfImg2(); fakeNameOfImg=imgFileName.getFakeNameOfImg2();  break;
	    		case 2 : mfile = mfile3; nameOfImg=imgFileName.getNameOfImg3(); fakeNameOfImg=imgFileName.getFakeNameOfImg3();  break;
	    		case 3 : mfile = mfile4; nameOfImg=imgFileName.getNameOfImg4(); fakeNameOfImg=imgFileName.getFakeNameOfImg4();  break;
	    		case 4 : mfile = mfile5; nameOfImg=imgFileName.getNameOfImg5(); fakeNameOfImg=imgFileName.getFakeNameOfImg5();  break;
	    		case 5 : mfile = mfile6; nameOfImg=imgFileName.getNameOfImg6(); fakeNameOfImg=imgFileName.getFakeNameOfImg6();  break;
	    		default : 
	    	}
	    	
	    	System.out.println("SVC_updateGoods_순번"+i + " : "+mfile);
	    	System.out.println("SVC_updateGoods_순번"+i + " : "+nameOfImg);
	    	System.out.println("SVC_updateGoods_순번"+i + " : "+fakeNameOfImg);
	    	
	    	GoodImage img =repeatImgFileChk(mfile, nameOfImg, fakeNameOfImg, request);
	    	
	    	System.out.println("SVC_updateGoods_img"+i + " : "+img);
	    	imgList.add(img);
	    }
	    
	    System.out.println("SVC_updateGoods_imgList  : "+imgList);
	    System.out.println("SVC_updateGoods_imgList cnt : "+imgList.size());
	    
	    // 2. Goods DB 업데이트
	    int r1 = DAO.updateGoods(goods);
	    System.out.println("SVC_updateGoods_goods  : "+goods);
	    System.out.println("SVC_updateGoods_updateGoods  : "+r1);
	    
	    // 3. Goods_Img_list DB 업데이트
	    Map imgMap = new HashMap<>();
		
		for (int i=0 ; i<imgList.size(); i++)
		{			
			if (i == 0)
			{
				imgMap.put("Goodsno", goods.getGoodsno());
				imgMap.put("ImgFileName1", imgList.get(i).getImgFileName());
				imgMap.put("ImgFileFakeName1", imgList.get(i).getImgFakeFileName());
			}else if (i == imgList.size()-1)
			{
				imgMap.put("ImgFileName6", imgList.get(i).getImgFileName());
				imgMap.put("ImgFileFakeName6", imgList.get(i).getImgFakeFileName());
			}else
			{
				String name = "ImgFileName" +(i+1);
				String fakeName = "ImgFileFakeName" +(i+1);
				
				if (imgList.get(i) == null) 
				{
					System.out.println(i);
					System.out.println(name);
					System.out.println(fakeName);
					imgMap.put(name, "");
					imgMap.put(fakeName, "");
				}else
				{
					imgMap.put(name, imgList.get(i).getImgFileName());
					imgMap.put(fakeName, imgList.get(i).getImgFakeFileName());
				}
				
			}
		}
		
		System.out.println(imgMap);
		int r2 = DAO.updateGoodsImgFile(imgMap);
			
		System.out.println("SVC_updateGoods_r 값 - r1 : "+r1 + " / r2 : "+r2);
		
		if ((r1 + r2) > 1) updated = true;
		
		
		return updated;
	}
	
	// 상품 업데이트시 파일들 정보 가공을 위한 루틴
	public GoodImage repeatImgFileChk(MultipartFile mfile, String nameOfImg, String fakeNameOfImg, HttpServletRequest request)
	{
		if (mfile == null)
	    {
	    	if (nameOfImg == "X")
	    	{
	    		return null;
	    	}else
	    	{
	    		GoodImage img = new GoodImage();
	    		img.setImgFileName(nameOfImg);
	    		img.setImgFakeFileName(fakeNameOfImg);
	    		return img;
	    	}
	    }else
	    {
	    	String OriginalFilename = mfile.getOriginalFilename();
	    	if (OriginalFilename != "")
	    	{
	    		return saveImgFile(mfile, request);
	    	}else
	    	{
	    		return null;
	    	}
	    }	
		
	
		
	}
	
	// 이미지 파일 폴더에 생성용 루틴
	public GoodImage saveImgFile(MultipartFile mfile, HttpServletRequest request )
	{
		ServletContext context = request.getServletContext();
	    String savePath = context.getRealPath("/images/goods");
	    
		 UUID  uuid = UUID.randomUUID();
	     String uuidStr = uuid.toString();
	     
	     String OriginalFilename = mfile.getOriginalFilename();
	     
         String[] token = OriginalFilename.split("\\.");
	     String f1 = token[0];
	     String f2 = token[1];
	         
	     String FakeFileName = f1 + "_" + uuidStr + "." + f2;
	         		         
	     try {
			mfile.transferTo(new File(savePath+"/"+FakeFileName));
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

         //MultipartFile 주요 메소드
         String cType = mfile.getContentType();         
         Resource res = mfile.getResource();
         long fSize = mfile.getSize();
         boolean empty = mfile.isEmpty();
         GoodImage img = new GoodImage();
         img.setImgFileName(OriginalFilename);
         img.setImgFakeFileName(FakeFileName); 
         img.setImgSize(fSize);
         img.setImgType(cType);
         
		return img;
	}

	public boolean deleteFile(int goodsno, int fileno, String name, String fakeName, HttpServletRequest request) 
	{
		boolean deleted = false;
		// 1. DB 업데이트 (goods_img_list 테이블의 해당 컬럼의 파일 명을 삭제-업데이트 한다.)
		Map imgMap = new HashMap<>();
		imgMap.put("goodsno", goodsno);
		imgMap.put("fileno", fileno);
		
		System.out.println("SVC_deleteFile_변수 : goodsno - "+imgMap.get("goodsno") + " / fileno - "+imgMap.get("fileno"));
		
		int r1 = DAO.deleteGoodsImgFile(imgMap);
		
		// 2. 파일 삭제
		ServletContext context = request.getServletContext();
	    String savePath = context.getRealPath("/images/goods");
		String fname = fakeName;
		File delFile = new File(savePath, fname);
		boolean delF = delFile.delete();	
		
		if (r1 > 0 && delF == true) deleted = true;
		return deleted;
	}

	public boolean putInCart(CartVO cart) 
	{			
		Date d = new Date();
	 	long dt = d.getTime();
		java.sql.Date inDate = new java.sql.Date(dt);
		
		cart.setCartRegistryDay(inDate);
		
		int r = DAO.putInCart(cart); 
		if (r > 0) return true;
		return false;
	}

	public List<Map> getCartList(int userno) 
	{
		List<Map> cart = DAO.getCartList(userno);
		return cart;
	}

	@Transactional
	public boolean purchase(int userno, int[] value) 
	{
		System.out.println("SVC_purchase_userno : "+userno);
		// 1. orderNo 만들기
		int orderNo = DAO.getOrderNo();
		if (orderNo == 0) orderNo = 1;
		else orderNo += 1;
		
		System.out.println("SVC_purchase_orderNo : "+orderNo);
		
		// 2. 입력일 만들기
		Date d = new Date();
	 	long dt = d.getTime();
		java.sql.Date inDate = new java.sql.Date(dt);
		
		// 3. 상태 값 만들기
		String status = "payCompl";
		
		// 4. Cart 값 orderVO에 가져오기
		List<OrderVO> orderList = new ArrayList<>();
		List<CartVO> cartList = new ArrayList<>();
		for(int i = 0 ; i < value.length ; i++)
		{
			Map map = new HashMap<>();
			map.put("userno", userno);
			map.put("cartno", value[i]);
			OrderVO orderVO = DAO.getCartOrderList(map);
			orderVO.setOrderNo(orderNo);
			orderVO.setOrderRegistryDay(inDate);
			orderVO.setOrderStatus(status);
			
			CartVO cartVO = new CartVO();
			cartVO.setCartno(value[i]);
			
			System.out.println("SVC_purchase_orderVO : "+orderVO);
			
			orderList.add(orderVO);
			cartList.add(cartVO);
		}
				
		// 5. order Table 에 저장
		boolean saveChk = false;
		int r1 = DAO.addOrder(orderList);
		
		System.out.println("SVC_purchase_r1 : "+r1);
		
		if (value.length == r1) saveChk = true;
		
		
		// 6. cart Table 삭제
		boolean deleteChk = false;
		int r2 = 0;
		for(int i = 0 ; i < cartList.size(); i++)
		{
			int r = DAO.deletedCart(cartList.get(i));
			r2 += r;
		}
		
		// 7. goods 테이블 재고수량 업데이트
		for (int i = 0 ; i < orderList.size(); i++)
		{
			System.out.println("SVC_purchase_orderList : "+orderList.get(i));
			
			Map map = new HashMap<>();
			map.put("goodsNo", orderList.get(i).getGoodsno());
			map.put("goodsSaleQty", orderList.get(i).getGoodsSaleQty());
			int r = DAO.updateGoodsQty(map);
		}
		
			
		System.out.println("SVC_purchase_r2 : "+r2);
		
		if (cartList.size() == r2) deleteChk = true;

		if (saveChk == true && deleteChk == true) return true;
		
		return false;
	}

	public List<Map> getOrderListMember(int userno) 
	{
		List<Map> orderList = DAO.getOrderListMember(userno);
		return orderList;
	}

	public List<Map> getGoodsCategoryListAll() 
	{
		List<Map> list = DAO.getGoodsCategoryListAll();
		return list;
	}

	public List<Map> getGoodsMain() 
	{
		List<Map> list = DAO.getGoodsMain();
		return list;
	}

	public PageInfo<Map> getGoodsSearch(int pageNum, int pageSize, String keyword, int lvl, String code) 
	{
		PageHelper.startPage(pageNum, pageSize);
		PageInfo<Map> pageInfo = null;
		
		if (keyword.equals("N")) 
		{
			String srch = "";
			if (lvl == 1) srch = code.substring(0, 2);
			else if (lvl == 2) srch = code.substring(0, 4);
			else srch = code;
			
			System.out.println("SVC_getGoodsSearch_srch : "+srch);
			
			Map map = new HashMap<>();
			map.put("lvl", lvl);
			map.put("code", srch);
			pageInfo = new PageInfo<>(DAO.getGoodsSearchCategory(map));			
		}
		else 
		{
			String key = "%"+keyword+"%";
			pageInfo = new PageInfo<>(DAO.getGoodsSearchKeyword(key));
		}
		
		return pageInfo;
	}

	public List<Map> goodsList(int sellernum) 
	{
		return DAO.goodsList(sellernum);
	}

	public boolean endSale(int goodsno) 
	{
		return DAO.endSale(goodsno);
	}

	public boolean startSale(int goodsno) 
	{
		return DAO.startSale( goodsno) ;
	}

	public boolean changeSaleQty(int cartno, int qty) 
	{
		boolean changeSaleQty = false;
		Map map = new HashMap<>();
		map.put("cartno", cartno);
		map.put("goodsSaleQty", qty);
		int r = DAO.changeSaleQty(map);
		if ( r > 0 ) changeSaleQty = true;
		return  changeSaleQty ;
	}

	public boolean deleteGoods(int goodsno, int sno) 
	{
		boolean deleteGoods = false;
		
		Date d = new Date();
		long dt = d.getTime();
		java.sql.Date delDate = new java.sql.Date(dt);
		
		Map map = new HashMap<>();
		map.put("goodsno", goodsno);
		map.put("goodsno", goodsno);
		map.put("deleteDay", delDate);
		
		int r = DAO.deleteGoods(map);
		if ( r > 0 ) deleteGoods = true;
		return  deleteGoods ;
	}

}
