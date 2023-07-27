package com.ezen.goods;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
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
public class OrderVO 
{
	private int no;
	private int orderno;
	private int memberno;
	private int goodsno;
	private int sellerno;
	private int goodsSalePrice;
	private int goodsSaleQty;
	private java.sql.Date orderRegistryDay;
	private String status;
}
