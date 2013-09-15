package com.lw.eeg.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.naming.InitialContext;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import com.lw.eeg.data.*;
import com.lw.eeg.plot.*;
import com.lw.eeg.processing.*;

	
public class ChannelButtonsFFT implements ActionListener{
	private String[] whichch= new String[]{"true", "false","false","false","false","false","false","false","false","false","false","false","false","false"};
	private String[] chName= new String[]{"AF3","F7","F3","FC5","T7","P7","O1","O2","P8","T8","FC6","F4","F8","AF4"};
	private List<String> chNameList= new ArrayList<String>();
	private List<String> whichchList = new ArrayList<String>();
	private SingleLineChartPlot singleLineChartPlot;
	private EEGData eegHelper = new EEGData();
	public boolean click = false;
	public JPanel singlefftPanel;
	private List<List<List<String>>> fftDataList;
	private List<List<List<String>>> ftsDataList;
	private String[][] rawData;
	private JPanel fftJPanel;
	private JPanel ftsJPanel;
	
	
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
	
	private JCheckBox chkBoxHam;
	private JCheckBox chkBoxHan;
	private JCheckBox chkBoxRec;
	private JCheckBox chkBoxBlk;
	
	
	
	
	public ChannelButtonsFFT(JPanel p){
		for(int i=0; i<chName.length; i++){
			chNameList.add(chName[i]);
			whichchList.add(whichch[i]);
		}
		init(p);
	}
	

	private void init(JPanel tabPanel_1) {
		rdbtnAF3 = new JRadioButton("AF3");
		rdbtnAF3.setBackground(Color.WHITE);
		rdbtnAF3.setBounds(63, 116, 51, 23);
		tabPanel_1.add(rdbtnAF3);
		rdbtnAF3.addActionListener(this);
		rdbtnAF3.setActionCommand(rdbtnAF3.getText());
		rdbtnAF3.setSelected(true);
		
		rdbtnF7 = new JRadioButton("F7");
		rdbtnF7.setBackground(Color.WHITE);
		rdbtnF7.setBounds(63, 147, 51, 23);
		tabPanel_1.add(rdbtnF7);
		rdbtnF7.addActionListener(this);
		rdbtnF7.setActionCommand(rdbtnF7.getText());
		
		rdbtnF3 = new JRadioButton("F3");
		rdbtnF3.setBackground(Color.WHITE);
		rdbtnF3.setBounds(63, 178, 51, 23);
		tabPanel_1.add(rdbtnF3);
		rdbtnF3.addActionListener(this);
		rdbtnF3.setActionCommand(rdbtnF3.getText());
		
		rdbtnFC5= new JRadioButton("FC5");
		rdbtnFC5.setBackground(Color.WHITE);
		rdbtnFC5.setBounds(63, 209, 51, 23);
		tabPanel_1.add(rdbtnFC5);
		rdbtnFC5.addActionListener(this);
		rdbtnFC5.setActionCommand(rdbtnFC5.getText());
		
		rdbtnT7= new JRadioButton("T7");
		rdbtnT7.setBackground(Color.WHITE);
		rdbtnT7.setBounds(63, 240, 51, 23);
		tabPanel_1.add(rdbtnT7);
		rdbtnT7.addActionListener(this);
		rdbtnT7.setActionCommand(rdbtnT7.getText());
		
		rdbtnP7 = new JRadioButton("P7");
		rdbtnP7.setBackground(Color.WHITE);
		rdbtnP7.setBounds(63, 274, 51, 23);
		tabPanel_1.add(rdbtnP7);
		rdbtnP7.addActionListener(this);
		rdbtnP7.setActionCommand(rdbtnP7.getText());
		
		rdbtnO1 = new JRadioButton("O1");
		rdbtnO1.setBackground(Color.WHITE);
		rdbtnO1.setBounds(63, 305, 51, 23);
		tabPanel_1.add(rdbtnO1);
		rdbtnO1.addActionListener(this);
		rdbtnO1.setActionCommand(rdbtnO1.getText());
		
		rdbtnO2 = new JRadioButton("O2");
		rdbtnO2.setBackground(Color.WHITE);
		rdbtnO2.setBounds(137, 116, 51, 23);
		tabPanel_1.add(rdbtnO2);
		rdbtnO2.addActionListener(this);
		rdbtnO2.setActionCommand(rdbtnO2.getText());
		
		rdbtnP8 = new JRadioButton("P8");
		rdbtnP8.setBackground(Color.WHITE);
		rdbtnP8.setBounds(137, 147, 51, 23);
		tabPanel_1.add(rdbtnP8);
		rdbtnP8.addActionListener(this);
		rdbtnP8.setActionCommand(rdbtnP8.getText());
		
		rdbtnT8 = new JRadioButton("T8");
		rdbtnT8.setBackground(Color.WHITE);
		rdbtnT8.setBounds(137, 178, 51, 23);
		tabPanel_1.add(rdbtnT8);
		rdbtnT8.addActionListener(this);
		rdbtnT8.setActionCommand(rdbtnT8.getText());
		
		rdbtnFC6 = new JRadioButton("FC6");
		rdbtnFC6.setBackground(Color.WHITE);
		rdbtnFC6.setBounds(137, 209, 51, 23);
		tabPanel_1.add(rdbtnFC6);
		rdbtnFC6.addActionListener(this);
		rdbtnFC6.setActionCommand(rdbtnFC6.getText());
		
		rdbtnF4 = new JRadioButton("F4");
		rdbtnF4.setBackground(Color.WHITE);
		rdbtnF4.setBounds(137, 240, 51, 23);
		tabPanel_1.add(rdbtnF4);
		rdbtnF4.addActionListener(this);
		rdbtnF4.setActionCommand(rdbtnF4.getText());
		
		rdbtnF8 = new JRadioButton("F8");
		rdbtnF8.setBackground(Color.WHITE);
		rdbtnF8.setBounds(137, 271, 51, 23);
		tabPanel_1.add(rdbtnF8);
		rdbtnF8.addActionListener(this);
		rdbtnF8.setActionCommand(rdbtnF8.getText());
		
		rdbtnAF4 = new JRadioButton("AF4");
		rdbtnAF4.setBackground(Color.WHITE);
		rdbtnAF4.setBounds(137, 302, 51, 23);
		tabPanel_1.add(		rdbtnAF4);
		rdbtnAF4.addActionListener(this);
		rdbtnAF4.setActionCommand(rdbtnAF4.getText());
		
		
		chkBoxHan = new JCheckBox("Hanning");
		chkBoxHan.setBackground(Color.WHITE);
		chkBoxHan.setBounds(65, 558, 74, 23);
		tabPanel_1.add(chkBoxHan);
		chkBoxHan.addActionListener(this);
		chkBoxHan.setActionCommand(chkBoxHan.getText());
		
		chkBoxHam = new JCheckBox("Hamming");
		chkBoxHam.setBackground(Color.WHITE);
		chkBoxHam.setBounds(65, 521, 74, 23);
		tabPanel_1.add(chkBoxHam);
		chkBoxHam.addActionListener(this);
		chkBoxHam.setActionCommand(chkBoxHam.getText());
		
		chkBoxBlk = new JCheckBox("Blackman");
		chkBoxBlk.setBackground(Color.WHITE);
		chkBoxBlk.setBounds(65, 478, 74, 23);
		tabPanel_1.add(chkBoxBlk);
		chkBoxBlk.addActionListener(this);
		chkBoxBlk.setActionCommand(chkBoxBlk.getText());
		
		
		chkBoxRec = new JCheckBox("Rectangular");
		chkBoxRec.setBackground(Color.WHITE);
		chkBoxRec.setBounds(65, 598, 109, 23);
		tabPanel_1.add(chkBoxRec);
		chkBoxRec.addActionListener(this);
		chkBoxRec.setActionCommand(chkBoxRec.getText());
		
		
		ButtonGroup group = new ButtonGroup();
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
	    
	    
	    ButtonGroup winGroup = new ButtonGroup();
	    winGroup.add(chkBoxBlk);
	    winGroup.add(chkBoxHam);
	    winGroup.add(chkBoxHan);
	    winGroup.add(chkBoxRec);
	    
		setUnenable();
		
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		//System.out.print(click);
		for(int i=0; i<chName.length; i++){
			whichchList.add(whichch[i]);
		}
		if(chNameList.contains(e.getActionCommand())){
			
			if(e.getActionCommand()!="AF3"){
				System.out.println("ChannelButtonsFFT.actionPerformed()");
				System.err.println("Channel" + e.getActionCommand() + " =>" + chNameList.indexOf(e.getActionCommand()));
				
				rdbtnAF3.setSelected(false);
				whichchList.add(chNameList.indexOf(e.getActionCommand()), "true");
				Data helper =new Data();
				List<List<String>> ftsTemp= new ArrayList<List<String>>();
				List<List<String>> fftTemp= new ArrayList<List<String>>();
				ftsTemp = ftsDataList.get(chNameList.indexOf(e.getActionCommand()));
				fftTemp = fftDataList.get(chNameList.indexOf(e.getActionCommand()));
				
				for(int f=0; f<ftsTemp.size()-1; f++){
//					System.out.println("BARCHART" + f);
						ftsJPanel.removeAll();
						double[] featurebuffer = new double[4];
						featurebuffer = helper.convertTodouble(ftsTemp.get(f));
						BarChart3DPlot featurePlot = new BarChart3DPlot();
						featurePlot.setData(featurebuffer);
						ChartPanel featurep = new ChartPanel(featurePlot.getChart());
						ftsJPanel.add(featurep, BorderLayout.CENTER);
						featurep.revalidate();
						featurep.repaint();

				}

				for(int f=0; f<fftTemp.size()-1; f++){
					System.out.println("LineCHART" + f);
						fftJPanel.removeAll();
						double[] fftbuffer = new double[4];
						fftbuffer = helper.convertTodouble(fftTemp.get(f));		
						System.err.println("Delta=> " + fftbuffer[0]);
						SingleLineChartPlot singleLineChartPlot = new SingleLineChartPlot();
						singleLineChartPlot.setPanel(fftJPanel);
						singleLineChartPlot.setData(fftbuffer);
				}

			}else{
				whichchList.add(chNameList.indexOf(e.getActionCommand()), "true");
			}
			
		}else {
			if(e.getActionCommand()!="Hanning"){
				FeaturesCalc fCalc = new FeaturesCalc();
				fCalc.setWindow(e.getActionCommand());
				fCalc.calc(rawData);
				
				Data helper =new Data();
				List<List<String>> ftsTemp= new ArrayList<List<String>>();
				List<List<String>> fftTemp= new ArrayList<List<String>>();
				System.err.println("In window func " + whichchList.indexOf("true"));
				ftsTemp = fCalc.getftsList().get(whichchList.indexOf("true"));
				fftTemp = fCalc.getfftList().get(whichchList.indexOf("true"));
				
				for(int f=0; f<ftsTemp.size()-1; f++){
//					System.out.println("BARCHART" + f);
						ftsJPanel.removeAll();
						double[] featurebuffer = new double[4];
						featurebuffer = helper.convertTodouble(ftsTemp.get(f));
						BarChart3DPlot featurePlot = new BarChart3DPlot();
						featurePlot.setData(featurebuffer);
						ChartPanel featurep = new ChartPanel(featurePlot.getChart());
						ftsJPanel.add(featurep, BorderLayout.CENTER);
						featurep.revalidate();
						featurep.repaint();

				}

				for(int f=0; f<fftTemp.size()-1; f++){
					System.out.println("LineCHART" + f);
						fftJPanel.removeAll();
						double[] fftbuffer = new double[4];
						fftbuffer = helper.convertTodouble(fftTemp.get(f));		
						System.err.println("Delta=> " + fftbuffer[0]);
						SingleLineChartPlot singleLineChartPlot = new SingleLineChartPlot();
						singleLineChartPlot.setPanel(fftJPanel);
						singleLineChartPlot.setData(fftbuffer);
				}
				
				
				
			}
			//if e.getActionCommand == win
			//get current channel, then 
			//removeall re fcalc plotall
		}
		
		
	}
	public void setfftPanel(JPanel p){
		fftJPanel = p;
	}
	
	public void setftsPanel(JPanel p){
		ftsJPanel = p;
	}
	
	
	public JFreeChart getChart(){
		return singleLineChartPlot.getChart();
	}
	
	public void setfftData(List<List<List<String>>> _fftDataList){
		fftDataList =_fftDataList;
	}
	
	public void setftsData(List<List<List<String>>> _ftsDataList){
		ftsDataList =_ftsDataList;
	}
	
	public void setRawData(String[][] r){
		rawData=r;
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
	}
	

}