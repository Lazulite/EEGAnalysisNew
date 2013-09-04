package com.lw.eeg.plot;


import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;


public class SingleChannelPlot {

    private static float MIN = 0;
    private static float MAX =0;
    private static int COUNT = 0;
    private static int FAST = 0;
    private Timer timer;
    private JFreeChart chart;
    private double[] channel;
    private int i=0;
    private int mean=0;
    private String type;
    
    public SingleChannelPlot() {    	
    }
    
    public void setType(String _type){
    	type=_type;
    	
    	if(type=="HRV"){
    		COUNT=50;
    		FAST=500;
    		MIN=850;
    		MAX=1200;
    	}
    	if(type == "HR"){
    		COUNT=50;
    		FAST=500;
    		MIN=50;
    		MAX=120;
    	}
    	if(type=="singleEEG")
    	{
    		COUNT=128*5;
    		FAST=1;
    		MIN=3500;
    		MAX=5500;
    	}
    	if(type == "EEGFFT"){
    		
    	}
    	if(type == "HRVFFT"){
    		
    	}

    }
    
    public void init(){
    	 final DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(1, COUNT, new Second());
         dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
         
         dataset.addSeries(toDoulbe(), 0, "");
         chart = createChart(dataset);
         timer = new Timer(FAST, new ActionListener() {

             float[] newData = new float[1];
            
             @Override
             public void actionPerformed(ActionEvent e) {
            	 if(i>=channel.length-1){
            		 newData[0]=1000;
            	 }else{
                 newData[0] = (float) getNext();
            	 }
                 dataset.advanceTime();
                 dataset.appendData(newData);
             }
         });
         timer.start();
    }

    public void setData(double[] _channel){
    	channel=_channel;
    	System.err.println("In set Data" + System.currentTimeMillis());
    }
    
    private float[] toDoulbe() {
		float[] result = new float[channel.length];
    	for(int d=0; d<channel.length; d++){
    		result[d]=Float.valueOf(String.valueOf(channel[d]));
    	}
    		
		return result;
	}
    
    
    private double getNext() {
    	i++;
    	//System.err.println("getNext " + channel[i] + "  "+i);
        return channel[i];  
    }

	private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "", "", "", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        plot.getRenderer().setSeriesVisibleInLegend(false);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setAutoRange(true);
        range.setRange(MIN, MAX);
        return result;
    }

    public JFreeChart getChart() {
        return chart;
    }

}