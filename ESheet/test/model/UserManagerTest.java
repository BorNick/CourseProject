package model;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.junit.Test;
import org.junit.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class UserManagerTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("../webapps/ESheet/Users.txt")));
        bw.write("admin admin admin 1 2 3");
        bw.close();
    }
    
    @AfterClass
    public static void tearDownClass() throws IOException{
        Path path = FileSystems.getDefault().getPath("../webapps/ESheet/Users.txt");
        Files.deleteIfExists(path);
    }
    
    @Before
    public void setUp() throws IOException{
        LinkedList<Integer> sheets = new LinkedList<Integer>();
        sheets.add(2);
        UserManager.createUser("test", "test", "student", sheets);
    }
    
    @After
    public void tearDown() throws IOException{
        UserManager.deleteUser("test");
    }

    @Test
    public void testCreateUser() throws IOException{
        UserManager.deleteUser("test");
        LinkedList<Integer> sheets = new LinkedList<Integer>();
        sheets.add(2);
        UserManager.createUser("test", "test", "student", sheets);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        if(fileScanner.hasNextLine()){
            fileScanner.nextLine();
        }
        String user = "";
        if(fileScanner.hasNextLine()){
            user = fileScanner.nextLine();
        }
        assertEquals("test test student 2", user);
    }

    @Test
    public void testEditUser() throws IOException{
        LinkedList<Integer> sheets = new LinkedList<Integer>();
        sheets.add(2);
        sheets.add(3);
        UserManager.editUser("test", "test", "student", sheets);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        if(fileScanner.hasNextLine()){
            fileScanner.nextLine();
        }
        String user = "";
        if(fileScanner.hasNextLine()){
            user = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals("test test student 2 3", user);
    }

    @Test
    public void testCheckPassword() throws IOException{
        assertEquals(true, UserManager.checkPassword("test", "test"));
    }

    @Test
    public void testCheckRole() throws IOException{
        assertEquals("student", UserManager.checkRole("test"));
    }

    @Test
    public void testCheckSheets() throws IOException{
        LinkedList<Integer> sheets = new LinkedList<Integer>();
        sheets.add(2);
        assertEquals(sheets, UserManager.checkSheets("test"));
    }

    @Test
    public void testCheckSheet() throws IOException{
        assertEquals(true, UserManager.checkSheet("test", 2));
    }

    @Test
    public void testAddSheetToUser() throws IOException{
        UserManager.addSheetToUser("test", 3);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        if(fileScanner.hasNextLine()){
            fileScanner.nextLine();
        }
        String user = "";
        if(fileScanner.hasNextLine()){
            user = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals("test test student 2 3", user);
    }

    @Test
    public void testDeleteSheet() throws IOException{
        UserManager.addSheetToUser("test", 7);
        UserManager.deleteSheet(7);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        if(fileScanner.hasNextLine()){
            fileScanner.nextLine();
        }
        String user = "";
        if(fileScanner.hasNextLine()){
            user = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals("test test student 2", user);
    }

    @Test
    public void testDeleteUser() throws IOException{
        UserManager.deleteUser("test");
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Users.txt"));
        if(fileScanner.hasNextLine()){
            fileScanner.nextLine();
        }
        String user = "";
        if(fileScanner.hasNextLine()){
            user = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals("", user);
    }
}
