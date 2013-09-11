package com.lw.eeg.jsonlib;

import java.util.ArrayList;
import java.util.List;
/*
{
	  "data_points": [
	    {
	      "at": 0,
	      "timetag": "string",
	      "value_list": [
	        {
	          "unit_id": "string",
	          "val": "double",
	          "val_tag": "string"
	        }
	      ]
	    }
	  ]
	}*/

public class Datapoints {
	public long at;
	public String timetag;
	public List<Value> value_list = new ArrayList<Value>();
	
	public Datapoints(long _at, String t){
		this.at = _at;
		this.timetag = t;
		
	}
	public void addValueList(Value v){
		value_list.add(v);
	}
}
