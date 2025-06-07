<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="model.Movie" %>
<%
    Movie movie = (Movie) request.getAttribute("movie");
    if (movie == null) {
%>
    <div style="text-align:center; margin-top: 50px;">
        <h2>⚠️ لم يتم العثور على الفيلم المطلوب تعديله.</h2>
        <a href="MoviesServlet" style="color: #fff; text-decoration: underline;">🔙 العودة إلى قائمة الأفلام</a>
    </div>
<%
        return;
    }
%>

<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>تعديل الفيلم - <%= movie.getTitle() %></title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #1a1a1a, #2c003e);
            color: #fff;
            direction: rtl;
        }

        .container {
            max-width: 600px;
            margin: 60px auto;
            background-color: #1f1f1f;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.5);
        }

        h2 {
            text-align: center;
            margin-bottom: 25px;
            color: #ffcc00;
        }

        label {
            display: block;
            margin-bottom: 6px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="number"],
        textarea {
            width: 100%;
            padding: 10px;
            margin-bottom: 18px;
            border: none;
            border-radius: 6px;
            background-color: #2c2c2c;
            color: #fff;
            font-size: 14px;
        }

        textarea {
            resize: vertical;
            height: 80px;
        }

        .buttons {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .buttons input[type="submit"],
        .buttons a {
            padding: 10px 20px;
            font-size: 14px;
            border: none;
            border-radius: 6px;
            cursor: pointer;
            text-decoration: none;
            transition: 0.3s;
        }

        .buttons input[type="submit"] {
            background-color: #27ae60;
            color: white;
        }

        .buttons input[type="submit"]:hover {
            background-color: #1e8449;
        }

        .buttons a {
            background-color: #c0392b;
            color: white;
        }

        .buttons a:hover {
            background-color: #a93226;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>✏️ تعديل بيانات الفيلم</h2>
    <form action="edit-movie" method="post">
        <input type="hidden" name="id" value="<%= movie.getId() %>">

        <label>عنوان الفيلم</label>
        <input type="text" name="title" value="<%= movie.getTitle() %>" required>

        <label>تاريخ الإصدار</label>
        <input type="text" name="release_date" value="<%= movie.getReleaseDate() %>" required>

        <label>التقييم</label>
        <input type="number" step="0.1" name="rating" value="<%= movie.getRating() %>" required>

        <label>رابط الصورة</label>
        <input type="text" name="image_url" value="<%= movie.getImageUrl() %>" required>

        <label>رابط التريلر</label>
        <input type="text" name="trailer_url" value="<%= movie.getTrailerUrl() %>">

        <label>الوصف</label>
        <textarea name="description"><%= movie.getDescription() %></textarea>

        <div class="buttons">
            <input type="submit" value="💾 حفظ التعديلات">
            <a href="welcome">🔙 العودة</a>
        </div>
    </form>
</div>

</body>
</html>
