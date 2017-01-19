package view;
import model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AllUsersServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String name;
        String role;
        HttpSession session = request.getSession(false);
        if (session != null) {
            name = (String) session.getAttribute("name");
            role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                request.getRequestDispatcher("adminMenu.html").include(request, response);
            } else {
                request.getRequestDispatcher("menu.html").include(request, response);
            }
        } else {
            request.getRequestDispatcher("menu.html").include(request, response);
        }

        if (session != null) {
            name = (String) session.getAttribute("name");
            role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                out.print(UserViewer.getAllUsersPage());
                out.print("\n<br>\n" + SheetViewer.getSheetTable());
            }else{
                out.print("You don't have permission to view this page.");
            }
        } else {
            out.print("Please, login first.");
            request.getRequestDispatcher("login.html").include(request, response);
        }

        out.println("</body></html>");
        out.close();
    }
}
