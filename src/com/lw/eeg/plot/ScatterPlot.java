package com.lw.eeg.plot;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Shape;
import java.util.*;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.util.ShapeUtilities;

public class ScatterPlot {
	private static double[] paraX;
	private static double[] paraY;
	private static String X;
	private static String Y;
	
    public ScatterPlot(double[] _paraX, double[] _paraY, String _X, String _Y) {
    	paraX=_paraX;
    	paraY=_paraY;
    	X=_X;
    	Y=_Y;
    }

    public static JFreeChart createChart() {
        JFreeChart jfreechart = ChartFactory.createScatterPlot(
            "", X, Y, samplexydataset2(),
            PlotOrientation.VERTICAL, true, true, false);
        Shape diamond = ShapeUtilities.createDiagonalCross((float)3, (float)0.5);
        XYPlot xyPlot = (XYPlot) jfreechart.getPlot();
        xyPlot.setBackgroundPaint(Color.white);
        xyPlot.setDomainGridlinePaint(Color.black);
        xyPlot.setRangeGridlinePaint(Color.black);
        xyPlot.setDomainCrosshairVisible(true);
        xyPlot.setRangeCrosshairVisible(true);
        XYItemRenderer renderer = xyPlot.getRenderer();
        renderer.setSeriesShape(0, diamond);
        renderer.setSeriesPaint(0, Color.orange);
 
        
        return jfreechart;
    }

    private static XYDataset samplexydataset2() {

        XYSeriesCollection xySeriesCollection = new XYSeriesCollection();
        XYSeries series = new XYSeries("Random");
        for(int i=0; i<paraX.length;i++){
        	series.add(paraX[i],paraY[i]);
        }
        xySeriesCollection.addSeries(series);
        return xySeriesCollection;
    }
}