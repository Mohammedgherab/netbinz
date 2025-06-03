<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>إنشاء حساب</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background-color: #f2f2f2;
            direction: rtl;
            text-align: center;
            padding-top: 50px;
        }

        form {
            background-color: #ffffff;
            padding: 20px;
            margin: auto;
            width: 300px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        input[type="text"], input[type="password"] {
            width: 90%;
            padding: 8px;
            margin: 8px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
        }

        input[type="submit"] {
            background-color: #2196F3;
            color: white;
            padding: 10px 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #0b7dda;
        }

        p {
            margin-top: 20px;
        }

        a {
            display: inline-block;
            margin-top: 10px;
            color: #333;
            text-decoration: none;
        }

        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>

    <h2>إنشاء حساب</h2>

    <form action="RegisterServlet" method="post">
        اسم المستخدم: <br><input type="text" name="username" required><br>
        كلمة المرور: <br><input type="password" name="password" required><br>
        <input type="submit" value="تسجيل">
    </form>

    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;">اسم المستخدم موجود أو حدث خطأ أثناء التسجيل</p>
    <% } %>

    <a href="login.jsp">العودة إلى تسجيل الدخول</a>

</body>
</html>
