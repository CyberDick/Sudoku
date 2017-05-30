package ui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by haotianliang on 16/05/2017.
 * Sudoku 游戏主界面
 */
public class SudokuMainFrame extends JFrame {

    private SudokuCanvers sudokuCanvers;

    public static int pass = 1;

    public static long usedTime = 0;

    public static Timer userTimeAction;

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
        panel.setLayout(new GridLayout(3, 1));

        addPanelMsg(panel);

        addPanelTimer(panel);

        addPanelFunction(panel);

        this.add(panel, BorderLayout.EAST);
    }

    private void addPanelFunction(JPanel panel) {
        JPanel buttonPanel = new JPanel();
        JButton buttonSave = new JButton("保存游戏");
        JButton buttonAbandon = new JButton("放弃游戏");
        JButton buttonClear = new JButton("重新开始");
        JButton buttonCancel = new JButton("撤销操作");
        ButtonFunction buttonFunction = new ButtonFunction();

        buttonPanel.setLayout(new GridLayout(2, 2));
        buttonPanel.add(buttonCancel);
        buttonPanel.add(buttonClear);
        buttonPanel.add(buttonSave);
        buttonPanel.add(buttonAbandon);

        buttonFunction.addActionSaveGameListenser(buttonSave);
        panel.add(buttonPanel);
    }

    private void addPanelTimer(JPanel panel) {
        JPanel panelTime = new JPanel();
        panelTime.setBorder(new TitledBorder("时间"));
        panelTime.setLayout(new GridLayout(2, 1));

        final JLabel lbSysTime = new JLabel();
        final JLabel lbUserTime = new JLabel();

        panelTime.add(lbSysTime, BorderLayout.NORTH);
        panelTime.add(lbUserTime, BorderLayout.SOUTH);

        // 设置系统时间定时器
        Timer sysTimeAction = new Timer(500, event -> {
            long timeMillis = System.currentTimeMillis();
            SimpleDateFormat df = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");
            lbSysTime.setText("    系统时间：  " + df.format(timeMillis));
        });

        sysTimeAction.start();
        userTimeAction = new Timer(1000, event ->
                lbUserTime.setText("    您已用时：  " + (++usedTime) + " sec.")
        );
        userTimeAction.start();

        panel.add(panelTime, BorderLayout.EAST);
    }

    private void addPanelMsg(JPanel panel) {
        JPanel panelTime = new JPanel();
        panelTime.setBorder(new TitledBorder("游戏信息"));
        panelTime.setLayout(new GridLayout(2, 1));
        panel.add(panelTime);
    }

    private void init() {
        this.setSize(900, 500);
        this.setLocation(500, 200);
        this.setTitle("数独游戏");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class ButtonFunction extends JButton {
    public void addActionSaveGameListenser(JButton button){
        button.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileNameExtensionFilter("GameSave", "bak"));
            chooser.showSaveDialog(this);

            String filename = chooser.getSelectedFile().getAbsolutePath();
            try{
                GameFiles.saveGame(filename);
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}