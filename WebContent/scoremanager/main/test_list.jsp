<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>成績一覧（科目）</title>
</head>
<body>
<h2>成績一覧（科目）</h2>
<form method="get" action="TestList.action">
  <div>
    <label>入学年度:</label>
    <select name="entYear">
      <c:forEach var="year" items="${entYearList}">
        <option value="${year}" <c:if test="${year == param.entYear}">selected</c:if>>${year}</option>
      </c:forEach>
    </select>

    <label>クラス:</label>
    <select name="classNum">
      <c:forEach var="cls" items="${classNumList}">
        <option value="${cls}" <c:if test="${cls == param.classNum}">selected</c:if>>${cls}</option>
      </c:forEach>
    </select>

    <label>科目:</label>
    <select name="subjectCd">
      <c:forEach var="sub" items="${subjectList}">
        <option value="${sub.cd}" <c:if test="${sub.cd == param.subjectCd}">selected</c:if>>${sub.name}</option>
      </c:forEach>
    </select>

    <input type="submit" value="検索" />
  </div>
  <br/>

  <div>
    <label>学生番号:</label>
    <input type="text" name="studentNo" value="${param.studentNo}" />
    <input type="submit" value="検索" />
  </div>
</form>

<c:choose>
  <c:when test="${empty testList}">
    <p style="color: black ;">学生情報が存在しませんでした</p>
  </c:when>
  <c:otherwise>
    <p>科目：${selectedSubject.name}</p>
    <table border="1">
      <tr>
        <th>入学年度</th>
        <th>クラス</th>
        <th>学生番号</th>
        <th>氏名</th>
        <th>1回</th>
        <th>2回</th>
      </tr>
      <c:forEach var="subj" items="${testList}">
        <tr>
          <td>${subj.entYear}</td>
          <td>${subj.classNum}</td>
          <td>${subj.studentNo}</td>
          <td>${subj.studentName}</td>
          <td>${subj.points[1]}</td>
          <td>${subj.points[2]}</td>
        </tr>
      </c:forEach>
    </table>
  </c:otherwise>
</c:choose>

</body>
</html>
