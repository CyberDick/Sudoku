package ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import src.Sudoku;

/**
 * Created by hongzhiwen on 03/06/2017.
 */

public class GameSettings extends JFrame{
    private JFrame main;
    
	public GameSettings(JFrame main){
        this.main=main;
		init();
        addPanel();
    }

    private void addPanel(){
    	//为了空一行，行参数被设为5
        JPanel panel = new JPanel(new GridLayout(5,3,20,30));
        addAssistantRadioButton(this, panel);
        this.add(panel);
    }
    
    private void addAssistantRadioButton(GameSettings gs, JPanel panel){
        String[] tipText={"判错辅助","视觉辅助","逻辑辅助"};
    	ButtonGroup group[] = new ButtonGroup[3];
    	JRadioButton[] ButtonOn=new JRadioButton[3];
        //空一行
    	panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(new JLabel());
        JRadioButton yes;
        JRadioButton no;
        JLabel label;
        for(int i=0;i<3;i++){
        	label=new JLabel(tipText[i]);
        	label.setFont(new java.awt.Font("黑体",0,16));
        	panel.add(label);
        	group[i] = new ButtonGroup();
            yes = new JRadioButton("开启");
            no = new JRadioButton("关闭");
            group[i].add(yes);
            group[i].add(no);
            if (Sudoku.settings[i]==1){
            	yes.setSelected(true);
            }else{
            	no.setSelected(true);
            }
            panel.add(yes);
            panel.add(no);
            ButtonOn[i]=yes;
        }
        
        JButton ExitButton=new JButton("返回");
        JButton SaveButton=new JButton("保存");
        
        ExitButton.addActionListener(
        	new ActionListener()
        	{
	        	public void actionPerformed(ActionEvent e){
	        		gs.setVisible(false);
	        		main.setVisible(true);
	        	}
        	}
        );
        SaveButton.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		for (int i=0; i<3; i++){
        			Sudoku.settings[i]=0;
        			if (ButtonOn[i].isSelected()) {
        				Sudoku.settings[i]=1;
        			}
        		}
        	}
        });
        
        panel.add(ExitButton);
        panel.add(SaveButton);
        
        add(panel);
        setLayout(new FlowLayout());//流式布局
        setLocationRelativeTo(null);//居中
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
    private void init(){
        this.setSize(400,400);
        this.setVisible(true);
        this.setTitle("游戏设置");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
