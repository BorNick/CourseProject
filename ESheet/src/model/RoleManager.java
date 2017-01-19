package model;
import java.io.*;
import java.util.*;

public class RoleManager {

    public static boolean checkPermission(String checkRole, String permission) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        int skip = 4;
        if (permission.equals("create")) {
            skip = 0;
        }
        if (permission.equals("read")) {
            skip = 1;
        }
        if (permission.equals("update")) {
            skip = 2;
        }
        if (permission.equals("delete")) {
            skip = 3;
        }
        while (fileScanner.hasNextLine()) {
            String role = fileScanner.nextLine();
            Scanner roleScanner = new Scanner(role);
            if (roleScanner.hasNext()) {
                if (roleScanner.next().equals(checkRole)) {
                    for (int i = 0; i < skip; i++) {
                        if (roleScanner.hasNextInt()) {
                            roleScanner.nextInt();
                        }
                    }
                    if (roleScanner.hasNextInt()) {
                        if (roleScanner.nextInt() == 1) {
                            fileScanner.close();
                            return true;
                        } else {
                            fileScanner.close();
                            return false;
                        }
                    }
                }
            }
        }
        fileScanner.close();
        return false;
    }
    public static void createRole(String rolename, int create, int read, int update, int delete) throws IOException{
        if(rolename.equals("admin")){
            return;
        }
        String newRoles = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        if(fileScanner.hasNextLine()){
            newRoles += fileScanner.nextLine();
        }
        while(fileScanner.hasNextLine()){
            String role = fileScanner.nextLine();
            Scanner roleScanner = new Scanner(role);
            if(roleScanner.hasNext()){
                if(roleScanner.next().equals(rolename)){
                    fileScanner.close();
                    return;
                }
            }
            newRoles += "\n" + role;
        }
        newRoles += "\n" + rolename + " " + create + " " + read + " " + update + " " + delete;
        fileScanner.close();
        FileWriter fw= new FileWriter("../webapps/ESheet/Roles.txt");
        fw.write(newRoles);
        fw.flush();
        fw.close();
    }
    public static void deleteRole(String delrole) throws IOException{
        if(delrole.equals("admin")){
            return;
        }
        String newRoles = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        if(fileScanner.hasNextLine()){
            newRoles += fileScanner.nextLine();
        }
        while(fileScanner.hasNextLine()){
            String role = fileScanner.nextLine();
            Scanner roleScanner = new Scanner(role);
            if(roleScanner.hasNext()){
                if(!roleScanner.next().equals(delrole)){
                    newRoles += "\n" + role;
                }
            }
        }
        fileScanner.close();
        FileWriter fw= new FileWriter("../webapps/ESheet/Roles.txt");
        fw.write(newRoles);
        fw.flush();
        fw.close();
    }
    public static LinkedList<String> getAllRoles() throws FileNotFoundException{
        LinkedList<String> allRoles = new LinkedList<String>();
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        while(fileScanner.hasNextLine()){
            Scanner roleScanner = new Scanner(fileScanner.nextLine());
            if(roleScanner.hasNext()){
                allRoles.add(roleScanner.next());
            }
        }
        fileScanner.close();
        return allRoles;
    }
}
