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

    private static final float MINMAX = 100;
    private static final int COUNT = 2 * 200;
    private static final int FAST = 100;
    private static final int SLOW = FAST * 5;
    private Timer timer;
    private JFreeChart chart;
    private double[] channel;
    private int i;

    public SingleChannelPlot() {
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
                 newData[0] = (float) getNext();
                 dataset.advanceTime();
                 dataset.appendData(newData);
             }
         });
         timer.start();
    }

    public void setData(double[] _channel){
    	channel=_channel;
    	System.err.println(channel[0]);
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
    	System.err.println("getNext " + channel[i]);
        return channel[i];  
    }

	private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart(
            "", "", "", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        plot.getRenderer().setSeriesVisibleInLegend(false);
        plot.setBackgroundPaint(Color.black);
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-MINMAX, MINMAX);
        return result;
    }

    public JFreeChart getChart() {
        return chart;
    }

}