package controllers;

import dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

import java.io.IOException;

@WebServlet("/edit-movie")
public class EditMovieServlet extends HttpServlet {

    // عرض صفحة التعديل
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idStr = request.getParameter("id");

        if (idStr != null && !idStr.isEmpty()) {
            try {
                int id = Integer.parseInt(idStr);
                MovieDAO movieDAO = new MovieDAO();
                Movie movie = movieDAO.getMovieById(id);

                if (movie != null) {
                    request.setAttribute("movie", movie);
                    request.getRequestDispatcher("edit-movie.jsp").forward(request, response);
                } else {
                    response.sendRedirect("MoviesServlet?error=notfound");
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                response.sendRedirect("MoviesServlet?error=invalidId");
            }
        } else {
            response.sendRedirect("MoviesServlet?error=noparam");
        }
    }

    // معالجة حفظ التعديلات
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            int id = Integer.parseInt(request.getParameter("id"));
            String title = request.getParameter("title");
            String releaseDate = request.getParameter("release_date");
            double rating = Double.parseDouble(request.getParameter("rating"));
            String imageUrl = request.getParameter("image_url");
            String trailerUrl = request.getParameter("trailer_url");
            String description = request.getParameter("description");

            // استخدام الكونستركتر بالترتيب الصحيح:
            Movie updatedMovie = new Movie(id, title, imageUrl, releaseDate, rating, description, trailerUrl);

            MovieDAO movieDAO = new MovieDAO();
            movieDAO.updateMovie(updatedMovie);

            response.sendRedirect("MoviesServlet?success=updated");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("MoviesServlet?error=updateFailed");
        }
    }
}
