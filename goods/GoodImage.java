package com.ezen.goods;

import org.hibernate.annotations.Comment;
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
public class GoodImage 
{
	private int goodsno;
	private String imgFileName;
	private String imgFakeFileName;
	private long imgSize;
	private String imgType;
}
