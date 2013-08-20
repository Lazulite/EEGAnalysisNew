package com.lw.eeg.plot;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JComponent;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.annotations.XYTextAnnotation;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CombinedDomainXYPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.StandardXYItemRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class CombinedXYPlot extends ApplicationFrame {
	
	private String[][] rawdata;
	private double[] fftdata;
	private JFreeChart chart;
	//AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,
	private String[] channel= new String[]{"AF3","F7","F3","FC5","T7","P7","O1","O2","P8","T8","FC6","F4","F8","AF4"};

    /**
     * Constructs a new demonstration application.
     *
     * @param title  the frame title.
     */
    public CombinedXYPlot(final String title, String[][] _data) {

        super(title);
        rawdata=_data;
        
        if(title == "EEG Raw rawdata"){
        	chart = createSeperateChart();
        }
        if(title == "EEG Raw rawdata Combination"){
        	chart = createCombinedChart();
        }
        
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        addListener(panel);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);

    }
    
    public CombinedXYPlot(final String title, double[] _data, String channelname) {

        super(title);
        fftdata=_data;
        if(title == "FFT"){
        	chart=createSingleChart(channelname);
        }
      
        final ChartPanel panel = new ChartPanel(chart, true, true, true, false, true);
        addListener(panel);
        panel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(panel);

    }
    
    

    private JFreeChart createSingleChart(String channelname) {
		// TODO Auto-generated method stub
    	NumberAxis domainAxis = new NumberAxis("Frequency Domain");
    	CombinedDomainXYPlot plot = new CombinedDomainXYPlot(domainAxis);
    	XYDataset data = createFFTDataset(channelname);
        XYItemRenderer renderer = new StandardXYItemRenderer();
        
        NumberAxis rangeAxis = new NumberAxis("Magnitude");
        //NumberAxis domainAxis = new NumberAxis("Frequency Domain");
        domainAxis.setRange(0, 64);
        rangeAxis.setAutoRange(true);
        XYPlot subplot1 = new XYPlot(data, domainAxis, rangeAxis, renderer);
        plot.add(subplot1, 1);
        plot.setOrientation(PlotOrientation.VERTICAL);
        // return a new chart containing the overlaid plot...
        return new JFreeChart("FFT Specturm",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	}

	

	private JFreeChart createCombinedChart() {
    	final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
    	XYDataset data1 = createDataset1();
        XYItemRenderer renderer1 = new StandardXYItemRenderer();
        
        NumberAxis rangeAxis1 = new NumberAxis("Channels");
        NumberAxis domainAxis1 = new NumberAxis("domain 1");
        domainAxis1.setTickUnit(new NumberTickUnit(0.001));
        rangeAxis1.setRange(3200,5200);
        XYPlot subplot1 = new XYPlot(data1, domainAxis1, rangeAxis1, renderer1);
        plot.add(subplot1, 1);
        subplot1.setBackgroundPaint(Color.white);
        plot.setOrientation(PlotOrientation.VERTICAL);
        // return a new chart containing the overlaid plot...
        return new JFreeChart("EEG Raw rawdata",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);
	}

	/**
     * 
     * Creates a combined chart.
     *
     * @return the combined chart.
     */
    private JFreeChart createSeperateChart(){

        // create subplot 1...
    	final CombinedDomainXYPlot plot = new CombinedDomainXYPlot(new NumberAxis("Domain"));
    	for(int i=0; i<14; i++){
    		XYDataset data1 = createDataset(i);
            XYItemRenderer renderer1 = new StandardXYItemRenderer();
            
            NumberAxis rangeAxis1 = new NumberAxis(channel[i]);
            NumberAxis domainAxis1 = new NumberAxis("domain 1");
            domainAxis1.setTickUnit(new NumberTickUnit(0.001));
            rangeAxis1.setRange(3700,5000);
            XYPlot subplot1 = new XYPlot(data1, domainAxis1, rangeAxis1, renderer1);
            subplot1.setBackgroundPaint(Color.white);
            plot.add(subplot1, 1);
    		
    	}

  
        //subplot1.setRangeAxisLocation(AxisLocation.BOTTOM_OR_LEFT);
        
//        final XYTextAnnotation annotation = new XYTextAnnotation("Hello!", 50.0, 10000.0);
//        annotation.setFont(new Font("SansSerif", Font.PLAIN, 9));
//        annotation.setRotationAngle(Math.PI / 4.0);
//        subplot1.addAnnotation(annotation);
        
         ///create subplot 2...

        //rangeAxis2.setAutoRangeIncludesZero(false);
    	// parent plot...
        plot.setOrientation(PlotOrientation.VERTICAL);
        // return a new chart containing the overlaid plot...
        return new JFreeChart("EEG Raw rawdata",
                              JFreeChart.DEFAULT_TITLE_FONT, plot, true);

    }

   
    //Combined eeg Dataset
    private XYDataset createDataset(int j) {

        // create dataset 1...
    	XYSeriesCollection collection = new XYSeriesCollection();
		XYSeries series1 = new XYSeries(channel[j]);
        for(int i=0; i<rawdata.length-1;i++){
        	//System.out.println(rawdata[i][17]+"  " + rawdata[0][17] + "  " +rawdata[i][j+1]);
        	series1.add(Double.valueOf(rawdata[i][17])-Double.valueOf(rawdata[0][17]),Double.valueOf(rawdata[i][j+1]));
        }
        collection.addSeries(series1);
        return collection;

    }
    
    //FFT dataset
    private XYDataset createFFTDataset(String channel) {
    	
    	XYSeriesCollection collection = new XYSeriesCollection();
    	
		XYSeries series = new XYSeries(channel);
        for(int i=1; i<fftdata.length-1;i++){
        	series.add(i,fftdata[i]);
        }
        collection.addSeries(series);

        return collection;
	}
    
    //Separate eeg raw dataset
    
    private XYDataset createDataset1() {

    	XYSeriesCollection collection = new XYSeriesCollection();
    	for(int j=0; j<14;j++){
    	
			XYSeries series1 = new XYSeries(channel[j]);
	        for(int i=0; i<rawdata.length-1;i++){
	        	series1.add(Double.valueOf(rawdata[i][17])-Double.valueOf(rawdata[0][17]),Double.valueOf(rawdata[i][j+1]));
	        }
	        collection.addSeries(series1);
    	}
        return collection;

    }
    
    // Zoom Listener
    protected void  addListener(ChartPanel chartPanel){
    	chartPanel.addMouseWheelListener(new MouseWheelListener() {

		    public void mouseWheelMoved(MouseWheelEvent e) {
		        if (e.getScrollType() != MouseWheelEvent.WHEEL_UNIT_SCROLL) return;
		        if (e.getWheelRotation()< 0) increaseZoom((ChartPanel)e.getComponent(), true);
		        else                          decreaseZoom((ChartPanel)e.getComponent(), true);
		    }
		   
		    public synchronized void increaseZoom(JComponent chart, boolean saveAction){
		        ChartPanel ch = (ChartPanel)chart;
		        zoomChartAxis(ch, true);
		    } 
		   
		    public synchronized void decreaseZoom(JComponent chart, boolean saveAction){
		        ChartPanel ch = (ChartPanel)chart;
		        zoomChartAxis(ch, false);
		    } 
		   
		    private void zoomChartAxis(ChartPanel chartP, boolean increase){              
		        int width = chartP.getMaximumDrawWidth() - chartP.getMinimumDrawWidth();
		        int height = chartP.getMaximumDrawHeight() - chartP.getMinimumDrawWidth();       
		        if(increase){
		           chartP.zoomInBoth(width/4, height/4);
		        }else{
		           chartP.zoomOutBoth(width/4, height/4);
		        }
		
		    }
    	});
    }
    
}
