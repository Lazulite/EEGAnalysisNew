package com.lw.eeg.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

public class AllChannelPlot {

    private static final String TITLE = "Dynamic Series";
    private static final String START = "Start";
    private static final String STOP = "Stop";
    private static final float MINMAX = 5500;
    private static final int COUNT = 2 * 200;
    private static final int FAST = 100;
    private static final int SLOW = FAST * 5;
    private static final Random random = new Random();
    private Timer timer;
    private JFreeChart chart;
    private String[][] rawdata;
    //AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
    private float[] AF3;
    private float[] F7;
    private float[] F3;
    private float[] FC5;
    private float[] T7;
    private float[] P7;
    private float[] O1;
    private float[] O2;
    private float[] P8;
    private float[] T8;
    private float[] FC6;
    private float[] F4;
    private float[] F8;
    private float[] AF4;

    
    private int i;

    public AllChannelPlot(final String title, final String[][] _rawdata) {
        rawdata = _rawdata;
        i=0;
    	final DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(16, COUNT, new Second());
    	dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
    	
    	
    	//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
    	AF3 = new float[rawdata.length];
    	F7 = new float[rawdata.length];
    	F3 = new float[rawdata.length];
    	FC5 = new float[rawdata.length];
    	T7 = new float[rawdata.length];
    	P7 = new float[rawdata.length];
    	O1 = new float[rawdata.length];
    	O2 = new float[rawdata.length];
    	P8 = new float[rawdata.length];
    	T8 = new float[rawdata.length];
    	FC6 = new float[rawdata.length];
    	F4 = new float[rawdata.length];
    	F8 = new float[rawdata.length];
    	AF4 = new float[rawdata.length];
    	
    	
    	
    	//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
    	for(int i=0; i<rawdata.length-1;i++){
        	AF3[i] = Float.valueOf(rawdata[i][1])+750;
    	}
    	for(int i=0; i<rawdata.length-1;i++){
        	F7[i] = Float.valueOf(rawdata[i][2])-400;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	F3[i] = Float.valueOf(rawdata[i][3])-400*2-750;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	FC5[i] = Float.valueOf(rawdata[i][4])-400*3+500;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	T7[i] = Float.valueOf(rawdata[i][5])-400*4-100;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	P7[i] = Float.valueOf(rawdata[i][6])-400*5-750;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	O1[i] = Float.valueOf(rawdata[i][7])-400*6-750;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	O2[i] = Float.valueOf(rawdata[i][8])-400*7-1500;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	P8[i] = Float.valueOf(rawdata[i][9])-400*8-1500;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	T8[i] = Float.valueOf(rawdata[i][10])-400*9-2000;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	FC6[i] = Float.valueOf(rawdata[i][11])-400*10-2350;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	F4[i] = Float.valueOf(rawdata[i][12])-400*11-3750;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	F8[i] = Float.valueOf(rawdata[i][13])-400*12-2500;
        }
    	for(int i=0; i<rawdata.length-1;i++){
        	AF4[i] = Float.valueOf(rawdata[i][14])-400*13-3150;
        }
    	//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY
    	dataset.addSeries(AF3,0, "");
    	dataset.addSeries(F7,1, "");
    	dataset.addSeries(F3,2, "");
    	dataset.addSeries(FC5,3, "");
    	dataset.addSeries(T7,4, "");
    	dataset.addSeries(P7,5, "");
    	dataset.addSeries(O1,6, "");
    	dataset.addSeries(O2,7, "");
    	dataset.addSeries(P8,8, "");
    	dataset.addSeries(T8,9, "");
    	dataset.addSeries(FC6,10, "");
    	dataset.addSeries(F4,11, "");
    	dataset.addSeries(F8,12, "");
    	dataset.addSeries(AF4,13, "");
    	
        chart = createChart(dataset);
        
        timer = new Timer(FAST, new ActionListener() {

            float[] newData = new float[14];

            @Override
            public void actionPerformed(ActionEvent e) {
                newData[0] = (float) getNext(AF3);
                newData[1] = (float) getNext(F7);
                newData[2] = (float) getNext(F3);
                newData[3] = (float) getNext(FC5);
                newData[4] = (float) getNext(T7);
                newData[5] = (float) getNext(P7);
                newData[6] = (float) getNext(O1);
                newData[7] = (float) getNext(O2);
                newData[8] = (float) getNext(P8);
                newData[9] = (float) getNext(T8);
                newData[10] = (float) getNext(FC6);
                newData[11] = (float) getNext(F4);
                newData[12] = (float) getNext(F8);
                newData[13] = (float) getNext(AF4);
                dataset.advanceTime();
                dataset.appendData(newData);
            }
        });
        
        timer.start();
        
    }

    private double getNext(float[] channel) {
    	i++;
        return channel[i];  
    }

    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart("", "", "", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        plot.getRenderer().setSeriesVisibleInLegend(false);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.clearRangeMarkers();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-MINMAX, MINMAX);
        return result;
    }
   
    public JFreeChart getChart(){
    	return chart;
    }
}