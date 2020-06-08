package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePersonalInfo extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"updateuserinfo.php";
    private Map<String,String> parmas;
    public UpdatePersonalInfo(String UserID, String Room,String MedicalID,String Doctor,String Nurse, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("UserID",UserID);
        parmas.put("Room",Room);
        parmas.put("MedicalID",MedicalID);
        parmas.put("Doctor",Doctor);
        parmas.put("Nurse",Nurse);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
