<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
	<head>
		<title>得点管理システム</title>
	</head>
	<body>
		<form action="LoginExecute.action" method="post">
			<h2>ログイン</h2>
			<div>ID<input name="id" type="text" value="${id}" /></div>

			<div>パスワード<input name="password" type="password" value="" /></div>

			<div><input name="chk_d_ps" type="checkbox" /><label>パスワードを表示</label></div>

			<div><input name="login" type="submit" value="ログイン" /></div>
		</form>
	</body>
</html>
