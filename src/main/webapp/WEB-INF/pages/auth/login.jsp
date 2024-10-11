<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>登入頁面</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">登入</h2>

        <%-- 顯示成功訊息 --%>
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <div class="alert alert-success" role="alert">
                <%= successMessage %>
            </div>
        <% } %>

        <%-- 顯示錯誤訊息 --%>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <div class="alert alert-danger" role="alert">
                <%= errorMessage %>
            </div>
        <% } %>

        <form id="loginForm" action="${pageContext.request.contextPath}/login" method="POST" class="mt-4">
            <div class="form-group">
                <label for="account">帳號:</label>
                <input type="text" class="form-control" id="account" name="account" required>
            </div>
            <div class="form-group">
                <label for="password">密碼:</label>
                <input type="password" class="form-control" id="password" name="password" required>
            </div>
            <div class="text-center">
                <button type="reset" class="btn btn-secondary">重設</button>
                <button type="submit" class="btn btn-primary">登入</button>
                <button id="ajaxLoginBtn" class="btn btn-info">AJAX 登入</button>
                <a href="register" class="btn btn-link">註冊帳號</a>
            </div>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    
    <script>
        $(document).ready(function() {
            // AJAX 登入按鈕處理邏輯
            $("#ajaxLoginBtn").click(function(event) {
                event.preventDefault(); // 阻止默認提交

                var account = $("#account").val();
                var password = $("#password").val();

                $.ajax({
                    url: '/P20240930/ajaxLogin',
                    type: 'POST',
                    data: {
                        account: $('#account').val(),
                        password: $('#password').val()
                    },
                    success: function(data) {
                        // 处理成功的登录
                        window.location.href = '/P20240930/index'; // 成功后重定向
                    },
                    error: function(jqXHR) {
                        if (jqXHR.status === 401) {
                            // 处理错误响应，解析 JSON 并显示错误消息
                            var response = JSON.parse(jqXHR.responseText);
                            alert(response.error); // 显示错误消息
                        } else {
                            alert('發生錯誤，請稍後再試！'); // 处理其他错误
                        }
                    }
                });
            });
        });
    </script>
</body>
</html>