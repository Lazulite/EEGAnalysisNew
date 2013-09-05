package com.lw.eeg.Main;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.TabableView;

import org.jfree.chart.ChartPanel;
import org.omg.CORBA.SystemException;

import weka.classifiers.Classifier;
import weka.core.Instances;
import weka.core.Utils;

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
	private String[][] adjeegdata;
	private ChannelButtons channelButtons;
	private EEGLog eegLogger;
	private String angerPath;
	private String sleepyPath;
	private String stressPath;
	private String testPath;
	
	private double[] paraX;
	private double[] paraY;
	
	private final static String newline = "\n";
	private int windowSize;
	private static final int sampleRate = 128;
	
	public MainView() throws Exception {
		setPanel();
		txtrEegDataLog = new JTextArea();
		angerPath = "C:\\Users\\Leslie\\Desktop\\EEGdata\\final\\anger\\"+"20130831_235426_rawdata.csv";
		sleepyPath = "C:\\Users\\Leslie\\Desktop\\EEGdata\\final\\sleepy\\"+"20130831_234145_rawdata.csv";
		testPath = "C:\\Users\\Leslie\\Desktop\\EEGdata\\newData\\whiteNoise\\"+"20130816_173214_rawdata.csv";
		stressPath = null;
		
		
	}
	
	public void setPanel()throws Exception{
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(MainView.class.getResource("/com/lw/gui/resource/brain_spawn.png")));
		setTitle("JHealth");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 1200, 700);
		//setExtendedState(JFrame.MAXIMIZED_BOTH); 
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
				System.exit(0);
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
		
		
		//Tab Panel Init
		// Tab Panel

		//TODO - change tab label bcolor
			//tabbedPane.addTab(null, myComponent);
		 	//tabbedPane.setTabComponentAt(0, new JLabel("Tab"));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		ChangeListener changeListener = new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		        int index = sourceTabbedPane.getSelectedIndex();
		        System.out.println("Tab changed to: " + sourceTabbedPane.getTitleAt(index));
		        if(sourceTabbedPane.getTitleAt(index) != "Raw Data"){
		        	allChannelPanel.removeAll();
		        }
		        if(sourceTabbedPane.getTitleAt(index) == "Raw Data"){
		        }
			}
		 };
		
		
		tabbedPane.addChangeListener(changeListener);
		JPanel tabPanel = new JPanel();
		tabPanel.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Raw Data", null, tabPanel, null);
		tabPanel.setLayout(null);
		
		allChannelPanel = new JPanel();
		allChannelPanel.setBounds(63, 6, 1150, 534);
		tabPanel.add(allChannelPanel);
		allChannelPanel.setLayout(new BorderLayout(0, 0));
		///XXXX
//				SingleChannelPlot singleChannelPlot = new SingleChannelPlot("xx");
//				ChartPanel p = new ChartPanel(singleChannelPlot.getChart());
//				allChannelPanel.add(p, BorderLayout.CENTER);
//				p.validate();

	
		singleChannelPanel = new JPanel();
		singleChannelPanel.setBounds(63, 550, 1150, 117);
		tabPanel.add(singleChannelPanel);
		singleChannelPanel.setLayout(new BorderLayout(0, 0));
		
		channelButtons = new ChannelButtons(tabPanel);
		/*channelButtons.setData(adjeegdata);
		
		ChartPanel p = new ChartPanel(channelButtons.getChart());
		singleChannelPanel.add(p, BorderLayout.CENTER);
		p.validate();*/
		
		
		
		JCheckBox chckbxALL = new JCheckBox("All");
		chckbxALL.setFont(new Font("Tahoma", Font.PLAIN, 12));
		chckbxALL.setSelected(true);
		chckbxALL.setBackground(Color.WHITE);
		chckbxALL.setBounds(6, 514, 51, 23);
		tabPanel.add(chckbxALL);
		tabPanel.validate();
		chckbxALL.addActionListener(new ActionListener() {
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
		
		ChannelButtonsFFT channelButtonsFFT = new ChannelButtonsFFT(tabPanel_1);
		
		JCheckBox checkBox = new JCheckBox("Hanning");
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(65, 558, 74, 23);
		tabPanel_1.add(checkBox);
		checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox checkBox_1 = new JCheckBox("Hamming");
		checkBox_1.setBackground(Color.WHITE);
		checkBox_1.setBounds(65, 521, 74, 23);
		tabPanel_1.add(checkBox_1);
		checkBox_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JCheckBox checkBox_2 = new JCheckBox("Blackman");
		checkBox_2.setBackground(Color.WHITE);
		checkBox_2.setBounds(65, 478, 74, 23);
		tabPanel_1.add(checkBox_2);
		checkBox_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		JCheckBox checkBox_3 = new JCheckBox("Rectangular");
		checkBox_3.setBackground(Color.WHITE);
		checkBox_3.setBounds(65, 598, 109, 23);
		tabPanel_1.add(checkBox_3);
		
		
		// Tab panel3
		JPanel tabPanel_2 = new JPanel();
		tabPanel_2.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Estate", null, tabPanel_2, null);
		tabPanel_2.setLayout(null);
		
		JPanel estatePanel = new JPanel();
		estatePanel.setBounds(168, 10, 864, 284);
		tabPanel_2.add(estatePanel);
		
		JCheckBox chckbxKNN = new JCheckBox("KNN");
		chckbxKNN.setFont(new Font("Tahoma", Font.BOLD, 16));
		chckbxKNN.setBounds(904, 347, 103, 23);
		tabPanel_2.add(chckbxKNN);
		
		JCheckBox chckbxSVM = new JCheckBox("SVM");
		chckbxSVM.setFont(new Font("Tahoma", Font.BOLD, 16));
		chckbxSVM.setBounds(904, 402, 103, 23);
		tabPanel_2.add(chckbxSVM);
		
		JTextArea wekaText = new JTextArea();
		wekaText.setBounds(167, 347, 609, 305);
		tabPanel_2.add(wekaText);
		
		JPanel tabPanel_3 = new JPanel();
		tabPanel_3.setBackground(new Color(255, 255, 255));
		tabbedPane.addTab("Correlation", null, tabPanel_3, null);
		tabPanel_3.setLayout(null);
		
		JPanel correlationPanel = new JPanel();
		estatePanel.setBounds(168, 10, 864, 284);
		tabPanel_3.add(correlationPanel);
		
		final JPanel scatterpanel = new JPanel();
		scatterpanel.setBackground(Color.WHITE);
		scatterpanel.setBounds(332, 25, 779, 473);
		tabPanel_3.add(scatterpanel);
		

		final JTextArea correlResult = new JTextArea();
		correlResult.setBounds(332, 508, 710, 84);
		tabPanel_3.add(correlResult);
		

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
						//tring emoPath="C:\\Users\\Leslie\\Desktop\\EEGdata\\final\\anger\\"+"20130816_175754_emostate.csv";
						try {
							eegRawData = eegData.init();
							eegRawData=eegData.readData(eegData.init());
							adjeegdata=eegData.adjustData(eegRawData, 128*5,128*5);
//							
//							EMOData emoData = new EMOData(emoPath);
//							String[][] emoRawData = emoData.readData(emoData.init());
//							String[][] adjEmoData=emoData.adjustData(emoRawData, 128*5, 128*5);
							
							
							AllChannelPlot allChannelPlot = new AllChannelPlot("xx", adjeegdata);
							ChartPanel allChannelp = new ChartPanel(allChannelPlot.getChart());
							allChannelPanel.add(allChannelp, BorderLayout.CENTER);
							allChannelp.validate();
							
							//System.err.println("Before fCals");
							FeaturesCalc fCalc = new FeaturesCalc();
							fCalc.calc(adjeegdata);
							double[] fft =fCalc.getFFTResult();
							double[][] avgFeatureAF3 = fCalc.getAvgFeatures(); // get all features of every 5 second
							double[] featurebuffer = new double[4];
							//System.err.println(avgFeatureAF3[0][0] +" length :"+ avgFeatureAF3.length);
							
							//System.err.println("After fCals, before plot");
							BarChart3DPlot featurePlot = new BarChart3DPlot();
							ChartPanel featurep = new ChartPanel(featurePlot.getChart());
							featurePanel.add(featurep, BorderLayout.CENTER);
							featurep.validate();
							
							for(int f=0; f<avgFeatureAF3.length; f++){
								//featurePanel.removeAll();
								System.arraycopy(avgFeatureAF3[f], 0, featurebuffer, 0, 4);
								featurePlot.updateDataset(featurebuffer);
							}
							
							//fCalc.getFFTResult();
							channelButtons.setEnable();
							channelButtons.setData(adjeegdata);
							if(channelButtons.click){
								ChartPanel singleChannelp = new ChartPanel(channelButtons.getChart());
								singleChannelPanel.add(singleChannelp, BorderLayout.CENTER);
								singleChannelp.validate();
							}
							
							SingleLineChartPlot singleLineChartPlot = new SingleLineChartPlot();
							singleLineChartPlot.setData(fft);
							ChartPanel fftChartPanel = new ChartPanel(singleLineChartPlot.getChart());
							fftPanel.add(fftChartPanel,BorderLayout.CENTER);
							fftChartPanel.validate();
							
							
							
							
							
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
		btnStart.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				eegLogger  = new EEGLog();
				eegLogger.startRecord();
				ChartPanel allChannelp = new ChartPanel(eegLogger.getRealtimeChart());
				allChannelPanel.add(allChannelp, BorderLayout.CENTER);
				allChannelp.validate();
			}
		});
		
		JButton btnAnalysis = new JButton("");
		btnAnalysis.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/lightbulb.png")));
		btnAnalysis.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				
				try{
					
				List<List<Double>> trainSet = new ArrayList<List<Double>>();
				List<Double> temp = new ArrayList<Double>();
				List<List<Double>> testSet = new ArrayList<List<Double>>();
				//read tranning data
				EEGData angerData = new EEGData(angerPath);
				String[][] angereeg = angerData.init();
				angereeg=angerData.readData(angerData.init());
				String[][] angerAdj=angerData.adjustData(angereeg, 128*3,0);
				
				//calc Paras 
				FeaturesCalc fCalc = new FeaturesCalc();
				fCalc.calc(angerAdj);
				double[] angerFeatures= fCalc.calParas(fCalc.getTotalFeature()); 
				for(double d: angerFeatures){
					temp.add(Double.valueOf(d));

				}
				
				trainSet.add(temp);
				
//				System.err.println("tranSet.size" + trainSet.size() + "  " + trainSet.get(0).size());
				
				
				// Sleepy
				EEGData sleepyData = new EEGData(sleepyPath);
				String[][] sleepyeeg = sleepyData.init();
				sleepyeeg=sleepyData.readData(sleepyData.init());
				String[][] sleepyAdj=sleepyData.adjustData(sleepyeeg, 128*3,0);
				
				
				fCalc.calc(sleepyAdj);
				double[] sleepyFeatures= fCalc.calParas(fCalc.getTotalFeature()); 
				
				List<Double> tempx = new ArrayList<Double>();
				//System.err.println("!!!!!!!!!!!" + temp.size());
				for(double d: sleepyFeatures){
					tempx.add(Double.valueOf(d));
					//System.err.println(d);
				}
				
				trainSet.add(tempx);
//				System.err.println("tranSet.size" + trainSet.size() + "  " + trainSet.get(0).size());
//				for(int row=0; row<trainSet.size(); row++){
//					System.err.println("row=============" + row);
//					for(int col=0; col<trainSet.get(0).size(); col++)
//					{
//						System.out.println(trainSet.get(row).get(col));
//					}
//				}
				// trainning

				ARFFWraper simpleARFF = new ARFFWraper(trainSet);
				simpleARFF.create();
				Instances mInstances = simpleARFF.getInstances(); 
				mInstances.setClassIndex(mInstances.numAttributes()-1);
				
				WekaClassifier mWeka = new WekaClassifier(mInstances);
				Classifier classifier;
		
				classifier = mWeka.createClassifier("NaiveBayes");
					
				// test
				// read test data
				windowSize = 5;
				int blockSize=sampleRate*windowSize;
				EEGData testData = new EEGData(testPath);
				String[][] testeeg = testData.init();
				testeeg=testData.readData(testData.init());
				String[][] testAdj=testData.adjustData(testeeg, 128*3,0);
				
				int numWindow =(int) testAdj.length/(blockSize);
				String[][] winData = new String[blockSize][testAdj[0].length];
				
				for(int n=0; n<numWindow; n++){
					
					//System.err.println(n+" =============================");
					winData = testData.getSegment(testAdj, n*blockSize, blockSize);
					fCalc.calc(winData);
					double[] winFeatures = fCalc.calParas(fCalc.getTotalFeature());
					List<Double> testTemp = new ArrayList<Double>();
					for(double d: winFeatures){
						testTemp.add(Double.valueOf(d));
					}
					testSet.add(testTemp);
				}
					
					ARFFWraper simpleARFFtest = new ARFFWraper(testSet);
					simpleARFFtest.create();
					Instances mtest = simpleARFFtest.getInstances(); 
					mtest.setClassIndex(mtest.numAttributes()-1);
					
					mWeka.Evaluation(classifier,mtest);
					

			//		double[] dist =classifier.distributionForInstance(test.instance(i)); 
					for (int i = 0; i < mtest.numInstances(); i++) {
						double clsLabel = classifier.classifyInstance(mtest.instance(i));
						double[] dist = classifier.distributionForInstance(mtest.instance(i)); 
						System.out.print((i+1) + " - ");
						System.out.print(mtest.instance(i).toString(mtest.classIndex()) + " *-* ");
						System.out.print(mtest.classAttribute().value((int) clsLabel) + " => ");
						System.out.println(Utils.arrayToString(dist));
						//mtest.instance(i).setClassValue(clsLabel);
					}
					
					
//					double clsLabel = classifier.classifyInstance(mtest.firstInstance());
//					double[] dist = classifier.distributionForInstance(mtest.firstInstance()); 
//					System.err.println("Classification result");
//					System.out.print(mtest.firstInstance().toString(mtest.classIndex()));
////					System.out.print(mtest.classAttribute().value((int) clsLabel) + " - ");
//					System.out.println(Utils.arrayToString(dist));

				
					
					
				}catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				// calc paras
				// put into ARFF wrapper
/*				System.err.println("before construction ");
				ARFFWraper simpleARFFtest = new ARFFWraper(paratest);
				System.err.println("after construction before weired things occurs");
				simpleARFFtest.createTest();
				System.err.println("weired things occur before this line, I made two copies of weired output");
				Instances mtest = simpleARFF.getTestInstances(); 
				mtest.setClassIndex(mtest.numAttributes()-1);*/
				
				
				// evaluation
/*				mWeka.Evaluation(classifier,mtest);
				
				double clsLabel = classifier.classifyInstance(mtest.firstInstance());
				System.err.println("Classification result");
				System.out.print(mtest.firstInstance().toString(mtest.classIndex()));*/
	
			}
			
		});
	

		JButton btnVideo = new JButton("");
		btnVideo.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/video_information_32.png")));
		btnVideo.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){

				VLCPlayer vlcPlayer = new VLCPlayer();
				vlcPlayer.play();

			}
		});
		
		JButton btnStop = new JButton("");
		btnStop.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/stop.png")));
		btnStop.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				eegLogger.stopRecord();
				
			}
		});
		
		JButton btnCapture = new JButton("");
		btnCapture.setIcon(new ImageIcon(MainView.class.getResource("/com/lw/gui/resource/photo.png")));
		btnCapture.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				System.out
				.println("MainView.setPanel().new MouseAdapter() {...}.mouseClicked()");
				correlResult.append("Peason's correlation coefficient :" + newline);
				Correlation correlation = new Correlation();
				// Normalize
				paraX = correlation.Normalize(paraX, 1, -1);
				paraY = correlation.Normalize(paraY, 1, -1);
				int i=0;
				Data helper = new Data();
				while(Math.abs(paraY.length*i - paraX.length)>=paraY.length){
					i++;
				}
				paraX = helper.getSegment(paraX, 0, paraY.length*i);
				//Scale
				paraY = correlation.Scale(paraY, paraX.length/paraY.length);
				
				ScatterPlot scatterPlot = new ScatterPlot(paraX, paraY, "Long Term Excitement", "Heart rate");
				ChartPanel scatterChartPanel = new ChartPanel(scatterPlot.createChart());
				scatterpanel.add(scatterChartPanel, BorderLayout.CENTER);
				scatterChartPanel.validate();
				

				double pearson = correlation.GetCorrelation(paraX, paraY);
				System.out.println(pearson);
				correlResult.append(String.valueOf(pearson) + newline);

			}
		});
		

		
		
		
		
		
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
							.addComponent(btnStart, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnVideo, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
							.addComponent(btnAnalysis, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE))
						.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
						.addComponent(panelText, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCapture, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
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
					.addComponent(btnStop, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnAnalysis)
					.addGap(18)
					.addComponent(btnVideo)
					.addGap(18)
					.addComponent(btnCapture, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(panelText, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(89, Short.MAX_VALUE))
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
		);
		
		
		
		
		
		JButton btnEEG = new JButton("EEG");
		btnEEG.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				System.out.println("Load Data");
				FileDialog dialog1 = new FileDialog(frame);
				dialog1.setSize(300, 200);
				dialog1.setVisible(true);
				dialog1.setTitle("Load Emotion Data");
				if(dialog1.getDirectory() != null){
					StringTokenizer str = new StringTokenizer(dialog1.getFile(), ".");
					ArrayList<String> list = new ArrayList<String>();
					while(str.hasMoreTokens()){
						list.add(str.nextToken());
					}
					int last = list.size() - 1;
					if(list.get(last).equals("csv")){
						String path = dialog1.getDirectory() + dialog1.getFile();
						EMOData emoData = new EMOData(path);
						String[][] emoRawData;
						try{
							emoRawData = emoData.init();
							emoRawData = emoData.readData(emoData.init());
							String[][] adjEmoData = emoData.adjustData(emoRawData, 4*4, 4*4);
							double[] shortExcit = emoData.getChannel(adjEmoData, 0);
							paraX = new double[shortExcit.length];
							System.arraycopy(shortExcit, 0, paraX, 0, shortExcit.length);

						}catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						final Dialog dialog_confirm = new Dialog(frame, "Whoop!");
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
		btnEEG.setBounds(102, 120, 93, 37);
		tabPanel_3.add(btnEEG);
		
		JButton btnHR = new JButton("Heart Rate");
		btnHR.setBounds(102, 187, 93, 37);
		tabPanel_3.add(btnHR);
		btnHR.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				FileDialog dialog2 = new FileDialog(frame);
				dialog2.setSize(300, 200);
				dialog2.setVisible(true);
				dialog2.setTitle("Load HR Data");
				if(dialog2.getDirectory() != null){
					StringTokenizer str = new StringTokenizer(dialog2.getFile(), ".");
					ArrayList<String> list = new ArrayList<String>();
					while(str.hasMoreTokens()){
						list.add(str.nextToken());
					}
					int last = list.size() - 1;
					if(list.get(last).equals("csv")){
						String path = dialog2.getDirectory() + dialog2.getFile();
						HRVData hrvData = new HRVData(path);
						String[][] hrvRawData;

						try{
							hrvRawData = hrvData.init();
							hrvRawData=hrvData.readData(hrvData.init());
							double[] hrRawData = hrvData.getChannel(hrvData.readData(hrvData.init()),1);
							paraY = new double[hrRawData.length];
							System.arraycopy(hrRawData, 0, paraY, 0, hrRawData.length);
						}catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						final Dialog dialog_confirm = new Dialog(frame, "Whoop!");
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

		
		JButton btnHRV = new JButton("HRV");
		btnHRV.setBounds(102, 261, 93, 37);
		tabPanel_3.add(btnHRV);
		btnHRV.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				FileDialog dialog2 = new FileDialog(frame);
				dialog2.setSize(300, 200);
				dialog2.setVisible(true);
				dialog2.setTitle("Load HR Data");
				if(dialog2.getDirectory() != null){
					StringTokenizer str = new StringTokenizer(dialog2.getFile(), ".");
					ArrayList<String> list = new ArrayList<String>();
					while(str.hasMoreTokens()){
						list.add(str.nextToken());
					}
					int last = list.size() - 1;
					if(list.get(last).equals("csv")){
						String path = dialog2.getDirectory() + dialog2.getFile();
						HRVData hrvData = new HRVData(path);
						String[][] hrvRawData;

						try{
							hrvRawData = hrvData.init();
							hrvRawData=hrvData.readData(hrvData.init());
							double[] hrRawData = hrvData.getChannel(hrvData.readData(hrvData.init()),2);
							paraY = new double[hrRawData.length];
							System.arraycopy(hrRawData, 0, paraY, 0, hrRawData.length);
						}catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}else{
						final Dialog dialog_confirm = new Dialog(frame, "Whoop!");
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

		
		

		contentPane.setLayout(gl_contentPane);
		
	}
}
