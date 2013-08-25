package com.lw.eeg.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartPanel;

import com.lw.eeg.data.*;
import com.lw.eeg.EEGLog.*;
import com.lw.eeg.plot.*;
import com.lw.eeg.processing.*;

public class MainView extends JFrame {
	
	private static JPanel contentPane;
	private static JFrame frame;
	private static JTextArea txtrEegDataLog; 
	private static JPanel allChannelPanel; // AllChannel panel
	private static JPanel singleChannelPanel; // Singchannel panel
	private static JPanel featurePanel; // Feature Panel
	private static JPanel fftPanel; // FFT panel
	//private static FeaturesCalc fCalc;
	
	
	public MainView() throws Exception {
		setPanel();
		txtrEegDataLog = new JTextArea();
	}
	
	public void setPanel()throws Exception{
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/com/lw/gui/resource/brain_spawn.png")));
		setTitle("EEGTerminator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setBounds(100, 50, 1200, 700);
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		// Menu Panel
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// Menu Item
		JMenu mnFile = new JMenu("File");

		menuBar.add(mnFile);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mntmLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmLoad);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnFile.add(mntmExit);
		
		JMenu mnAnalysis = new JMenu("Analysis");
		menuBar.add(mnAnalysis);
		
		JMenuItem mntmStart = new JMenuItem("Start");
		mntmStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnAnalysis.add(mntmStart);
		
		JMenu mnAbout = new JMenu("About");
		menuBar.add(mnAbout);
		
		JMenuItem mntmAbout = new JMenuItem("About");
		mntmAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		mnAbout.add(mntmAbout);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		

		/// TextLog

		JScrollPane scrollpane = new JScrollPane(txtrEegDataLog);
		scrollpane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		JPanel panelText = new JPanel();
		panelText.setBackground(Color.WHITE);
		panelText.add(scrollpane, BorderLayout.CENTER);
		//txtrEegDataLog.setText("EEG Data Log.....\n");
		
		
		// Button Item

		JButton btnLoad = new JButton("");
		btnLoad.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/brainstorming.png")));
		
		btnLoad.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				System.out.println("Load Data");
				FileDialog dialog = new FileDialog(frame);
				dialog.setSize(300, 200);
				dialog.setVisible(true);
				
				if(dialog.getDirectory() != null){
					StringTokenizer str = new StringTokenizer(dialog.getFile(), ".");
					ArrayList<String> list = new ArrayList<String>();
					while(str.hasMoreTokens()){
						list.add(str.nextToken());
					}
					int last = list.size() - 1;
					if(list.get(last).equals("csv")){
						String path = dialog.getDirectory() + dialog.getFile();
//						EEG_Main.filepath = path;
//						txtrEegDataLog.append(path + "\n");
//						eegdata = new EEG_Data(dialog.getDirectory() + dialog.getFile());
//						eeg_panel.set_data(eegdata.getDate_all());
//						panel_3d.setdata();
//						fft.fft_on();
//						eeg_panel.set_on();
						EEGData eegData = new EEGData(path);
						String[][] eegRawData;
						try {
							eegRawData = eegData.init();
							eegRawData=eegData.readData(eegData.init());
							String[][] adjeegdata=eegData.adjustData(eegRawData, 128*5,128*5);
							
							AllChannelPlot allChannelPlot = new AllChannelPlot("xx", adjeegdata);
							ChartPanel p = new ChartPanel(allChannelPlot.getChart());
							allChannelPanel.add(p, BorderLayout.CENTER);
							p.validate();
							
							//System.err.println("Before fCals");
							FeaturesCalc fCalc = new FeaturesCalc(adjeegdata);
							fCalc.calc(adjeegdata);
							double[][] avgFeatureAF3 = fCalc.getAvgFeatures();
							double[] featurebuffer = new double[4];
							//System.err.println(avgFeatureAF3[0][0] +" length :"+ avgFeatureAF3.length);
							
							//System.err.println("After fCals, before plot");
							BarChart3DPlot featurePlot = new BarChart3DPlot();
							ChartPanel p2 = new ChartPanel(featurePlot.getChart());
							featurePanel.add(p2, BorderLayout.CENTER);
							p2.validate();
							
							for(int f=0; f<avgFeatureAF3.length; f++){
								//featurePanel.removeAll();
								System.arraycopy(avgFeatureAF3[f], 0, featurebuffer, 0, 4);
								featurePlot.updateDataset(featurebuffer);
							}
							
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}else{
						final Dialog dialog_confirm = new Dialog(frame, "Confirm");
						dialog_confirm.setLayout(new BorderLayout(0, 0));
						dialog_confirm.setBackground(Color.WHITE);
						dialog_confirm.add(new Label("Check your File, Please Select \".csv\" File!!", Label.CENTER));
						txtrEegDataLog.append("Check your File, Please Select \".csv\" File!!\n");
						dialog_confirm.setSize(400, 100);
						dialog_confirm.setLocation(100, 100);
						dialog_confirm.setVisible(true);
						dialog_confirm.addWindowListener(new WindowAdapter() {
							public void windowClosing(WindowEvent e){
								dialog_confirm.dispose();
							}
						});
					}
				}
			}
		});
		
		

		JButton btnLoad_1 = new JButton("");
		btnLoad_1.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/heart.png")));
		btnLoad_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnStart = new JButton("");
		btnStart.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/playback_play.png")));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnAnalysis = new JButton("");
		btnAnalysis.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/lightbulb.png")));
		btnAnalysis.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
	

		JButton btnCapture = new JButton("");
		btnCapture.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/photo.png")));
		btnCapture.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
	
		

		// Tab Panel

		//TODO - change tab label bcolor
			//tabbedPane.addTab(null, myComponent);
		 	//tabbedPane.setTabComponentAt(0, new JLabel("Tab"));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		JPanel tabPanel = new JPanel();
		tabPanel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Raw Data", null, tabPanel, null);
		tabPanel.setLayout(null);
		
		allChannelPanel = new JPanel();
		allChannelPanel.setBounds(63, 6, 1150, 531);
		tabPanel.add(allChannelPanel);
		allChannelPanel.setLayout(new BorderLayout(0, 0));
		///XXXX
//		SingleChannelPlot singleChannelPlot = new SingleChannelPlot("xx");
//		ChartPanel p = new ChartPanel(singleChannelPlot.getChart());
//		allChannelPanel.add(p, BorderLayout.CENTER);
//		p.validate();

	
		singleChannelPanel = new JPanel();
		singleChannelPanel.setBounds(63, 477, 1150, 190);
		tabPanel.add(singleChannelPanel);
		singleChannelPanel.setLayout(new BorderLayout(0, 0));
		
		
		JRadioButton rdbtnAF3 = new JRadioButton("AF3");
		rdbtnAF3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAF3.setForeground(new Color(0, 191, 255));
		rdbtnAF3.setBounds(6, 22, 51, 23);
		rdbtnAF3.setBackground(Color.WHITE);
		tabPanel.add(rdbtnAF3);
		rdbtnAF3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JRadioButton rdbtnF7 = new JRadioButton("F7");
		rdbtnF7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF7.setForeground(new Color(128, 0, 128));
		rdbtnF7.setBounds(6, 56, 51, 23);
		rdbtnF7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF7);
		rdbtnF7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnF3 = new JRadioButton("F3");
		rdbtnF3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF3.setForeground(new Color(0, 128, 0));
		rdbtnF3.setBounds(6, 90, 51, 23);
		rdbtnF3.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF3);
		rdbtnF3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnFC5 = new JRadioButton("FC5");
		rdbtnFC5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFC5.setForeground(new Color(184, 134, 11));
		rdbtnFC5.setBounds(6, 122, 51, 23);
		rdbtnFC5.setBackground(Color.WHITE);
		tabPanel.add(rdbtnFC5);
		rdbtnFC5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnT7 = new JRadioButton("T7");
		rdbtnT7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnT7.setForeground(new Color(0, 0, 128));
		rdbtnT7.setBounds(6, 153, 51, 23);
		rdbtnT7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnT7);
		rdbtnT7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnP7 = new JRadioButton("P7");
		rdbtnP7.setForeground(new Color(178, 34, 34));
		rdbtnP7.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnP7.setBounds(6, 187, 51, 23);
		rdbtnP7.setBackground(Color.WHITE);
		tabPanel.add(rdbtnP7);
		rdbtnP7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnO1 = new JRadioButton("O1");
		rdbtnO1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnO1.setForeground(new Color(105, 105, 105));
		rdbtnO1.setBounds(6, 222, 51, 23);
		rdbtnO1.setBackground(Color.WHITE);
		tabPanel.add(rdbtnO1);
		rdbtnO1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		JRadioButton rdbtnO2 = new JRadioButton("O2");
		rdbtnO2.setForeground(new Color(255, 182, 193));
		rdbtnO2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnO2.setBounds(6, 255, 51, 23);
		rdbtnO2.setBackground(Color.WHITE);
		tabPanel.add(rdbtnO2);
		rdbtnO2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnP8 = new JRadioButton("P8");
		rdbtnP8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnP8.setForeground(new Color(0, 255, 255));
		rdbtnP8.setBounds(6, 286, 51, 23);
		rdbtnP8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnP8);
		rdbtnP8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnT8 = new JRadioButton("T8");
		rdbtnT8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnT8.setForeground(new Color(255, 0, 255));
		rdbtnT8.setBounds(6, 317, 51, 23);
		rdbtnT8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnT8);
		rdbtnT8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnFC6 = new JRadioButton("FC6");
		rdbtnFC6.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnFC6.setForeground(new Color(255, 215, 0));
		rdbtnFC6.setBounds(6, 348, 51, 23);
		rdbtnFC6.setBackground(Color.WHITE);
		tabPanel.add(rdbtnFC6);
		rdbtnFC6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnF4 = new JRadioButton("F4");
		rdbtnF4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF4.setForeground(new Color(0, 0, 205));
		rdbtnF4.setBounds(6, 379, 51, 23);
		rdbtnF4.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF4);
		rdbtnF4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnF8 = new JRadioButton("F8");
		rdbtnF8.setForeground(new Color(50, 205, 50));
		rdbtnF8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnF8.setBounds(6, 410, 51, 23);
		rdbtnF8.setBackground(Color.WHITE);
		tabPanel.add(rdbtnF8);
		rdbtnF8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnAF4 = new JRadioButton("AF4");
		rdbtnAF4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnAF4.setForeground(new Color(255, 0, 0));
		rdbtnAF4.setBounds(6, 444, 51, 23);
		rdbtnAF4.setBackground(Color.WHITE);
		tabPanel.add(rdbtnAF4);
		rdbtnAF4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton rdbtnHR = new JRadioButton("HR");
		rdbtnHR.setFont(new Font("Tahoma", Font.PLAIN, 12));
		rdbtnHR.setBackground(Color.WHITE);
		rdbtnHR.setBounds(6, 477, 51, 23);
		tabPanel.add(rdbtnHR);
		rdbtnHR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox chckbxALL = new JCheckBox("All");
		chckbxALL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxALL.setSelected(true);
		chckbxALL.setBackground(Color.WHITE);
		chckbxALL.setBounds(6, 514, 51, 23);
		tabPanel.add(chckbxALL);
		tabPanel.validate();
		rdbtnHR.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		

		
		//Tab Panel 2
		
		
		JPanel tabPanel_1 = new JPanel();
		tabPanel_1.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab(" FFT ", null, tabPanel_1, null);
		tabPanel_1.setLayout(null);
		
		
		
		featurePanel = new JPanel();
		featurePanel.setBackground(new Color(255, 255, 255));
		featurePanel.setBounds(290, 10, 743, 441);
		tabPanel_1.add(featurePanel);
		
		fftPanel = new JPanel();
		fftPanel.setBounds(290, 452, 743, 245);
		tabPanel_1.add(fftPanel);
		
		
		JRadioButton radioButton = new JRadioButton("AF3");
		radioButton.setBackground(Color.WHITE);
		radioButton.setBounds(63, 116, 51, 23);
		tabPanel_1.add(radioButton);
		radioButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_1 = new JRadioButton("F7");
		radioButton_1.setBackground(Color.WHITE);
		radioButton_1.setBounds(63, 147, 51, 23);
		tabPanel_1.add(radioButton_1);
		radioButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_2 = new JRadioButton("F3");
		radioButton_2.setBackground(Color.WHITE);
		radioButton_2.setBounds(63, 178, 51, 23);
		tabPanel_1.add(radioButton_2);
		radioButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_3 = new JRadioButton("FC5");
		radioButton_3.setBackground(Color.WHITE);
		radioButton_3.setBounds(63, 209, 51, 23);
		tabPanel_1.add(radioButton_3);
		radioButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_4 = new JRadioButton("T7");
		radioButton_4.setBackground(Color.WHITE);
		radioButton_4.setBounds(63, 240, 51, 23);
		tabPanel_1.add(radioButton_4);
		radioButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_5 = new JRadioButton("P7");
		radioButton_5.setBackground(Color.WHITE);
		radioButton_5.setBounds(63, 274, 51, 23);
		tabPanel_1.add(radioButton_5);
		radioButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_6 = new JRadioButton("O1");
		radioButton_6.setBackground(Color.WHITE);
		radioButton_6.setBounds(63, 305, 51, 23);
		tabPanel_1.add(radioButton_6);
		radioButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_7 = new JRadioButton("O2");
		radioButton_7.setBackground(Color.WHITE);
		radioButton_7.setBounds(137, 116, 51, 23);
		tabPanel_1.add(radioButton_7);
		radioButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_8 = new JRadioButton("P8");
		radioButton_8.setBackground(Color.WHITE);
		radioButton_8.setBounds(137, 147, 51, 23);
		tabPanel_1.add(radioButton_8);
		radioButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_9 = new JRadioButton("T8");
		radioButton_9.setBackground(Color.WHITE);
		radioButton_9.setBounds(137, 178, 51, 23);
		tabPanel_1.add(radioButton_9);
		radioButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_10 = new JRadioButton("FC6");
		radioButton_10.setBackground(Color.WHITE);
		radioButton_10.setBounds(137, 209, 51, 23);
		tabPanel_1.add(radioButton_10);
		radioButton_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_11 = new JRadioButton("F4");
		radioButton_11.setBackground(Color.WHITE);
		radioButton_11.setBounds(137, 240, 51, 23);
		tabPanel_1.add(radioButton_11);
		radioButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_12 = new JRadioButton("F8");
		radioButton_12.setBackground(Color.WHITE);
		radioButton_12.setBounds(137, 271, 51, 23);
		tabPanel_1.add(radioButton_12);
		radioButton_12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_13 = new JRadioButton("AF4");
		radioButton_13.setBackground(Color.WHITE);
		radioButton_13.setBounds(137, 302, 51, 23);
		tabPanel_1.add(radioButton_13);
		radioButton_13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JRadioButton radioButton_14 = new JRadioButton("HR");
		radioButton_14.setBackground(Color.WHITE);
		radioButton_14.setBounds(63, 346, 51, 23);
		tabPanel_1.add(radioButton_14);
		radioButton_14.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox checkBox = new JCheckBox("Hanning");
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(24, 536, 74, 23);
		tabPanel_1.add(checkBox);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox checkBox_1 = new JCheckBox("Hamming");
		checkBox_1.setBackground(Color.WHITE);
		checkBox_1.setBounds(24, 499, 74, 23);
		tabPanel_1.add(checkBox_1);
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox checkBox_2 = new JCheckBox("Blackman");
		checkBox_2.setBackground(Color.WHITE);
		checkBox_2.setBounds(24, 456, 74, 23);
		tabPanel_1.add(checkBox_2);
		checkBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		JCheckBox checkBox_3 = new JCheckBox("Rectangular");
		checkBox_3.setBackground(Color.WHITE);
		checkBox_3.setBounds(24, 576, 109, 23);
		tabPanel_1.add(checkBox_3);
		
		
		
		// Tab panel3
		JPanel tabPanel_2 = new JPanel();
		tabPanel_2.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Estate", null, tabPanel_2, null);
		tabPanel_2.setLayout(null);
		
		
		//UIManager.put("TabbedPane.selected", Color.black);
		
		
		// Layout 
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnLoad_1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnLoad, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
							.addComponent(btnCapture, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
							.addComponent(btnAnalysis, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
							.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(panelText, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE))
					.addGap(12)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 1047, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addComponent(btnLoad)
					.addGap(18)
					.addComponent(btnLoad_1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnStart)
					.addGap(18)
					.addComponent(btnAnalysis)
					.addGap(18)
					.addComponent(btnCapture)
					.addGap(69)
					.addComponent(panelText, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(119, Short.MAX_VALUE))
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 631, Short.MAX_VALUE)
		);
		
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.addTab("Analysis", null, tabbedPane_1, null);

		contentPane.setLayout(gl_contentPane);
		
	}
}
