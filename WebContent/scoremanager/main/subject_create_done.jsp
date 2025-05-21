<%-- 科目登録完了JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="content">
		<div id="wrap_box">
			<h2>科目情報登録</h2>
			<div id="wrap_box">
				<p>登録が完了しました</p>
				<a href="SubjectCreate.action">戻る</a>
				<a href="SubjectList.action">科目一覧</a>
			</div>
		</div>
	</c:param>
</c:import>