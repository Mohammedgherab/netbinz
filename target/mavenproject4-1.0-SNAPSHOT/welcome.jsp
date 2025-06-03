<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Movie" %>
<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>أحدث الأفلام</title>
    <style>
        body {
            margin: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(to right, #1a1a1a, #2c003e);
            color: #fff;
        }

        .navbar {
            background-color: #1f1f1f;
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 30px;
            position: sticky;
            top: 0;
            z-index: 1000;
            box-shadow: 0 2px 8px rgba(0,0,0,0.5);
        }

        .navbar a {
            color: #fff;
            padding: 10px 16px;
            text-decoration: none;
            font-size: 16px;
            border-radius: 6px;
            transition: background 0.3s ease;
        }

        .navbar a:hover {
            background-color: #333;
            color: #ffcc00;
        }

        .navbar .logout {
            background-color: #e74c3c;
            font-weight: bold;
        }

        .navbar .logout:hover {
            background-color: #c0392b;
        }

        .section-header {
            background: linear-gradient(to left, #2c003e, #1f1f1f);
            padding: 20px 30px;
            font-size: 22px;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .add-button {
            background-color: #27ae60;
            color: white;
            padding: 10px 18px;
            font-size: 14px;
            border-radius: 6px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .add-button:hover {
            background-color: #1e8449;
        }

        .movies-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
            gap: 20px;
            padding: 30px;
        }

        .movie-card {
            background-color: #1c1c1c;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 4px 8px rgba(0,0,0,0.6);
            transition: transform 0.3s ease;
        }

        .movie-card:hover {
            transform: scale(1.05);
        }

        .movie-card img {
            width: 100%;
            height: 270px;
            object-fit: cover;
            border-bottom: 2px solid #333;
        }

        .movie-info {
            padding: 12px;
        }

        .movie-title {
            font-size: 17px;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .movie-date {
            font-size: 13px;
            color: #bbb;
            margin-bottom: 8px;
        }

        .movie-rating {
            background-color: #ffcc00;
            color: #000;
            font-size: 13px;
            font-weight: bold;
            padding: 2px 6px;
            border-radius: 4px;
            display: inline-block;
        }

        .movie-actions {
            margin-top: 10px;
        }

        .movie-actions a {
            padding: 6px 12px;
            font-size: 13px;
            border-radius: 5px;
            text-decoration: none;
            margin-left: 5px;
            display: inline-block;
            transition: background 0.3s;
        }

        .movie-actions a.edit {
            background-color: #3498db;
            color: white;
        }

        .movie-actions a.edit:hover {
            background-color: #2980b9;
        }

        .movie-actions a.delete {
            background-color: #e74c3c;
            color: white;
        }

        .movie-actions a.delete:hover {
            background-color: #c0392b;
        }

        .no-movies {
            text-align: center;
            font-size: 18px;
            color: #ccc;
            padding: 50px;
        }

        @media (max-width: 600px) {
            .section-header {
                flex-direction: column;
                gap: 10px;
                text-align: center;
            }

            .add-button {
                width: 100%;
                text-align: center;
            }
        }
    </style>
</head>
<body>

<!-- شريط التنقل -->
<div class="navbar">
    <a href="welcome.jsp">🏠 الصفحة الرئيسية</a>
    <a href="logout.jsp" class="logout">🚪 تسجيل الخروج</a>
</div>

<!-- عنوان القسم مع زر الإضافة -->
<div class="section-header">
    🎬 الأفلام المضافة حديثًا
    <a href="add-movie.jsp" class="add-button">➕ إضافة فيلم</a>
</div>

<!-- عرض الأفلام -->
<div class="movies-grid">
<%
    List<Movie> movies = (List<Movie>) request.getAttribute("movies");
    if (movies != null && !movies.isEmpty()) {
        for (Movie m : movies) {
%>
    <div class="movie-card">
    <a href="<%= m.getTrailerUrl() %>" target="_blank">
        <img src="images/<%= m.getImageUrl() %>" alt="<%= m.getTitle() %>">
    </a>
    <div class="movie-info">
        <div class="movie-title"><%= m.getTitle() %></div>
        <div class="movie-date">📅 <%= m.getReleaseDate() %></div>
        <div class="movie-rating">⭐ <%= m.getRating() %></div>
        <div class="movie-actions">
            <a href="edit-movie?id=<%= m.getId() %>" class="edit">✏️ تعديل</a>
            <a href="delete-movie.jsp?id=<%= m.getId() %>" class="delete" onclick="return confirm('هل أنت متأكد من حذف الفيلم؟');">🗑️ حذف</a>
        </div>
    </div>
</div>

<%
        }
    } else {
%>
    <div class="no-movies">🚫 لا توجد أفلام مضافة حالياً.</div>
<%
    }
%>
</div>

</body>
</html>
