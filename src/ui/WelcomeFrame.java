package ui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

/**
 * Created by haotianliang on 17/05/2017.
 */
public class WelcomeFrame extends JFrame {
    public WelcomeFrame() {
        init();
        addPanel();
    }

    private void addPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));
        addNewGameButton(panel);
        addLoadGameButton(panel);
        addGameSettingsButton(panel);
        this.add(panel);
    }

    private void addNewGameButton(JPanel panel) {
        JButton button = new JButton("新游戏");
        button.addActionListener(event -> {
            JFrame frame = new SudokuMainFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        });
        panel.add(button);
    }

    private void addLoadGameButton(JPanel panel) {
        JButton button = new JButton("载入游戏");
        button.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileNameExtensionFilter("GameSave", "bak"));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                //todo 读取存档文件

            }

        });
        panel.add(button);
    }

    private void addGameSettingsButton(JPanel panel) {
        JButton button = new JButton("游戏设置");
        button.addActionListener(event -> {
            JFrame frame = new GameSettings();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
        });
        panel.add(button);
    }

    private void init() {
        this.setSize(200, 200);
        this.setLocation(500, 200);
        this.setTitle("欢迎界面");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

class GameSettings extends JFrame {
    private JComboBox<String> comboBox;
    private JLabel gameLabel;

    public GameSettings() {
        init();
        setGameDifficulty();
        addReturnButton();
    }

    private void addReturnButton() {
        JPanel panel = new JPanel();
        JButton button = new JButton("返回主页面");
        button.addActionListener(event -> {
            JFrame frame = new WelcomeFrame();
            frame.setSize(300, 270);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();
        });
        button.setSize(10,10);
        panel.add(button);
        this.add(panel,BorderLayout.SOUTH);
    }

    private void setGameDifficulty() {
        JPanel comboPanel = new JPanel();
        gameLabel = new JLabel("游戏难度设置");
        comboPanel.add(gameLabel);

        comboBox = new JComboBox<>();
        comboBox.addItem("等级1");
        comboBox.addItem("等级2");
        comboBox.addItem("等级3");
        comboBox.addItem("等级4");
        comboBox.addItem("等级5");
        comboBox.addItem("等级6");
        comboBox.addItem("等级7");
        comboBox.addItem("等级8");
        comboBox.addItem("等级9");
        comboBox.addItem("等级10");

        comboBox.addActionListener(event -> {
            //todo 设置难度
            SudokuMainFrame.pass = comboBox.getSelectedIndex() + 1;
        });
        comboPanel.add(comboBox);
        this.add(comboPanel);
    }

    private void init() {
        this.setSize(200, 200);
        this.setLocation(500, 200);
        this.setTitle("游戏设置");
        this.setResizable(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
