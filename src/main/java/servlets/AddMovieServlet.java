package servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Movie;
import dao.MovieDAO;

public class AddMovieServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Movie movie = new Movie();
        movie.setTitle(request.getParameter("title"));
        movie.setReleaseDate(request.getParameter("releaseDate"));
        movie.setRating(Double.parseDouble(request.getParameter("rating")));
        movie.setImageUrl(request.getParameter("imageUrl"));
        movie.setTrailerUrl(request.getParameter("trailerUrl"));
        movie.setDescription(request.getParameter("description"));

        MovieDAO dao = new MovieDAO();
        dao.addMovie(movie);

        response.sendRedirect("welcome");
    }
}
