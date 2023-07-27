package com.ezen.goods;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GoodsMgtMapper 
{

	List<Map> getGoodsCategoryList1();

	List<Map> getGoodsCategoryList2(String code);

	List<Map> getGoodsCategoryList3(String code);

	int addGoods(GoodsVO goods);

	int saveGoodsImgFile(Map imgMap);

	Map detailGoodsForm(int goodsno);

	Map updateGoodsForm(int goodsno);

	int updateGoods(GoodsVO goods);

	int updateGoodsImgFile(Map imgMap);

	int deleteGoodsImgFile(Map imgMap);

	int putInCart(CartVO cart);

	List<Map> getCartList(int userno);

	int getOrderNo();

	OrderVO getCartOrderList(Map map);

	int addOrder(List<OrderVO> orderList);

	int deletedCart(CartVO cartVO);

	List<Map> getOrderListMember(int userno);

	List<Map> getGoodsCategoryListAll();

	List<Map> getGoodsMain();

	List<Map> getGoodsSearchKeyword(String keyword);

	List<Map> getGoodsSearchCategory(int lvl, String code);

	List<Map> getGoodsSearchCategory(Map map);

	int updateGoodsQty(Map map);

	List<Map> goodsList(int sellernum);

	boolean endSale(int goodsno);

	boolean startSale(int goodsno);

}
