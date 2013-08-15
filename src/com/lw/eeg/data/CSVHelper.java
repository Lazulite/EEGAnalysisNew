package com.lw.eeg.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;

public class CSVHelper {
	private String fileName;
	private FileWriter fileWriter;

	public void setFilename(String _filename){
		fileName=_filename;
	}
	
	public FileWriter createFile_rawData(){	
		try {
			fileWriter = new FileWriter("C:\\Users\\Leslie\\Desktop\\EEGdata\\WithEmotivAPI\\"+fileName);
			fileWriter.write("COUNTER, AF3,F7,F3,FC5,T7,P7,O1,O2,P8,T8,FC6,F4,F8,AF4,GYROX,GYROY,EEG_TIMESTAMP,ES_TIMESTAMP,TIMESTAMP\n"); 
			return fileWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public FileWriter createFile_emoState(){	
		try {
			fileWriter = new FileWriter("C:\\Users\\Leslie\\Desktop\\EEGdata\\WithEmotivAPI\\"+fileName);
			fileWriter.write("ExcitementShortTermScore,ExcitementShortTermScore,EngagementBoredomScore,FrustrationScore,MeditationScore,ES_Timestamp,Timestamp\n"); 
			return fileWriter;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	public void writeCSV(String data) {
	
		try {
			fileWriter.append(data);
			fileWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getSimpleTime(Date t) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
		String currentDateandTime = sdf.format(t);
		return currentDateandTime;
	}
	
	public int[] count() throws IOException {
	    InputStream is = new BufferedInputStream(new FileInputStream(fileName));
	    try {
	        byte[] c = new byte[1024];
	        int [] size = new int[] {0,0};
	        int col = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++size[0];
	                }
	                if(c[i]==','){
	                	++size[1];
	                }
	            }
	        }
	        
	        //System.out.println("count"+size[0]+"=="+size[1]);
	        size[1]=size[1]/size[0]+1;
	        //System.out.println("====count"+size[0]+"=="+size[1]);
	        //size[0]--;
	        return size;
	    } finally {
	        is.close();
	    }
	}
	
	
}
