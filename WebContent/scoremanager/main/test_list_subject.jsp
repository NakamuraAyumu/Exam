<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section>
      <h2>成績一覧（科目）</h2>

      <form action="TestList.action" method="get">
      </form>
      <br />

      <!-- 科目表示 -->
      <div>
        科目：${subjectName}
      </div>

      <!-- 成績一覧テーブル -->
      <table>
        <tr>
          <th>入学年度</th>
          <th>クラス</th>
          <th>学生番号</th>
          <th>氏名</th>
          <th>1回</th>
          <th>2回</th>
        </tr>

        <c:forEach var="score" items="${scoreList}">
          <tr>
            <td>${score.entYear}</td>
            <td>${score.classNum}</td>
            <td>${score.studentNo}</td>
            <td>${score.studentName}</td>
            <td>
			  <c:choose>
			    <c:when test="${not empty score.points[1]}">${score.points[1]}</c:when>
			  </c:choose>
			</td>

			<td>
			  <c:choose>
			    <c:when test="${not empty score.points[2]}">${score.points[2]}</c:when>
			  </c:choose>
			</td>
          </tr>
        </c:forEach>

      </table>
    </section>
  </c:param>
</c:import>
