<%-- エラーページ --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="ja">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>エラーページ</title>
</head>
<body>
	<div>
		<header>
			<div>
				<h1>得点管理システム</h1>
			</div>
			<c:if test="${user.isAuthenticated()}">
				<div>
					<span>${user.getName()}様</span>
					<a href="/exam_login/scoremanager/main/Logout.action">ログアウト</a>
				</div>
			</c:if>
		</header>

		<div>
			<c:choose>
				<%-- ログイン済みの場合 --%>
				<c:when test="${user.isAuthenticated()}">
					<nav>
						<ul>
							<li style="display:inline;"><a href="Menu.action">メニュー</a></li>
							<li style="display:inline;"><a href="StudentList.action">学生管理</a></li>
							<li style="display:inline;"><a href="TestRegist.action">成績登録</a></li>
							<li style="display:inline;"><a href="TestList.action">成績参照</a></li>
							<li style="display:inline;"><a href="SubjectList.action">科目管理</a></li>
							<li style="display:inline;"><a href="ClassList.action">クラス管理</a></li>
						</ul>
					</nav>

					<section>
						<p>エラーが発生しました</p>
					</section>
				</c:when>
				<%-- 未ログインの場合 --%>
				<c:otherwise>
					<section>
						<p>エラーが発生しました</p>
					</section>
				</c:otherwise>
			</c:choose>
		</div>
		<footer>
			<p>&copy; 2023 TIC </p>
			<p>大原学園</p>
		</footer>
	</div>
</body>
</html>
