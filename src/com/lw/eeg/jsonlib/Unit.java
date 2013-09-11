package com.lw.eeg.jsonlib;


public class Unit {
	public String unit_id = "string";
	public String unit_symbol = "string";
	public String unit_label;
	public String value_type;
	public void setLabel(String label){
		this.unit_label=label;
	}
	public void setValueType(String _value_type){
		this.value_type=_value_type;
	}
}
