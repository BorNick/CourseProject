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

public class SheetManagerTest {

    @BeforeClass
    public static void setUpClass() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(new File("../webapps/ESheet/Sheets/SheetList.txt")));
        bw.write("");
        bw.close();
    }
    
    @AfterClass
    public static void tearDownClass() throws IOException {
        Path path = FileSystems.getDefault().getPath("../webapps/ESheet/Sheets/1.txt");
        Files.deleteIfExists(path);
        path = FileSystems.getDefault().getPath("../webapps/ESheet/Sheets/SheetList.txt");
        Files.deleteIfExists(path);
    }

    @Before
    public void setUp() throws IOException{
        SheetManager.createSheet("test", 3, 3);
    }
    
    @After
    public void tearDown() throws IOException{
        SheetManager.deleteSheet(1);
    }

    @Test
    public void testCreateSheet() throws IOException{
        SheetManager.deleteSheet(1);
        SheetManager.createSheet("test", 3, 3);
        String sheet = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        while(fileScanner.hasNextLine()){
            sheet = fileScanner.nextLine();
        }
        fileScanner.close();
        assertEquals(sheet, "1~test");
        try{
            Scanner s = new Scanner(new File("../webapps/ESheet/Sheets/1.txt"));
        }catch(IOException e){Assert.fail();}
    }

    @Test
    public void testGetNameById() throws IOException{
        assertEquals(SheetManager.getNameById(1),"test");
    }

    @Test
    public void testUpdateCell() throws IOException{
        SheetManager.updateCell(1, "5", "0_0");
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/1.txt"));
        fileScanner.useDelimiter("[~]");
        String cell = "";
        if(fileScanner.hasNext()){
            cell = fileScanner.next();
        }
        assertEquals(cell, "5");
    }

    @Test
    public void testChangeSize() throws IOException{
        SheetManager.changeSize(1, 3, 5);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/1.txt"));
        String row = "";
        if(fileScanner.hasNextLine()){
            row = fileScanner.nextLine();
        }
        Scanner rowScanner = new Scanner(row);
        rowScanner.useDelimiter("[~]");
        int width = 0;
        while(rowScanner.hasNext()){
            rowScanner.next();
            width++;
        }
        assertEquals(width, 5);
    }

    @Test
    public void testDeleteSheet() throws IOException{
        SheetManager.deleteSheet(1);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        if(fileScanner.hasNextLine()){
            if(!fileScanner.nextLine().equals(""))
                Assert.fail();
        }
        try{
            Scanner s = new Scanner(new File("../webapps/ESheet/Sheets/1.txt"));
            Assert.fail();
        }catch(IOException e){}
    }
}
