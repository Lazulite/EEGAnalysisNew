package com.lw.eeg.processing;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.*;
import weka.classifiers.pmml.consumer.SupportVectorMachineModel;
import weka.classifiers.trees.*;
import weka.core.Instances;
import com.lw.eeg.processing.*;


public class WekaClassifier {
	private Instances mInstances;
	private ARFFWraper simpleARFF;
	
	public WekaClassifier(Instances _instance){
		mInstances=_instance;
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
        return cModel;
        
	}
	
	public void Evaluation(Classifier cModel) throws Exception{
		
		Evaluation eTest = new Evaluation(mInstances);
        eTest.evaluateModel(cModel, mInstances);
         
        // Print the result ид la Weka explorer:
        String strSummary = eTest.toSummaryString();
        System.out.println(strSummary);
         
        // Get the confusion matrix
        double[][] cmMatrix = eTest.confusionMatrix();
        for(int row_i=0; row_i<cmMatrix.length; row_i++){
            for(int col_i=0; col_i<cmMatrix.length; col_i++){
                System.out.print(cmMatrix[row_i][col_i]);
                System.out.print("|");
            }
            System.out.println();
        }
	}
	
}
