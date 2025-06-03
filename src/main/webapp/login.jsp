<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>تسجيل الدخول</title>
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
            background-color: #4CAF50;
            color: white;
            padding: 10px 18px;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        input[type="submit"]:hover {
            background-color: #45a049;
        }

        p {
            margin-top: 20px;
        }
    </style>
</head>
<body>

    <h2>تسجيل الدخول</h2>
    
    <form action="LoginServlet" method="post">
        اسم المستخدم: <br><input type="text" name="username" required><br>
        كلمة المرور: <br><input type="password" name="password" required><br>
        <input type="submit" value="دخول">
    </form>

    <% if (request.getParameter("error") != null) { %>
        <p style="color:red;">اسم المستخدم أو كلمة المرور غير صحيحة</p>
    <% } else if (request.getParameter("register") != null) { %>
        <p style="color:green;">تم التسجيل بنجاح. سجل الدخول الآن.</p>
    <% } %>

    <p>ليس لديك حساب؟</p>
    <form action="register.jsp">
        <input type="submit" value="إنشاء حساب جديد">
    </form>

</body>
</html>
