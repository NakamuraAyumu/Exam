<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
 <head>
  <title>得点管理システム</title>
 </head>
 <body>
 	<form action="StudentCreateExecute.action" method="post">
	<div><h2>学生情報登録</h2></div>
	<div><label>入学年度</label></div>
	<div><select name="ent_year">
	<option value="0">-----------</option>
	<c:forEach var="year" items="${ent_year_set }">
	<%-- 現在のyearと選択されていたent_yearが一致していた場合selectedを追記 --%>
	<option value="${year }" <c:if test="${year==ent_year }">selected</c:if>>${year }</option>
	</c:forEach>
	</select>
	<div>${errors.get("ent_year")}</div>
	</div>
	<div><label>学生番号</label>
	<input type="text" name="no" value="${no}" />
	</div>
	<div><label>氏名</label>
	<input type="text" name="name" value="${name}" />
	</div>
	<div><label>クラス</label>
	<select name="class_num">
	<c:forEach var="num" items="${class_num_set}">
	<%--現在のnumと選択されていたclass_numが一致していた場合selectedを追記 --%>
	<option value="${num}" <c:if test="${num==class_num}">selected</c:if>>${num }
	</option>
	</c:forEach>
	</select>
	</div>
	<div>
	<input type="submit" name="end" value="登録して終了">
	</div>
	<a href="student_list.jsp">戻る	</a>
	</form>
 </body>
</html>