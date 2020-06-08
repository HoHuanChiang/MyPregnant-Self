package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsertUserInfo extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"insertuserinfo.php";
    private Map<String,String> parmas;
    public InsertUserInfo(String Account,String Name, String BirthdayDate, String Password,String LastPeriodDate,String Weight,String Height,String Room,String MedicalID,String Doctor,String Nurse, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("Account",Account);
        parmas.put("Name",Name);
        parmas.put("BirthdayDate",BirthdayDate);
        parmas.put("Password",Password);
        parmas.put("LastPeriodDate",LastPeriodDate);
        parmas.put("Weight",Weight);
        parmas.put("Height",Height);
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
