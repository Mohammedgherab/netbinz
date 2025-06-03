package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import model.Movie;
import utils.DBUtil;
import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MovieListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Movie> movies = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection()) {
            String sql = "SELECT * FROM movies ORDER BY release_date DESC";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movie m = new Movie();
                m.setTitle(rs.getString("title"));
                m.setPosterUrl(rs.getString("poster_url"));
                m.setReleaseDate(rs.getString("release_date"));
                m.setRating(rs.getDouble("rating"));
                movies.add(m);
            }

            request.setAttribute("movies", movies);
            RequestDispatcher rd = request.getRequestDispatcher("welcome.jsp");
            rd.forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
