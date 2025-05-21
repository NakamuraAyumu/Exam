<!-- 科目変更JSP -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2>科目情報変更</h2>
			<form action="SubjectUpdateExecute.action" method="get">
				<div>
					<label>科目コード</label><br>
					<input type="text" id="cd" name="cd" value="${cd}" />
				</div>
				<div>${errors.get("1") }</div>

				<div>
					<label>科目名</label><br>
					<input type="text" id="name" name="name" value="${name}" />
				</div>

				<div>
					<input type="submit" value="変更" />
				</div>
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>