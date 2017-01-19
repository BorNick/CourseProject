package view;
import model.*;
import java.io.*;
import java.util.*;

public class SheetViewer {
    public static String getSheetById(int id) throws FileNotFoundException {
        String htmlString = "\n<h2>" + SheetManager.getNameById(id) + "</h2>\n<table style=\"width:100%;\">";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/" + id + ".txt"));
        if (fileScanner.hasNextLine()) {
            String header = fileScanner.nextLine();
            Scanner headerScanner = new Scanner(header);
            headerScanner.useDelimiter("[~]");
            htmlString += "\n  <tr>";
            while (headerScanner.hasNext()) {
                htmlString += "\n    <th>" + headerScanner.next() + "</th>";
            }
            htmlString += "\n  <tr>";
        }
        while (fileScanner.hasNextLine()) {
            String student = fileScanner.nextLine();
            Scanner studentScanner = new Scanner(student);
            studentScanner.useDelimiter("[~]");
            if (studentScanner.hasNext()) {
                htmlString += "\n  <tr>\n    <th>" + studentScanner.next() + "</th>";
                while (studentScanner.hasNext()) {
                    htmlString += "\n    <td>" + studentScanner.next() + "</td>";
                }
                htmlString += "\n  <tr>";
            }
        }
        htmlString += "\n<table>";
        fileScanner.close();
        return htmlString;
    }
    public static String getEditableSheetById(int id) throws FileNotFoundException {
        Scanner scriptScanner = new Scanner(new File("../webapps/ESheet/script.html"));
        scriptScanner.useDelimiter("[~]");
        String script = "";
        while (scriptScanner.hasNext()) {
            script += scriptScanner.next();
        }
        scriptScanner.close();

        int i = 0;
        int j = 0;
        String htmlString = "\n" + script + "\n<h2>" + SheetManager.getNameById(id) + "</h2>\n<table style=\"width:100%;\"id=\"editable\">";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/" + id + ".txt"));
        if (fileScanner.hasNextLine()) {
            String header = fileScanner.nextLine();
            Scanner headerScanner = new Scanner(header);
            headerScanner.useDelimiter("[~]");
            htmlString += "\n  <tr>";
            while (headerScanner.hasNext()) {
                htmlString += "\n    <th id = \"" + i + "_" + j + "\">" + headerScanner.next() + "</th>";
                j++;
            }
            i++;
            j = 0;
            htmlString += "\n  <tr>";
        }
        while (fileScanner.hasNextLine()) {
            String student = fileScanner.nextLine();
            Scanner studentScanner = new Scanner(student);
            studentScanner.useDelimiter("[~]");
            if (studentScanner.hasNext()) {
                htmlString += "\n  <tr>\n    <th id = \"" + i + "_" + j + "\">" + studentScanner.next() + "</th>";
                j++;
                while (studentScanner.hasNext()) {
                    htmlString += "\n    <td id = \"" + i + "_" + j + "\">" + studentScanner.next() + "</td>";
                    j++;
                }
                htmlString += "\n  <tr>";
                i++;
                j = 0;
            }
        }
        htmlString += "\n<table>";
        fileScanner.close();
        return htmlString;
    }
    public static String getSheetTable() throws FileNotFoundException{
        String htmlString = "\n<table>";
        htmlString += "\n<tr>\n<th>id</th>\n<th>subject</th>\n</tr>";
        Scanner fileScanner = new Scanner(new File("../webapps/ESheet/Sheets/SheetList.txt"));
        while(fileScanner.hasNextLine()){
            String sheet = fileScanner.nextLine();
            Scanner sheetScanner = new Scanner(sheet);
            sheetScanner.useDelimiter("[~]");
            htmlString += "\n<tr>";
            if(sheetScanner.hasNextInt()){
                htmlString += "\n<td>" + sheetScanner.nextInt() + "</td>";
            }
            if(sheetScanner.hasNext()){
                htmlString += "\n<td>" + sheetScanner.next() + "</td>";
            }
            htmlString += "\n</tr>";
        }
        htmlString += "\n</table>";
        return htmlString;
    }
}
