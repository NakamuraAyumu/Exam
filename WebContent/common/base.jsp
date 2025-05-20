<%-- 共通テンプレート --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>${param.title}</title>
	<%--  ${param.scripts} --%>
</head>
<body>
	<div id="wrapper" class="container">
		<header><c:import url="/common/header.jsp" /></header>

		<div>
			<c:choose>
				<%-- ログイン済みの場合 --%>
				<c:when test="${user.isAuthenticated()}">
					<nav>
						<c:import url="/common/navigation.jsp" />
					</nav>
					<div> ${param.content}</div>
				</c:when>
				<%-- 未ログインの場合 --%>
				<c:otherwise>
					<div> ${param.content}</div>
				</c:otherwise>
			</c:choose>
		</div>
		<footer><c:import url="/common/footer.jsp" /></footer>
	</div>
</body>
</html>
