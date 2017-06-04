package ui;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.Hashtable;
import java.lang.*;

import src.Sudoku;
import ui.GameSettings;
import ui.SudokuMainFrame;

/**
 * Created by haotianliang on 17/05/2017.
 * Edited by hongzhiwen on 03/06/2017
 */
public class WelcomeFrame extends JFrame {
    //设定分别对应：判错辅助，视觉辅助，逻辑辅助
    		
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
        button.setFont(new java.awt.Font("黑体",0,16));
        button.addActionListener(event -> {
            /*JFrame frame = new SudokuMainFrame();
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.dispose();*/
        	JFrame frame= new DifficuitySettings(this);
        	frame.setVisible(true);
        	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	
        });
        panel.add(button);
    }

    private void addLoadGameButton(JPanel panel) {
        JButton button = new JButton("载入游戏");
        button.setFont(new java.awt.Font("黑体",0,16));
        button.addActionListener(event -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("."));
            chooser.setFileFilter(new FileNameExtensionFilter("GameSave","bak"));
            int result = chooser.showOpenDialog(this);
            if(result == JFileChooser.APPROVE_OPTION){
                //TODO 读取存档文件

            }

        });
        panel.add(button);
    }

    private void addGameSettingsButton(JPanel panel){
        JButton button = new JButton("游戏设置");
        button.setFont(new java.awt.Font("黑体",0,16));
        button.addActionListener(event -> {
            JFrame frame = new GameSettings(this);
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

/**
 * Created by hongzhiwen on 03/06/2017.
 */

class DifficuitySettings extends JFrame{
    private JFrame main;
    
	public DifficuitySettings(JFrame main){
		this.main=main;
		init();
        addPanel();
    }

    private void addPanel(){
    	//为了空一行，行参数被设为5
        JPanel panel = new JPanel(null);
    	JLabel label=new JLabel("空格数量");
    	label.setFont(new java.awt.Font("黑体",0,16));
    	panel.add(label);

        JSlider slider=new JSlider(JSlider.HORIZONTAL,20,64,42);
		slider.setMajorTickSpacing(10);//主刻度间隔
		slider.setMinorTickSpacing(1);//次刻度间隔
		slider.setPaintTicks(true);//是否显示刻度标记
		
		Hashtable<Integer,JLabel> labeltable=new Hashtable<Integer,JLabel>();
		labeltable.put(new Integer(20),new JLabel("入门"));
		labeltable.put(new Integer(31),new JLabel("简单"));
		labeltable.put(new Integer(42),new JLabel("中等"));
		labeltable.put(new Integer(53),new JLabel("困难"));
		labeltable.put(new Integer(64),new JLabel("噩梦"));
		slider.setLabelTable(labeltable);
		slider.setPaintLabels(true);       
		panel.add(slider);
		
		JTextField text=new JTextField("42",32);	
		text.setFont(new java.awt.Font("黑体",0,16));
		text.setEditable(true);
		panel.add(text);
		
		JButton button=new JButton("开始游戏");
		button.setFont(new java.awt.Font("黑体",0,14));
        panel.add(button);
		
        label.setBounds(160,0,80,40);
        slider.setBounds(50,40,280,50);
        text.setBounds(180, 95, 20, 40);
        button.setBounds(140,140,100,40);
        
        text.addKeyListener(new KeyListener(){  
        	public void keyTyped(KeyEvent e) {  
            }
            
            public void keyPressed(KeyEvent e) {       
            }
            
            public void keyReleased(KeyEvent e) {  
            	try{
            		int value=Integer.parseInt(((JTextField) e.getSource()).getText().trim());    
                	if (value>9){
                		if (value>63) slider.setValue(63);
                        else if (value<20) slider.setValue(20);
                        else slider.setValue(value);
                	}
            	}catch(Exception err){System.out.print("InputError");}  
            }  
        });
        
        slider.addChangeListener(new ChangeListener(){
        	public void stateChanged(ChangeEvent e){
        		int value=((JSlider)e.getSource()).getValue(); 
        		if (value>63) {
        			value=63;
        		}	
        		text.setVisible(false);
        		text.setText(""+value);
        		text.setVisible(true);
        		text.repaint();
        	}
        });
        
        button.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e) {
				main.dispose();
				DifficuitySettings.this.dispose();
				SudokuMainFrame sudokuMainFrame=new SudokuMainFrame(slider.getValue());
				sudokuMainFrame.setVisible(true);
			}
			
        });
		
        this.add(panel);

    }
    
    private void init(){
        this.setSize(400,250);
        this.setVisible(true);
        this.setTitle("难度选项");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

