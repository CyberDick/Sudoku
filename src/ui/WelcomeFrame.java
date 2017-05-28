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
        panel.setLayout(new GridLayout(3,1));
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
            chooser.setFileFilter(new FileNameExtensionFilter("GameSave","bak"));
            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                //todo 读取存档文件

            }

        });
        panel.add(button);
    }

    private void addGameSettingsButton(JPanel panel){
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

class GameSettings extends JFrame{
    public GameSettings(){
        init();
        addPanel();
    }

    private void addPanel(){
        JPanel panel = new JPanel();
        addAssistantRadioButton(panel);
        this.add(panel);
    }
    private void addAssistantRadioButton(JPanel panel){
        ButtonGroup group = new ButtonGroup();
        JRadioButton yes = new JRadioButton("Yes");
        JRadioButton no = new JRadioButton("No");
        group.add(yes);
        group.add(no);
        panel.add(yes);
        panel.add(no);
    }
    private void init(){
        this.setSize(400,400);
        this.setVisible(true);
        this.setTitle("游戏设置");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
