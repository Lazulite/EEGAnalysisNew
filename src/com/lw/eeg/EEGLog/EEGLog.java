package com.lw.eeg.EEGLog;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.runner.notification.StoppedByUserException;


import com.lw.eeg.data.CSVHelper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EEGLog {
	static int y1 = 0;
	public static boolean EEG_CONNECT = false;
	public Thread_EEG eeg;
	public EEGLog(){
		eeg = new Thread_EEG();
		eeg.setDaemon(true);
		eeg.start();
	}
	@SuppressWarnings("deprecation")
	public void stopRecord(){
		eeg.stop();
	}
	
	public List<List<String>> getRawEEG(){
		return eeg.getEEG();
	}
	public List<List<String>> getRawEState(){
		return eeg.getEState();
	}


class Thread_EEG extends Thread{
	
	public List<List<String>> raweeg = new ArrayList<List<String>>();
	public List<List<String>> rawestate = new ArrayList<List<String>>();
	
	public void run(){
    	Pointer eEvent				= Edk.INSTANCE.EE_EmoEngineEventCreate();
    	Pointer eState				= Edk.INSTANCE.EE_EmoStateCreate();
    	IntByReference userID 		= null;
		IntByReference nSamplesTaken= null;
    	short composerPort			= 1726;
    	int option 					= 1;
    	int state  					= 0;
    	float secs 					= 1;
    	boolean readytocollect 		= false;
    	int eventType=0;
    	double oldtime=0.0000;
    	//List<List<String>> raweeg = new ArrayList<List<String>>();
    	//List<List<String>> rawestate = new ArrayList<List<String>>();
    	
    	userID 			= new IntByReference(0);
		nSamplesTaken	= new IntByReference(0);
		
		String path = "C:\\Users\\Leslie\\Desktop\\EEGdata\\";
		CSVHelper csvHelper1 = new CSVHelper();
		CSVHelper csvHelper2 = new CSVHelper();
		
		Date time = new Date();
		csvHelper1.setFilename(csvHelper1.getSimpleTime(time) + "_rawdata.csv");
		csvHelper1.createFile_rawData();
		csvHelper2.setFilename(csvHelper2.getSimpleTime(time) + "_emostate.csv");
		csvHelper2.createFile_emoState();
		
    	switch (option) {
		case 1:
		{
			if (Edk.INSTANCE.EE_EngineConnect("Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Emotiv Engine start up failed.");
				return;
			}
			break;
		}
		case 2:
		{
			System.out.println("Target IP of EmoComposer: [127.0.0.1] ");

			if (Edk.INSTANCE.EE_EngineRemoteConnect("127.0.0.1", composerPort, "Emotiv Systems-5") != EdkErrorCode.EDK_OK.ToInt()) {
				System.out.println("Cannot connect to EmoComposer on [127.0.0.1]");
				return;
			}
			System.out.println("Connected to EmoComposer on [127.0.0.1]");
			break;
		}
		default:
			System.out.println("Invalid option...");
			return;
    	}
    	
		Pointer hData = Edk.INSTANCE.EE_DataCreate();
		Edk.INSTANCE.EE_DataSetBufferSizeInSec(secs);
		System.out.print("Buffer size in secs: ");
		System.out.println(secs);
    		
    	System.out.println("Start receiving EEG Data!");
		while (true) 
		{	
			state = Edk.INSTANCE.EE_EngineGetNextEvent(eEvent);

			// New event needs to be handled
			if (state == EdkErrorCode.EDK_OK.ToInt()) 
			{
				eventType = Edk.INSTANCE.EE_EmoEngineEventGetType(eEvent);
				Edk.INSTANCE.EE_EmoEngineEventGetUserId(eEvent, userID);

				// Log the EmoState if it has been updated
				if (eventType == Edk.EE_Event_t.EE_UserAdded.ToInt()) 
				if (userID != null)
					{
						System.out.println("User added");
						Edk.INSTANCE.EE_DataAcquisitionEnable(userID.getValue(),true);
						readytocollect = true;
					}
				
				
			}
			else if (state != EdkErrorCode.EDK_NO_EVENT.ToInt()) {
				System.out.println("Internal error in Emotiv Engine!");
				break;
			}
			
			

			if (readytocollect) 
			{
				Edk.INSTANCE.EE_DataUpdateHandle(0, hData);

				Edk.INSTANCE.EE_DataGetNumberOfSample(hData, nSamplesTaken);
				
				//FileWriter dataWriter = createFile();
				
				if (nSamplesTaken != null)
				{
					if (nSamplesTaken.getValue() != 0) {
						
						System.out.print("Updated: ");
						System.out.println(nSamplesTaken.getValue());
						
						double[] data = new double[nSamplesTaken.getValue()];
						List<String> innereeg = new ArrayList();
						
						for (int sampleIdx=0 ; sampleIdx<nSamplesTaken.getValue() ; ++ sampleIdx) {
							Date date= new Date();
							String timestamp=String.valueOf(date.getTime());
							double currenttime = EmoState.INSTANCE.ES_GetTimeFromStart(eState);
							
							for (int i = 0 ; i < 24 ; i++) {
								
								Edk.INSTANCE.EE_DataGet(hData, i, data, nSamplesTaken.getValue());
								String element=String.valueOf(data[sampleIdx]);
						
								
								if (i==0){
									csvHelper1.writeCSV(element+",");
								}else if(i>2 && i<21){
									csvHelper1.writeCSV( element+",");
								}else if(i==22){
									csvHelper1.writeCSV(timestamp+"\n");
								}
								
								System.out.print("["+i+"]");
								System.out.print(data[sampleIdx]);
								System.out.print(",");
								
								innereeg.add(element);
								
							}
							innereeg.add(String.valueOf(currenttime));
							innereeg.add(timestamp);
						    for(int s=0; s<innereeg.size();s++){
						    	System.out.print(innereeg.get(s));
						    }
						    
							System.out.println();
						}
						
						raweeg.add(innereeg);
					}
				}
				
				
				if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()){
					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
					//System.err.println("Estate: " + EmoState.INSTANCE.ES_GetTimeFromStart(eState));
					
					BigDecimal b = new BigDecimal(oldtime);  
					double currenttime = EmoState.INSTANCE.ES_GetTimeFromStart(eState);
					b = new BigDecimal(currenttime); 
					currenttime = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					List<String> innerestate = new ArrayList();
					if(currenttime-oldtime>0.0001){
						oldtime=currenttime;
						double[] emoState = new double[5];
						emoState[0] = EmoState.INSTANCE.ES_AffectivGetExcitementLongTermScore(eState);
						emoState[1] = EmoState.INSTANCE.ES_AffectivGetExcitementShortTermScore(eState);
						emoState[2] = EmoState.INSTANCE.ES_AffectivGetEngagementBoredomScore(eState);
						emoState[3] = EmoState.INSTANCE.ES_AffectivGetFrustrationScore(eState);
						emoState[4] = EmoState.INSTANCE.ES_AffectivGetMeditationScore(eState);
						
						EmoState.INSTANCE.ES_GetTimeFromStart(eState);
						Date date= new Date();
						String timestamp=String.valueOf(date.getTime());
						for(double d:emoState){	
							csvHelper2.writeCSV(d+",");
							
						}
						csvHelper2.writeCSV(EmoState.INSTANCE.ES_GetTimeFromStart(eState)+","+timestamp+"\n");
						for(double d:emoState){	
							innerestate.add(String.valueOf(d));
						}
						rawestate.add(innerestate);
					}
				}
				
				
			}
		}
    	
    	Edk.INSTANCE.EE_EngineDisconnect();
    	Edk.INSTANCE.EE_EmoStateFree(eState);
    	Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
    	System.out.println("Disconnected!");
    }
	public List<List<String>> getEEG(){
		return raweeg;
	}
	public  List<List<String>> getEState(){
		return rawestate;
	}
	
	
}
	

}
