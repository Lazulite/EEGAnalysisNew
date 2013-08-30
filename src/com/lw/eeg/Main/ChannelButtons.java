package com.lw.eeg.Main;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Array;
import java.util.Arrays;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jfree.chart.JFreeChart;

import com.lw.eeg.data.EEGData;
import com.lw.eeg.plot.SingleChannelPlot;

public class ChannelButtons implements ActionListener {
	
	private String[][] totalData;
	private String[] chName= new String[]{"AF3","F7","F3","FC5","T7","P7","O1","O2","P8","T8","FC6","F4","F8","AF4"};
	private SingleChannelPlot singleChannelPlot;
	private EEGData eegHelper = new EEGData();
	public boolean click = false;
	
	private JRadioButton rdbtnAF3;
	private JRadioButton rdbtnF7;
	private JRadioButton rdbtnF3;
	private JRadioButton rdbtnFC5;
	private JRadioButton rdbtnT7;
	private JRadioButton rdbtnP7;
	private JRadioButton rdbtnO1;
	private JRadioButton rdbtnO2;
	private JRadioButton rdbtnP8;
	private JRadioButton rdbtnT8;
	private JRadioButton rdbtnFC6;
	private JRadioButton rdbtnF4;
	private JRadioButton rdbtnF8;
	private JRadioButton rdbtnAF4;
	private JRadioButton rdbtnHR;
	
	public ChannelButtons(JPanel tabPanel){
		init(tabPanel);
	}
	public void init(JPanel tabPanel){
		
		
	    
		rdbtnAF3 = new JRadioButton("AF3");
		rdbtnAF3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAF3.setForeground(new Color(0, 191, 255));
		rdbtnAF3.setBounds(6, 22, 51, 23);
		rdbtnAF3.setBackground(Color.WHITE);
		tabPanel.add(rdbtnAF3);
		rdbtnAF3.addActionListener(this);
		rdbtnAF3.setActionCommand(rdbtnAF3.getText());
		
		rdbtnF7 = new JRadioButton("F7");
		rdbtnF7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF7.setForeground(new Color(128, 0, 128));
		rdbtnF7.setBounds(6, 56, 51, 23);
		rdbtnF7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF7);
		rdbtnF7.addActionListener(this);
		rdbtnF7.setActionCommand(rdbtnF7.getText());
		
		rdbtnF3 = new JRadioButton("F3");
		rdbtnF3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF3.setForeground(new Color(0, 128, 0));
		rdbtnF3.setBounds(6, 90, 51, 23);
		rdbtnF3.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF3);
		rdbtnF3.addActionListener(this);
		rdbtnF3.setActionCommand(rdbtnF3.getText());
		
		rdbtnFC5 = new JRadioButton("FC5");
		rdbtnFC5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFC5.setForeground(new Color(184, 134, 11));
		rdbtnFC5.setBounds(6, 122, 51, 23);
		rdbtnFC5.setBackground(Color.WHITE);
		tabPanel.add(rdbtnFC5);
		rdbtnFC5.addActionListener(this);
		rdbtnFC5.setActionCommand(rdbtnFC5.getText());
		
		rdbtnT7 = new JRadioButton("T7");
		rdbtnT7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnT7.setForeground(new Color(0, 0, 128));
		rdbtnT7.setBounds(6, 153, 51, 23);
		rdbtnT7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnT7);
		rdbtnT7.addActionListener(this);
		rdbtnT7.setActionCommand(rdbtnT7.getText());
		
		rdbtnP7 = new JRadioButton("P7");
		rdbtnP7.setForeground(new Color(178, 34, 34));
		rdbtnP7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnP7.setBounds(6, 187, 51, 23);
		rdbtnP7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnP7);
		rdbtnP7.addActionListener(this);
		rdbtnP7.setActionCommand(rdbtnP7.getText());
		
		rdbtnO1 = new JRadioButton("O1");
		rdbtnO1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnO1.setForeground(new Color(105, 105, 105));
		rdbtnO1.setBounds(6, 222, 51, 23);
		rdbtnO1.setBackground(Color.WHITE);
		tabPanel.add(rdbtnO1);
		rdbtnO1.addActionListener(this);
		rdbtnO1.setActionCommand(rdbtnO1.getText());
		
		rdbtnO2 = new JRadioButton("O2");
		rdbtnO2.setForeground(new Color(255, 182, 193));
		rdbtnO2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnO2.setBounds(6, 255, 51, 23);
		rdbtnO2.setBackground(Color.WHITE);
		tabPanel.add(rdbtnO2);
		rdbtnO2.addActionListener(this);
		rdbtnO2.setActionCommand(rdbtnO2.getText());
		
		rdbtnP8 = new JRadioButton("P8");
		rdbtnP8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnP8.setForeground(new Color(0, 255, 255));
		rdbtnP8.setBounds(6, 286, 51, 23);
		rdbtnP8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnP8);
		rdbtnP8.addActionListener(this);
		rdbtnP8.setActionCommand(rdbtnP8.getText());
		
		rdbtnT8 = new JRadioButton("T8");
		rdbtnT8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnT8.setForeground(new Color(255, 0, 255));
		rdbtnT8.setBounds(6, 317, 51, 23);
		rdbtnT8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnT8);
		rdbtnT8.addActionListener(this);
		rdbtnT8.setActionCommand(rdbtnT8.getText());
		
		rdbtnFC6 = new JRadioButton("FC6");
		rdbtnFC6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFC6.setForeground(new Color(255, 215, 0));
		rdbtnFC6.setBounds(6, 348, 51, 23);
		rdbtnFC6.setBackground(Color.WHITE);
		tabPanel.add(rdbtnFC6);
		rdbtnFC6.addActionListener(this);
		rdbtnFC6.setActionCommand(rdbtnFC6.getText());
		
		rdbtnF4 = new JRadioButton("F4");
		rdbtnF4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF4.setForeground(new Color(0, 0, 205));
		rdbtnF4.setBounds(6, 379, 51, 23);
		rdbtnF4.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF4);
		rdbtnF4.addActionListener(this);
		rdbtnF4.setActionCommand(rdbtnF4.getText());
		
		rdbtnF8 = new JRadioButton("F8");
		rdbtnF8.setForeground(new Color(50, 205, 50));
		rdbtnF8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF8.setBounds(6, 410, 51, 23);
		rdbtnF8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF8);
		rdbtnF8.addActionListener(this);
		rdbtnF8.setActionCommand(rdbtnF8.getText());
		
		rdbtnAF4 = new JRadioButton("AF4");
		rdbtnAF4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAF4.setForeground(new Color(255, 0, 0));
		rdbtnAF4.setBounds(6, 444, 51, 23);
		rdbtnAF4.setBackground(Color.WHITE);
		tabPanel.add(rdbtnAF4);
		rdbtnAF4.addActionListener(this);
		rdbtnAF4.setActionCommand(rdbtnAF4.getText());

		
		rdbtnHR = new JRadioButton("HR");
		rdbtnHR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnHR.setBackground(Color.WHITE);
		rdbtnHR.setBounds(6, 477, 51, 23);
		tabPanel.add(rdbtnHR);
		rdbtnHR.addActionListener(this);
		rdbtnHR.setActionCommand(rdbtnHR.getText());
		
		
		ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnAF3);
	    group.add(rdbtnF7);
	    group.add(rdbtnF3);
	    group.add(rdbtnF3);
	    group.add(rdbtnFC5);
	    group.add(rdbtnT7);
	    group.add(rdbtnP7);	    
	    group.add(rdbtnO1);
	    group.add(rdbtnO2);
	    group.add(rdbtnP8);
	    group.add(rdbtnT8);
	    group.add(rdbtnFC6);
	    group.add(rdbtnF4);
	    group.add(rdbtnF8);
	    
	    singleChannelPlot = new SingleChannelPlot();
		setUnenable();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		click=true;
		if(e.getActionCommand()!="HR"){
			System.err.println(e.getActionCommand());
			singleChannelPlot.setData(eegHelper.getChannel(totalData, Arrays.asList(chName).indexOf(e.getActionCommand())+1));
			singleChannelPlot.init();
			
		}else {
			
		}
		
	}
	
	public void setEnable(){
		rdbtnAF3.setEnabled(true);
		rdbtnF7.setEnabled(true);
		rdbtnF3.setEnabled(true);
		rdbtnFC5.setEnabled(true);
		rdbtnT7.setEnabled(true);
		rdbtnP7.setEnabled(true);
		rdbtnO1.setEnabled(true);
		rdbtnO2.setEnabled(true);
		rdbtnP8.setEnabled(true);
		rdbtnT8.setEnabled(true);
		rdbtnFC6.setEnabled(true);
		rdbtnF4.setEnabled(true);
		rdbtnF8.setEnabled(true);
		rdbtnAF4.setEnabled(true);
		rdbtnHR.setEnabled(true);
	}
	
	public void setUnenable(){
		rdbtnAF3.setEnabled(false);
		rdbtnF7.setEnabled(false);
		rdbtnF3.setEnabled(false);
		rdbtnFC5.setEnabled(false);
		rdbtnT7.setEnabled(false);
		rdbtnP7.setEnabled(false);
		rdbtnO1.setEnabled(false);
		rdbtnO2.setEnabled(false);
		rdbtnP8.setEnabled(false);
		rdbtnT8.setEnabled(false);
		rdbtnFC6.setEnabled(false);
		rdbtnF4.setEnabled(false);
		rdbtnF8.setEnabled(false);
		rdbtnAF4.setEnabled(false);
		rdbtnHR.setEnabled(false);
	}
	
	
	public JFreeChart getChart(){
		return singleChannelPlot.getChart();
	}
	
	public void setData(String[][] _totalData){
		totalData = _totalData;
		System.err.println(_totalData[0][0]);
	}
}
