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
	private double[] data;
	private JFreeChart mChart;
	private JPanel fftJPanel;

	public SingleLineChartPlot() {

	}

	public JFreeChart getChart(){
		System.out.println("SingleLineChartPlot.getChart()");
		return mChart;
	}

	public void setData(double[]  _data){
		System.out.println("SingleLineChartPlot.setData()");
		data = new double[_data.length];
		System.arraycopy(data, 0, _data, 0, _data.length);
		for(double d: data)
			System.out.println(d);
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
        System.out.println("SingleLineChartPlot.creatChart()");
        return chart;

    }
    private XYSeriesCollection createSeries(){
    	System.out.println("SingleLineChartPlot.createSeries()");
    	XYSeriesCollection collection = new XYSeriesCollection();
    	
		XYSeries series = new XYSeries("");
        for(int i=0; i<data.length;i++){
        	series.add(Double.valueOf(i),Double.valueOf(data[i]));
        }
        collection.addSeries(series);
        return collection;
    }

}