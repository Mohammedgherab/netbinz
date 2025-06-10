package servlets;

// استيراد الحزم الخاصة بـ Servlet و HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// استيراد أداة الاتصال بقاعدة البيانات
import utils.DBUtil;

import java.io.*;
// import javax.servlet.*;
// import javax.servlet.http.*;
import java.sql.*;

// السيرفلت المسؤول عن عملية تسجيل مستخدم جديد
public class RegisterServlet extends HttpServlet {

    // يتم تنفيذ هذا الميثود عند إرسال طلب POST (من نموذج التسجيل مثلاً)
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        
        // الحصول على اسم المستخدم وكلمة المرور من نموذج HTML
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // محاولة إنشاء اتصال بقاعدة البيانات وإدخال البيانات
        try (Connection conn = DBUtil.getConnection()) {

            // أمر SQL لإدخال بيانات المستخدم الجديد
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // تعيين القيم للمعاملات في أمر SQL
            stmt.setString(1, username);
            stmt.setString(2, password); // ⚠️ ملاحظة: في المشاريع الحقيقية يجب تشفير كلمة المرور

            // تنفيذ عملية الإدخال
            stmt.executeUpdate();

            // إعادة توجيه المستخدم إلى صفحة تسجيل الدخول مع رسالة نجاح
            response.sendRedirect("login.jsp?register=success");

        } catch (Exception e) {
            // في حال حدوث خطأ (مثل اسم مستخدم مكرر أو مشكلة في الاتصال)
            e.printStackTrace();

            // إعادة التوجيه إلى صفحة التسجيل مع رسالة خطأ
            response.sendRedirect("register.jsp?error=1");
        }
    }
}
