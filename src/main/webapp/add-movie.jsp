<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>إضافة فيلم جديد</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
        }

        .form-container {
            max-width: 500px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }

        h2 {
            text-align: center;
            color: #333;
        }

        input, textarea {
            width: 100%;
            padding: 10px;
            margin: 12px 0;
            border-radius: 6px;
            border: 1px solid #ccc;
        }

        button {
            background-color: #27ae60;
            color: white;
            padding: 12px;
            border: none;
            width: 100%;
            border-radius: 6px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }

        button:hover {
            background-color: #219150;
        }

        .back-button {
            background-color: #3498db;
        }

        .back-button:hover {
            background-color: #2980b9;
        }
    </style>
</head>
<body>
    <div class="form-container">
        <h2>📽️ إضافة فيلم جديد</h2>
        <form action="AddMovieServlet" method="post">
            <input type="text" name="title" placeholder="عنوان الفيلم" required>
            <input type="text" name="releaseDate" placeholder="تاريخ الإصدار (yyyy-mm-dd)" required>
            <input type="number" step="0.1" name="rating" placeholder="التقييم (من 10)" required>
            <textarea name="description" placeholder="وصف الفيلم" rows="4"></textarea>
            <input type="text" name="imageUrl" placeholder="اسم ملف الصورة (مثلاً: 1.jpg)" required>
            <input type="text" name="trailerUrl" placeholder="رابط التريلر (YouTube أو غيره)">
            <button type="submit">💾 حفظ الفيلم</button>
        </form>

        <!-- زر العودة إلى القائمة الرئيسية -->
        <form action="welcome" method="get">
            <button type="submit" class="back-button">🏠 العودة إلى القائمة الرئيسية</button>
        </form>
    </div>
</body>
</html>
