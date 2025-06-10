package servlets;

// استيراد الحزم اللازمة من Jakarta Servlet API
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

// استيراد الكائن Movie (نموذج البيانات)
import model.Movie;

// استيراد كائن DAO المسؤول عن التعامل مع قاعدة البيانات
import dao.MovieDAO;

// Servlet لإضافة فيلم جديد (يتم استدعاؤه عند إرسال النموذج عبر POST)
public class AddMovieServlet extends HttpServlet {

    // هذا الميثود يُستدعى تلقائيًا عند إرسال نموذج عبر POST
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // إنشاء كائن فيلم جديد وتعبئة بياناته من النموذج (Form)
        Movie movie = new Movie();

        // تعيين عنوان الفيلم
        movie.setTitle(request.getParameter("title"));

        // تعيين تاريخ الإصدار
        movie.setReleaseDate(request.getParameter("releaseDate"));

        // تعيين التقييم (يتم تحويله من String إلى double)
        movie.setRating(Double.parseDouble(request.getParameter("rating")));

        // تعيين رابط الصورة
        movie.setImageUrl(request.getParameter("imageUrl"));

        // تعيين رابط المقطع الدعائي
        movie.setTrailerUrl(request.getParameter("trailerUrl"));

        // تعيين وصف الفيلم
        movie.setDescription(request.getParameter("description"));

        // إنشاء كائن DAO للتعامل مع قاعدة البيانات
        MovieDAO dao = new MovieDAO();

        // استدعاء دالة إضافة الفيلم إلى قاعدة البيانات
        dao.addMovie(movie);

        // بعد الحفظ، إعادة توجيه المستخدم إلى صفحة "welcome" (عادةً صفحة عرض الأفلام)
        response.sendRedirect("welcome");
    }
}
