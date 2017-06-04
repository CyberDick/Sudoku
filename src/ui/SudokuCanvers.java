package ui;

import src.Sudoku;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.UIManager.getColor;

/**
 * Created by haotianliang on 16/05/2017.
 * Edited by hongzhiwen on 03/06/2017
 */
public class SudokuCanvers extends JPanel implements MouseListener {
    static SudokuCell[][] cells;
    static int[][] maps = new int[9][9];
    static boolean[][] mask = new boolean[9][9];
    private SelectNumFrame selectNum;
    Sudoku SudokuHelper;
    
    public SudokuCanvers(int blocks) {
    	SudokuHelper=new Sudoku(blocks);
    	refresh();
    }

    public void refresh(){
        maps = SudokuHelper.getMap();
        this.setLayout(null);
        if (cells != null){
        	for (int i=0; i<9; i++){
        		for (int j=0; j<9; j++){
        			cells[i][j].setVisible(false);
        		}
        	}
        }
        cells = new SudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 创建单元格
                cells[i][j] = new SudokuCell(i,j);
                
                // 设置位置
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                // 设置数字              
                if (maps[i][j]!=0) {
                	cells[i][j].setText(Integer.toString(Math.abs(maps[i][j])));
                    // 设置视觉辅助
                	if (Sudoku.settings[1]==1)
                    	cells[i][j].setBackground(getColor(Math.abs(maps[i][j])));
                    else cells[i][j].setBackground(new Color(255, 255, 204));
                    
                	//设置判错辅助
                	if (Sudoku.settings[0]==1){
                		if (!SudokuHelper.check(i, j)){
                			cells[i][j].setBackground(Color.red);
                		}
                	}
                	
                    if (maps[i][j]>0){
                    	cells[i][j].setEnabled(false);
                    }else{
                    	cells[i][j].setFont(new Font("黑体",Font.PLAIN,25));
                    	cells[i][j].addMouseListener(this);
                    }
                } else {
                	cells[i][j].setText("");
                	cells[i][j].setBackground(Color.white);
                    cells[i][j].addMouseListener(this);
                }
                
                cells[i][j].addMouseListener(new MouseListener(){
                	public void mouseReleased(MouseEvent e) {}			
        			public void mousePressed(MouseEvent e) {
        	        	if ((selectNum != null) && (selectNum.isShowing())) {
        	            	selectNum.dispose();
        	            }
        			}			
        			public void mouseExited(MouseEvent e) {}		
        			public void mouseEntered(MouseEvent e) {
        		    	if (Sudoku.settings[2]==1){
        		    		if (((JButton) e.getSource()).getText()==""){
                				for (int i=0; i<9; i++){
                					for (int j=0; j<9; j++){
                						if (Sudoku.settings[1]==1)
        			    					cells[i][j].setBackground(getColor(Math.abs(maps[i][j])));
        			                    else cells[i][j].setBackground(new Color(255, 255, 204));
                					}
                				}
			                }else{
			                	for (int i=0; i<9; i++){	
	        			    		for (int j=0; j<9; j++){
	        			    			if (maps[i][j]==Integer.parseInt(((JButton) e.getSource()).getText())){
	        			    				cells[i][j].setBackground(Color.blue);
	        			    			}else if(SudokuHelper.checkNum(i,j,Integer.parseInt(((JButton) e.getSource()).getText()))){
	        			    				cells[i][j].setBackground(Color.green);
	        			    			}else{
	        			    				if (Sudoku.settings[1]==1)
	        			    					cells[i][j].setBackground(getColor(Math.abs(maps[i][j])));
	        			                    else cells[i][j].setBackground(new Color(255, 255, 204));
	        			    			}
	        			    		}
			                	}
        			    	}
        		    	}
        			}		
        			public void mouseClicked(MouseEvent e) {}
        	    });
                
                this.add(cells[i][j]);
            }
        }
        this.repaint();
        if (SudokuHelper.checkFinish()){
            // 停止用户用时计时器
            SudokuMainFrame.userTimeAction.stop();
            // 清除所有cell监听
            clearAllListener();
            // TODO 准备跳转至结束画面
           
        }
    }
    
    private Color getColor(int i) {
        Color color = Color.pink;
        switch (i) {
            case 1:
                color = new Color(255, 255, 204);
                break;
            case 2:
                color = new Color(204, 255, 255);
                break;
            case 3:
                color = new Color(242, 204, 255);
                break;
            case 4:
                color = new Color(255, 217, 179);
                break;
            case 5:
                color = new Color(218, 255, 179);
                break;
            case 6:
                color = new Color(242, 229, 255);
                break;
            case 7:
                color = new Color(179, 198, 255);
                break;
            case 8:
                color = new Color(255, 194, 179);
                break;
            case 9:
                color = new Color(221, 221, 187);
                break;
            default:
                color = new Color(255, 255, 255);
                break;
        }
        return color;
    }

    private void clearAllListener() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j].removeMouseListener(this);
            }
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON3_MASK) != 0) {// 点击鼠标右键
            // 清空点击单元格上的内容
        	SudokuHelper.insert(((SudokuCell) e.getSource()).i,((SudokuCell) e.getSource()).j,0);
        	refresh();
        } else if ((modes & InputEvent.BUTTON1_MASK) != 0) {// 点击鼠标左键
            // 如果选择数字窗口存在则销毁
        	if (selectNum != null) {
            	selectNum.dispose();
            }
            // 新建一个选择窗口
            selectNum = new SelectNumFrame(this, SudokuHelper, (SudokuCell) e.getSource());
            // 设置成模态窗口
            selectNum.setModal(false);
            // 设置选择窗口在显示器上的位置
            selectNum.setLocation(e.getLocationOnScreen().x,
                    e.getLocationOnScreen().y);
            // 显示数字选择窗口
            selectNum.setVisible(true);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
    
    /*
     * 撤销操作
     */
    public void cancel(){
    	SudokuHelper.cancel();
    	refresh();
    }
    
    /*
     * 重新开始
     */
    public void clear(){
    	SudokuHelper.clear();
    	refresh();
    }
}
