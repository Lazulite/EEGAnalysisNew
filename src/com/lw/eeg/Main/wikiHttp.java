package com.lw.eeg.Main;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;


public class wikiHttp {
	private String method=null;
    private String URL=null;
    private InputStream is = null;
	private Reader reader = null;
	private List<NameValuePair> paras;
	private String jobj=null;
	
	public wikiHttp(String _url, String _method, String _jobj , List<NameValuePair> _paras) {
        URL=_url;
        method=_method;
        jobj = _jobj;
        paras=_paras;
    }
    public InputStream doInBackground() {

        try {
        	if(method.equals("POST")){

        		HttpClient httpclient = new DefaultHttpClient();
        		HttpPost httpPostRequest = new HttpPost(URL);
        		httpPostRequest.setHeader("Accept", "application/json");
        		httpPostRequest.setHeader("Content-type", "application/json");
        		// Set HTTP parameters
        		StringEntity se = new StringEntity(jobj);
      			httpPostRequest.setEntity(se);
        		//Reader reader = new InputStreamReader(httpPostRequest.getEntity().getContent());
        		
//        		BufferedReader r = new BufferedReader(new InputStreamReader(httpPostRequest.getEntity().getContent()));
//        		StringBuilder total = new StringBuilder();
//        		String line;
//        		while ((line = r.readLine()) != null) {
//        		    total.append(line);
//        		}   		
//        		Log.e("In post", total.toString());
        		HttpResponse response = (HttpResponse) httpclient.execute(httpPostRequest);
        		is = response.getEntity().getContent();

        	}else if(method == "GET"){
            // request method is GET
	            DefaultHttpClient httpClient = new DefaultHttpClient();
	            if(paras!=null)
	            {	String paramString = URLEncodedUtils.format(paras, "utf-8");
	            	URL += "?" + paramString;
	            }
	            HttpGet httpGet = new HttpGet(URL);
	
	            HttpResponse httpResponse = httpClient.execute(httpGet);
	            HttpEntity httpEntity = httpResponse.getEntity();
	            is = httpEntity.getContent();
        	}           

        } catch (UnsupportedEncodingException e) {
        	e.printStackTrace();
        } catch (ClientProtocolException e) {
        	e.printStackTrace();
        } catch (IOException e) {
        	e.printStackTrace();
    }

    return is;

   }
		
	
		
		
		
}
