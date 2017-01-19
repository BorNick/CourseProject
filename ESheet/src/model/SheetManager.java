package model;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class SheetManager {

    public static String getNameById(int id) throws FileNotFoundException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        while (fileScanner.hasNextLine()) {
            String sheet = fileScanner.nextLine();
            Scanner sheetScanner = new Scanner(sheet);
            sheetScanner.useDelimiter("[~]");
            if (sheetScanner.hasNextInt()) {
                if (sheetScanner.nextInt() == id) {
                    if (sheetScanner.hasNext()) {
                        fileScanner.close();
                        return sheetScanner.next();
                    }
                }
            }
        }
        fileScanner.close();
        return null;
    }

    public static int getXFromString(String coords) {
        Scanner coordScanner = new Scanner(coords);
        coordScanner.useDelimiter("[_]");
        if (coordScanner.hasNextInt()) {
            return coordScanner.nextInt();
        } else {
            return -1;
        }
    }

    public static int getYFromString(String coords) {
        Scanner coordScanner = new Scanner(coords);
        coordScanner.useDelimiter("[_]");
        if (coordScanner.hasNextInt()) {
            coordScanner.nextInt();
            if (coordScanner.hasNextInt()) {
                return coordScanner.nextInt();
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }

    public static void updateCell(int id, String value, String coords) throws IOException {
        int x = getXFromString(coords);
        int y = getYFromString(coords);
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/" + id + ".txt"));
        String newSheet = "";
        for (int i = 0; i < x; i++) {
            if (fileScanner.hasNextLine()) {
                newSheet += fileScanner.nextLine() + "\n";
            }
        }
        if (fileScanner.hasNextLine()) {
            String row = fileScanner.nextLine();
            Scanner rowScanner = new Scanner(row);
            rowScanner.useDelimiter("[~]");
            for (int j = 0; j < y; j++) {
                if (rowScanner.hasNext()) {
                    newSheet += rowScanner.next() + "~";
                }
            }
            if (rowScanner.hasNext()) {
                rowScanner.next();
                newSheet += value;
            }
            while (rowScanner.hasNext()) {
                newSheet += "~" + rowScanner.next();
            }
        }
        while (fileScanner.hasNextLine()) {
            newSheet += "\n" + fileScanner.nextLine();
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Sheets/" + id + ".txt");
        fw.write(newSheet);
        fw.flush();
        fw.close();
    }

    public static void changeSize(int id, int height, int width) throws IOException {
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/" + id + ".txt"));
        String newSheet = "";
        for (int i = 0; i < height; i++) {
            if (fileScanner.hasNextLine()) {
                String row = fileScanner.nextLine();
                Scanner rowScanner = new Scanner(row);
                rowScanner.useDelimiter("[~]");
                for (int j = 0; j < width; j++) {
                    if (rowScanner.hasNext()) {
                        if (j == 0) {
                            if(i != 0){
                                newSheet += "\n";
                            }
                            newSheet += rowScanner.next();
                        } else {
                            if (j == 0 && i != 0) {
                                newSheet += "\n";
                            }
                            newSheet += "~" + rowScanner.next();
                        }
                    } else if (j == 0) {
                        newSheet += " ";
                    } else {
                        newSheet += "~" + " ";
                    }
                }
            } else {
                for (int j = 0; j < width; j++) {
                    if (j == 0) {
                        if(i != 0){
                            newSheet += "\n";
                        }
                        newSheet += " ";
                    } else {
                        newSheet += "~" + " ";
                    }
                }
            }
        }
        fileScanner.close();
        FileWriter fw = new FileWriter("../webapps/ESheet/Sheets/" + id + ".txt");
        fw.write(newSheet);
        fw.flush();
        fw.close();
    }
    public static int createSheet(String name, int height, int width) throws IOException{
        String newList = "";
        LinkedList<Integer> ids = new LinkedList<Integer>();
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        while (fileScanner.hasNextLine()) {
            String sheet = fileScanner.nextLine();
            newList +=sheet + "\n";
            Scanner sheetScanner = new Scanner(sheet);
            sheetScanner.useDelimiter("[~]");
            if (sheetScanner.hasNextInt()) {
                ids.add(sheetScanner.nextInt());
            }
        }
        fileScanner.close();
        
        int id = 0;
        int idCand = 0;
        while(id == 0){
            idCand++;
            boolean idExists = false;
            ListIterator<Integer> iter = ids.listIterator();
            while(iter.hasNext()){
                if(iter.next()==idCand){
                    idExists = true;
                    break;
                }
            }
            if(!idExists){
                id = idCand;
            }
        }
        newList += id + "~" + name;
        FileWriter fw = new FileWriter("../webapps/ESheet/Sheets/SheetList.txt");
        fw.write(newList);
        fw.flush();
        fw.close();
        String sheet = " ";
        for(int j = 1; j < width; j++){
            sheet += "~ ";
        }
        for(int i = 1; i < height; i++){
            sheet +="\n ";
            for(int j = 1; j < width; j++){
                sheet += "~ ";
            }
        }
        fw= new FileWriter("../webapps/ESheet/Sheets/" + id + ".txt");
        fw.write(sheet);
        fw.flush();
        fw.close();
        return id;
    }
    public static void deleteSheet(int id) throws IOException{
        String newList = "";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        if(fileScanner.hasNextLine()){
            String sheet1 = fileScanner.nextLine();
            Scanner sheet1Scanner = new Scanner(sheet1);
            sheet1Scanner.useDelimiter("[~]");
            if(sheet1Scanner.hasNextInt()){
                if(!(sheet1Scanner.nextInt()==id)){
                    newList += sheet1;
                }
            }
        }
        while (fileScanner.hasNextLine()) {
            String sheet = fileScanner.nextLine();
            Scanner sheetScanner = new Scanner(sheet);
            sheetScanner.useDelimiter("[~]");
            if (sheetScanner.hasNextInt()) {
                if(!(sheetScanner.nextInt()==id)){
                    newList += "\n" + sheet;
                }
            }
        }
        fileScanner.close();
        FileWriter fw= new FileWriter("../webapps/ESheet/Sheets/SheetList.txt");
        fw.write(newList);
        fw.flush();
        fw.close();
        Path path = FileSystems.getDefault().getPath("../webapps/ESheet/Sheets/", id + ".txt");
        Files.deleteIfExists(path);
    }
}
