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
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;


public class BarChart3DPlot {
	private JFreeChart chart;
	
    public BarChart3DPlot(double[][] data) {
    	
        final CategoryDataset dataset = createDataset();
        chart = createChart(dataset);
    }

    
    private CategoryDataset createDataset() {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(5.0, "1", "Delta");   
        dataset.addValue(4.0, "2", "Theta");   
        dataset.addValue(9.0, "3", "Alpha");   
        dataset.addValue(9.0, "4", "Beta");    
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