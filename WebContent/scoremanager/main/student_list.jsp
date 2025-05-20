<%@page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
 <head>
  <title>得点管理システム</title>
 </head>
 <body>
  <form action="StudentList.action" method="post">
   <h2>学生管理</h2>
   <div><a href="StudentCreate.action">新規登録</a></div>
   <div><label>入学年度</label>
   <select name="f1">
    <option value="0">------------</option>
 <c:forEach var="year" items="${ent_year_set }">
    	<%-- 現在のyearと選択されていたf1が一致していた場合selectedを追記 --%>
    		<option value="${year }"<c:if test="${year==f1 }">selected</c:if>>${year }</option></c:forEach>
    </select>
   </div>
   <div><label>クラス</label>
   <select name="f2">
    <option value="0">-----------</option>
    <c:forEach var="num" items="${class_num_set }">
    	<%-- 現在のnumと選択されていたf2が一致していた場合selectedを追記 --%>
    		<option value="${num }"<c:if test="${num==f2 }">selected</c:if>>${num }</option></c:forEach>
    </select>
   </div>
   <div><label>在学中</label>
   <input type="checkbox" name="f3" value="t">
   </div>
   <div>
   <input type="submit" name="" value="絞込み"/>
   </div>
  </form>
  <c:choose>
  <c:when test="${students.size()>0 }">
  <div>検索結果:${students.size()}件</div>
  <table>
   <tr>
   <th>入学年度</th>
   <th>学生番号</th>
   <th>氏名</th>
   <th>クラス</th>
   <th>在学中</th>
   <th></th>
   </tr>
   <c:forEach var="student" items="${students }">
   <tr>
   <td>${student.entYear }</td>
   <td>${student.no }</td>
   <td>${student.name }</td>
   <td>${student.classNum }</td>
   <td class="text-center">
  	<%-- 在学フラグがたっている場合「〇」それ以外は「×」を表示 --%>
   	<c:choose>
   		<c:when test="${student.isAttend() }">
   			〇
   		</c:when>
   		<c:otherwise>
   			×
   		</c:otherwise>
   	</c:choose>
   	</td>
   	<td><a href="StudentUpdate.action?no=${sutdent.no }">変更</a></td>
   	</tr>
   	</c:forEach>
   </table>
   </c:when>
   <c:otherwise>
   <div>学生情報が存在しませんでした</div>
   </c:otherwise>
   </c:choose>
 </body>
</html>