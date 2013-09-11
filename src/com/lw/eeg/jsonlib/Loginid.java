package com.lw.eeg.jsonlib;

/*
{
  "loginid": "string",
  "password": "string",
  "expire_in_seconds": 0
}
*/
public class Loginid {
	
	public String loginid;
	public String password;
	public int expire_in_seconds;
	
	public Loginid(String _loginid, String _passoword, int _expire_in_seconds){
		this.loginid=_loginid;
		this.password=_passoword;
		this.expire_in_seconds=_expire_in_seconds;
	}
}
