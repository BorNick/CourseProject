package view;
import model.*;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class EditUserServlet extends HttpServlet {
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String name = (String) session.getAttribute("name");
            String role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                request.getRequestDispatcher("adminMenu.html").include(request, response);
                String action = request.getParameter("action");
                if (action.equals("create")) {
                    out.print("<h2>Create</h2><form action=\"http://localhost:8080/ESheet/EditUser\" method=\"post\">\n"
                            + "  <input type=\"text\" name=\"action\" value=\"create\" hidden>\n"
                            + "  User name:\n"
                            + "  <input type=\"text\" name=\"username\" required>\n"
                            + "  <br>\n"
                            + "  Password:\n"
                            + "  <input type=\"text\" name=\"password\" required>\n"
                            + "  <br>\n"
                            + "  Role:<select name=\"role\"> ");
                    LinkedList<String> allRoles = RoleManager.getAllRoles();
                    ListIterator<String> iter = allRoles.listIterator();
                    while (iter.hasNext()) {
                        String currentRole = iter.next();
                        out.print("\n<option value=\"" + currentRole + "\"");
                        if (currentRole.equals("student")) {
                            out.print(" selected");
                        }
                        out.print("> " + currentRole + "</option>");
                    }
                    out.print("</select><br>Enter sheet ids sepparated by commas<br>\n"
                            + "  <input type=\"text\" name=\"sheets\">\n"
                            + "  <br>\n"
                            + "  <input type=\"submit\" value=\"Create User\">\n"
                            + "</form>");
                }
                if (action.equals("edit")) {
                    String username = request.getParameter("username");
                    String password = request.getParameter("password");
                    String roleString = request.getParameter("role");
                    String sheets = request.getParameter("sheets");
                    out.print("<h2>Edit</h2><form action=\"http://localhost:8080/ESheet/EditUser\" method=\"post\">\n"
                            + "  <input type=\"text\" name=\"action\" value=\"edit\" hidden>\n"
                            + "  User name:\n"
                            + "  <input type=\"text\" name=\"username\" value=\"" + username + "\" readonly>\n"
                            + "  <br>\n"
                            + "  Password:\n"
                            + "  <input type=\"text\" name=\"password\" value=\"" + password + "\" required>\n"
                            + "  <br>\n"
                            + "  Role:<select name=\"role\"> ");
                    LinkedList<String> allRoles = RoleManager.getAllRoles();
                    ListIterator<String> iter = allRoles.listIterator();
                    while (iter.hasNext()) {
                        String currentRole = iter.next();
                        out.print("\n<option value=\"" + currentRole + "\"");
                        if (currentRole.equals(roleString)) {
                            out.print(" selected");
                        }
                        out.print("> " + currentRole + "</option>");
                    }
                    out.print("</select><br>Enter sheet ids sepparated by commas<br>\n"
                            + "  <input type=\"text\" name=\"sheets\" value=\"" + sheets + "\">\n"
                            + "  <br>\n"
                            + "  <input type=\"submit\" value=\"Edit User\">\n"
                            + "</form>");
                }
                out.print("<br>" + SheetViewer.getSheetTable());
            }
        }
        
        out.println("</body></html>");
        out.close();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session != null) {
            String name = (String) session.getAttribute("name");
            String role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                String action = request.getParameter("action");
                String username = request.getParameter("username");
                if (action.equals("create")) {
                    String password = request.getParameter("password");
                    String roleString = request.getParameter("role");
                    String sheetsString = request.getParameter("sheets");
                    UserManager.createUser(username, password, roleString, getIntsFromString(sheetsString));
                    response.sendRedirect("http://localhost:8080/ESheet/AllUsers");
                }
                if (action.equals("edit")) {
                    String password = request.getParameter("password");
                    String roleString = request.getParameter("role");
                    String sheetsString = request.getParameter("sheets");
                    UserManager.editUser(username, password, roleString, getIntsFromString(sheetsString));
                    response.sendRedirect("http://localhost:8080/ESheet/AllUsers");
                }
                if (action.equals("delete")) {
                    UserManager.deleteUser(username);
                    response.sendRedirect("http://localhost:8080/ESheet/AllUsers");
                }
            }
        }
    }
    
    private LinkedList<Integer> getIntsFromString(String numbers) {
        LinkedList<Integer> intNumbers = new LinkedList<Integer>();
        numbers = numbers.replace(" ", "");
        numbers = numbers.replace(".", "");
        Scanner numberScanner = new Scanner(numbers);
        numberScanner.useDelimiter("[,]");
        while (numberScanner.hasNextInt()) {
            intNumbers.add(numberScanner.nextInt());
        }
        return intNumbers;
    }
}
