package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetAdvertiseData extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getadvertisedata.php";
    private Map<String,String> parmas;
    public GetAdvertiseData(String UserID, String PregnantWeek, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
        parmas.put("PregnantWeek",PregnantWeek);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
