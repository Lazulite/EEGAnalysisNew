package com.lw.eeg.Main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.RowFilter;

import org.jfree.ui.RefineryUtilities;
import org.omg.CORBA.PRIVATE_MEMBER;
import org.omg.PortableServer.ImplicitActivationPolicy;

import weka.core.Instances;


import com.lw.eeg.EEGLog.*;
import com.lw.eeg.data.*;
import com.lw.eeg.plot.*;
import com.lw.eeg.processing.*;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;


public class EEGAnalysis {
	private static EEGLog eegLogger;
	private static Data dataHelper;
	private static EEGData eegData;
	private static EMOData emoData;
	private static FeatureExtraction feature;
	private static String[][] eegdata;
	
	public static void main(String[] args) throws Exception{
		String eegFile="C:\\Users\\Leslie\\Desktop\\EEGdata\\newData\\gaokao\\"+"20130816_175754_rawdata.csv";
		String emoFile="C:\\Users\\Leslie\\Desktop\\EEGdata\\newData\\cosplay\\"+"20130816_175255_emostate.csv";
		String emoFiletest="C:\\Users\\Leslie\\Desktop\\EEGdata\\MusicTest\\"+"20130814_233030_emostate.csv";
		
		String hbFile="";
		String sensorFile="";
		
		
		eegLogger = new EEGLog();
		eegData = new EEGData(eegFile);
		
		eegdata=eegData.init();
	
		eegdata=eegData.readData(eegData.init());
		String[][] adjeegdata=eegData.adjustData(eegdata, 0,0);
		//System.out.println(eegdata.length+ "  " +eegdata[0].length+"   "+ eegdata[0][0]);
		
		
		
//		emoData = new EMOData(emoFile);
//		dataHelper= new Data(eegFile);
//		String[][] emodata=emoData.init();
//		emodata=emoData.readData(emoData.init());
//		String[][] adjemodata=emoData.adjustData(emodata, 128, 128);
		//dataHelper.getTotolData(eegdata, null, null);
		
		
		
		double[][] totalFeature = new double[14][4];
		for(int ch=1; ch<15; ch++){
			
			int index=0;
			int count=1;
			double[] featuresbuf=new double[4];
			double[] featuressum=new double[4];
			
			double[] channel=eegData.getChannel(adjeegdata, ch);
//			for(double d:featuressum){
//				System.out.println("channel: "+ d);
//			}
			
			while(Math.abs(index-adjeegdata.length)>128){
				
				double[] segment=eegData.getSegment(channel, index, 128);
				
				feature=new FeatureExtraction(segment,segment.length);
				feature.applyWindowFunc("HANNING");
				feature.applyFFT();
		        //double[] fftresult= feature.getFFTresult();
		        feature.calcEEGFeature();
		        double[] features=feature.getFeature();
 
		        for(int f=0; f<4;f++){
		        	featuresbuf[f]+=features[f];
		        	
		        } 
				if(count%5==0){
					for(int f=0; f<4;f++){
						featuresbuf[f]=featuresbuf[f]/5;
			        	featuressum[f]+=featuresbuf[f];	
			        } 
				}
				count++;
				index+=128;
			}
			
			for(int t=0; t<4; t++){
				System.err.println("ch=======" + ch);
				System.out.println("featuresum : " + featuressum[t]);
				totalFeature[ch-1][t]=featuressum[t];
				
			}
			
		}
		
		for(int row=0; row<totalFeature.length;row++){
			for(int col=0;col<totalFeature[0].length;col++){
				System.out.print(totalFeature[row][col]+" ");
			}
			System.out.println("row "+ row + " : " );
		}
		
		
		ARFFWraper simpleARFF = new ARFFWraper();
		simpleARFF.create();
		Instances mInstances = simpleARFF.getInstances(); 
		mInstances.setClassIndex(mInstances.numAttributes()-1);
		WekaClassifier mWeka = new WekaClassifier(mInstances);
		mWeka.createClassifier("NaiveBayes");
		
//		String[][] adjeegdata=eegData.adjustData(eegdata, 0, 128);
//		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,
//		double[] AF3=eegData.getChannel(adjeegdata, 1);
//		
//		feature=new FeatureExtraction(AF3,AF3.length);
//		feature.applyWindowFunc("HANNING");
//		
//		feature.applyFFT();
//        double[] fftresult= feature.getFFTresult();
//        feature.calcFeature();
        
		
		//Plot module
//
//		CombinedXYPlot chart1= new CombinedXYPlot("EEG Raw Data",adjeegdata);
//        chart1.pack();
//        RefineryUtilities.centerFrameOnScreen(chart1);
//        chart1.setVisible(true);
//        
//        CombinedXYPlot chart2 = new CombinedXYPlot("EEG Raw Data Combination",adjeegdata);
//        chart2.pack();
//        RefineryUtilities.centerFrameOnScreen(chart2);
//        chart2.setVisible(true);
        
        
//		XYSeriesPlot chart3 = new XYSeriesPlot("EState Data",adjemodata);
//        chart3.pack();
//        RefineryUtilities.centerFrameOnScreen(chart3);
//        chart3.setVisible(true);
		
//		CombinedXYPlot chart2 = new CombinedXYPlot("FFT",fftresult,"AF3");
//		chart2.pack();
//		RefineryUtilities.centerFrameOnScreen(chart2);
//		chart2.setVisible(true);
	}

	
		
		
}



