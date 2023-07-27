package com.ezen.goods;


import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class GoodsVO 
{
	private int Goodsno;
	private String inputS3Code;
	private String inputS3Name;
	private String GoodsName;
	private int GoodsSaleQty;
	private int GoodsSalePrice;
	private int GoodsPrice;
	private String GoodsDetail;
	private String GoodsStatus;
	private java.sql.Date GoodsRegistryDay;
	private java.sql.Date GoodsRecognitionDay;
	private java.sql.Date GoodsDeleteDay;
	private int SellerCode;
}
