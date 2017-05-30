package app;

import ui.SudokuMainFrame;
import ui.WelcomeFrame;

import javax.swing.*;
import java.awt.*;

/**
 * Created by haotianliang on 16/05/2017.
 */
public class AppStart {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JFrame frame = new WelcomeFrame();
            frame.setSize(300,270);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        });
    }
}
