package ui;

import src.SudokuHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by haotianliang on 16/05/2017.
 */
public class SudokuCanvers extends JPanel  {
    SudokuCell[][] cells;
    int [][]maps = new int[9][9];

    public SudokuCanvers() {
        maps = SudokuHelper.getMap();
        this.setLayout(null);

        cells = new SudokuCell[9][9];
        for (int i = 0 ; i < 9 ; i++){
            for (int j = 0 ; j < 9 ; j++){
                cells[i][j] = new SudokuCell();
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50 + (j / 3) * 5);

                cells[i][j].setText("" + maps[i][j]);
                cells[i][j].setEnabled(false);
                cells[i][j].setForeground(Color.gray);

                this.add(cells[i][j]);
            }
        }
        this.repaint();
    }
}
