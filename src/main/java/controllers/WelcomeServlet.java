package controllers;

import dao.MovieDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Movie;

import java.io.IOException;
import java.util.List;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

           MovieDAO dao = new MovieDAO();
        List<Movie> movies = dao.getAllMovies();

        System.out.println("عدد الأفلام المسترجعة: " + movies.size()); // ✅ سليم

        request.setAttribute("movies", movies);  // ✅ اسم المتغير متناسق مع JSP
        request.getRequestDispatcher("welcome.jsp").forward(request, response);
    }
}
