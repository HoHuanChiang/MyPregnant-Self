package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetDetailsRecord extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"GetDetailsRecord.php";
    private Map<String,String> parmas;
    public GetDetailsRecord(String UserID, String WeekStartDate, String Category,Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
        parmas.put("WeekStartDate",WeekStartDate);
        parmas.put("Category",Category);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
