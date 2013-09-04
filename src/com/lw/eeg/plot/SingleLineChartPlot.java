package com.lw.eeg.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SingleLineChartPlot {
	private List<String> data;
	private JFreeChart mChart;
	private JPanel fftJPanel;
	
	public SingleLineChartPlot() {
		
	}
	
	public JFreeChart getChart(){
		return mChart;
	}
	
	public void setData(List<String> _data){
		data=_data;
		XYSeriesCollection seriesCollection = createSeries();
		JFreeChart mChart = creatChart(seriesCollection);	
//		ChartPanel fftp = new ChartPanel(mChart);
//		fftJPanel.add(fftp, BorderLayout.CENTER);
//		fftp.validate();
	}
	
	public void setPanel(JPanel _fftJPanel){
		fftJPanel=_fftJPanel;
	}
	
    public JFreeChart creatChart(XYSeriesCollection xCollection) {
        JFreeChart chart = ChartFactory.createXYLineChart(
            "",
            "", 
            "", 
            xCollection,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        return chart;

    }
    private XYSeriesCollection createSeries(){
    	XYSeriesCollection collection = new XYSeriesCollection();
    	
		XYSeries series = new XYSeries("");
        for(double i=0; i<data.size();i++){
        	series.add(Double.valueOf(data.get((int)i)),Double.valueOf(i));
        }
        collection.addSeries(series);
        return collection;
    }

}
