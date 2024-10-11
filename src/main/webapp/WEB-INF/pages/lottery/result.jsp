<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-Hant">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <title>抽獎結果</title>
</head>
<body>
    <div class="container mt-5">
        <h2 class="text-center">抽獎結果</h2>
        <table class="table table-bordered table-striped mt-4">
            <thead class="thead-light">
                <tr>
                    <th>組別</th>
                    <th>抽獎號碼</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<int[]> results = (List<int[]>) request.getAttribute("results");
                    for (int i = 0; i < results.size(); i++) {
                        int[] result = results.get(i);
                %>
                <tr>
                    <td>第 <%= (i + 1) %> 組</td>
                    <td>
                        <%
                            for (int num : result) {
                                out.print(num + " ");
                            }
                        %>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <div class="text-center mt-4">
            <a href="../P20240930/index.jsp" class="btn btn-secondary mt-2">返回首頁</a>
            <br></br>
            <form action="logout" method="GET">
                <button type="submit" class="btn btn-danger">登出</button>
            </form>
        </div>
    </div>

    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
