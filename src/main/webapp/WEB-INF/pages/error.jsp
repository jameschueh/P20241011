<%@page contentType="text/html" pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>404 - Page Not Found</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            height: 100vh;
            text-align: center;
        }
        h1 {
            font-size: 3rem;
            color: #dc3545;
        }
        p {
            font-size: 1.2rem;
        }
        .btn {
            margin-top: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>您訪問的頁面不存在</h1>
        <p>請檢查您輸入的網址是否正確。</p>
        <a class="btn btn-primary" href="<%= request.getContextPath() %>/login">返回登入頁面</a>
    </div>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
