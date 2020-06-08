package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetMainProducts extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getshopmainProduct.php";
    private Map<String,String> parmas;
    public GetMainProducts(String Category, String Session, String CurrentSession, Response.Listener<String> listener) {
        super(Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("Category",Category);
        parmas.put("Session",Session);
        parmas.put("CurrentSession",CurrentSession);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
