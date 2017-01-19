package model;
import java.io.*;
import java.util.*;

public class UserManager {

    public static boolean checkPassword(String name, String password) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(name)) {
                    if (userScanner.hasNext()) {
                        if (userScanner.next().equals(password)) {
                            fileScanner.close();
                            return true;
                        } else {
                            fileScanner.close();
                            return false;
                        }
                    } else {
                        fileScanner.close();
                        return false;
                    }
                }
            }
        }
        fileScanner.close();
        return false;
    }

    public static String checkRole(String name) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(name)) {
                    if (userScanner.hasNext()) {
                        userScanner.next();
                        if (userScanner.hasNext()) {
                            fileScanner.close();
                            return userScanner.next();
                        } else {
                            fileScanner.close();
                            return null;
                        }
                    } else {
                        fileScanner.close();
                        return null;
                    }
                }
            }
        }
        fileScanner.close();
        return null;
    }

    public static LinkedList<Integer> checkSheets(String name) throws FileNotFoundException {
        LinkedList<Integer> sheets = new LinkedList<Integer>();
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(name)) {
                    if (userScanner.hasNext()) {
                        userScanner.next();
                        if (userScanner.hasNext()) {
                            userScanner.next();
                            while (userScanner.hasNextInt()) {
                                sheets.add(userScanner.nextInt());
                            }
                        } else {
                            fileScanner.close();
                            return sheets;
                        }
                    } else {
                        fileScanner.close();
                        return sheets;
                    }
                }
            }
        }
        fileScanner.close();
        return sheets;
    }

    public static boolean checkSheet(String name, int id) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(name)) {
                    if (userScanner.hasNext()) {
                        userScanner.next();
                        if (userScanner.hasNext()) {
                            userScanner.next();
                            while (userScanner.hasNextInt()) {
                                if (userScanner.nextInt() == id) {
                                    fileScanner.close();
                                    return true;
                                }
                            }
                            fileScanner.close();
                            return false;
                        }
                    } else {
                        fileScanner.close();
                        return false;
                    }
                }
            }
        }
        fileScanner.close();
        return false;
    }

    public static void addSheetToUser(String name, int id) throws IOException {
        String newUsers = "";
        boolean line1 = true;
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            if (line1) {
                newUsers += user;
                line1 = false;
            } else {
                newUsers += "\n" + user;
            }
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(name)) {
                    if (userScanner.hasNext()) {
                        userScanner.next();
                        if (userScanner.hasNext()) {
                            userScanner.next();
                            while (userScanner.hasNextInt()) {
                                if (userScanner.nextInt() == id) {
                                    fileScanner.close();
                                    return;
                                }
                            }
                            newUsers += " " + id;
                        }
                    }
                }
            }
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Users.txt");
        fw.write(newUsers);
        fw.flush();
        fw.close();
    }

    public static void deleteSheet(int id) throws IOException {
        String newUsers = "";
        boolean line1 = true;
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            if (!line1) {
                newUsers += "\n";
            }
            line1 = false;
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                newUsers += userScanner.next();
                if (userScanner.hasNext()) {
                    newUsers += " " + userScanner.next();
                    if (userScanner.hasNext()) {
                        newUsers += " " + userScanner.next();
                        while (userScanner.hasNextInt()) {
                            int sheetid = userScanner.nextInt();
                            if (sheetid != id) {
                                newUsers += " " + sheetid;
                            }
                        }
                    }
                }
            }
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Users.txt");
        fw.write(newUsers);
        fw.flush();
        fw.close();
    }


    public static void createUser(String username, String password, String role, LinkedList<Integer> sheets) throws IOException {
        String newUsers = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(username)) {
                    fileScanner.close();
                    return;
                }
            }
            newUsers += user + "\n";
        }
        newUsers += username + " " + password + " " + role;
        ListIterator<Integer> iter = sheets.listIterator();
        while (iter.hasNext()) {
            newUsers += " " + iter.next();
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Users.txt");
        fw.write(newUsers);
        fw.flush();
        fw.close();
    }

    public static void editUser(String username, String password, String role, LinkedList<Integer> sheets) throws IOException {
        String newUsers = "";
        boolean line1 = true;
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            if(!line1){
                newUsers += "\n";
            }
            line1 = false;
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (userScanner.next().equals(username)) {
                    newUsers += username + " " + password + " " + role;
                    ListIterator<Integer> iter = sheets.listIterator();
                    while (iter.hasNext()) {
                        newUsers += " " + iter.next();
                    }
                } else {
                    newUsers += user;
                }
            }
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Users.txt");
        fw.write(newUsers);
        fw.flush();
        fw.close();
    }

    public static void deleteUser(String username) throws IOException {
        String newUsers = "";
        boolean line1 = true;
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        while (fileScanner.hasNextLine()) {
            String user = fileScanner.nextLine();
            Scanner userScanner = new Scanner(user);
            if (userScanner.hasNext()) {
                if (!(userScanner.next().equals(username))) {
                    if (line1) {
                        newUsers += user;
                        line1 = false;
                    } else {
                        newUsers += "\n" + user;
                    }
                }
            }
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Users.txt");
        fw.write(newUsers);
        fw.flush();
        fw.close();
    }
}
