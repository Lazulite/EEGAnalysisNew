package com.lw.eeg.Main;




import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.jfree.ui.RefineryUtilities;
import org.omg.CORBA.PRIVATE_MEMBER;


import com.lw.eeg.EEGLog.*;
import com.lw.eeg.data.*;
import com.lw.eeg.plot.*;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;


public class EEGAnalysis {
	private static EEGLog eegLogger;
	private static Data dataHelper;
	private static EEGData eegData;
	private static EMOData emoData;
	
	public static void main(String[] args) throws Exception{
		String eegFile="C:\\Users\\Leslie\\Desktop\\EEGdata\\Old\\"+"20130813_103319.csv";
		String emoFile="C:\\Users\\Leslie\\Desktop\\EEGdata\\WithEmotivAPI\\"+"20130815_125803_emostate.csv";
		String hbFile="";
		String sensorFile="";
		
		
		eegLogger = new EEGLog();
		eegData = new EEGData(eegFile);
		emoData = new EMOData(emoFile);
		dataHelper= new Data(eegFile);
	
		String[][] eegdata=eegData.init();
	
		eegdata=eegData.readData(eegData.init());
		String[][] adjeegdata=eegData.adjustData(eegdata, 128, 128);
		
		
		String[][] emodata=emoData.init();
		
		emodata=emoData.readData(emoData.init());
		String[][] adjemodata=emoData.adjustData(emodata, 128, 128);
		
		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,
	/*	double[] AF3=eegData.getChannel(adjdata, 1);

		//dataHelper.getTotolData(eegdata, null, null);
		
		//TODO fft
		//TODO bandpass filter
		//
		
		DoubleFFT_1D doublefft= new DoubleFFT_1D(AF3.length);
		double[] fftData = new double[AF3.length * 2];
		
		System.arraycopy(AF3, 0, fftData, 0, AF3.length);
		
		doublefft.realForward(fftData);
		
//		for(double d: fftData) {
//            System.out.println(d);
//        }
//	
		Double[] spectrum = new Double[fftData.length];
		for (int k= 0; k<AF3.length/2 - 1; k++)
		{
		   spectrum[k] = Math.sqrt(fftData[2*k]*fftData[2*k]  + fftData[2*k+1]*fftData[2*k+1]);
		   System.out.println("k:  "+k+"   "+spectrum[k]);
		}

		CombinedXYPlot chart1= new CombinedXYPlot("EEG Raw Data",adjdata);
        chart1.pack();
        RefineryUtilities.centerFrameOnScreen(chart1);
        chart1.setVisible(true);
        
        CombinedXYPlot chart2 = new CombinedXYPlot("EEG Raw Data Combination",adjdata);
        chart2.pack();
        RefineryUtilities.centerFrameOnScreen(chart2);
        chart2.setVisible(true);
        */
        
		XYSeriesPlot chart2 = new XYSeriesPlot("EState Data",adjemodata);
        chart2.pack();
        RefineryUtilities.centerFrameOnScreen(chart2);
        chart2.setVisible(true);
	}

	
		
		
}



