package view;
import model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AllRolesServlet extends HttpServlet {

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
                out.print(RoleViewer.getAllRolesPage());
                
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
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session != null) {
            String name = (String) session.getAttribute("name");
            String role = UserManager.checkRole(name);
            if(role.equals("admin")){
                String action = request.getParameter("action");
                String rolename = request.getParameter("rolename");
                if(action.equals("create")){
                    String createString = request.getParameter("create");
                    String readString = request.getParameter("read");
                    String updateString = request.getParameter("update");
                    String deleteString = request.getParameter("delete");
                    int create;
                    int read;
                    int update;
                    int delete;
                    if(createString != null){
                        create = 1;
                    }else{
                        create = 0;
                    }
                    if(readString != null){
                        read = 1;
                    }else{
                        read = 0;
                    }
                    if(updateString != null){
                        update = 1;
                    }else{
                        update = 0;
                    }
                    if(deleteString != null){
                        delete = 1;
                    }else{
                        delete = 0;
                    }
                    RoleManager.createRole(rolename, create, read, update, delete);
                    response.sendRedirect("http://localhost:8080/ESheet/AllRoles");
                }
                if(action.equals("delete")){
                    RoleManager.deleteRole(rolename);
                    response.sendRedirect("http://localhost:8080/ESheet/AllRoles");
                }
            }
        }
    }
}
