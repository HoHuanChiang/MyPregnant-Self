package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdatePhysical extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"updatephysical.php";
    private Map<String,String> parmas;
    public UpdatePhysical(String inputJSON,String category, Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("inputJSON",inputJSON);
        parmas.put("category",category);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
