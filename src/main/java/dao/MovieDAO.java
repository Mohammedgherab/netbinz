package dao;
import utils.DBUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import static utils.DBUtil.getConnection;

public class MovieDAO {

    public static void addFavorite(String userId, int movieId) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    // ✅ جلب كل الأفلام
    public List<Movie> getAllMovies() {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String query = "SELECT * FROM movies";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setReleaseDate(rs.getString("release_date"));
                m.setRating(rs.getDouble("rating"));
                m.setDescription(rs.getString("description"));
                m.setImageUrl(rs.getString("image_url"));
                m.setTrailerUrl(rs.getString("trailer_url"));
                movies.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movies;
    }

    // ✅ جلب فيلم باستخدام المعرف
    public Movie getMovieById(int id) {
        Movie movie = null;

        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                movie = new Movie();
                movie.setId(rs.getInt("id"));
                movie.setTitle(rs.getString("title"));
                movie.setReleaseDate(rs.getString("release_date"));
                movie.setRating(rs.getDouble("rating"));
                movie.setImageUrl(rs.getString("image_url"));
                movie.setTrailerUrl(rs.getString("trailer_url"));
                movie.setDescription(rs.getString("description"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return movie;
    }

    // ✅ إضافة فيلم جديد
    public void addMovie(Movie movie) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO movies (title, release_date, rating, image_url, trailer_url, description) " +
                         "VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getReleaseDate());
            stmt.setDouble(3, movie.getRating());
            stmt.setString(4, movie.getImageUrl());
            stmt.setString(5, movie.getTrailerUrl());
            stmt.setString(6, movie.getDescription());

            int rows = stmt.executeUpdate();
            System.out.println("✅ تمت إضافة " + rows + " فيلم(ـاً) إلى قاعدة البيانات.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ تحديث معلومات فيلم
    public void updateMovie(Movie movie) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE movies SET title = ?, release_date = ?, rating = ?, image_url = ?, trailer_url = ?, description = ? " +
                         "WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, movie.getTitle());
            stmt.setString(2, movie.getReleaseDate());
            stmt.setDouble(3, movie.getRating());
            stmt.setString(4, movie.getImageUrl());
            stmt.setString(5, movie.getTrailerUrl());
            stmt.setString(6, movie.getDescription());
            stmt.setInt(7, movie.getId());

            int rows = stmt.executeUpdate();
            System.out.println("✏️ تم تعديل " + rows + " فيلم(ـاً) بنجاح.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ حذف فيلم
    public void deleteMovie(int id) {
        try (Connection conn = getConnection()) {
            String sql = "DELETE FROM movies WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            int rows = stmt.executeUpdate();
            System.out.println("🗑️ تم حذف " + rows + " فيلم(ـاً) من قاعدة البيانات.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ✅ جلب الأفلام المفضلة لمستخدم معين
    public List<Movie> getFavoriteMovies(String userId) {
        List<Movie> favoriteMovies = new ArrayList<>();

        try (Connection conn = getConnection()) {
            String sql = "SELECT m.* FROM movies m " +
                         "JOIN favorites f ON m.id = f.movie_id " +
                         "WHERE f.user_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie m = new Movie();
                m.setId(rs.getInt("id"));
                m.setTitle(rs.getString("title"));
                m.setReleaseDate(rs.getString("release_date"));
                m.setRating(rs.getDouble("rating"));
                m.setDescription(rs.getString("description"));
                m.setImageUrl(rs.getString("image_url"));
                m.setTrailerUrl(rs.getString("trailer_url"));
                favoriteMovies.add(m);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return favoriteMovies;
    }

    // ✅ إضافة/إزالة مفضلة (حسب الحالة الحالية)
    public boolean toggleFavorite(String userId, int movieId) {
        boolean isNowFavorited = false;

        try (Connection conn = getConnection()) {
            // التحقق مما إذا كانت المفضلة موجودة
            String checkSql = "SELECT COUNT(*) FROM favorites WHERE user_id = ? AND movie_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setString(1, userId);
            checkStmt.setInt(2, movieId);
            ResultSet rs = checkStmt.executeQuery();
            rs.next();
            int count = rs.getInt(1);

            if (count > 0) {
                // إذا كانت موجودة، يتم حذفها
                String deleteSql = "DELETE FROM favorites WHERE user_id = ? AND movie_id = ?";
                PreparedStatement delStmt = conn.prepareStatement(deleteSql);
                delStmt.setString(1, userId);
                delStmt.setInt(2, movieId);
                delStmt.executeUpdate();
                isNowFavorited = false;
            } else {
                // إذا لم تكن موجودة، يتم إضافتها
                String insertSql = "INSERT INTO favorites (user_id, movie_id) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql);
                insertStmt.setString(1, userId);
                insertStmt.setInt(2, movieId);
                insertStmt.executeUpdate();
                isNowFavorited = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return isNowFavorited;
    }
    // ✅ التحقق مما إذا كان الفيلم مفضلاً
 public static boolean isFavorited(int userId, int movieId) {
        boolean favorited = false;

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT 1 FROM favorites WHERE user_id = ? AND movie_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, userId);
            stmt.setInt(2, movieId);
            ResultSet rs = stmt.executeQuery();

            favorited = rs.next(); // إذا وجدت نتيجة، يعني أنه مفضل
        } catch (Exception e) {
            e.printStackTrace();
        }

        return favorited;
    }

    // ✅ إضافة فيلم للمفضلة
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

    // ✅ إزالة فيلم من المفضلة
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
