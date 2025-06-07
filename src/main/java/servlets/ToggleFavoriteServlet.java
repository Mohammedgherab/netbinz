package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.MovieDAO;

@WebServlet("/ToggleFavoriteServlet")
public class ToggleFavoriteServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
       Integer userId = (Integer) session.getAttribute("userId");


        if (userId == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int movieId = Integer.parseInt(request.getParameter("movieId"));

        if (MovieDAO.isFavorited(userId, movieId)) {
            MovieDAO.removeFavorite(userId, movieId);
}    else {
            MovieDAO.addFavorite(userId, movieId);
     }


        response.sendRedirect("MoviesServlet");
    }
}
