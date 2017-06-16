package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by haotianliang on 16/05/2017.
 */
public class SelectNumFrame extends JDialog implements MouseListener {

    private SudokuCell cell;

    public void setCell(SudokuCell cell) {
        this.cell = cell;
    }

    public SelectNumFrame() {
        //隐藏界面上面的工具栏
        this.setUndecorated(true);
        this.setSize(150, 150);
        this.setBackground(new Color(100, 222, 255, 123));
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
                this.add(button);
            }
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mousePressed(MouseEvent e) {
        int modes = e.getModifiers();
        if ((modes & InputEvent.BUTTON1_MASK) != 0) {
            JButton button = (JButton) e.getSource();
            cell.setText(button.getText());
        }
        this.dispose();
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
