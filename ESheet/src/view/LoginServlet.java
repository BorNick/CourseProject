package view;
import model.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        String name = request.getParameter("name");
        String password = request.getParameter("password");

        PrintWriter out = response.getWriter();
        out.println("<html>");


        if (UserManager.checkPassword(name, password)) {
            HttpSession session = request.getSession();
            session.setAttribute("name", name);
            String role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                request.getRequestDispatcher("adminMenu.html").include(request, response);
            } else {
                request.getRequestDispatcher("menu.html").include(request, response);
            }
            out.print("Welcome, " + name);
        } else {
            request.getRequestDispatcher("menu.html").include(request, response);
            out.print("Sorry, username or password error!");
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</body></html>");
        out.close();
    }
}
