package com.lw.eeg.plot;

import java.awt.Color;

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
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.lw.eeg.data.Data;


public class BarChart3DPlot {
	private JFreeChart chart;
	private DefaultCategoryDataset dataset;
	
    public BarChart3DPlot() {
    	
        final CategoryDataset dataset = createDataset();
        //System.err.println("after create dataset");
        chart = createChart(dataset);
        //System.err.println("after create chart");
       
    }

    
    public void updateDataset(double[] data){
    	 dataset.setValue(data[0], dataset.getRowKey(0), dataset.getColumnKey(0));
    	 dataset.setValue(data[1], dataset.getRowKey(1), dataset.getColumnKey(1));
    	 dataset.setValue(data[2], dataset.getRowKey(2), dataset.getColumnKey(2));
    	 dataset.setValue(data[3], dataset.getRowKey(3), dataset.getColumnKey(3));
    }
    private CategoryDataset createDataset() {

        dataset = new DefaultCategoryDataset();
        dataset.addValue(0.00, "1", "Delta");
        dataset.addValue(0.00, "2", "Theta");
        dataset.addValue(0.00, "3", "Alpha");
        dataset.addValue(0.00, "4", "Beta");
        
//        
//        dataset.addValue(5.0, "1", "Delta");   
//        dataset.addValue(4.0, "2", "Theta");   
//        dataset.addValue(9.0, "3", "Alpha");   
//        dataset.addValue(9.0, "4", "Beta");    
        return dataset;
    }

    private JFreeChart createChart(final CategoryDataset dataset) {
        
        final JFreeChart chart = ChartFactory.createBarChart3D(
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
        plot.setBackgroundPaint(Color.black);
        plot.setRangeZeroBaselinePaint(Color.black);
        plot.setDomainCrosshairPaint(Color.black);
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

}