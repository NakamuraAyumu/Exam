<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
 <head>
  <title>得点管理システム</title>
 </head>
 <body>
 	<form action="StudentUpdateExecute.action" method="post">
 	<h2>学生情報変更</h2>
 	<div><label>入学年度</label>
 		<input type="text" name="ent_year" value="${ent_year}"/>
	</div>
	<div><label>学生番号</label>
	<input type="text" name="no" value="${no}"/>
	</div>
	<div><label>氏名</label>
	<input type="text" name="name" value="${name}" />
	</div>
	<div><label>クラス</label>
	<select name="class_num">
	<c:forEach var="num" items="${class_num_set}">
	<option value="${num }"<c:if test="${num==class_num }">selected</c:if>>${num }</option>
	</c:forEach>
	</select>
	</div>
	<div><label>在学中</label>
   	<input type="checkbox" name="is_attend">
   	<c:if test="${is_attend }">checked</c:if>
   	</div>
   	<div>
	<input type="submit" name="login" value="変更">
	</div>
	</form>
	<div><a href="StudentList.action">戻る</a></div>
 </body>
</html>