<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Gyu's Page</title>
<script>
    // **원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해 
    function list(page){
        location.href="${path}/board/list?curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
    }
    
</script>
<%@ include file="./commons/_header.jspf" %>
</head>
<body>
	<%@ include file="./commons/_top.jspf" %>

	 <!-- Header 맨앞 사진,문구/ 페북등 버튼  -->
    <a name="about"></a>
    <div class="intro-header">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="intro-message">
                        <h1>WelCome</h1>
                        <h3>Gyu's Page</h3>
                        <hr class="intro-divider">
                        <ul class="list-inline intro-social-buttons">
                            <li>
                                <a hrsef="https://www.facebook.com/profile.php?id=100002282055147" class="btn btn-default btn-lg"><i class="fa fa-facebook fa-fw"></i> <span class="network-name">FaceBook</span></a>
                            </li>
                            <li>
                                <a href="https://github.com/cotgyu" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                            </li>
                            <li>
                                <a href="https://cotmulgyu.blogspot.kr" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Blog</span></a>
                            </li>
                        </ul>
                    </div>
                </div>
            </div>

        </div>
        <!-- /.container -->
    </div>
    <!-- /.intro-header -->
  <!-- Page Content -->

	<a  name="services"></a>
    <div class="content-section-a">
	<div class="container">
            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">웹 관련 정보를 공유해요!</h2><br>
                    <p class="lead">자신이 공부한 내용을 올려 사람들과 공유해볼까요?</p>
                    
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                  	인기 게시물
					<table border="2" align="center" width="100%" style= "background-color: white">					
					<c:forEach var="row" items="${map.poplist}">
					<tr>					
					<td height="30">&nbsp;<a href="${path}/board/view?bnum=${row.bnum}">${row.title}
								<!-- ** 댓글이 있으면 게시글 이름 옆에 출력하기 -->
				                    <c:if test="${row.recnt > 0}">
				                    <span style="color: red;">(${row.recnt})</span>
				                    </c:if>	
								</a></td></tr>
					</c:forEach>
					</table>
					
                </div>
            </div>
	
        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

    <div class="content-section-b">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-lg-offset-1 col-sm-push-6  col-sm-6" >
                                
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">사진을 올려주세요!</h2><br>
                    <p class="lead">자신의 관심분야 사진을 공유해볼까요?</p>
                </div>
                <div class="col-lg-6 col-sm-pull-7 col-sm-20">
                &emsp;&emsp;&emsp;&nbsp;인기게시물
                <ul>
					<c:forEach items="${map.popImglist}" var="map">						
						<li>						
							<a href="/gallery/list"><img src="/resources/uploads/${map.imgfile}" width="110" height="170"></a>					
						</li>
					</c:forEach>
				</ul>               
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-b -->

    <div class="content-section-a">

        <div class="container">

            <div class="row">
                <div class="col-lg-5 col-sm-6">
                    <hr class="section-heading-spacer">
                    <div class="clearfix"></div>
                    <h2 class="section-heading">자유롭게 의견을 나눠요!</h2><br>
                    <p class="lead">자유롭게 의견을 나눠볼까요? </p>
                   
                </div>
                <div class="col-lg-5 col-lg-offset-2 col-sm-6">
                	인기게시물
                    <table border="2" align="center" style= "background-color: white" width="100%" >
					<c:forEach var="row2" items="${map.popFlist}">
					<tr>
					<td height="30">&nbsp;<a href="${path}/fboard/view?bnum=${row2.bnum}">${row2.title}
								<!-- ** 댓글이 있으면 게시글 이름 옆에 출력하기 -->
				                    <c:if test="${row2.recnt > 0}">
				                    <span style="color: red;">(${row2.recnt})
				                    </span>
				                    </c:if>	
								</a></td></tr>
					</c:forEach>
						</table>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.content-section-a -->

	<a  name="contact"></a>
    <div class="banner">

        <div class="container">

            <div class="row">
                <div class="col-lg-6">
                    
                </div>
                <div class="col-lg-6">
                    <ul class="list-inline banner-social-buttons">
                        <li>
                            <a href="https://www.facebook.co.kr" class="btn btn-default btn-lg"><i class="fa fa-facebook fa-fw"></i> <span class="network-name">facebook</span></a>
                        </li>
                        <li>
                            <a href="https://github.com/IronSummitMedia/startbootstrap" class="btn btn-default btn-lg"><i class="fa fa-github fa-fw"></i> <span class="network-name">Github</span></a>
                        </li>
                        <li>
                            <a href="https://cotmulgyu.blogspot.kr/" class="btn btn-default btn-lg"><i class="fa fa-linkedin fa-fw"></i> <span class="network-name">Blog</span></a>
                        </li>
                    </ul>
                </div>
            </div>

        </div>
        <!-- /.container -->

    </div>
    <!-- /.banner -->

 	<%@ include file="../views/commons/_foot.jspf"%>


</body>
</html>