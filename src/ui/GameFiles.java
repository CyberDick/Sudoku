package ui;

import javax.swing.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by haotianliang on 28/05/2017.
 */
public class GameFiles {
    public static void saveGame(String filename) throws IOException{
        PrintWriter out = new PrintWriter(filename,"UTF-8");
        out.print(SudokuCanvers.maps);
    }

    public static void loadGame(String filename) throws IOException{
        Scanner in = new Scanner(Paths.get(filename),"UTF-8");

    }
}
