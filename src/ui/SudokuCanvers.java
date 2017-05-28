package ui;

import src.SudokuHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static javax.swing.UIManager.getColor;

/**
 * Created by haotianliang on 16/05/2017.
 */
public class SudokuCanvers extends JPanel implements MouseListener {
    static SudokuCell[][] cells;
    static int[][] maps = new int[9][9];
    static boolean[][] mask = new boolean[9][9];
    private SelectNumFrame selectNum;

    public SudokuCanvers() {
        maps = SudokuHelper.getMap();
        this.setLayout(null);

        cells = new SudokuCell[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                // 创建单元格
                cells[i][j] = new SudokuCell();
                // 设置位置
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                if (passRole(SudokuMainFrame.pass)) {
                    cells[i][j].setText("" + maps[i][j]);
                    // 设置背景颜色
                    cells[i][j].setBackground(getColor(maps[i][j]));
                    cells[i][j].setEnabled(false);
                    cells[i][j].setForeground(Color.gray);
                } else {
                    cells[i][j].addMouseListener(this);
                }
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
        SudokuMainFrame.usedTime = 0;
        maps = SudokuHelper.getMap();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                this.remove(cells[i][j]);
                // 创建单元格
                cells[i][j] = new SudokuCell();
                // 设置位置
                cells[i][j].setLocation(20 + i * 50 + (i / 3) * 5, 20 + j * 50
                        + (j / 3) * 5);
                if (passRole(SudokuMainFrame.pass)) {
                    cells[i][j].setText("" + maps[i][j]);
                    // 设置背景颜色
                    cells[i][j].setBackground(getColor(maps[i][j]));
                    cells[i][j].setEnabled(false);
                    cells[i][j].setForeground(Color.gray);
                } else {
                    cells[i][j].addMouseListener(this);
                }
            }
        }
        this.repaint();
        checkFinish();
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
                color = new Color(255, 204, 204);
                break;
            case 4:
                color = new Color(255, 204, 153);
                break;
            case 5:
                color = new Color(204, 255, 153);
                break;
            case 6:
                color = new Color(204, 204, 204);
                break;
            case 7:
                color = new Color(255, 204, 204);
                break;
            case 8:
                color = new Color(255, 255, 255);
                break;
            case 9:
                color = new Color(153, 255, 153);
                break;
            default:
                break;
        }
        return color;
    }

    private boolean passRole(int pass) {
        return Math.random() * 11 > pass;
    }

    private void checkFinish() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!check(i, j)) {
                    return;
                }
            }
        }

        // 停止用户用时计时器
        SudokuMainFrame.userTimeAction.stop();
        // 清除所有cell监听
        clearAllListener();
        // 闯关数加一
        SudokuMainFrame.pass += 1;
        if (SudokuMainFrame.pass > 10) {
            int o = JOptionPane
                    .showConfirmDialog(this, "您已经通关了，是否重头开始？", "", 0);
            if (o == 1) {
                System.exit(0);
            } else {
                SudokuMainFrame.pass = 1;
            }
        } else {
            JOptionPane.showMessageDialog(this, "恭喜你通过本关！用时："
                    + SudokuMainFrame.usedTime + "秒\n即将进入下一关！");
        }
        // 开始新的关卡
        reLoadCanvers();
        // 打开用户用时计时器
        SudokuMainFrame.userTimeAction.start();

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
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON3_MASK) != 0) {// 点击鼠标右键
            // 清空点击单元格上的内容
            ((SudokuCell) e.getSource()).setText("");
        } else if ((modes & InputEvent.BUTTON1_MASK) != 0) {// 点击鼠标左键
            // 如果选择数字窗口存在则销毁
            if (selectNum != null) {
                selectNum.dispose();
            }
            // 新建一个选择窗口
            selectNum = new SelectNumFrame();
            // 设置成模态窗口
            selectNum.setModal(true);
            // 设置选择窗口在显示器上的位置
            selectNum.setLocation(e.getLocationOnScreen().x,
                    e.getLocationOnScreen().y);
            // 将点击的单元格传递给数字选择窗口
            selectNum.setCell((SudokuCell) e.getSource());
            // 显示数字选择窗口
            selectNum.setVisible(true);
        }
        checkFinish();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

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
}
