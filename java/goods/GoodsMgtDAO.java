package com.ezen.goods;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class GoodsMgtDAO 
{
	@Autowired
	private GoodsMgtMapper mapper;
	
	public List<Map> getGoodsCategoryList1() 
	{
		List<Map> map = mapper.getGoodsCategoryList1();
		return map;
	}

	public List<Map> getGoodsCategoryList2(String code) 
	{
		List<Map> map = mapper.getGoodsCategoryList2(code);
		return map;
	}

	public List<Map> getGoodsCategoryList3(String code) 
	{
		List<Map> map = mapper.getGoodsCategoryList3(code);
		return map;
	}

	public int addGoods(GoodsVO goods) 
	{
		int r = mapper.addGoods(goods);
		return r;
	}

	public int saveGoodsImgFile(Map imgMap) 
	{
		int r = mapper.saveGoodsImgFile(imgMap);
		return r;
	}

	public Map detailGoodsForm(int goodsno) 
	{
		Map map = mapper.detailGoodsForm(goodsno);
		return map;
	}

	public Map updateGoodsForm(int goodsno) 
	{
		Map map = mapper.updateGoodsForm(goodsno);
		return map;
	}

	public int updateGoods(GoodsVO goods) 
	{
		int r = mapper.updateGoods(goods);
		return r;
	}

	public int updateGoodsImgFile(Map imgMap) 
	{
		int r = mapper.updateGoodsImgFile(imgMap);
		return r;
	}

	public int deleteGoodsImgFile(Map imgMap) 
	{
		int r = mapper.deleteGoodsImgFile(imgMap);
		return r;
	}

	public int putInCart(CartVO cart) 
	{
		int r = mapper.putInCart(cart);
		return r;
	}

	public List<Map> getCartList(int userno) 
	{
		List<Map> cart = mapper.getCartList(userno);
		return cart;
	}

	public int getOrderNo() 
	{
		int orderNo = mapper.getOrderNo();
		return orderNo;
	}

	public OrderVO getCartOrderList(Map map) 
	{
		OrderVO VO = mapper.getCartOrderList(map) ;
		return VO;
	}

	public int addOrder(List<OrderVO> orderList) 
	{
		int r = mapper.addOrder(orderList);
		return r;
	}

	public int deletedCart(CartVO cartVO) 
	{
		int r = mapper.deletedCart(cartVO);
		return r;
	}

	public List<Map> getOrderListMember(int userno) 
	{
		List<Map> orderList = mapper.getOrderListMember(userno);
		return orderList;
	}

	public List<Map> getGoodsCategoryListAll() 
	{
		List<Map> list = mapper.getGoodsCategoryListAll() ;
		return list;
	}

	public List<Map> getGoodsMain() 
	{
		List<Map> list = mapper.getGoodsMain() ;
		return list;
	}

	public List<Map> getGoodsSearchKeyword(String keyword) 
	{
		return mapper.getGoodsSearchKeyword(keyword) ;
	}

	public List<Map> getGoodsSearchCategory(Map map) 
	{
		return mapper.getGoodsSearchCategory(map) ;
	}

	public int updateGoodsQty(Map map) 
	{
		return mapper.updateGoodsQty(map);
	}

	public List<Map> goodsList(int sellernum) 
	{
		return mapper.goodsList(sellernum);
	}

	public boolean endSale(int goodsno) 
	{
		return mapper.endSale(goodsno) ;
	}

	public boolean startSale(int goodsno) 
	{
		return mapper.startSale(goodsno);
	}

}
