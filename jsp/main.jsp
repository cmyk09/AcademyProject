<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>

<html>

<head>

	<meta charset="utf-8">
	<title>Insert title here</title>	
	<style type="text/css">
		#header { height: 100px; border: 1px solid black;}
		
		footer {
		  position: fixed;
		  bottom: 0;
		  width: 100%;
		  padding: 10px;
		  height: 50px;
		  background-color: #f0f0f0;
		  border-top: 1px solid #ccc; 
		}
		#icon {text-align: right;}
		#info {height: 100px; text-align: right;}
		#search {height: 30px; text-align: center;}

		#row {float: left; margin: 40px;}
		#img1 {width: 200px; height: 200px;}
		#clr {clear: left;}

		#menu {
				    height: 50px;				
				    background: silver;				
				}

				.main1 {				
				    width: 800px;				
				    height: 100%;				
				    margin: 0 auto;				
				}				

				.main1>li {				
				    float: left;				
				    width: 20%;				
				    line-height: 50px;				
				    text-align: center;				
				    position: relative;				
				}

				.main1>li:hover .main2 {
				    left: 0;
				}


				.main1>li a {
				    display: block;
				}


				.main1>li a:hover {				
				    background: #B21016;
				    color: #fff;
				    font-weight: bold;
				}

				.main2 {
				    position: absolute;
				    top: 50px;
				    left: -9999px;
				    background: #ccc;
				    width: 120%;
				}

				

				.main2>li {
				    position: relative;
				}

				.main2>li:hover .main3 {
				    left: 100%;
				}

				
				.main2>li a, .main3>li a {
				    border-radius: 10px;
				    margin: 10px;
				}

	
				.main3 {			
				    position: absolute;
				    top: 0;
				    background: #aaaaaa;
				    width: 80%;
				    left: -9999px;
				    /*left: 100%;*/
				    /*display: none;*/
				}
				

				.main3>li a:hover {
				    background: blue;
				    color: #fff;
				}

				li {
				  list-style: none;
				}
		</style>
		<script src="https://code.jquery.com/jquery-3.7.0.min.js" integrity="sha256-2Pmvv0kuTBOenSvLm6bvfBSSHrUJ+3A7x6P5Ebd07/g=" crossorigin="anonymous"></script>
		<script type="text/javascript">
			function searched()
			{
				var keyword = document.getElementById("keyword").value;
				
				location.href = "/ezen/goods/search/"+keyword+"/0/N/1" ;	
			}
		</script>	
		
		<script type="text/javascript">
		function mlogout()
		{
			if(!confirm('로그아웃하시겠습니까?')) return;
			
			$.ajax({
				url:'/ezen/member/logout',
				method:'get',
				cache:false,
				dataType:'json',
				success:function(res){
					if(res.isLogout) {
						alert('로그아웃 성공');
						location.href="/ezen/goods/main";
					}
				},
				error:function(xhr,status,err){
					alert(status +'/'+err);
				}
			});
			return false;
		}
		
		</script>
		
		<script type="text/javascript">
		function goCart(uid)
		{
			if (uid == null)
			{
				alert("로그인이 필요합니다.");
				location.href="/ezen/member/loginForm";
			}else
			{
				location.href="/ezen/goods/cartList/${uid}";	
			}						
		}
		</script>
		
		<script type="text/javascript">
		function goMemerInfo(uid)
		{
			if (uid == null)
			{
				alert("로그인이 필요합니다.");
				location.href="/ezen/member/loginForm";
			}else
			{
				location.href="/ezen/member/memberInfo";	
			}						
		}
		</script>
</head>

<body>

	<main>
	
	<div id="hearder">
	
	 	    <div> 
	 	    	[<a href="/ezen/goods/main">main</a>]
	 	    </div>
	 	   
			<div id="icon">
				[<a href="javascript:goMemerInfo(${uid});">내정보 보기</a>]
				[<a href="javascript:goCart(${uid});">장바구니</a>]
			</div>
			
	 		<div id="info">
				<c:choose>
					<c:when test="${uid > 0 }">
						|<a href="/ezen/member/memberInfo">${uid} 고객님</a>|<a href="javascript:mlogout()">로그아웃</a>|<a href="javascript:test();">고객센터</a>|	
					</c:when>
					<c:otherwise>
						|<a href="/ezen/member/loginForm">로그인</a>|<a href="/ezen/member/signup">회원가입</a>|<a href="#">고객센터</a>|	
					</c:otherwise>
				</c:choose>
	 			

	 			

	 		</div>
	 		
	 		<div id="search">
	 			<input id="keyword" name="keyword" type="text">
	 			<button type="button" onclick="searched();">검색</button>
	 		</div>

	 		<div id="menu"> 			 		
			   	<ul class="main1">	
			    
	 			<c:set var="first" value="${'000000'}"></c:set>
		 		<c:set var="second" value="${'000000'}"></c:set>
		 		<c:set var="third" value="${'000000'}"></c:set>
		 		
		 		<c:forEach var="list" items="${list}" varStatus="status">
		 		 
		 		
		 			<c:choose>
		 			
		 				<c:when test="${list.code_first != first}">		 				
		 				
			 				<c:choose>
			 					<c:when test="${status.index+1 == 1}">
			 						<li><a href="/ezen/goods/search/N/1/${list.code_first}/1">${list.name_first}</a>
									<ul class="main2">
			 					</c:when>
			 					<c:otherwise>
						                    </ul>
						                </li>		
						            </ul>
							        </li>		 					
			 						<li><a href="/ezen/goods/search/N/1/${list.code_first}/1">${list.name_first}</a>
					            	<ul class="main2">
			 					</c:otherwise>
			 				</c:choose>
		 				
		 					<c:choose>
		 						<c:when test="${list.code_second != second}">
		 						
						                <li><a href="/ezen/goods/search/N/2/${list.code_second}/1">${list.name_second}</a>
						                    <ul class="main3">
												 							
		 							 <c:choose>
		 							 	<c:when test="${list.code_third != third}">
		 							 	
				                        		<li><a href="/ezen/goods/search/N/3/${list.code_third}/1">${list.name_third}</a></li>
		 							 		
		 							 	</c:when>
		 							 </c:choose>
		 						</c:when>
		 						<c:otherwise>
		 							 
						                    </ul>
						                </li>
						                <li><a href="/ezen/goods/search/N/2/${list.code_second}/1">${list.name_second}</a>
					    	                <ul class="main3">
			                
		 						</c:otherwise>
		 					</c:choose>
		 					
		 				</c:when>
		 				<c:otherwise>
		 					<c:choose>
		 						<c:when test="${list.code_second != second}">
		 						
					        	            </ul>
					            	    </li>
					                	<li><a href="/ezen/goods/search/N/2/${list.code_second}/1">${list.name_second}</a>
					                    <ul class="main3">
					                    
		 						</c:when>
		 						<c:otherwise>
		                	        		<li><a href="/ezen/goods/search/N/3/${list.code_third}/1">${list.name_third}</a></li>
		 						</c:otherwise>
		 					</c:choose>
		 				</c:otherwise>
		 				
		 				 				
		 			</c:choose>
		 			
		 			<c:set var="first" value="${list.code_first}"></c:set>
		 			<c:set var="second" value="${list.code_second}"></c:set>
		 			<c:set var="third" value="${list.code_third}"></c:set>
		 		</c:forEach>	
		 		
						                    </ul>
						                </li>		
						            </ul>
							        </li>	

			    </ul>

			</div>

	 	</div>
	 	<div id="main">
	 		
	 	 	
				<c:forEach var="goods" items="${goods}">	
					
					<div id="row">
						<a href="/ezen/goods/detailGoodsForm/${goods.Goodsno}"><img id="img1" name="img1" alt="이미지없음" src="/images/goods/${goods.ImgFileFakeName1}"></a>
						<div>${goods.GoodsName}</div>	
						<div>${goods.GoodsSalePrice}</div>			
					</div>	
					
				</c:forEach>
			
			
			<div id="clr"></div>
		</div>
		
		<footer>

	 	footer

	 	</footer>
	 </main>

</body>

</html>