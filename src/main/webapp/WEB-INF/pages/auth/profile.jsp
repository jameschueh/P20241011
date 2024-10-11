<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>個人資料</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">個人資料</h2>

        <%-- 顯示成功訊息 --%>
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <div class="alert alert-success" role="alert">
                <%= successMessage %>
            </div>
        <% } %>

        <form action="${pageContext.request.contextPath}/updateProfile" method="POST" class="mt-4">
            <div class="form-group">
                <label for="account">帳號:</label>
                <input type="text" class="form-control" id="account" name="account" value="${member.account}" required>
            </div>
            <div class="form-group">
                <label for="password">密碼:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="text-center">
                <button type="reset" class="btn btn-secondary">重設</button>
                <button type="submit" class="btn btn-primary">更新資料</button>
                <a href="${pageContext.request.contextPath}/index" class="btn btn-link">回首頁</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-link">登出</a>
            </div>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
