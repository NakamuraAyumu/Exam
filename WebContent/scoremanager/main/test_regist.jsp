<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>得点管理システム</title>
	</head>
	<body>
		<form action="TestRegistExecute.action" method="post">
			<div><h2>成績管理</h2></div>
			<div><label>入学年度</label>
				<select name="f1">
				<option value="0">--------</option>
				<c:forEach var="year" items="${ent_year_set }">
			    		<option value="${year }"<c:if test="${year==f1 }">selected</c:if>>${year }</option></c:forEach>
				</select>

				<label>クラス</label>
				<select name="f2">
				<option value="0">--------</option>
				<c:forEach var="num" items="${class_num_set }">
						<option value="${num }"<c:if test="${num==f2 }">selected</c:if>>${num }</option></c:forEach>
				</select>

				<label>科目</label>
				<select name="f3">
				<option value="0">--------</option>
				<c:forEach var="s" items="${subject_set}">
						<option value="${s.subject }"<c:if test="${s.subject==f3 }">selected</c:if>>${s.subjectName }</option></c:forEach>
				</select>

				<label>回数</label>
				<select name="f4">
				<option value="0">--------</option>
				<c:forEach var="count" items="${count_set}">
						<option value="${count }"<c:if test="${count==f4 }">selected</c:if>>${count }</option></c:forEach>
				</select>

				<a href="test_regist.action">検索</a>
			</div>

		</form>
	</body>
</html>