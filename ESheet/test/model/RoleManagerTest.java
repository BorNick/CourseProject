package model;
import java.io.*;
import java.util.*;
import java.nio.file.*;
import org.junit.Test;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import static org.junit.Assert.assertEquals;

public class RoleManagerTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("../webapps/ESheet/Roles.txt")));
        bw.write("");
        bw.close();
    }
    
    @AfterClass
    public static void tearDownClass() throws IOException {
        Path path = FileSystems.getDefault().getPath("../webapps/ESheet/Roles.txt");
        Files.deleteIfExists(path);
    }

    @Before
    public void setUp() throws IOException{
        RoleManager.createRole("test", 1, 1, 1, 0);
    }
    
    @After
    public void tearDown() throws IOException{
        RoleManager.deleteRole("test");
    }

    @Test
    public void testCreateRole() throws IOException{
        RoleManager.deleteRole("test");
        RoleManager.createRole("test", 1, 1, 1, 0);
        String role = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        while(fileScanner.hasNextLine()){
            role = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals(role, "test 1 1 1 0");
    }

    @Test
    public void testCheckPermission() throws IOException{
        assertEquals(RoleManager.checkPermission("test", "create"), true);
    }

    @Test
    public void testGetAllRoles() throws IOException{
        LinkedList<String> allRoles = new LinkedList<String>();
        allRoles.add("test");
        assertEquals(RoleManager.getAllRoles(), allRoles);
    }

    @Test
    public void testDeleteRole() throws IOException{
        RoleManager.deleteRole("test");
        String role = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Roles.txt"));
        while(fileScanner.hasNextLine()){
            role = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals(role, "");
    }
}
