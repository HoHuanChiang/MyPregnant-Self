package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertSchedule extends StringRequest {

    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"insertschedule.php";
    private Map<String,String> parmas;
    public InsertSchedule(String UserID, String Date, String Number, String Note, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
        parmas.put("Date",Date);
        parmas.put("Number",Number);
        parmas.put("Note",Note);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
