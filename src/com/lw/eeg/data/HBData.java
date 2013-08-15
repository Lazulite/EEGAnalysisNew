package com.lw.eeg.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;



public class HBData extends Data{

	public HBData(String _filepath) {
		super(_filepath);
		// TODO Auto-generated constructor stub
	}
	
	protected String[][] readData(String[][] data) {
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
	
}
