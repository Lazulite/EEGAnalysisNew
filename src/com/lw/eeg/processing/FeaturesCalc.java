package com.lw.eeg.processing;

import java.util.ArrayList;
import java.util.List;

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
	
	List<List<List<String>>> fftList = new ArrayList<List<List<String>>>();
	List<List<List<String>>> ftsList = new ArrayList<List<List<String>>>();

	//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
	List<List<String>> fft_1 = new ArrayList<List<String>>();
	List<List<String>> fft_2 = new ArrayList<List<String>>();
	List<List<String>> fft_3 = new ArrayList<List<String>>();
	List<List<String>> fft_4 = new ArrayList<List<String>>();
	List<List<String>> fft_5 = new ArrayList<List<String>>();
	List<List<String>> fft_6 = new ArrayList<List<String>>();
	List<List<String>> fft_7 = new ArrayList<List<String>>();
	List<List<String>> fft_8 = new ArrayList<List<String>>();
	List<List<String>> fft_9 = new ArrayList<List<String>>();
	List<List<String>> fft_10 = new ArrayList<List<String>>();
	List<List<String>> fft_11 = new ArrayList<List<String>>();
	List<List<String>> fft_12 = new ArrayList<List<String>>();
	List<List<String>> fft_13 = new ArrayList<List<String>>();
	List<List<String>> fft_14 = new ArrayList<List<String>>();
	
	
	List<List<String>> fts_1 = new ArrayList<List<String>>();
	List<List<String>> fts_2 = new ArrayList<List<String>>();
	List<List<String>> fts_3 = new ArrayList<List<String>>();
	List<List<String>> fts_4 = new ArrayList<List<String>>();
	List<List<String>> fts_5 = new ArrayList<List<String>>();
	List<List<String>> fts_6 = new ArrayList<List<String>>();
	List<List<String>> fts_7 = new ArrayList<List<String>>();
	List<List<String>> fts_8 = new ArrayList<List<String>>();
	List<List<String>> fts_9 = new ArrayList<List<String>>();
	List<List<String>> fts_10 = new ArrayList<List<String>>();
	List<List<String>> fts_11 = new ArrayList<List<String>>();
	List<List<String>> fts_12 = new ArrayList<List<String>>();
	List<List<String>> fts_13 = new ArrayList<List<String>>();
	List<List<String>> fts_14 = new ArrayList<List<String>>();
	
	
	
	public FeaturesCalc(){
		eegData = new EEGData();
		totalFeature = new double[14][4];
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
		System.out.println("FeaturesCalc.getFFTResult()");
		System.err.println("All result are 0, fix later");
//		for(double d: fftresult){
//		System.out.println(d);
//		}
		return fftresult;
	}

	public void calc( String[][] adjeegdata){
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
				boolean flag = true;
				while(Math.abs(index-adjeegdata.length)>=128){
					
					double[] segment=eegData.getSegment(channel, index, 128);
					
					feature = new FeatureExtraction(segment,segment.length);
					feature.applyWindowFunc(windowType);
					feature.applyFFT();
				
					double[] fftbuf= feature.getFFTresult();
			        feature.calcEEGFeature();
			        double[] features=feature.getFeature();
			        
			        List<String> ffttemp= new ArrayList<String>();
			        List<String> featuretemp = new ArrayList<String>();
			         
			        if(ch==1){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_1.add(ffttemp);
			        	fts_1.add(featuretemp);
			        }
			        if(ch==2){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_2.add(ffttemp);
			        	fts_2.add(featuretemp);
			        }
			        if(ch==3){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_3.add(ffttemp);
			        	fts_3.add(featuretemp);
			        }
			        if(ch==4){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_4.add(ffttemp);
			        	fts_4.add(featuretemp);
			        }
			        if(ch==5){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_5.add(ffttemp);
			        	fts_5.add(featuretemp);
			        }
			        if(ch==6){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_6.add(ffttemp);
			        	fts_6.add(featuretemp);
			        }
			        if(ch==7){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_7.add(ffttemp);
			        	fts_7.add(featuretemp);
			        }
			        if(ch==8){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_8.add(ffttemp);
			        	fts_8.add(featuretemp);
			        }
			        if(ch==9){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_9.add(ffttemp);
			        	fts_9.add(featuretemp);
			        }
			        if(ch==10){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_10.add(ffttemp);
			        	fts_10.add(featuretemp);
			        }
			        if(ch==11){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_11.add(ffttemp);
			        	fts_11.add(featuretemp);
			        }
			        if(ch==12){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_12.add(ffttemp);
			        	fts_12.add(featuretemp);
			        }
			        if(ch==13){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_13.add(ffttemp);
			        	fts_13.add(featuretemp);
			        }
			        if(ch==14){
			        	for(double f:fftbuf)
			        		ffttemp.add(String.valueOf(f));
			        	for(double fs:features)
			        		featuretemp.add(String.valueOf(fs));
			        	fft_14.add(ffttemp);
			        	fts_14.add(featuretemp);
			        }
			        
			        
			        
//			        if(ch==1 && index>1000 &&flag){
//			        	//System.out.println("FeaturesCalc.calc() if{}");
//			        	flag=false;
//			        	fftresult = new double[fftbuf.length];
//			        	System.arraycopy(fftbuf, 0, fftresult, 0, fftbuf.length);
////			        	for(double d: fftresult){
////			        		System.out.println(d);
////			        	}
//			        }
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
				
				for(int t=0; t<4; t++){
					totalFeature[ch-1][t]=featuressum[t];					
				}
			
		}
		
		fftList.add(fft_1);
		fftList.add(fft_2);
		fftList.add(fft_3);
		fftList.add(fft_4);
		fftList.add(fft_5);
		fftList.add(fft_6);
		fftList.add(fft_7);
		fftList.add(fft_8);
		fftList.add(fft_9);
		fftList.add(fft_10);
		fftList.add(fft_11);
		fftList.add(fft_12);
		fftList.add(fft_13);
		fftList.add(fft_14);
		
		ftsList.add(fts_1);
		ftsList.add(fts_2);
		ftsList.add(fts_3);
		ftsList.add(fts_4);
		ftsList.add(fts_5);
		ftsList.add(fts_6);
		ftsList.add(fts_7);
		ftsList.add(fts_8);
		ftsList.add(fts_9);
		ftsList.add(fts_10);
		ftsList.add(fts_11);
		ftsList.add(fts_12);
		ftsList.add(fts_13);
		ftsList.add(fts_14);

	}
	
	public List< List<List<String>>> getfftList(){
		return fftList;
	}
	
	public List< List<List<String>>> getftsList(){
		return ftsList;
	}
	
	
	
	public List<List<String>> getfft_1(){
		return fft_1;
	}
	public List<List<String>> getfft_2(){
		return fft_2;
	}
	public List<List<String>> getfft_3(){
		return fft_3;
	}
	public List<List<String>> getfft_4(){
		return fft_4;
	}
	public List<List<String>> getfft_5(){
		return fft_5;
	}
	public List<List<String>> getfft_6(){
		return fft_6;
	}
	public List<List<String>> getfft_7(){
		return fft_7;
	}
	public List<List<String>> getfft_8(){
		return fft_8;
	}
	public List<List<String>> getfft_9(){
		return fft_9;
	}
	public List<List<String>> getfft_10(){
		return fft_10;
	}
	public List<List<String>> getfft_11(){
		return fft_11;
	}
	public List<List<String>> getfft_12(){
		return fft_12;
	}
	public List<List<String>> getfft_13(){
		return fft_13;
	}
	public List<List<String>> getfft_14(){
		return fft_14;
	}
	
	
	
	public List<List<String>> getfts_1(){
		return fts_1;
	}
	public List<List<String>> getfts_2(){
		return fts_2;
	}
	public List<List<String>> getfts_3(){
		return fts_3;
	}
	public List<List<String>> getfts_4(){
		return fts_4;
	}
	public List<List<String>> getfts_5(){
		return fts_5;
	}
	public List<List<String>> getfts_6(){
		return fts_6;
	}
	public List<List<String>> getfts_7(){
		return fts_7;
	}
	public List<List<String>> getfts_8(){
		return fts_8;
	}
	public List<List<String>> getfts_9(){
		return fts_9;
	}
	public List<List<String>> getfts_10(){
		return fts_10;
	}
	public List<List<String>> getfts_11(){
		return fts_11;
	}
	public List<List<String>> getfts_12(){
		return fts_12;
	}
	public List<List<String>> getfts_13(){
		return fts_13;
	}
	public List<List<String>> getfts_14(){
		return fts_14;
	}
	
	
	
	public double[] calParas(double[][] AllFeature){

		double low = 0.0000;
		double high = 0.0000;

		paras = new double[8];
		
		//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
		//paras[0] low/high ratio from sum of all channel
		//paras[1][2][3] beta ratio between three pair of channels
		
		for(int row=0; row<totalFeature.length;row++){
			for(int col=0;col<totalFeature[0].length;col++){
				if(row == 0){
					low = totalFeature[row][0]+totalFeature[row][1];
					high = totalFeature[row][2]+totalFeature[row][3];				
				}
				if(row>0){
					low += totalFeature[row][0]+totalFeature[row][1];
					high += totalFeature[row][2]+totalFeature[row][3];
				}
//				if(row == 3){
//					low += totalFeature[row][0]+totalFeature[row][1];
//					high += totalFeature[row][2]+totalFeature[row][3];
//				}
//				if(row == 13){
//					low += totalFeature[row][0]+totalFeature[row][1];
//					high += totalFeature[row][2]+totalFeature[row][3];
//				}
//				if(row == 11){
//					low += totalFeature[row][0]+totalFeature[row][1];
//					high += totalFeature[row][2]+totalFeature[row][3];
//				}
//				if(row == 10){
//					low += totalFeature[row][0]+totalFeature[row][1];
//					high += totalFeature[row][2]+totalFeature[row][3];
//				}
				
				paras[0]= low/ high;
//				paras[1]=totalFeature[0][2]/totalFeature[13][2];
//				paras[2]=totalFeature[2][2]/totalFeature[11][2];
//				paras[3]=totalFeature[3][2]/totalFeature[10][2];
//				paras[4]=totalFeature[0][3]/totalFeature[13][3];
//				paras[5]=totalFeature[2][3]/totalFeature[11][3];
//				paras[5]=totalFeature[3][3]/totalFeature[10][3];
				
//				paras[1]=totalFeature[0][2]/totalFeature[13][2];
//				paras[2]=totalFeature[0][3]/totalFeature[13][3];
				
				
				paras[1]=totalFeature[0][3]/totalFeature[13][3];
				paras[2]=totalFeature[1][3]/totalFeature[12][3];
				paras[3]=totalFeature[2][3]/totalFeature[11][3];
				paras[4]=totalFeature[3][3]/totalFeature[10][3];
				paras[5]=totalFeature[4][3]/totalFeature[9][3];
				paras[6]=totalFeature[5][3]/totalFeature[8][3];
				paras[7]=totalFeature[6][3]/totalFeature[7][3];

			}
		}
	
		return paras;
	}

	
}
