package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetSchedule extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getSchedule.php";
    private Map<String,String> parmas;
    public GetSchedule(String UserID , Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
