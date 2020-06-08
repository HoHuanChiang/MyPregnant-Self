package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetEventFlow extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"geteventflow.php";
    private Map<String,String> parmas;
    public GetEventFlow(String isEducational,Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("isEducational",isEducational);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
