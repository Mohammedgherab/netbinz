package servlets;

// استيراد كلاس MovieDAO للتعامل مع قاعدة البيانات
import dao.MovieDAO;

// استيراد الحزم اللازمة لـ Servlet و HTTP
import jakarta.servlet.*;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;
import model.Movie; // استيراد نموذج كائن الفيلم

// Servlet مخصص لاسترجاع قائمة جميع الأفلام وعرضها في صفحة JSP
public class MoviesServlet extends HttpServlet {

    // يتم تنفيذ هذا الميثود عندما يُرسل المستخدم طلب GET لهذا السيرفلت
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // إنشاء كائن من MovieDAO للتعامل مع قاعدة البيانات
        MovieDAO dao = new MovieDAO();

        // استدعاء الدالة لجلب جميع الأفلام من قاعدة البيانات
        List<Movie> movies = dao.getAllMovies();

        // تمرير قائمة الأفلام إلى صفحة JSP عبر خاصية request attribute
        request.setAttribute("movies", movies);

        // توجيه الطلب إلى صفحة welcome.jsp لعرض قائمة الأفلام
        RequestDispatcher dispatcher = request.getRequestDispatcher("welcome.jsp");
        dispatcher.forward(request, response);
    }
}
