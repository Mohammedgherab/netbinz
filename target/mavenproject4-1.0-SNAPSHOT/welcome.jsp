<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, model.Movie, model.FavoriteDAO" %>
<%@ page import="dao.MovieDAO" %>

<%
    // تحقق من الجلسة
    String role = (String) session.getAttribute("role");
    if (role == null) {
        response.sendRedirect("login.jsp");
        return;
    }

    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>

<!DOCTYPE html>
<html lang="ar" dir="rtl">
<head>
    <meta charset="UTF-8">
    <title>أحدث الأفلام</title>
    <style>
        /* تنسيقات CSS كما هي دون تغيير */
        :root {
            --primary-color: #4a90e2;
            --secondary-color: #f5f7fa;
            --background-color: #ffffff;
            --text-color: #333;
            --card-bg: #fdfdfd;
            --hover-color: #e0e7ff;
            --button-bg: #4a90e2;
            --button-hover-bg: #357ABD;
            --delete-color: #e74c3c;
            --delete-hover: #c0392b;
        }

        body {
            margin: 0;
            font-family: 'Cairo', sans-serif;
            background-color: var(--secondary-color);
            color: var(--text-color);
        }

        .navbar {
            background-color: var(--primary-color);
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding: 15px 30px;
            color: white;
        }

        .navbar a {
            color: white;
            padding: 10px 16px;
            text-decoration: none;
            font-size: 16px;
            border-radius: 6px;
            transition: background 0.3s ease;
        }

        .navbar a:hover {
            background-color: rgba(255, 255, 255, 0.2);
        }

        .logout {
            background-color: var(--delete-color);
        }

        .logout:hover {
            background-color: var(--delete-hover);
        }

        .section-header {
            padding: 20px 30px;
            font-size: 22px;
            font-weight: bold;
            display: flex;
            justify-content: space-between;
            align-items: center;
            background-color: #fff;
            box-shadow: 0 1px 4px rgba(0,0,0,0.1);
        }

        .add-button {
            background-color: var(--button-bg);
            color: white;
            padding: 10px 18px;
            font-size: 14px;
            border-radius: 6px;
            text-decoration: none;
            transition: background-color 0.3s ease;
        }

        .add-button:hover {
            background-color: var(--button-hover-bg);
        }

        .movies-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 20px;
            padding: 30px;
        }

        .movie-card {
            background-color: var(--card-bg);
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 6px rgba(0,0,0,0.08);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .movie-card:hover {
            transform: translateY(-4px);
            box-shadow: 0 4px 12px rgba(0,0,0,0.15);
        }

        .movie-card img {
            width: 100%;
            height: 260px;
            object-fit: cover;
        }

        .movie-info {
            padding: 15px;
        }

        .movie-title {
            font-size: 18px;
            font-weight: bold;
            margin-bottom: 5px;
            color: #222;
        }

        .movie-date {
            font-size: 14px;
            color: #777;
            margin-bottom: 8px;
        }

        .movie-rating {
            background-color: #ffd700;
            color: #000;
            font-size: 13px;
            font-weight: bold;
            padding: 4px 8px;
            border-radius: 6px;
            display: inline-block;
        }

        .movie-actions {
            margin-top: 12px;
        }

        .movie-actions a {
            padding: 6px 12px;
            font-size: 13px;
            border-radius: 5px;
            text-decoration: none;
            margin-left: 6px;
            transition: all 0.3s;
        }

        .movie-actions a.edit {
            background-color: var(--primary-color);
            color: white;
        }

        .movie-actions a.edit:hover {
            background-color: var(--button-hover-bg);
        }

        .movie-actions a.delete {
            background-color: var(--delete-color);
            color: white;
        }

        .movie-actions a.delete:hover {
            background-color: var(--delete-hover);
        }

        .no-movies {
            text-align: center;
            font-size: 18px;
            color: #666;
            padding: 50px;
        }

        .movie-favorite {
            display: inline-block;
            font-size: 18px;
            margin-right: 8px;
            cursor: pointer;
            transition: color 0.3s ease;
            user-select: none;
        }

        .movie-favorite.liked {
            color: red;
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

<div class="navbar">
    <a href="welcome">🏠 الصفحة الرئيسية</a>
    <a href="favorites.jsp">📌 المفضلة</a>
    <a href="logout.jsp" class="logout">🚪 تسجيل الخروج</a>
</div>

<div class="section-header">
    🎬 الأفلام المضافة حديثًا
    <% if ("admin".equals(role)) { %>
        <a href="add-movie.jsp" class="add-button">➕ إضافة فيلم</a>
    <% } %>
</div>

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
            <div class="movie-rating">⭐ <%= m.getRating() %>
              <span class="movie-favorite <%= MovieDAO.isFavorited(userId, m.getId()) ? "liked" : "" %>" 
      data-movie-id="<%= m.getId() %>">
    <%= MovieDAO.isFavorited(userId, m.getId()) ? "❤️" : "🤍" %>
</span>
            </div>

            <% if ("admin".equals(role)) { %>
            <div class="movie-actions">
                <a href="edit-movie?id=<%= m.getId() %>" class="edit">✏️ تعديل</a>
                <a href="delete-movie.jsp?id=<%= m.getId() %>" class="delete"
                   onclick="return confirm('هل أنت متأكد من حذف الفيلم؟');">🗑️ حذف</a>
            </div>
            <% } %>
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

<script>
document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".movie-favorite").forEach(function (heart) {
        heart.addEventListener("click", function () {
            const movieId = heart.getAttribute("data-movie-id");

            fetch("toggle-favorite", {
                method: "POST",
                headers: {
                    "Content-Type": "application/x-www-form-urlencoded"
                },
                body: "movieId=" + encodeURIComponent(movieId)
            })
            .then(response => response.json())
            .then(data => {
                if (data.favorited) {
                    heart.textContent = "❤️";
                    heart.classList.add("liked");
                } else {
                    heart.textContent = "🤍";
                    heart.classList.remove("liked");
                }
            });
        });
    });
});
</script>

</body>
</html>
