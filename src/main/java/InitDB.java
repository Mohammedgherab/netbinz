import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class InitDB {
    public static void main(String[] args) {
        String url = "jdbc:sqlite:C:/Users/mohamed gherab/Documents/users.db";

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                Statement stmt = conn.createStatement();

                // إنشاء جدول المستخدمين
                String userTable = "CREATE TABLE IF NOT EXISTS users ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "username TEXT UNIQUE NOT NULL, "
                        + "password TEXT NOT NULL"
                        + ");";
                stmt.execute(userTable);

                // إنشاء جدول الأفلام
                String movieTable = "CREATE TABLE IF NOT EXISTS movies ("
                        + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "title TEXT NOT NULL, "
                        + "release_date TEXT, "
                        + "rating REAL, "
                        + "image_url TEXT, "
                        + "trailer_url TEXT, "
                        + "description TEXT"
                        + ");";
                stmt.execute(movieTable);

                System.out.println("✅ تم إنشاء قاعدة البيانات والجداول بنجاح.");
            }
        } catch (Exception e) {
            System.out.println("❌ حدث خطأ أثناء إنشاء قاعدة البيانات:");
            e.printStackTrace();
        }
    }
}
