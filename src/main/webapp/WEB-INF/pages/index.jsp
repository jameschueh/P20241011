

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>首頁</title>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">首頁</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="lottery">抽獎</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="register">註冊</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="profile">更新帳號密碼</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="logout">登出</a>
                </li>
            </ul>
        </div>
    </nav>

    <div class="container mt-5 text-center">
        <h2>歡迎來到首頁!</h2>
        <p>
            <a href="lottery" class="btn btn-primary btn-lg">開始抽獎</a>
        </p>
    </div>

    <footer class="text-center mt-5">
        <p>© 2024 systex.</p>
    </footer>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
