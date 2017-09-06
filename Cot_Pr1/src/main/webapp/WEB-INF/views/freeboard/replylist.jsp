<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
// **댓글 수정화면 생성 함수
function showReplyModify(rnum) {
	$.ajax({
		type : "get",
		url : "${path}/freereply/detail/" + rnum,
		success : function(result) {
			$("#modifyReply").html(result);
			// 태그.css("속성", "값")
			$("#modifyReply").css("visibility", "visible");
		}
	})
}
// **댓글  코멘트 생성 함수
function showReplyComment(rnum) {
	$.ajax({
		type : "get",
		url : "${path}/freereply/commentwrite/" + rnum,
		success : function(result) {
			$("#ReplyComment").html(result);
			// 태그.css("속성", "값")
			$("#ReplyComment").css("visibility", "visible");
		}
	})
}

	</script>
</head>
<body>
   <div style="width:73%;" >
        <c:forEach var="row" items="${list}">
       		<!-- 댓글 indent에 따라 div창 밀기  -->
            <div style= "margin-left: <c:out value="${30*row.reindent}"/>px; border:1px solid; background-color:white">
             <c:forEach var="i" begin="1" end="${row.reindent}" step="1">
             	
        	 </c:forEach>
        	  	<input type="image" src="\resources\images\re.png" width="40" height="18" >
                ${row.replyer} &emsp; <fmt:formatDate value="${row.date}" pattern="yyyy-MM-dd a HH:mm" />
                <br>
         		&emsp;
                ${row.replytext}<br>
              	<c:if test="${sessionScope.userId != null}">
                <c:if test="${sessionScope.userId == row.replyer}">
                    <button type="button" class="btn btn-default" onClick="location.href='/freereply/delete?rnum=${row.rnum}&bnum=${row.bnum}'">삭제 </button>
                    <input type="button" class="btn btn-default" id="btnModify" value="수정" onclick="showReplyModify('${row.rnum}')"> 
                </c:if>
					<input type="button" class="btn btn-default" id="btnComment" value="댓글" onclick="showReplyComment('${row.rnum}')">
                </c:if>
            </div>
       
        </c:forEach>
    </div>
    <!-- **댓글 수정, 작성 출력할 위치 -->
    <div id="modifyReply"></div>    
	
	<div id="ReplyComment"></div>
</body>
</html>
