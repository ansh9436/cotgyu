<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<title>Login Page</title>
<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
<script type="text/javascript">
$(document).ready(function(){
	
$("loginbtn").click(function(){
	if($("#loginid").val()==""){
		alert("아이디를 입력해주세요.");
		$("#loginid").focus();
		}else if($("#loginpwd").val() ==""){
			alert("비밀번호를 입력해주세요");
			$("#loginpwd").focus();
			
			}else{
				$("#loginfrm").attr("action", "<c:url value='/users/j_spring_security_check'/>");
				$("#loginfrm").submit();
			}
});

});

</script>
</head>
<body>
<h3>Login with Username and Password</h3>
<form id='loginfrm' name="loginfrm" action='/users/j_spring_security_check' method='POST'>
<table>
	<tr>
		<td>User: </td>
		<td>
			<input type='text' id='loginid' name='loginid' value=''/>
		</td>
	</tr>
	
	<tr>
		<td>Password:</td>
		<td>
			<input type='password' id='loginpwd' name='loginpwd'/>
		</td>
	</tr>
	<tr>
		<td colspan='2'>
			<input type='button' id='loginbtn' value="로그인"/>
		</td>
	</tr>
	<c:if test="${not empty param.fail}">
	<tr>
		<td colspan="2">
			<font color="red">
			<p>로그인 시도가 성공적이지 못했습니다. 다시 시도해주세요.</p>
			<p>reason: ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}</p>			
			</font>
			<c:remove scope="session" var="SPRING_SECURITY_LAST_EXCEPTION" />
		</td>
	</tr>
	</c:if>
</table>
</form>

</body>
</html>