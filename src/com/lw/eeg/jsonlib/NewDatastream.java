package com.lw.eeg.jsonlib;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

/*
{
	  "datastream_id": "string",
	  "title": "string",
	  "tags": "string",
	  "desc": "string",
	  "owner": "string",
	  "updated_time": "Date",
	  "created_time": "Date",
	  "total_units": 0,
	  "total_blocks": 0,
	  "units_list": [
	    {
	      "unit_id": "string",
	      "unit_symbol": "string",
	      "unit_label": "string",
	      "value_type": "string"
	    }
	  ]
	}
*/
public class NewDatastream {
	public String dataStream_id = "string";
	public String title;
	public String tags= "string";
	public String desc= "string";
	public String owner= "string";
	public Date updated_timeDate;
	public Date created_time;
	public int total_units = 0;
	public int total_bloacks =0;
	public List<Unit> units_list = new ArrayList<Unit>(); 
	public NewDatastream(String title){
		this.title=title;
	}
	
	public void addUnit(Unit newunit){
		units_list.add(newunit);
	}
	
}
