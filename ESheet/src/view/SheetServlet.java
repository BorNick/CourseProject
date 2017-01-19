package view;
import model.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;

public class SheetServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String uri = request.getRequestURI();
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        String name;
        String role;
        Scanner fileScanner;
        HttpSession session = request.getSession(false);
        if (session != null) {
            name = (String) session.getAttribute("name");
            role = UserManager.checkRole(name);
            if (role.equals("admin")) {
                fileScanner = new Scanner(new File("../webapps/ESheet/adminMenu.html"));
            } else {
                fileScanner = new Scanner(new File("../webapps/ESheet/menu.html"));
            }
        } else {
            fileScanner = new Scanner(new File("../webapps/ESheet/menu.html"));
        }
        fileScanner.useDelimiter("[~]");
        String menu = "";
        while (fileScanner.hasNext()) {
            menu += fileScanner.next();
        }
        out.print(menu);
        fileScanner.close();

        if (uri.equals("/ESheet/Sheet/read")) {
            if (session != null) {
                name = (String) session.getAttribute("name");
                role = UserManager.checkRole(name);
                int id = Integer.valueOf(request.getParameter("id"));
                if (RoleManager.checkPermission(role, "read")) {
                    if (UserManager.checkSheet(name, id)) {
                        if (RoleManager.checkPermission(role, "update")) {
                            out.print("\n<a href=\"update?id=" + id + "\"><button>Update</button></a>");
                        }
                        out.print(SheetViewer.getSheetById(id));
                    } else {
                        out.print("You have no access to this sheet.");
                    }
                } else {
                    out.print("You have no permission to read this sheet.");
                }
            } else {
                out.print("Please login first");
                fileScanner = new Scanner(new File("../webapps/ESheet/login.html"));
                fileScanner.useDelimiter("[~]");
                String login = "";
                while (fileScanner.hasNext()) {
                    login += fileScanner.next();
                }
                out.print(login);
            }
        }
        if (uri.equals("/ESheet/Sheet/update")) {
            if (session != null) {
                name = (String) session.getAttribute("name");
                role = UserManager.checkRole(name);
                int id = Integer.valueOf(request.getParameter("id"));
                if (RoleManager.checkPermission(role, "update")) {
                    if (UserManager.checkSheet(name, id)) {
                        Scanner tMenuScanner = new Scanner(new File("../webapps/ESheet/teacherMenu.html"));
                        tMenuScanner.useDelimiter("[~]");
                        String tMenu = "";
                        while (tMenuScanner.hasNext()) {
                            tMenu += tMenuScanner.next();
                        }
                        out.print(tMenu);
                        tMenuScanner.close();
                        out.print(SheetViewer.getEditableSheetById(id));
                    } else {
                        out.print("You have no access to this sheet.");
                    }
                } else {
                    out.print("You have no permission to update this sheet.");
                }
            } else {
                out.print("Please login first");
                fileScanner = new Scanner(new File("../webapps/ESheet/login.html"));
                fileScanner.useDelimiter("[~]");
                String login = "";
                while (fileScanner.hasNext()) {
                    login += fileScanner.next();
                }
                out.print(login);
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
            if (RoleManager.checkPermission(role, "update")) {
                System.out.println("caught");
                String itemid = request.getParameter("itemid");
                String content = request.getParameter("content");
                String url = request.getParameter("id");
                System.out.println(url);
                String height = request.getParameter("height");
                String width = request.getParameter("width");
                int id = 0;
                int beg = 0;
                int end = url.length();
                for (int i = 0; i < url.length() - 1; i++) {
                    if (url.substring(i, i + 2).equals("id")) {
                        beg = i + 3;
                        for (int j = beg; j < url.length(); j++) {
                            if (url.charAt(i) == '&') {
                                end = j;
                            }
                        }
                        id = Integer.valueOf(url.substring(beg, end));
                        break;
                    }
                }
                //int id = Integer.valueOf(request.getParameter("id"));
                if (UserManager.checkSheet(name, id)) {
                    if (height != null && width != null) {
                        SheetManager.changeSize(id, Integer.valueOf(height), Integer.valueOf(width));
                        response.sendRedirect("http://localhost:8080/ESheet/Sheet/update?id=" + id);
                    }
                    if (content != null && itemid != null) {
                        SheetManager.updateCell(id, content, itemid);
                    }
                }
            }
        }
    }
}
