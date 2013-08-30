package com.lw.eeg.plot;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class XYLineChartPlot {
	private String[][] data;
	
    public XYLineChartPlot(String[][] _data) {
        data=_data;
        JFreeChart chart = ChartFactory.createXYLineChart(
            "",
            "Range", 
            "Time Domain", 
            createSeries(),
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );

    }
    private XYSeriesCollection createSeries(){
    	XYSeriesCollection collection = new XYSeriesCollection();
    	
    	for(int j=0; j<5;j++){
    		XYSeries series1 = new XYSeries("Series"+j);
            for(int i=0; i<data.length;i++){
            	series1.add(Double.valueOf(data[i][5]),Double.valueOf(data[i][j]));
            }
            collection.addSeries(series1);
    		
    	}
        return collection;
    }

}
