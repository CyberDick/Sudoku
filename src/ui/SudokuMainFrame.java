package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

/**
 * Created by haotianliang on 16/05/2017.
 * Sudoku 游戏主界面
 */
public class SudokuMainFrame extends JFrame {

    private SudokuCanvers sudokuCanvers;

    public SudokuMainFrame() {
        init();
        addCanvers();
        addComponent();
    }

    private void addCanvers() {

        sudokuCanvers = new SudokuCanvers();
        sudokuCanvers.setBorder(new TitledBorder("主游戏区"));

        this.add(sudokuCanvers, BorderLayout.CENTER);
    }

    private void addComponent() {
        JPanel panel = new JPanel();

        addPanelMsg(panel);

        this.add(panel,BorderLayout.EAST);
    }
    private void addPanelMsg(JPanel panel){
        JLabel label = new JLabel("hhhhhh");
        panel.add(label);
    }
    private void init() {
        this.setSize(600, 500);
        this.setLocation(500, 200);
        this.setTitle("数独游戏");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
