package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetFetalHB extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getfetalhb.php";
    private Map<String,String> parmas;
    public GetFetalHB(String UserID, String WeekStartDate, Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
        parmas.put("WeekStartDate",WeekStartDate);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}