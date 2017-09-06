<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SLiPP :: 자유 게시판</title>
<%@ include file="../commons/_header.jspf"%>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		listReply(); // **댓글 목록 불러오기
		//listReply2(); // ** json 리턴방식

	});

	// Controller방식
	// **댓글 목록1
	function listReply() {
		$.ajax({
			type : "get",
			url : "${path}/freereply/list.do?bnum=${dto.bnum}",
			success : function(result) {
				// responseText가 result에 저장됨.
				$("#listReply").html(result);
			}
		});
	}

	// RestController방식 (Json)
	// **댓글 목록2 (json)
	function listReply2() {
		$.ajax({
			type : "get",
			//contentType: "application/json", ==> 생략가능(RestController이기때문에 가능)
			url : "${path}/freereply/listJson.do?bnum=${dto.bnum}",
			success : function(result) {
				console.log(result);
				var output = "<table>";
				for ( var i in result) {
					output += "<tr>";
					output += "<td>" + result[i].replyer;
					output += "(" + changeDate(result[i].date) + ")<br>";
					output += result[i].replytext + "</td>";
					output += "<tr>";
				}
				output += "</table>";
				$("#listReply").html(output);
			}
		});
	}


</script>
</head>
<body>
	<%@ include file="../commons/_top.jspf"%>
	<div class="boardpadding">
	<h3>자유게시판</h3>
	<br>
	<table border="1" width="73%"  style= "background-color: white">
	<h3>${dto.title}</h3>
	<tr>
		<td>
			<div style="display: inline;">
			&nbsp;<img src="/resources/profile/${profileimg}" width="50" height="40">${dto.writer}
			</div>
			<div style="float: right; display: inline;" >
			태그: ${dto.tag} &emsp;작성일자 : <fmt:formatDate value="${dto.date}" pattern="yyyy-MM-dd a HH:mm" /> &emsp; 조회수 : ${dto.hit}
			</div>
		</td>
	</tr>
	
	<tr>
	<td>
		<div>
			${dto.content}
		</div>
	</td>
	</tr>
	
		</table>
			<!-- 게시물번호를 hidden으로 처리 -->
			<input type="hidden" name="bnum" value="${dto.bnum}">
			<c:if test="${sessionScope.userId == dto.writer}">				
				<button type="button" class="btn btn-default" onClick="location.href='updatedetail/${dto.bnum}'">수정</button>
				<button type="button" class="btn btn-default" onClick="location.href='delete?bnum=${dto.bnum}'">삭제</button>
			</c:if>
			<button type="button" class="btn btn-default" onClick="location.href='/freeboard/list'">목록</button>		
	</div>
	
	<br><br>
	
	<div class="boardpadding">
		<!-- **댓글 목록 출력할 위치 -->
		<div id="listReply">
		</div>
		
	<form name="form2" method="post" action="/freereply/insert.do" method="${method}">
		<div style="width: 15%;">
			<br>
			<!-- **로그인 한 회원에게만 댓글 작성폼이 보이게 처리 -->
			<c:if test="${sessionScope.userId != null}">
				${sessionScope.userId}
				<textarea rows="5" cols="82" name="replytext" placeholder="댓글을 작성해주세요"></textarea>
				<input type="hidden" name="bnum" value="${dto.bnum}">
				<br>
				<button type="submit" class="btn btn-default">댓글 작성</button>
			</c:if>
		</div>
	</form>
	<br>
	
	</div>
	<div>
<br><br><br><br><br><br><br><br><br>
	<%@ include file="../commons/_foot.jspf"%>
	</div>
	
</body>
</html>