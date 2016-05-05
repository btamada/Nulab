import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Refactor: Use OAuth 2.0 Authentication
        try {

            // newly created session
            String login = request.getParameter("login");
            String password = request.getParameter("password");
            if (login.equalsIgnoreCase("nulab") && password.equalsIgnoreCase("nulab")) {
                // if credentials match then proceed to login page
                request.getRequestDispatcher("/WEB-INF/myapp/login.jsp").forward(request, response);
            } else {
                // if credentials do not match then throw error which
                // takes you to the error page
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "A problem was found in the Login Servlet.");
            request.getRequestDispatcher("/WEB-INF/myapp/error.jsp").forward(request, response);
        }
    }
}
