package dao;

import utils.DBUtil; // لاستدعاء أداة الاتصال بقاعدة البيانات
import java.sql.*; // استيراد مكتبة JDBC
import java.util.ArrayList;
import java.util.List;
import model.Movie; // استيراد الكلاس Movie الذي يمثل بيانات الفيلم

public class MovieDAO {

    // 🔁 دالة مساعدة لتحويل بيانات ResultSet إلى كائن Movie
    private static Movie mapResultSetToMovie(ResultSet rs) throws SQLException {
        Movie m = new Movie(); // إنشاء كائن جديد من نوع Movie
        m.setId(rs.getInt("id")); // تعيين المعرّف
        m.setTitle(rs.getString("title")); // تعيين العنوان
        m.setReleaseDate(rs.getString("release_date")); // تعيين تاريخ الإصدار
        m.setRating(rs.getDouble("rating")); // تعيين التقييم
        m.setDescription(rs.getString("description")); // تعيين الوصف
        m.setImageUrl(rs.getString("image_url")); // تعيين رابط الصورة
        m.setTrailerUrl(rs.getString("trailer_url")); // تعيين رابط العرض الترويجي
        return m; // إرجاع كائن الفيلم
    }

    // 📥 جلب جميع الأفلام من قاعدة البيانات
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String query = "SELECT * FROM movies"; // استعلام لجلب كل الأفلام
            PreparedStatement ps = conn.prepareStatement(query); // تجهيز الاستعلام
            ResultSet rs = ps.executeQuery(); // تنفيذ الاستعلام

            while (rs.next()) {
                movies.add(mapResultSetToMovie(rs)); // تحويل كل صف إلى كائن Movie
            }

        } catch (Exception e) {
            e.printStackTrace(); // طباعة الخطأ إذا حصل
        }

        return movies; // إرجاع قائمة الأفلام
    }

    // 🔍 جلب فيلم باستخدام المعرّف
    public Movie getMovieById(int id) {
        Movie movie = null;

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // تعيين المعرّف في الاستعلام
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movie = mapResultSetToMovie(rs); // تحويل النتيجة إلى كائن Movie
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    // ➕ إضافة فيلم جديد
    public void addMovie(Movie movie) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO movies (title, release_date, rating, image_url, trailer_url, description) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // تعيين القيم في الاستعلام
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getReleaseDate());
            stmt.setDouble(3, movie.getRating());
            stmt.setString(4, movie.getImageUrl());
            stmt.setString(5, movie.getTrailerUrl());
            stmt.setString(6, movie.getDescription());

            stmt.executeUpdate(); // تنفيذ عملية الإدخال
            System.out.println("✅ تم إضافة الفيلم بنجاح.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✏️ تعديل بيانات فيلم
    public void updateMovie(Movie movie) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "UPDATE movies SET title = ?, release_date = ?, rating = ?, image_url = ?, trailer_url = ?, description = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            // تعيين القيم الجديدة
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getReleaseDate());
            stmt.setDouble(3, movie.getRating());
            stmt.setString(4, movie.getImageUrl());
            stmt.setString(5, movie.getTrailerUrl());
            stmt.setString(6, movie.getDescription());
            stmt.setInt(7, movie.getId()); // تحديد الفيلم بالتعديل

            stmt.executeUpdate(); // تنفيذ التحديث
            System.out.println("✏️ تم تعديل الفيلم بنجاح.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🗑️ حذف فيلم باستخدام ID
    public void deleteMovie(int id) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id); // تعيين معرف الفيلم المراد حذفه
            stmt.executeUpdate(); // تنفيذ الحذف
            System.out.println("🗑️ تم حذف الفيلم بنجاح.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 🌟 جلب قائمة الأفلام المفضلة لمستخدم معين
    public static List<Movie> getFavoriteMovies(int userId) {
        List<Movie> favorites = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT m.* FROM movies m JOIN favorites f ON m.id = f.movie_id WHERE f.user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId); // تحديد المستخدم
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                favorites.add(mapResultSetToMovie(rs)); // تحويل الصف إلى كائن Movie
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favorites; // إرجاع قائمة المفضلات
    }

    // 🔁 تبديل حالة فيلم في المفضلة
    public boolean toggleFavorite(int userId, int movieId) {
        boolean isNowFavorited = false;

        try (Connection conn = DBUtil.getConnection()) {
            // التحقق هل الفيلم مضاف مسبقًا للمفضلة
            String checkSql = "SELECT 1 FROM favorites WHERE user_id = ? AND movie_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, userId);
            checkStmt.setInt(2, movieId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // إذا كان مضافًا، نحذفه من المفضلة
                removeFavorite(userId, movieId);
                isNowFavorited = false;
            } else {
                // إذا لم يكن مضافًا، نضيفه
                addFavorite(userId, movieId);
                isNowFavorited = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isNowFavorited;
    }

    // ❓ التحقق إذا كان فيلم مفضّل فعلاً من قبل المستخدم
    public static boolean isFavorited(int userId, int movieId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND movie_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // إرجاع true إذا وُجد صف واحد
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // ➕ إضافة فيلم إلى المفضلة
    public static void addFavorite(int userId, int movieId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "INSERT INTO favorites (user_id, movie_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ❌ إزالة فيلم من المفضلة
    public static void removeFavorite(int userId, int movieId) {
        try (Connection conn = DBUtil.getConnection()) {
            String sql = "DELETE FROM favorites WHERE user_id = ? AND movie_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
