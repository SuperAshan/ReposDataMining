package org.jabsorb.test;

import org.jabsorb.JSONRPCResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonObjectTest {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String string="{\"id\":1,\"method\":\"test.echoList\",\"params\":[[1,2,3,4,5,6,7,8,9]]}";
		JSONObject jsonReq=new JSONObject(string);
		final String encodedMethod;
	    final Object requestId;
	    final JSONArray arguments;
	    final JSONArray fixups;
	    try
	    {
	      encodedMethod = jsonReq.getString("method");
	      arguments = jsonReq.getJSONArray("params");
	      requestId = jsonReq.opt("id");
	      fixups = jsonReq.optJSONArray("fixups");
	    }
	    catch (JSONException e)
	    {
	    	e.printStackTrace();
	      //log.error("no method or parameters in request");
	      //return new JSONRPCResult(JSONRPCResult.CODE_ERR_NOMETHOD, null,
	          //JSONRPCResult.MSG_ERR_NOMETHOD);
	    }
	}

}
