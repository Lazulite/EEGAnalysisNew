package com.lw.eeg.processing;

import com.lw.eeg.data.EEGData;

public class FeaturesCalc {
	private String[][] data;
	private static EEGData eegData;
	private static FeatureExtraction feature;
	private double[][] totalFeature;
	
	
	public FeaturesCalc(String[][] _data){
		eegData = new EEGData();
		totalFeature = new double[14][4];
	}
	
	
	public double[][] getTotalFeature(){
		return totalFeature;
	}

	public static double[] calc( String[][] adjeegdata){
		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
				double[][] totalFeature = new double[14][4];
				for(int ch=1; ch<15; ch++){
					
					int index=0;
					int count=1;
					double[] featuresbuf=new double[4];
					double[] featuressum=new double[4];
					
					double[] channel=eegData.getChannel(adjeegdata, ch);
//					for(double d:featuressum){
//						System.out.println("channel: "+ d);
//					}
					
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
//						System.err.println("ch=======" + ch);
//						System.out.println("featuresum : " + featuressum[t]);
						totalFeature[ch-1][t]=featuressum[t];
						
					}
					
				}
				
//				for(int row=0; row<totalFeature.length;row++){
//					for(int col=0;col<totalFeature[0].length;col++){
//						System.out.print(totalFeature[row][col]+" ");
//					}
//					System.out.println("row "+ row + " : " );
//				}
				
				
				double para1 = 0.0000;
				double low = 0.0000;
				double high = 0.0000;
				double ratio = 0.0000;
				
				double afRatio=0.0000;
				double fRatio=0.0000;
				double fcRatio=0.0000;
				double[] paras = new double[4];
				for(int row=0; row<totalFeature.length;row++){
					for(int col=0;col<totalFeature[0].length;col++){
						if(row == 0){
							low = totalFeature[row][0]+totalFeature[row][1];
							high = totalFeature[row][2]+totalFeature[row][3];
							
						}
						if(row == 2){
							low += totalFeature[row][0]+totalFeature[row][1];
							high += totalFeature[row][2]+totalFeature[row][3];
						}
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
						paras[1]=totalFeature[0][3]/totalFeature[13][3];
						paras[2]=totalFeature[2][3]/totalFeature[11][3];
						paras[3]=totalFeature[3][3]/totalFeature[10][3];
					}
					//System.out.println("row "+ row + " : " );
				}
			
				return paras;
	}

	
}
