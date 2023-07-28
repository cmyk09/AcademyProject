package com.ezen.seller;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.ezen.member.MemberVO;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Component
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(exclude = {"busnessOcc", "busnessCate", "officeEmail", "officePhone", "officeFax", "sellerNick", "picEmail", "pictel", "picphone", "csPhone", "bankName", "sstatus"})
public class SellerVO //판매자 정보 VO 페이지. 
{
	
	private int sellernum;//가입자 번호(index용)
	private String officeName;//상호명
	private String sellerName;//대표자명
	private String offRegiNum;//사업자 등록번호
	private String officeAdd; //본사주소
	private String busnessNum;//통신판매업신고번호
	private String busnessOcc;//업태
	private String busnessCate;//종목
	private String officeEmail;//사업자연락처-이메일
	private String officePhone;//사업자연락처-전화
	private String officeFax;//사업자연락처-팩스
	
	private String adminId; //관리자ID
	private String adminpass; //괸리자 password
	private String adminpassagain; //재확인용 password
	private String sellerNick; //판매자 닉네임
	private String picName; //담당자(the person in charge) 이름
	private String picEmail;//담당자 이메일
	private String pictel; //담당자 전화번호
	private String picphone; //담당자 휴대폰번호
	
	private String csPhone; //CS연락처
	
	private String bankName; //판매대금 은행명
	private String accountholder; //예금주명
	private String accountadd; //예금주 계좌번호
	
	private String sstatus; //회원탈퇴 여부
	private java.sql.Date signUpDate; //회원가입일
}
