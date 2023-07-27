package com.ezen.member;

import org.springframework.stereotype.Component;

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
@EqualsAndHashCode(exclude = {"mnum", "mname", "memail", "mphone", "maddress", "mstatus"})
public class MemberVO 
{
	private int mnum;
	private String mid;
	private String mpwd;
	private String mname;
	private String memail;
	private String mphone;
	private String maddress;
	private java.util.Date mrdate;
	private String mstatus;
}
