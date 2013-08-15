
package com.lw.eeg.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

public class XYSeriesPlot extends ApplicationFrame {

    /**
     * A demonstration application showing an XY series containing a null value.
     *
     * @param title  the frame title.
     */
	
	private String[][] data;
	
    public XYSeriesPlot(final String title, String[][] _data) {
        super(title);
        data=_data;
        JFreeChart chart = ChartFactory.createXYLineChart(
            title,
            "Range", 
            "Time Domain", 
            createSeries(),
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
        
        
    }
    private XYSeriesCollection createSeries(){
    	XYSeriesCollection collection = new XYSeriesCollection();
    	
    	for(int j=0; j<4;j++){
    		XYSeries series1 = new XYSeries("Series"+j);
            for(int i=0; i<data.length;i++){
            	series1.add(Double.valueOf(data[i][5]),Double.valueOf(data[i][j]));
            }
            collection.addSeries(series1);
    		
    	}
        return collection;
        

    }

        
        

    }
