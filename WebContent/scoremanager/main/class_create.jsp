<!-- クラス登録JSP -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2>クラス情報登録</h2>
			<form action="ClassCreateExecute.action" method="get">
				<div>
					<label>クラス番号</label><br>
					<input type="text" name="class_num" id="class_num" value="${class_num }" />
				</div>
				<div>${errors.get("1") }</div>

				<div>
					<input type="submit" id="create-button" value="登録" />
				</div>
			</form>
			<a href="ClassList.action">戻る</a>
		</section>
	</c:param>
</c:import>