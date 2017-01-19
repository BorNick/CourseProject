package view;
import java.io.*;
import java.util.*;

public class UserViewer {
    public static String getAllUsersPage() throws FileNotFoundException {
        String htmlString = "<td><form action=\"http://localhost:8080/ESheet/EditUser\">\n<input type=\"text\" name=\"action\" value=\"create\" hidden>\n"
                + "<input type=\"submit\" value=\"Create User\">\n</form>";
        htmlString += "\n<table>\n<tr>\n<th>name</th>\n<th>password</th>\n<th>role</th>\n<th>sheets</th>\n<th> </th>\n<th> </th>\n</tr>";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            htmlString += "\n<tr>";
            String currentUser = "";
            String currentPassword = "";
            String currentRole = "";
            String currentSheets = "";
            if (userScanner.hasNext()) {
                currentUser = userScanner.next();
                htmlString += "\n<td>" + currentUser + "</td>";
            }
            if (userScanner.hasNext()) {
                currentPassword = userScanner.next();
                htmlString += "\n<td>" + currentPassword + "</td>";
            }
            if (userScanner.hasNext()) {
                currentRole = userScanner.next();
                htmlString += "\n<td>" + currentRole + "</td>";
            }
            htmlString += "\n<td>";
            while (userScanner.hasNextInt()) {
                currentSheets += userScanner.next();
                if (userScanner.hasNextInt()) {
                    currentSheets += ", ";
                } else {
                    currentSheets += ".";
                }
            }
            htmlString += currentSheets;
            htmlString += "\n</td>";
            if (currentUser.equals("admin")) {
                htmlString += "\n<td>" + " " + "</td>";
                htmlString += "\n<td>" + " " + "</td>";
            } else {
                htmlString += "\n<td><form action=\"http://localhost:8080/ESheet/EditUser\">\n"
                        + "  <input type=\"text\" name=\"action\" value=\"edit\" hidden>\n"
                        + "  <input type=\"text\" name=\"username\" value=\"" + currentUser + "\" hidden>\n"
                        + "  <input type=\"text\" name=\"password\" value=\"" + currentPassword + "\" hidden>\n"
                        + "  <input type=\"text\" name=\"role\" value=\"" + currentRole + "\" hidden>\n"
                        + "  <input type=\"text\" name=\"sheets\" value=\"" + currentSheets + "\" hidden>\n"
                        + "  <input type=\"submit\" value=\"Edit\">\n</form> </td>";
                htmlString += "\n<td><form action=\"http://localhost:8080/ESheet/EditUser\" method=\"post\">\n"
                        + "  <input type=\"text\" name=\"action\" value=\"delete\" hidden>\n"
                        + "  <input type=\"text\" name=\"username\" value=\"" + currentUser + "\" hidden>\n"
                        + "  <input type=\"submit\" value=\"Delete\">\n</form> </td>";
            }
            htmlString += "\n</tr>";
            currentUser = "";
        }
        htmlString += "\n</table>";
        return htmlString;
    }
}
