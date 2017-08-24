<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Gyu's Page :: Web게시판</title>

<%@ include file="../commons/_header.jspf"%>
<script>
    // **원하는 페이지로 이동시 검색조건, 키워드 값을 유지하기 위해 
    function list(page){
        location.href="${path}/webboard/list?curPage="+page+"&searchOption-${map.searchOption}"+"&keyword=${map.keyword}";
    }
//댓글 누를시 댓글 창 오픈 ...그창에서 삭제누르면 다시 본페이지로 돌아가기때문에 댓글창에서 댓글 쓰기~이런기능 불가 >아마 새로해야할듯,,?
    function openreply(bnum){
    	window.open("${path}/reply/list.do?bnum="+bnum, '댓글', "width=800, height=700, toolbar=no, menubar=no, scrollbars=no, resizable=yes" );  

        }
//검색에 이미지
	function search(){
		document.formId.method = "post"     
        document.formId.submit();
		}
    
</script>
<style>
body{
background: #F3F3F3;
}

</style>
</head>
<body>
	<%@ include file="../commons/_top.jspf"%>
	<br>
	<div class="popboard">
	<table border="2" align="center" width="58%" height="30" style= "background-color: white">
	<h3>인기게시판</h3>
	<c:forEach var="row2" items="${map.poplist}">
	<td width="300" height="40">&nbsp;<a href="${path}/webboard/view?bnum=${row2.bnum}">${row2.title}
				<!-- ** 댓글이 있으면 게시글 이름 옆에 출력하기 -->
                    <c:if test="${row2.recnt > 0}">
                    <span style="color: red;">(${row2.recnt})</span>
                    </c:if>	
				</a></td>
	</c:forEach>
	</table>
	</div>
	<br>
	<br><br>
	<div class="searchMenu">
	 <form name="form1" method="post" action="${path}/webboard/list">
        <select name="searchOption">
            <!-- 검색조건을 검색처리후 결과화면에 보여주기위해  c:out 출력태그 사용, 삼항연산자 -->
            <option value="all" <c:out value="${map.searchOption == 'all'?'selected':''}"/> >제목+이름+내용</option>
            <option value="writer" <c:out value="${map.searchOption == 'writer'?'selected':''}"/> >이름</option>
            <option value="content" <c:out value="${map.searchOption == 'content'?'selected':''}"/> >내용</option>
            <option value="title" <c:out value="${map.searchOption == 'title'?'selected':''}"/> >제목</option>
        </select>
        <input name="keyword" value="${map.keyword}">
        <input type="image" src="\resources\images\search2.png" onClick="javascript_:search();" width="40" height="18" >
       
    </form>
     ${map.count}개의 게시물이 있습니다. 
	<c:if test="${not empty sessionScope.userId}">
	<button type="button" class="btn btn-default" onClick="location.href='write'">글쓰기</button>
	</c:if>
	</div>
	
	<table class="board" border="2" width="58%" align="center" style= "background-color: white">
		<tr height="40" align="center" >
			<td width="30px">번호</td>
			<td width="600px">제목</td>
			<td width="120px">작성자</td>
			<td width="160px">작성일</td>
			<td width="60px">조회수</td>
		</tr>
		<c:forEach var="row" items="${map.list}">
			<tr height="40" align="center">
				<td>${row.bnum}</td>
				<td align="left">&nbsp;<a href="${path}/webboard/view?bnum=${row.bnum}&curPage=${map.boardPage.curPage}&searchOption=${map.searchOption}&keyword=${map.keyword}">${row.title}
				<!-- ** 댓글이 있으면 게시글 이름 옆에 출력하기 -->
                    <c:if test="${row.recnt > 0}">
                    <span style="color: red;">(${row.recnt})
                    </span>
                    </c:if>	
				</a></td>
				
				<!--  댓글누르면 이동하기..수정이 필요할듯.
				<td><a href="${path}/board/view?bnum=${row.bnum}">${row.title}</a>				
				<a href="#" OnClick="javascript:openreply('${row.bnum}');">
                    <c:if test="${row.recnt > 0}">
                    <span style="color: red;">(${row.recnt})
                    </span>
                    </c:if>
				
				</a></td>
				-->	 		
				<td>${row.writer}</td>
				<td>
				<fmt:formatDate value="${row.date}" pattern="yyyy-MM-dd a HH:mm" />
				</td>
				<td>${row.hit}</td>
			</tr>
		</c:forEach>
		 <tr height="30">
            <td colspan="5">
                <!-- **처음페이지로 이동 : 현재 페이지가 1보다 크면  [처음]하이퍼링크를 화면에 출력-->
                <c:if test="${map.boardPage.curBlock > 1}">
                    <a href="javascript:list('1')">[처음]</a>
                </c:if>
                
                <!-- **이전페이지 블록으로 이동 : 현재 페이지 블럭이 1보다 크면 [이전]하이퍼링크를 화면에 출력 -->
                <c:if test="${map.boardPage.curBlock > 1}">
                    <a href="javascript:list('${map.boardPage.prevPage}')">[이전]</a>
                </c:if>
                
                <!-- **하나의 블럭에서 반복문 수행 시작페이지부터 끝페이지까지 -->
                <c:forEach var="num" begin="${map.boardPage.blockBegin}" end="${map.boardPage.blockEnd}">
                    <!-- **현재페이지이면 하이퍼링크 제거 -->
                    <c:choose>
                        <c:when test="${num == map.boardPage.curPage}">
                            <span style="color: red">${num}</span>&nbsp;
                        </c:when>
                        <c:otherwise>
                            <a href="javascript:list('${num}')">${num}</a>&nbsp;
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
                
                <!-- **다음페이지 블록으로 이동 : 현재 페이지 블럭이 전체 페이지 블럭보다 작거나 같으면 [다음]하이퍼링크를 화면에 출력 -->
                <c:if test="${map.boardPage.curBlock <= map.boardPage.totBlock}">
                    <a href="javascript:list('${map.boardPage.nextPage}')">[다음]</a>
                </c:if>
                
                <!-- **끝페이지로 이동 : 현재 페이지가 전체 페이지보다 작거나 같으면 [끝]하이퍼링크를 화면에 출력 -->
                <c:if test="${map.boardPage.curPage <= map.boardPage.totPage}">
                    <a href="javascript:list('${map.boardPage.totPage}')">[끝] </a>   
                </c:if>
            </td>
        </tr>
	</table>
	<br><br><br><br><br><br><br><br><br>
	<%@ include file="../commons/_foot.jspf"%>
</body>
</html>