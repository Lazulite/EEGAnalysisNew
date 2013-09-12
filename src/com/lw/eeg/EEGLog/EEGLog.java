package com.lw.eeg.EEGLog;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Writer;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.swing.Timer;
import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.DynamicTimeSeriesCollection;
import org.jfree.data.time.Second;
import org.jfree.data.xy.XYDataset;
import org.junit.runner.notification.StoppedByUserException;

import com.lw.eeg.jsonlib.*;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.lw.eeg.Main.wikiHttp;
import com.lw.eeg.data.CSVHelper;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;

public class EEGLog {
	public boolean EEG_CONNECT = false;
	public volatile Thread_EEG eeg;
	public Thread_Plot plot;
	public boolean EEGUPDATE = false;
	public boolean ESTATEUPDATE = false;
	public List<String> EEGmessageList;
	public List<String> EstatemessageList;
	public int pCounter=0;
	public int cCounter=0;
	public List<List<String>> raweeg;
	public List<List<String>> rawestate;
	public wikiHttp wikiHelper;
	private String mtoken;
	private String rootURL="http://wikihealth.bigdatapro.org:55555/healthbook/v1/";
	private List<String> unitids = new ArrayList();
	public EEGLog(String m){
		mtoken = m;
		eeg = new Thread_EEG();
		plot = new Thread_Plot();
		raweeg = new ArrayList<List<String>>();
		rawestate = new ArrayList<List<String>>();
		eeg.setDaemon(true);
	}
	
	public void startRecord(){
		eeg.start();
		plot.init();
	}
	
	@SuppressWarnings("deprecation")
	public void stopRecord(){
		eeg=null;
		//EEG_CONNECT = false;
		System.err.println("Stop EEG Record");
	}
	
	public List<List<String>> getRawEEG(){
		return eeg.getEEG();
	}
	public List<List<String>> getRawEState(){
		return eeg.getEState();
	}
	public JFreeChart getRealtimeChart(){
		return plot.getChart();
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
		final String[] channel= new String[]{"AF3","F7","F3","FC5","T7","P7","O1","O2","P8","T8","FC6","F4","F8","AF4"};
		
		
		Gson gson = new Gson();
		NewDatastream newDatastream = new NewDatastream("eeg");
		for(int i=0; i<14; i++){
			Unit newUnit = new Unit();
			newUnit.setLabel(channel[i]);
			newUnit.setValueType("double");							
			newDatastream.addUnit(newUnit);
		}
		
		String newDT= gson.toJson(newDatastream);
		//mtoken = URLEncoder.encode(mtoken).replace("\"", "");
		
		String dtURL = rootURL+"health/title?accesstoken="+mtoken+"&api_key=special-key";  
		wikiHttp wiki=new wikiHttp( dtURL, "POST", newDT, null);
		InputStream result;
		result = wiki.doInBackground();

		JsonReader reader = new JsonReader(new InputStreamReader(result));
		reader.setLenient(true);
		JsonParser parser = new JsonParser();
		System.err.println("RESPONSE"+"before parsing json ");					
	    JsonObject obj = parser.parse(reader).getAsJsonObject();	
	    System.err.println("RESPONSE"+"Before obj.toString()");
	    System.err.println("RESPONSE"+obj.toString());

	    JsonObject dtresponse = obj.get("datastream").getAsJsonObject();
	    JsonArray unitList = dtresponse.get("units_list").getAsJsonArray(); 
	    for(int i = 0; i<unitList.size(); i++){
		    JsonObject unitLabel = unitList.get(i).getAsJsonObject();
		    String unit_id = unitLabel.get("unit_id").getAsString();
		    System.err.println("Unit label is "+unit_id);
		    unitids.add(unit_id);
			//response.append("Unit Label: " + unit_id + newline);
	    }

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
						EEG_CONNECT = true;
						System.out.print("Updated: ");
						System.out.println(nSamplesTaken.getValue());
						double[] data = new double[nSamplesTaken.getValue()];
						//List<String> innereeg = new ArrayList();
						
						
						for (int sampleIdx=0 ; sampleIdx<nSamplesTaken.getValue() ; ++ sampleIdx) {
							Date date= new Date();
							String timestamp=String.valueOf(date.getTime());
							double currenttime = EmoState.INSTANCE.ES_GetTimeFromStart(eState);
							
							List<String> innereeg = new ArrayList();
							
							for (int i = 0 ; i < 24 ; i++) {
								
								Edk.INSTANCE.EE_DataGet(hData, i, data, nSamplesTaken.getValue());
								String element=String.valueOf(data[sampleIdx]);
						
								
								if (i==0){
									csvHelper1.writeCSV(element+",");
								}else if(i>2 && i<17){
									csvHelper1.writeCSV( element+",");
									innereeg.add(element);
									for(int d=0; d<unitids.size();i++){
										Gson gsonhlp = new Gson();
										Value v;
										v = new Value(unitids.get(i), (double)Double.parseDouble(element), "string");
										Datapoints newdp= new Datapoints(Long.valueOf(timestamp), "string");
										newdp.addValueList(v);
										Datapoint newDatapoint = new Datapoint();
										newDatapoint.adddp(newdp);
										
										String newDP= gson.toJson(newDatapoint);
										String hrURL = rootURL+"health/title/"+"eeg/datapoints?accesstoken="+mtoken+"&api_key=special-key"; 
										wikiHttp wikihlp=new wikiHttp( hrURL, "POST", newDP, null);

										InputStream newresult = wiki.doInBackground();
					
										JsonReader newreader = new JsonReader(new InputStreamReader(newresult));
										reader.setLenient(true);
										JsonParser newparser = new JsonParser();
										System.err.println("RESPONSE"+"before parsing json ");					
									    JsonObject newobj = parser.parse(reader).getAsJsonObject();	
									    System.err.println("RESPONSE"+"Before obj.toString()");
									    System.err.println("RESPONSE"+newobj.toString());
									    System.err.println("RESPONSE"+newobj.get("result"));
				                	}
								}else if(i>16 && i<21){
									csvHelper1.writeCSV( element+",");
								}else if(i==22){
									csvHelper1.writeCSV(timestamp+"\n");
								}
//								
//								System.out.print("["+i+"]");
//								System.out.print(data[sampleIdx]);
//								System.out.print(",");
								
								
								
							}
							innereeg.add(String.valueOf(currenttime));
							innereeg.add(timestamp);

							System.out.println();
							
							//System.err.println("Updated innereeg size " + innereeg.size());

								raweeg.add(innereeg);
								//innereeg.clear();
								EEGUPDATE= true;
								pCounter++; 
								//System.err.println("Pcounter " + pCounter);

							System.out.println("raweeg : =>" + raweeg.size());
						}
						//After get a entire EEG data point, put it into Message
					
						
					}
				}
				
				
				if(eventType == Edk.EE_Event_t.EE_EmoStateUpdated.ToInt()){
					Edk.INSTANCE.EE_EmoEngineEventGetEmoState(eEvent, eState);
					//System.err.println("Estate: " + EmoState.INSTANCE.ES_GetTimeFromStart(eState));
					ESTATEUPDATE = true;
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
	private synchronized void putEEGMessage(List<String> innereeg) throws InterruptedException { 	        
		EEGmessageList = new ArrayList<String>();
		//System.arraycopy(innereeg, 0, EEGmessageList, 0, innereeg.size()-2);
		for(int i=0; i<innereeg.size()-2; i++){
			EEGmessageList.add(innereeg.get(i));
		}
	}
	
	public List<List<String>> getEEG(){
		return raweeg;
	}
	public  List<List<String>> getEState(){
		return rawestate;
	}
	
	
}
	
class Thread_Plot{
	
    private static final float MINMAX = 5500;
    private static final int COUNT = 128;
    
    private static final int FAST = 1;
    private Timer timer;
    private JFreeChart chart;
   
    private int i;

    public void Thread_Plot(){
    	
    }
    
    public void init(){
    	System.err.println("At the beginning of plot thread");
    	final DynamicTimeSeriesCollection dataset = new DynamicTimeSeriesCollection(16, COUNT, new Second());
    	dataset.setTimeBase(new Second(0, 0, 0, 1, 1, 2011));
    	final float[] intbuf= new float[14];
    
    	for(int d=0; d<14; d++)
    	{
    		intbuf[d]=0;
    	}
    	dataset.addSeries(intbuf,0, "");
    	dataset.addSeries(intbuf,1, "");
    	dataset.addSeries(intbuf,2, "");
    	dataset.addSeries(intbuf,3, "");
    	dataset.addSeries(intbuf,4, "");
    	dataset.addSeries(intbuf,5, "");
    	dataset.addSeries(intbuf,6, "");
    	dataset.addSeries(intbuf,7, "");
    	dataset.addSeries(intbuf,8, "");
    	dataset.addSeries(intbuf,9, "");
    	dataset.addSeries(intbuf,10, "");
    	dataset.addSeries(intbuf,11, "");
    	dataset.addSeries(intbuf,12, "");
    	dataset.addSeries(intbuf,13, "");
    	
    	
        chart = createChart(dataset);
        
        System.err.println("Before Time ActionListener");
        if(EEGUPDATE){
			timer = new Timer(FAST, new ActionListener() {
				float[] newData = new float[14];
				int n=0;
				@Override
				public void actionPerformed(ActionEvent e){
					float[] buf = new float[14];
					System.err.println("n===========================>" + n);
					if(!raweeg.isEmpty()){
						
						for(int i=0; i<14; i++){
							System.err.println("Dataponit " + i+ "   " + raweeg.get(n).get(i));
							switch (i) {
							case 0:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))+750;
								break;
							case 1:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400;
								break;
							case 2:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*2-750;
								break;
							case 3:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*3+500;
								break;
							case 4:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*4-100;
								break;
							case 5:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*5-750;
								break;
							case 6:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*6-750;
								break;
							case 7:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*7-1500;
								break;
							case 8:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*8-1500;
								break;
							case 9:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*9-1750;
								break;
							case 10:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*10-2350;
								break;
							case 11:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*11-3750;
								break;
							case 12:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*12-2500;
								break;
							case 13:
								buf[i] = Float.valueOf(raweeg.get(n).get(i))-400*13-3350;
								break;							
							default:
								break;
							}
				    		//buf[i] = Float.valueOf(raweeg.get(n).get(i));
				    	}
						System.arraycopy(buf, 0, newData, 0, 14);
					}
					
					System.out.println("buf size" + buf.length + " newData size " + newData.length);
					dataset.advanceTime();
					dataset.appendData(newData);
					while(n>=raweeg.size()-1){
					}
					n++;
					}
				});
				timer.start();
		}
    }
    
    private JFreeChart createChart(final XYDataset dataset) {
        final JFreeChart result = ChartFactory.createTimeSeriesChart("", "", "", dataset, true, true, false);
        final XYPlot plot = result.getXYPlot();
        plot.getRenderer().setSeriesVisibleInLegend(false);
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.black);
        plot.setRangeGridlinePaint(Color.black);
        plot.clearRangeMarkers();
        ValueAxis domain = plot.getDomainAxis();
        domain.setAutoRange(true);
        ValueAxis range = plot.getRangeAxis();
        range.setRange(-MINMAX, MINMAX);
        return result;
    }
   
    public JFreeChart getChart(){
    	return chart;
    }	
}



}
