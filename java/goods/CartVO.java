package com.ezen.goods;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
public class CartVO 
{
	private int cartno;
	private int userno;
	private int goodsno;
	private int goodsSalePrice;
	private int goodsSaleQty;
	private java.sql.Date cartRegistryDay;
}
