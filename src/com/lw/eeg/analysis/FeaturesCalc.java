package com.lw.eeg.analysis;

import java.util.ArrayList;
import java.util.List;

import weka.gui.Main.ChildFrameMDI;

import com.lw.eeg.data.EEGData;

public class FeaturesCalc {
	private String[][] data;
	private static EEGData eegData;
	private static FeatureExtraction feature;
	private static double[][] totalFeature;
	private static double[][] avgFeatureAF3;
	private static double[] paras;
	private static String windowType = "HANNING";
	private static double[] fftresult;
	private static List<List<String>> totalFFT;
	//private static List<List<String>> newTotalFFT;
	private double[][] newTotalFeature;
	
	
	public FeaturesCalc(){
		eegData = new EEGData();
		totalFeature = new double[14][4];
		newTotalFeature = new double[14][4];
		totalFFT = new ArrayList<List<String>>();
		
	}
	
	public List<List<String>> getTotalFFT(){
		return totalFFT;
	}
	
	public double[][] getTotalFeature(){
		return totalFeature;
	}
	
	public void setWindow(String _windowType){
		windowType = _windowType;
	}
	
	public double[][] getAvgFeatures(){
		return avgFeatureAF3;
	}
	public double[] getFFTResult(){
		return fftresult;
	}

	public double[][] getNewTotalFeature(){
		return newTotalFeature;
	}
	
	//FFT apply whole data, obtain whole channels features
	public void newFeatureCalc(String[][] data){
		for(int ch=1; ch<15; ch++){
			List<String> fftTemp = new ArrayList<String>();
			double[] channel=eegData.getChannel(data, ch);
			feature = new FeatureExtraction(channel,channel.length);
			feature.applyWindowFunc(windowType);
			feature.applyFFT();
			double[] fftbuf= feature.getFFTresult();
			feature.calcEEGFeature();
			double[] temp=feature.getFeature();
			for(int t=0; t<4; t++){
				newTotalFeature[ch-1][t]=temp[t];	
				//System.err.println(t+"====>>" + temp[t]);
			}
		}
	}
	
	// FFT every 1s, average per 5s
	public static void calc( String[][] adjeegdata){
		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
		int size = adjeegdata.length/640;
		avgFeatureAF3 = new double[size][4];
		
		for(int ch=1; ch<15; ch++){
			
				int index=0;
				int count=1;
				int fftnum=0;
				double[] featuresbuf=new double[4];
				double[] featuressum=new double[4];
	
				double[] channel=eegData.getChannel(adjeegdata, ch);
				List<String> fftTemp = new ArrayList<String>();
				
				while(Math.abs(index-adjeegdata.length)>=128){
					
					double[] segment=eegData.getSegment(channel, index, 128);
					
					feature = new FeatureExtraction(segment,segment.length);
					feature.applyWindowFunc(windowType);
					feature.applyFFT();
					
					double[] fftbuf= feature.getFFTresult();
					
					for(double f:fftbuf)
					{
						fftTemp.add(String.valueOf(f));
					}
					
					
			        feature.calcEEGFeature();
			        double[] features=feature.getFeature();
	 
			        for(int f=0; f<4;f++){
			        	featuresbuf[f]+=features[f];
			        } 
			        
					if(count%5==0){
						for(int f=0; f<4;f++){
							featuresbuf[f]=featuresbuf[f]/5;
							//System.err.println("featureBuf " + featuresbuf[f]);
				        	featuressum[f]+=featuresbuf[f];	
				        } 
						if(ch==1){
							System.arraycopy(featuresbuf, 0, avgFeatureAF3[fftnum], 0, 4);
						}
						fftnum++;
					}
					count++;
					index+=128;
				}
				
				totalFFT.add(fftTemp);
				
				for(int t=0; t<4; t++){
					totalFeature[ch-1][t]=featuressum[t];					
				}
			
		}
		
		
	}
	
	public double[] calParas(double[][] AllFeature){

		double low = 0.0000;
		double high = 0.0000;

		paras = new double[9];
		
		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
		//paras[0] low/high ratio from sum of all channel
		//paras[1][2][3] beta ratio between three pair of channels
		
		for(int row=0; row<totalFeature.length;row++){
			for(int col=0;col<totalFeature[0].length;col++){
				if(row == 0){
					low = totalFeature[row][0]+totalFeature[row][1];
					high = totalFeature[row][2]+totalFeature[row][3];				
				}
//				if(row>0){
//					low += totalFeature[row][0]+totalFeature[row][1];
//					high += totalFeature[row][2]+totalFeature[row][3];
//				}
				if(row == 3){
					low += totalFeature[row][0]+totalFeature[row][1];
					high += totalFeature[row][2]+totalFeature[row][3];
				}
				if(row == 13){
					low += totalFeature[row][0]+totalFeature[row][1];
					high += totalFeature[row][2]+totalFeature[row][3];
				}
				if(row == 11){
					low += totalFeature[row][0]+totalFeature[row][1];
					high += totalFeature[row][2]+totalFeature[row][3];
				}
				if(row == 10){
					low += totalFeature[row][0]+totalFeature[row][1];
					high += totalFeature[row][2]+totalFeature[row][3];
				}
				
				paras[0]= low/ high;
//				paras[1]=totalFeature[0][2]/totalFeature[13][2];
//				paras[2]=totalFeature[2][2]/totalFeature[11][2];
//				paras[3]=totalFeature[3][2]/totalFeature[10][2];
//				paras[4]=totalFeature[0][3]/totalFeature[13][3];
//				paras[5]=totalFeature[2][3]/totalFeature[11][3];
//				paras[5]=totalFeature[3][3]/totalFeature[10][3];
				
//				paras[1]=totalFeature[0][2]/totalFeature[13][2];
//				paras[2]=totalFeature[0][3]/totalFeature[13][3];
				
				
//				paras[1]=totalFeature[0][3]/totalFeature[13][3];
//				paras[2]=totalFeature[1][3]/totalFeature[12][3];
//				paras[3]=totalFeature[2][3]/totalFeature[11][3];
//				paras[4]=totalFeature[3][3]/totalFeature[10][3];
//				paras[5]=totalFeature[4][3]/totalFeature[9][3];
//				paras[6]=totalFeature[5][3]/totalFeature[8][3];
//				paras[7]=totalFeature[6][3]/totalFeature[7][3];
				
				paras[1]=totalFeature[0][2];
				paras[2]=totalFeature[0][3];
				paras[3]=totalFeature[13][2];
				paras[4]=totalFeature[13][3];
				paras[5]=totalFeature[2][2];
				paras[6]=totalFeature[2][3];
				paras[7]=totalFeature[11][2];
				paras[8]=totalFeature[11][3];

			}
		}
	
		return paras;
	}

	
}
