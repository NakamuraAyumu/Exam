<%-- クラス管理一覧JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2>クラス管理</h2>
			<form action="ClassList.action" method="get">
				<!-- クラス登録画面に遷移 -->
				<div>
					<a href="ClassCreate.action">新規登録</a>
				</div>

				<table>
					<tr>
						<th>クラス番号</th>
						<th></th>
					</tr>

					<c:forEach var="class_num" items="${list}">
						<tr>
							<td>${class_num }</td>
							<td><a href="ClassUpdate.action?class_num=${class_num }">変更</a></td>
						</tr>
					</c:forEach>
				</table>
			</form>
		</section>
	</c:param>
</c:import>