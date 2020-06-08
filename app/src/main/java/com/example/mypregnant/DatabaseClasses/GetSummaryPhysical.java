package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetSummaryPhysical extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getsummaryphysical.php";
    private Map<String,String> parmas;
    public GetSummaryPhysical(String userID,String lastPeriodDate,String category,Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",userID);
        parmas.put("LastPeriodDate",lastPeriodDate);
        parmas.put("Category",category);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
