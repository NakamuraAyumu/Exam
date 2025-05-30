<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<title>得点管理システム</title>
	</head>
	<body>
		<form action="TestRegist.action" method="get">
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
				<c:forEach var="subject" items="${subject_set}">
						<option value="${subject }"<c:if test="${subject==f3 }">selected</c:if>>${subject.name }</option></c:forEach>
				</select>

				<label>回数</label>
				<select name="f4">
				<option value="0">--------</option>
				<c:forEach var="count" items="${count_set}">
						<option value="${count }"<c:if test="${count==f4 }">selected</c:if>>${count }</option></c:forEach>
				</select>

				<button onclick="location.href='TestRegist.action'">検索</button>

			</div>
		</form>
		<c:choose>
		<c:when test="${testlist.size()>0 }">
			<form action="test_regist.jsp" method="get">
				<table>
					<tr>
						<th>入学年度</th>
						<th>クラス</th>
						<th>学生番号</th>
						<th>氏名</th>
						<th>点数</th>
					</tr>
					<c:forEach var="testitem" items="${testlist }">
						<tr>
							<th>${testitem.entYear }</th>
							<td>${testitem.classNum }</td>
							<td>${testitem.no }</td>
							<td>${testitem.name }</td>
							<td><input type="text" name="point_${test.student.no}" value="${test.point}" ></td>
						</tr>
						<c:if test = "point_${test.student.no} > 100 or point_${test.student.no} < 0" >
						</c:if>
					</c:forEach>
				</table>
				<p><input type="button" onclick="location.href='TestRegistExecuteAction.java'" value="登録して終了"></p>
				<input type="hidden" name="regist" value="${test.student.no}">
				<input type="hidden" name="count" value="${f4}">
				<input type="hidden" name="subject" value="${f3}">
			</form>
			</c:when>

		</c:choose>
	</body>
</html>