package servlets;

// استيراد الحزم الضرورية لمعالجة الطلبات والاستجابة وإنشاء الجلسات
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

// استيراد أداة الاتصال بقاعدة البيانات
import utils.DBUtil;

import java.io.IOException;
import java.sql.*;

public class LoginServlet extends HttpServlet {

    // يتم تنفيذ هذا الميثود عند إرسال نموذج تسجيل الدخول (POST)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        // الحصول على اسم المستخدم وكلمة المرور من النموذج
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection conn = DBUtil.getConnection()) {
            // أمر SQL للتحقق من وجود مستخدم مطابق
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // تمرير القيم إلى الاستعلام
            stmt.setString(1, username);
            stmt.setString(2, password);

            // تنفيذ الاستعلام وجلب النتائج
            ResultSet rs = stmt.executeQuery();

            // ✅ في حال تم العثور على مستخدم مطابق
            if (rs.next()) {
                // إنشاء جلسة جديدة أو الحصول على الجلسة الحالية
                HttpSession session = request.getSession();

                // تخزين معرف المستخدم في الجلسة لاستخدامه لاحقًا
                int userId = rs.getInt("id");
                session.setAttribute("userId", userId);

                // حفظ اسم المستخدم في الجلسة
                session.setAttribute("username", username);

                // ✅ تحديد نوع المستخدم (مثلاً: admin إذا كتب "1" و "1")
                if ("1".equals(username) && "1".equals(password)) {
                    session.setAttribute("role", "admin");
                } else {
                    session.setAttribute("role", "user");
                }

                // ✅ إعادة توجيه المستخدم إلى صفحة الترحيب بعد نجاح الدخول
                response.sendRedirect("welcome");
            } else {
                // ❌ في حال لم يتم العثور على حساب مطابق (خطأ في الاسم أو كلمة المرور)
                response.sendRedirect("login.jsp?error=1");
            }

        } catch (Exception e) {
            // في حال حدوث خطأ أثناء الاتصال بقاعدة البيانات أو الاستعلام
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=2");
        }
    }
}
