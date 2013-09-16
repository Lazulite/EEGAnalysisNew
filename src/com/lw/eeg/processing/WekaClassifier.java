package com.lw.eeg.processing;

import java.util.logging.Logger;

import javax.swing.JTextArea;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.*;
import weka.classifiers.pmml.consumer.SupportVectorMachineModel;
import weka.classifiers.trees.*;
import weka.core.Instances;
import weka.core.Utils;

import com.lw.eeg.processing.*;


public class WekaClassifier {
	private Instances mInstances;
	private ARFFWraper simpleARFF;
	private JTextArea logger;
	private final static String newline = "\n";
	
	public WekaClassifier(Instances _instance){
		mInstances=_instance;
	}
	
	public void setTextLogger(JTextArea log){
		logger = log;
	}
	public Classifier createClassifier(String classifier) throws Exception{
		
		Classifier cModel = null;
		if(classifier=="NaiveBayes"){
        
	        cModel=(Classifier) new NaiveBayes();
	        cModel.buildClassifier(mInstances);
		}
		
		if(classifier=="TreeJ48"){
			cModel=(Classifier) new J48();
	        cModel.buildClassifier(mInstances);
		}
		if(classifier == "SVM "){
			cModel = (Classifier) new SMO();
			cModel.buildClassifier(mInstances);
		}
        return cModel;
        
	}
	
	public void Evaluation(Classifier cModel, Instances test) throws Exception{
		
		Evaluation eTest = new Evaluation(mInstances);
        eTest.evaluateModel(cModel, test);
         
        // Print the result ид la Weka explorer:
        String strSummary = eTest.toSummaryString();
        logger.append(strSummary+newline);
         
        // Get the confusion matrix
        double[][] cmMatrix = eTest.confusionMatrix();
        for(int row_i=0; row_i<cmMatrix.length; row_i++){
            for(int col_i=0; col_i<cmMatrix.length; col_i++){
            	logger.append(String.valueOf(cmMatrix[row_i][col_i]));
                logger.append("|");
//                System.out.print(cmMatrix[row_i][col_i]);
//                System.out.print("|");
            }
            logger.append(newline);
//            System.out.println();
        }
        // output predictions
//        System.out.println("# - actual - predicted - distribution");
//        for (int i = 0; i < test.numInstances(); i++) {
//        double pred = cModel.classifyInstance(test.instance(i));
//        double[] dist = cModel.distributionForInstance(test.instance(i));    
//        System.out.print((i+1) + " - ");
//        System.out.print(test.instance(i).toString(test.classIndex()) + " - ");
//        System.out.print(test.classAttribute().value((int) pred) + " - ");
//        System.out.println(Utils.arrayToString(dist));
       // }
        
	}
	
}
