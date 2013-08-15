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


import com.lw.eeg.data.CSVHelper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EEGLog {
	static int y1 = 0;
	public static boolean EEG_CONNECT = false;
	public EEGLog(){
		Thread_EEG eeg = new Thread_EEG();
		eeg.setDaemon(true);
		eeg.start();
}


class Thread_EEG extends Thread{
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
						
						for (int sampleIdx=0 ; sampleIdx<nSamplesTaken.getValue() ; ++ sampleIdx) {
							for (int i = 0 ; i < 24 ; i++) {
								
								Edk.INSTANCE.EE_DataGet(hData, i, data, nSamplesTaken.getValue());

								
								String element=String.valueOf(data[sampleIdx]);
								Date date= new Date();
								String timestamp=String.valueOf(date.getTime());
								
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
							}
						    
							System.out.println();
						}
					}
				}
				
				
				if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()){
					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
					//System.err.println("Estate: " + EmoState.INSTANCE.ES_GetTimeFromStart(eState));
					
					BigDecimal b = new BigDecimal(oldtime);  
					double currenttime = EmoState.INSTANCE.ES_GetTimeFromStart(eState);
					b = new BigDecimal(currenttime); 
					currenttime = b.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue(); 
					
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
					}
				}
			}
		}
    	
    	Edk.INSTANCE.EE_EngineDisconnect();
    	Edk.INSTANCE.EE_EmoStateFree(eState);
    	Edk.INSTANCE.EE_EmoEngineEventFree(eEvent);
    	System.out.println("Disconnected!");
    }
}


}
