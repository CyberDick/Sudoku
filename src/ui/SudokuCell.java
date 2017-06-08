package ui;

import javax.swing.*;
import java.awt.*;


/**
 * Created by haotianliang on 16/05/2017.
 * Updated by hongzhiwen on 03/06/2017
 * Sudoku 游戏的单元格
 */
public class SudokuCell extends JButton {
	public int i,j;
    public SudokuCell(int i, int j) {
        this.setSize(50,50);
        Font font = new Font("黑体",Font.BOLD,28);
        this.setFont(font);
        this.setBackground(new Color(255,128,128));
        this.setForeground(Color.black);
        this.i=i;
        this.j=j;
    }
}
