package com.lw.eeg.processing;


public class Correlation {
	public Correlation(){}


	public double[] Normalize(double[] para, double upper, double lower){
		double sum=0.0, max=0.0, min=0.0;


		double[] result = new double[para.length];
		for(double d: para){
			sum+=d;
			if(d>max)
				max=d;
			if (d<min) 
				min=d;
		}

		//y=lower+(x-min)/(max-min)*(upper-lower)
		for(int i=0; i<para.length; i++){		
			result[i] = lower+(para[i])/(max-min)*(upper-lower);
		}

		return result;
	}

	public double[] Scale(double[] para, int size){
		double[] result = new double[para.length*size];

		for(int i=0; i<result.length; i++){
			result[i]=para[(int)i/size];
		}

		return result;
	}


	public double GetCorrelation(double[] xVect, double[] yVect) {
        double meanX = 0.0, meanY = 0.0;
        for(int i = 0; i < xVect.length; i++)
        {
            meanX += xVect[i];
            meanY += yVect[i];
        }

        meanX /= xVect.length;
        meanY /= yVect.length;

        double sumXY = 0.0, sumX2 = 0.0, sumY2 = 0.0;
        for(int i = 0; i < xVect.length; i++)
        {
          sumXY += ((xVect[i] - meanX) * (yVect[i] - meanY));
          sumX2 += Math.pow(xVect[i] - meanX, 2.0);
          sumY2 += Math.pow(yVect[i] - meanY, 2.0);
        }

        return (sumXY / (Math.sqrt(sumX2) * Math.sqrt(sumY2)));
      }//end: GetCorrelation(X,Y)



}