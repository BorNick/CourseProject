package view;
import java.io.*;
import java.util.*;

public class RoleViewer {
    public static String getAllRolesPage() throws FileNotFoundException{
        String htmlString = "<a href=\"http://localhost:8080/ESheet/createRole.html\"><button>Create Role</button></a>";
        htmlString += "\n<table>\n<tr>\n<th>role</th>\n<th>create</th>\n<th>read</th>\n<th>update</th>\n<th>delete</th>\n<th> </th>\n</tr>";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        while (fileScanner.hasNextLine()) {
            String role = fileScanner.nextLine();
            Scanner roleScanner = new Scanner(role);
            htmlString += "\n<tr>";
            String currentRole = "";
            if(roleScanner.hasNext()){
                currentRole = roleScanner.next();
                htmlString += "\n<td>" + currentRole +"</td>";
            }
            while(roleScanner.hasNext()){
                htmlString += "\n<td>" + roleScanner.next() +"</td>";
            }
            if(currentRole.equals("admin")){
                htmlString += "\n<td>" + " " +"</td>";
            }else{
                htmlString += "\n<td><form action=\"http://localhost:8080/ESheet/AllRoles\" method=\"post\">\n" +
"  <input type=\"text\" name=\"action\" value=\"delete\" hidden>\n" +
"  <input type=\"text\" name=\"rolename\" value=\"" + currentRole +"\" hidden>\n" +
"  <input type=\"submit\" value=\"Delete\">\n</form> </td>";
            }
            htmlString += "\n</tr>";
            currentRole = "";
        }
        htmlString += "\n</table>";
        return htmlString;
    }
}
