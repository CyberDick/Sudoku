package ui;

import src.SudokuHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by haotianliang on 16/05/2017.
 */
public class SudokuCanvers extends JPanel {
    SudokuCell[][] cells;
    int[][] maps = new int[9][9];

    public SudokuCanvers() {
        maps = SudokuHelper.getMap();
        this.setLayout(null);

        cells = new SudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
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

    private boolean check(int i, int j) {
        if (cells[i][j].getText().isEmpty()) {
            return false;
        }
        for (int k = 0; k < 9; k++) {
            if (cells[i][j].getText().trim().equals(cells[i][k].getText().trim()) && j != k) {
                return false;
            }
            if (cells[i][j].getText().trim().equals(cells[k][j].getText().trim()) && i != k) {
                return false;
            }
            int ii = (i / 3) * 3 + k / 3;
            int jj = (j / 3) * 3 + k % 3;
            if (cells[i][j].getText().trim().equals(cells[ii][jj].getText().trim()) && !(i == ii && j == jj)) {
                return false;
            }
        }
        return true;
    }
    public void reLoadCanvers() {
//        SodokuM.usedTime = 0;
//        maps = ShuduHelper.getMap();
//        for (int i = 0; i < 9; i++) {
//            for (int j = 0; j < 9; j++) {
//                this.remove(cells[i][j]);
//                // 创建单元格
//                cells[i][j] = new ShuduCell();
//                // 设置位置
//                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
//                        + (j / 3) * 5);
//                if (passRole(ShuduMainFrame.pass)) {
//                    cells[i][j].setText("" + maps[i][j]);
//                    // 设置背景颜色
//                    cells[i][j].setBackground(getColor(maps[i][j]));
//                    cells[i][j].setEnabled(false);
//                    cells[i][j].setForeground(Color.gray);
//                } else {
//                    cells[i][j].addMouseListener(this);
//                }
//            }
//        }
//        this.repaint();
//        checkFinish();

    }

}
