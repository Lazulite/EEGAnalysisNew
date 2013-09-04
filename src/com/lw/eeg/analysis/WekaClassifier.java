package com.lw.eeg.analysis;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.functions.*;
import weka.classifiers.lazy.IBk;
import weka.classifiers.pmml.consumer.SupportVectorMachineModel;
import weka.classifiers.trees.*;
import weka.core.Instances;
import weka.core.Utils;

import com.lw.eeg.analysis.*;


public class WekaClassifier {
	private Instances mInstances;
	private ARFFWraper simpleARFF;
	
	public WekaClassifier(Instances _instance){
		mInstances=_instance;
	}
	
	public Classifier createClassifier(String classifier, int para) throws Exception{
		
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
		if(classifier == "KNN"){
			if(para==-1)
				cModel = (Classifier) new IBk();
			else {
				cModel = (Classifier) new IBk(para);
			}
			cModel.buildClassifier(mInstances);
		}
        return cModel;
        
	}
	
	public void Evaluation(Classifier cModel, Instances test) throws Exception{
		
		Evaluation eTest = new Evaluation(mInstances);
        eTest.evaluateModel(cModel, test);
         
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
