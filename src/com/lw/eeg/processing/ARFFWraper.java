package com.lw.eeg.processing;

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

	private List      atts;
    private List	  classVal;
    private Instances       data;
    private double[]        vals;
    
    
    public ARFFWraper(){
    	
    }
    
    public Instances getInstances(){
    	return data;
    }
    public void create() throws Exception{
    	// 1. set up attributes
	    atts = new ArrayList();
	    
	    atts.add(new Attribute("att1"));
	    atts.add(new Attribute("att2"));
	    atts.add(new Attribute("att3"));
	    atts.add(new Attribute("att4"));
	    classVal = new ArrayList();
	   // classVal.add("dummy");
	    classVal.add("A");
	    classVal.add("B");
	    atts.add(new Attribute("att5",classVal));
//	    
	    
	    // 2. create Instances object 
	    data = new Instances("MyFeatures", (ArrayList<Attribute>) atts, 10);

	    // 3. fill with data
	    
	    // first instance
	    vals = new double[data.numAttributes()];
	    vals[0] = Math.PI;
	    vals[1] = 3333.3;
	    vals[2] = 551.8776;
	    vals[3] = 6666.6;
	    vals[4] = classVal.indexOf("A");
	    // add
	    data.add(new  DenseInstance(1.0, vals));

	    // second instance
	    vals = new double[data.numAttributes()];  // important: needs NEW array!
	    vals[0] = Math.E;
	    vals[1] = 333.3;
	    vals[2] = 5556.99;
	    vals[3] = 7777.7;
	    vals[4] = classVal.indexOf("B");
	    // add
	    data.add(new  DenseInstance(1.0, vals));
	    
	    
	    // 4. output data
	    System.out.println(data);
	    DataSink.write("C:\\Users\\Leslie\\Desktop\\arffData.arff", data);
    }

}