package view;
import model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class AllSheetsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html>");

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
            if (RoleManager.checkPermission(role, "create")) {
                request.getRequestDispatcher("createSheetForm.html").include(request, response);
            }
            if (RoleManager.checkPermission(role, "read")) {
                LinkedList<Integer> sheets = UserManager.checkSheets(name);
                ListIterator<Integer> iter = sheets.listIterator();
                while (iter.hasNext()) {
                    int id = iter.next();
                    out.println("\n<div class=\"sheets\">\n<a href=\"Sheet/read?id=" + id + "\"><h2>" + SheetManager.getNameById(id) + "</h2></a>\n");
                    if (RoleManager.checkPermission(role, "delete")) {
                        out.println("\n<form action=\"http://localhost:8080/ESheet/AllSheets\" method=\"post\">\n<input type=\"text\" name=\"sheetid\" value=\"" + id
                            + "\" hidden>\n<input type=\"submit\" value=\"Delete\">\n</form> \n");
                    }
                    out.println("</div>");
                }
            }else{
                out.print("You don't have permixxion to read sheets.");
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
            String subject = request.getParameter("subject");
            String height = request.getParameter("height");
            String width = request.getParameter("width");
            String sheetid = request.getParameter("sheetid");
            if(sheetid!=null){
                if (RoleManager.checkPermission(role, "delete")) {
                    System.out.println("Sheet id = " + sheetid);
                    SheetManager.deleteSheet(Integer.valueOf(sheetid));
                    UserManager.deleteSheet(Integer.valueOf(sheetid));
                    response.sendRedirect("http://localhost:8080/ESheet/AllSheets");
                }
            }
            if(subject!=null && height!=null && width!=null){
                if (RoleManager.checkPermission(role, "create")) {
                    int id =SheetManager.createSheet( subject, Integer.valueOf(height), Integer.valueOf(width));
                    UserManager.addSheetToUser(name, id);
                    if(!(name.equals("admin"))){
                        UserManager.addSheetToUser("admin", id);
                    }
                    response.sendRedirect("http://localhost:8080/ESheet/AllSheets");
                }
            }
        }
    }
}
