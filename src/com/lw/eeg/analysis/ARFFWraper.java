package com.lw.eeg.analysis;

import java.util.ArrayList;
import java.util.List;

import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.converters.ConverterUtils.DataSink;
import weka.core.Instance;
import weka.core.Instances;

/**
 * Generates a little ARFF file with different attribute types.
 *
 * @author FracPete
 */
public class ARFFWraper {

    private Instances   data;
    private Instances 	tdata;
    private double[][]	paras;
    private double[] test= new double[4];
    
    
    public ARFFWraper(double[][] _paras){
    	//copy
    	paras=_paras;
    }

    public ARFFWraper(double[] _test){
    	System.arraycopy(_test, 0, test, 0, _test.length);
//    	for(double d:test){
//    		System.err.println(d);
//    	}
    }
    
    public ARFFWraper(List<List<Double>> _pList){
    	
//    	System.out.println("ARFFWrapper Constructor   ");
//    	System.out.println("_pList =>" + _pList.size() + "  " + _pList.get(0).size());
    	paras = new double[_pList.size()][_pList.get(0).size()];
    	for(int row=0 ; row<_pList.size(); row++)
    	{
    		for(int col=0; col<_pList.get(0).size(); col++)
    			paras[row][col] = (double)_pList.get(row).get(col);
    	}
    	
    }
    
    public Instances getInstances(){
    	return data;
    }
    
    public Instances getSingleInstance(){
    	return tdata;
    }
    
    public void create() throws Exception{
    	// 1. set up attributes
	    List atts = new ArrayList();
	    
	    atts.add(new Attribute("att1"));
	    atts.add(new Attribute("att2"));
	    atts.add(new Attribute("att3"));
	    atts.add(new Attribute("att4"));
	    atts.add(new Attribute("att5"));
	    atts.add(new Attribute("att6"));
	    atts.add(new Attribute("att7"));
	    atts.add(new Attribute("att8"));
	    atts.add(new Attribute("att9"));
	    atts.add(new Attribute("att10"));
	    List classVal = new ArrayList();
	    //classVal.add("dummy");
	    classVal.add("A");
	    classVal.add("B");
	    atts.add(new Attribute("att11",classVal));
//	    
	    
	    // 2. create Instances object 
	    data = new Instances("MyFeatures", (ArrayList<Attribute>) atts, 10);

	    // 3. fill with data
	    
	    // first instance
	    for(int row=0; row<paras.length;row++){
	    	double[] vals = new double[data.numAttributes()];
		    for(int col=0; col<paras[0].length;col++){
		    	vals[col]=paras[row][col];
		    }
		   
	    	if(row==0)
		    	vals[data.numAttributes()-1]=classVal.indexOf("A");
		    if(row==1)
		    	vals[data.numAttributes()-1]=classVal.indexOf("B");
	    	
	    	data.add(new  DenseInstance(1.0, vals)); 
	    }
	    System.out.println(data);
	   // DataSink.write("C:\\Users\\Leslie\\Desktop\\arffData.arff", data);
    }
    
    public void createTest() throws Exception{
    	// 1. set up attributes
    	
	    List atts = new ArrayList();
	    
	    atts.add(new Attribute("att1"));
	    atts.add(new Attribute("att2"));
	    atts.add(new Attribute("att3"));
	    atts.add(new Attribute("att4"));
	    atts.add(new Attribute("att5"));
	    atts.add(new Attribute("att6"));
	    atts.add(new Attribute("att7"));
	    atts.add(new Attribute("att8"));
	    atts.add(new Attribute("att9"));
	    atts.add(new Attribute("att10"));
	    
	    List classVal = new ArrayList();
	    classVal.add("?");
	    //classVal.add("A");
	    atts.add(new Attribute("att11",classVal));

	    tdata = new Instances("MyFeatures", (ArrayList<Attribute>) atts, 10000);

    	double[] vals = new double[tdata.numAttributes()];

    	
    	for(int col=0; col<test.length; col++){
	    	vals[col]=test[col];
	    }
    	vals[4] = classVal.indexOf("?");
    	tdata.add(new  DenseInstance(1.0, vals));

	    System.out.println(tdata);
	    //DataSink.write("C:\\Users\\Leslie\\Desktop\\arffData.arff", data);
    }

}