package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Movie;
import static utils.DBUtil.getConnection;

public class MovieDAO {

    // جلب كل الأفلام
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

    // إضافة فيلم جديد
    public void addMovie(Movie movie) {
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO movies (title, release_date, rating, image_url, trailer_url, description) VALUES (?, ?, ?, ?, ?, ?)";
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

    // جلب فيلم باستخدام المعرف
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

    // حذف فيلم
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

    // تحديث معلومات فيلم
    public void updateMovie(Movie movie) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE movies SET title = ?, release_date = ?, rating = ?, image_url = ?, trailer_url = ?, description = ? WHERE id = ?";
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
}
