package com.lw.eeg.jsonlib;

/*"value_list": [
   	        {
   	          "unit_id": "string",
   	          "val": "double",
   	          "val_tag": "string"
   	        }
   	      ]*/


public class Value {
	public String unit_id;
	public double val;
	public String val_tag;
	
	public Value(String _uid, double _val, String _valtag){
		this.unit_id = _uid;
		this.val = _val;
		this.val_tag = _valtag;
	}

}
