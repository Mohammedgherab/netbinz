package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// ✅ كلاس مساعد (Utility Class) لتوفير اتصال بقاعدة البيانات
public class DBUtil {

    // دالة ثابتة تُستخدم للحصول على اتصال بقاعدة البيانات
    public static Connection getConnection() throws SQLException {
        try {
            // تحميل سائق (Driver) SQLite في الذاكرة
            // هذا السطر مطلوب مرة واحدة فقط لضمان توفر السائق
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            // إذا لم يتم العثور على السائق، يتم طباعة الخطأ
            e.printStackTrace();
        }

        // مسار قاعدة البيانات (على شكل ملف SQLite في المسار المحلي)
        String url = "jdbc:sqlite:C:/Users/mohamed gherab/Documents/users.db";

        // إنشاء وإرجاع اتصال بقاعدة البيانات باستخدام DriverManager
        return DriverManager.getConnection(url);
    }
}
