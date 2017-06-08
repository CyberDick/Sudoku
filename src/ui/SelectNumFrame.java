package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by haotianliang on 16/05/2017.
 * Edited by hongzhiwen on 03/06/2016.
 */
import src.Sudoku;
import ui.SudokuCanvers;

public class SelectNumFrame extends JDialog implements MouseListener {	
    private SudokuCell cell;
    private Sudoku SudokuHelper;
    private SudokuCanvers canver;
    
    public SelectNumFrame(SudokuCanvers canver,Sudoku SudokuHelper,SudokuCell cell) {
        //隐藏界面上面的工具栏
        this.cell = cell;
        this.SudokuHelper = SudokuHelper;
        this.canver = canver;
    	this.setUndecorated(true);
        this.setSize(150, 150);
        this.setBackground(new Color(255, 204, 153, 123));
        this.setLayout(null);
        addNum();
    }

    //添加数字1~9
    private void addNum() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                JButton button = new JButton();
                button.setSize(50, 50);
                button.setLocation(i * 50, j * 50);
                button.setText("" + (j * 3 + i + 1));
                button.addMouseListener(this);
                button.addKeyListener(new KeyListener(){
                	public void keyReleased(KeyEvent e){}
                	public void keyPressed(KeyEvent e){
                		if (e.getKeyCode()>=49 & e.getKeyCode()<=57){
                			SudokuHelper.insert(cell.i,cell.j,e.getKeyCode()-48);
                		}
                		SelectNumFrame.this.dispose();
                		canver.refresh();
                	}
                	public void keyTyped(KeyEvent e){}
                });
                if (Sudoku.settings[2]==1){
                	if (SudokuHelper.checkNum(cell.i,cell.j,(j*3+i+1))){
                		button.setForeground(Color.green);
                	}else{
                		button.setForeground(Color.red);
                	}
                }
                this.add(button);
            }
        }

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON1_MASK) != 0) {
            JButton button = (JButton) e.getSource();
            SudokuHelper.insert(cell.i,cell.j,Integer.parseInt(button.getText()));
        }
        this.dispose();
        canver.refresh();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
    	if (Sudoku.settings[2]==1){
    		for (int i=0; i<9; i++){
	    		for (int j=0; j<9; j++){
	    			if (SudokuHelper.checkNum(i,j,Integer.parseInt(((JButton) e.getSource()).getText()))){
	    				canver.cells[i][j].setBackground(Color.green);
	    			}
	    		}
	    	}
    	}
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
    	canver.refresh();
    }
}
