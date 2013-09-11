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


public class Datapoint {
	public List<Datapoints> data_points = new ArrayList<Datapoints>(); 
	public void adddp(Datapoints dp){
		data_points.add(dp);
	}
}
