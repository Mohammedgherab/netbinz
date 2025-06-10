package servlets;

// استيراد الحزم الضرورية من Jakarta Servlet API
import jakarta.servlet.*;
import jakarta.servlet.http.*;

// استيراد الكلاس Movie (نموذج الفيلم)
import model.Movie;

// استيراد أداة الاتصال بقاعدة البيانات
import utils.DBUtil;

import java.io.IOException;
import java.sql.*;
import java.util.*;

// Servlet يعرض قائمة الأفلام عند الوصول عبر HTTP GET
public class MovieListServlet extends HttpServlet {

    // يتم تنفيذ هذا الميثود عند تنفيذ طلب GET على هذا السيرفلت
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // إنشاء قائمة لتخزين الأفلام المسترجعة من قاعدة البيانات
        List<Movie> movies = new ArrayList<>();

        // استخدام try-with-resources لإغلاق الاتصال تلقائيًا بعد الاستخدام
        try (Connection conn = DBUtil.getConnection()) {

            // استعلام SQL لاسترجاع جميع الأفلام مرتبة حسب تاريخ الإصدار تنازليًا
            String sql = "SELECT * FROM movies ORDER BY release_date DESC";

            // تحضير الاستعلام للتنفيذ
            PreparedStatement stmt = conn.prepareStatement(sql);

            // تنفيذ الاستعلام والحصول على النتائج في كائن ResultSet
            ResultSet rs = stmt.executeQuery();

            // تكرار النتائج وبناء كائنات Movie منها
            while (rs.next()) {
                Movie m = new Movie(); // إنشاء كائن فيلم جديد

                // تعبئة خصائص الفيلم من نتائج الاستعلام
                m.setTitle(rs.getString("title"));
                m.setPosterUrl(rs.getString("poster_url"));
                m.setReleaseDate(rs.getString("release_date"));
                m.setRating(rs.getDouble("rating"));

                // إضافة الفيلم إلى القائمة
                movies.add(m);
            }

            // تخزين قائمة الأفلام في الطلب لإرسالها إلى JSP
            request.setAttribute("movies", movies);

            // توجيه المستخدم إلى صفحة welcome.jsp مع تمرير البيانات
            RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            // في حال حصول أي خطأ، يتم طباعته في سجل الخادم
            e.printStackTrace();
        }
    }
}
