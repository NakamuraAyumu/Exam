<%-- 科目削除JSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp" >
	<c:param name="title">
		得点管理システム
	</c:param>

	<c:param name="scripts"></c:param>

	<c:param name="content">
		<section>
			<h2>科目情報削除</h2>
			<form action="SubjectDeleteExecute.action" method="get">

				<div>
					<p>「${subject_name }（${subject_cd }）」を削除してもよろしいですか</p>
				</div>

				<div>
					<input class="btn btn-danger" type="submit" value="削除"/>
				</div>
				<div>
					<input type="hidden" name="subject_cd" value="${subject_cd }" />
					<input type="hidden" name="subject_name" value="${subject_name }" />
				</div>
			</form>
			<a href="SubjectList.action">戻る</a>
		</section>
	</c:param>
</c:import>