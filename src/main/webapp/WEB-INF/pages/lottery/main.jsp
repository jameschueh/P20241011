<%@page import="java.util.LinkedList"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>抽獎輸入</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">登入成功!</h2>
        <h3 class="text-center">抽獎輸入</h3>

        <%--Start Error Report --%>
        <%
            LinkedList<String> errors = (LinkedList<String>) request.getAttribute("errors");
            if (errors != null) {
        %>
        <div class="alert alert-danger">
            <ul>
                <% for (String error : errors) { %>
                    <li><%= error %></li>
                <% } %>
            </ul>
        </div>
        <%
            }
        %>
        <%--End Error Report --%>

        <form action="doLottery" method="POST">
            <div class="form-group">
                <label for="groups">組數(請輸入數字。例如:3)</label>
                <input type="text" class="form-control" id="groups" name="groups" value="${ param.groups }" />
            </div>
            <div class="form-group">
                <label for="exclude">排除(請輸入數字，並以空格區隔資料。例如:1 2 3)</label>
                <input type="text" class="form-control" id="exclude" name="exclude" value="${ param.exclude }" />
            </div>
            <button type="reset" class="btn btn-secondary">重設</button>
            <button type="submit" class="btn btn-primary">送出</button>
        </form>

        <form action="logout" method="GET" class="mt-4">
            <button type="submit" class="btn btn-danger">登出</button>
        </form>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
