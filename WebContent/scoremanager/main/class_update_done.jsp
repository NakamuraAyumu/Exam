<!-- クラス変更完了JSP -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div>
			<h2>クラス情報変更</h2>
			<div>
				<p>変更が完了しました</p>
				<!-- 科目管理一覧画面に遷移 -->
				<a href="ClassList.action">クラス一覧</a>
			</div>
		</div>
	</c:param>
</c:import>