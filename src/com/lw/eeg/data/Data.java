package com.lw.eeg.data;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;

import org.jfree.data.resources.DataPackageResources;

public class Data {
	private static CSVHelper csvHelper = new CSVHelper();
	protected String filepath;
	private int[] size=new int[2];
	
	public Data(String _filepath){
		filepath=_filepath;
	}
	public Data(){

	}
	public String[][] init()throws Exception{
		csvHelper.setFilename(filepath);
		size=csvHelper.count();
		String[][] initdata = new String[size[0]][size[1]];
		//System.out.println("init()"+ initdata.length);
		return initdata;
	}
	protected String[][] readData(String[][] data) {
		return null;
		
	}
	
	
	public String[][] adjustData(String[][] data,int fore, int tail){
		
		String[][] adjData= new String[data.length-fore-tail][data[0].length];
		
		for(int i=0;i<data.length-fore-tail;i++){
			for(int j=0;j<data[0].length;j++)
			adjData[i][j]=data[i+fore][j];
		}
		
		return adjData;		
	}
	
	public double[] getSegment(double[] data, int index, int size){
		double[] segment=new double[size];
		for(int i=0; i<size; i++){
			segment[i]=data[index+i];
		}
		return segment;
	}
	
	public String[][] getSegment(String[][] data, int index, int size){
		String[][] segment=new String[size][data[0].length];
		for(int i=0; i<size; i++){
			System.arraycopy(data[index+i], 0, segment[i], 0, data[0].length);
		}
		return segment;
	}
	
	public double[][] convertTodouble(String[][] data){
		double[][] temp = new double[data.length][data[0].length];
		for(int row=0; row<data.length; row++){
			for(int col=0; col<data[0].length; col++){
				temp[row][col] = Double.parseDouble(data[row][col]);
			}
		}
		return temp;
	}
	
	public String[][] getTotolData(String[][] eeg, String[][] hb, String[][] sensor){
		/*for(int i=0; i < eeg.length; i++){
			System.out.print("\n row "+i + ":   ");
			for(int j=0; j< eeg[0].length; j++){		
				System.out.print(eeg[i][j]);
			}
		}*/
		
		
		return null;
	}
	
	public double[] convertTodouble(List<String> data){
		double[] result = new double[data.size()];
		for(int d=0; d<data.size(); d++)
			result[d]=Double.parseDouble(data.get(d));	
		return result;
	}

}



