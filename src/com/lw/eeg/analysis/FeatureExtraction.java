package com.lw.eeg.analysis;

import java.awt.geom.AffineTransform;

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D;

public class FeatureExtraction {

	private double[] data;
	private double[] beforewindow;
	private double[] afterfft;
	private double[] feature;
	private double[] hrvFeature;
	private int size;
	private double fs=128;
	
	public FeatureExtraction(double[] _data, int _size){
		data=_data;
		size=_size;
		beforewindow=new double[data.length];
		System.arraycopy(data, 0, beforewindow, 0, data.length);
	}
	

	public void applyWindowFunc(String window ){
		WindowFunction w=new WindowFunction();
	    w.setWindowType(window);
	    double[] wdata=new double[data.length];
	    wdata=w.generate(data.length);
	    for(int d=0;d<wdata.length;d++){
	    	data[d]=data[d]*wdata[d];
	    }
		
	}
	
	public void applyFFT(){
		
		DoubleFFT_1D doublefft= new DoubleFFT_1D(size);
		double[] fftData = new double[size * 2];
		
		System.arraycopy(data, 0, fftData, 0, size);
		
		doublefft.realForwardFull(fftData);


		double[] spectrum = new double[fftData.length];
		for (int k= 0; k<size/2; k++)
		{
			spectrum[k] = 10*Math.log10(Math.sqrt(fftData[2*k]*fftData[2*k]  + fftData[2*k+1]*fftData[2*k+1]));
		}
		
		afterfft=new double[size/2];
		System.arraycopy(spectrum, 0, afterfft, 0, size/2);
		
	}
	
	public void calcEEGFeature(){
		
		double res=fs/size; 
		feature=new double[4];
		int delta=0;
		int theta=0;
		int alpha=0;
		int beta=0;
		for(int d=0;d<afterfft.length;d++){
			double freq=d*res ;
			if(freq>=1 && freq <=4){
				feature[0]+=afterfft[d];
				delta++;
			}else if(freq>4 && freq <=7){
				feature[1]+=afterfft[d];
				theta++;
			}else if(freq>7 && freq<=13){
				feature[2]+=afterfft[d];
				alpha++;
			}else if(freq>13 && freq<30){
				feature[3]+=afterfft[d];
				beta++;
			}
		}
		
		feature[0]=feature[0]/delta;
		feature[1]=feature[0]/theta;
		feature[2]=feature[0]/alpha;
		feature[3]=feature[0]/beta;

	}
	
	
	
	public void calcHRVFeature(){
		
		double res=5/size; 
		hrvFeature=new double[3];
		int vLow=0;
		int low=0;
		int high=0;

		for(int d=0;d<afterfft.length;d++){
			double freq=d*res ;
			if(freq<0.04){
				hrvFeature[0]+=afterfft[d];
				vLow++;
			}else if(freq>=0.04 && freq <=0.15){
				hrvFeature[1]+=afterfft[d];
				low++;
			}else if(freq>0.15 && freq<=0.4){
				hrvFeature[2]+=afterfft[d];
				high++;
			}
		}
		
		hrvFeature[0]=hrvFeature[0]/vLow;
		hrvFeature[1]=hrvFeature[0]/low;
		hrvFeature[2]=hrvFeature[0]/high;

	}
	
	
	
	public double[][] sumEEGFeatures(){

		return null;
	}
	
	
			
	public double[] getFFTresult(){
		return afterfft;
	}
	public void setSampleRate(int sfs){
		fs=sfs;
	}
	public double[] getFeature(){
		return feature;
	}
	
	public double[] getHRVFeature(){
		return hrvFeature;
	}
	
}
