package ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by haotianliang on 16/05/2017.
 * Sudoku 游戏的单元格
 */
public class SudokuCell extends JButton {

    public SudokuCell() {
        this.setSize(50,50);
        Font font = new Font("",Font.ITALIC,30);
        this.setFont(font);
        this.setBackground(new Color(255,128,128));
        this.setForeground(new Color(103,200,200));
    }
}
