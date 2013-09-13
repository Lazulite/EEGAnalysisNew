package com.lw.eeg.plot;

import java.awt.Color;

import javax.xml.bind.Marshaller.Listener;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DatasetChangeEvent;
import org.jfree.data.general.DatasetChangeListener;
import org.jfree.data.resources.DataPackageResources;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.lw.eeg.data.Data;


public  class BarChart3DPlot implements DatasetChangeListener {
	private JFreeChart chart;
	private DefaultCategoryDataset dataset;
	private double[] data;
	
    public BarChart3DPlot() {
       
    }
    public void setData(double[] _data){
    	data = new double[_data.length];
    	System.arraycopy(_data, 0, data, 0, _data.length);
    	CategoryDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
    }


    public void updateDataset(double[] data){
	    
		dataset.setValue(data[0], dataset.getRowKey(0), dataset.getColumnKey(0));
		dataset.setValue(data[1], dataset.getRowKey(1), dataset.getColumnKey(1));
		dataset.setValue(data[2], dataset.getRowKey(2), dataset.getColumnKey(2));
		dataset.setValue(data[3], dataset.getRowKey(3), dataset.getColumnKey(3));
//    	dataset.addValue(data[0], "1", "Delta");
//        dataset.addValue(data[1], "2", "Theta");
//        dataset.addValue(data[2], "3", "Alpha");
//        dataset.addValue(data[3], "4", "Beta");
		//System.err.println("update");
		//dataset.clear();
    }
    
    private CategoryDataset createDataset() {
//    	for(double d:data){
//    		System.err.println("HERE!!!!!!" +d);
//    	}
        dataset = new DefaultCategoryDataset();
        dataset.addValue(data[0], "1", "Delta");
        dataset.addValue(data[1], "2", "Theta");
        dataset.addValue(data[2], "3", "Alpha");
        dataset.addValue(data[3], "4", "Beta");
        //dataset.addChangeListener(this); 
        return dataset;
    }

    private JFreeChart createChart(final CategoryDataset dataset) {
        
        chart = ChartFactory.createBarChart3D(
            "",      // chart title
            "",               // domain axis label
            "",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        //plot.setRangeZeroBaselinePaint(Color.black);
        //plot.setDomainCrosshairPaint(Color.black);
        final CategoryAxis axis = plot.getDomainAxis();
        
//        axis.setCategoryLabelPositions(
//            CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 20.0)
//        );
        
        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setItemLabelsVisible(true);
        final BarRenderer r = (BarRenderer) chart.getCategoryPlot().getRenderer();;
        r.setMaximumBarWidth(0.09);
        r.setItemMargin(-1);
        
        return chart;

    }
    public JFreeChart getChart(){
    	return chart;
    }


	@Override
	public void datasetChanged(DatasetChangeEvent arg0) {
		// TODO Auto-generated method stub
//		newchart = createChart(dataset);
//		System.err.println("Chanege1!!!");
	}

}