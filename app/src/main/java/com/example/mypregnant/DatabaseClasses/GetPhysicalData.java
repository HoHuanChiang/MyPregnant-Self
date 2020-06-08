package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetPhysicalData extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getphysicalData.php";
    private Map<String,String> parmas;
    public GetPhysicalData(String userID, String weekStartDate, String category, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",userID);
        parmas.put("WeekStartDate",weekStartDate);
        parmas.put("Category",category);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
