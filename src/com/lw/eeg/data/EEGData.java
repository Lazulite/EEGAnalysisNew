package com.lw.eeg.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;


public class EEGData extends Data{

	public EEGData(String _filepath) {
		super(_filepath);
		// TODO Auto-generated constructor stub
	}
	
	public EEGData() {

	}
	
	public String[][] readData(String[][] data) {
		
		try {
			BufferedReader bufRdr;
			bufRdr = new BufferedReader(new FileReader(filepath));	
			String line = null;
			int row = 0;
			int col = 0;
			//read each line of text file
			bufRdr.readLine();
			while((line = bufRdr.readLine()) != null)
			{
				col=0;
				StringTokenizer st = new StringTokenizer(line,",");
				while (st.hasMoreTokens())
				{
					//get next token and store it in the array
					data[row][col] = st.nextToken();
					col++;
				}
				row++;
			}
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		return data;
	}
	
	
	
	public double[] getChannel(String[][] allchannel, int channel){
		double[] specificchannel = new double[allchannel.length];
		//System.out.println(allchannel.length);
		double mean=0;
		for(int i=0; i< allchannel.length; i++){		
			//System.err.println(" i "+i + "  "+ allchannel[i][channel]);
			mean+=Double.valueOf(allchannel[i][channel]);
			//System.out.println("i:   "+i+"  "+Double.valueOf(allchannel[i][channel]));
		}
		mean=mean/allchannel.length;
		for(int i=0; i< allchannel.length; i++){
			
			specificchannel[i]=Double.valueOf(allchannel[i][channel]);
			//System.out.println("i:   "+i+"  "+ specificchannel[i]);
		}
		return specificchannel;
	}
	
	
	
}