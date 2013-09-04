package com.lw.eeg.plot;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.List;

import javax.swing.JPanel;
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
import org.jfree.data.general.DatasetGroup;
import org.jfree.data.general.DatasetUtilities;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import com.lw.eeg.data.Data;


public  class BarChart3DPlot implements DatasetChangeListener{
	private JFreeChart mchart;
	private DefaultCategoryDataset dataset;
	private String type;
	private JPanel featureJPanel;
	private CategoryPlot plot; 
	
	
    public BarChart3DPlot() {
        
    }
    public void setType(String _type){
    	type=_type;
    	if(type=="EEG"){
    		CategoryDataset dataset = createEEGDataset();
    	}
    	if(type=="HRV"){
    		CategoryDataset dataset = createHRVDataset();
    	}
    	JFreeChart mchart = createChart(dataset);
//    	ChartPanel featurep = new ChartPanel(chart);
//		featureJPanel.add(featurep, BorderLayout.CENTER);
//		featurep.validate();
    	
    	
    }

    public void setPanel(JPanel _featureJPanel){
    	featureJPanel=_featureJPanel;
    }
    
    
    public void updateEEGDataset(double[] data){
    	//System.err.println("================================");
		dataset.setValue(data[0], dataset.getRowKey(0), dataset.getColumnKey(0));
		dataset.setValue(data[1], dataset.getRowKey(1), dataset.getColumnKey(1));
		dataset.setValue(data[2], dataset.getRowKey(2), dataset.getColumnKey(2));
		dataset.setValue(data[3], dataset.getRowKey(3), dataset.getColumnKey(3));
//		for(int i =0; i<4; i++){
//			System.err.println(dataset.getValue(i, i));
//		}

//    	dataset.clear();
//    	dataset.addValue(data[0], "1", "Delta");
//        dataset.addValue(data[1], "2", "Theta");
//        dataset.addValue(data[2], "3", "Alpha");
//        dataset.addValue(data[3], "4", "Beta");
////		System.err.println("update");
//		chart.getCategoryPlot().setDataset(dataset);
	
		
    }
    
    private CategoryDataset createEEGDataset() {

        dataset = new DefaultCategoryDataset();
        dataset.addValue(0.00, "1", "Delta");
        dataset.addValue(1.00, "2", "Theta");
        dataset.addValue(2.00, "3", "Alpha");
        dataset.addValue(3.00, "4", "Beta");
//        for(int i =0; i<4; i++){
//			System.err.println(dataset.getValue(i, i));
//		}

        dataset.addChangeListener(this); 
        return dataset;
    }
    
    private CategoryDataset createHRVDataset() {

        dataset = new DefaultCategoryDataset();
        dataset.addValue(0.00, "1", "VeryLow");
        dataset.addValue(0.00, "2", "Low");
        dataset.addValue(0.00, "3", "High");
        //dataset.addChangeListener(this); 
        return dataset;
    }
    
    public void updateHRVDataset(double[] data){
	    
		dataset.setValue(data[0], dataset.getRowKey(0), dataset.getColumnKey(0));
		dataset.setValue(data[1], dataset.getRowKey(1), dataset.getColumnKey(1));
		dataset.setValue(data[2], dataset.getRowKey(2), dataset.getColumnKey(2));
    }
    
    

    private JFreeChart createChart(final CategoryDataset dataset) {

        JFreeChart chart = ChartFactory.createBarChart3D(
            "",      // chart title
            "",               // domain axis label
            "",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            false,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        //plot.setD
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
    	return mchart;
    }


	@Override
	public void datasetChanged(DatasetChangeEvent arg0) {
		// TODO Auto-generated method stub
		//plot.setDataset(dataset);
	}

}