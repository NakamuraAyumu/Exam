<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
  <c:param name="title">得点管理システム</c:param>
  <c:param name="scripts"></c:param>

  <c:param name="content">
    <section>
      <h2>成績一覧（科目）</h2>

      <h3>科目情報</h3>　

 <!-- 入力フォーム -->
       <form action="TestList.action" method="get">
       <label for="entYear">入学年度:</label>
		<select id="entYear" name="entYear">
		  <option value="">-- 入学年度を選択 --</option>
		  <c:forEach var="year" items="${entYearList}">
		    <option value="${year}" ${year == param.entYear ? 'selected' : ''}>${year}</option>
		  </c:forEach>
		</select>
		<br/>


        <label for="classNum">クラス:</label>
		<select id="classNum" name="classNum">
		  <option value="">-- クラスを選択 --</option>
		  <c:forEach var="classNum" items="${classList}">
		    <option value="${classNum}" ${classNum == param.classNum ? 'selected' : ''}>${classNum}</option>
		  </c:forEach>
		</select>
		<br/>

        <label for="subject">科目:</label>
        <select id="subject" name="subject_cd">
        <option value="">-- 科目を選択 --</option>
          <c:forEach var="subject" items="${subjects}">
            <option value="${subject.cd}" ${subject.cd == param.subject_cd ? 'selected' : ''}>${subject.name}</option>
          </c:forEach>
        </select>
        <br/>

        <button type="submit">検索</button>

        <h3>学生情報:</h3>

        <label for="studentNo">学生番号:</label>
		<input type="text" id="studentNo" name="studentNo" value="${param.studentNo}" />
		<br/>

		<button type="submit">検索</button>

      </form>

      <br/>

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
			    <c:otherwise>-</c:otherwise>
			  </c:choose>
			</td>

			<td>
			  <c:choose>
			    <c:when test="${not empty score.points[2]}">${score.points[2]}</c:when>
			    <c:otherwise>-</c:otherwise>
			  </c:choose>
			</td>
          </tr>
        </c:forEach>
      </table>
    </section>
  </c:param>
</c:import>
