package com.example.mypregnant.DatabaseClasses;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class GetQuestionnaireQuestions extends StringRequest {
    private static final String LOGIN_REQUEST_URL=DatabaseInfo.ip+"getquestionnairequestions.php";
    private Map<String,String> parmas;
    public GetQuestionnaireQuestions(String questionnaireID, Response.Listener<String> listener) {
        super(Request.Method.POST,LOGIN_REQUEST_URL,listener,null);
        parmas=new HashMap<String,String>();
        parmas.put("QuestionnaireID",questionnaireID);
    }

    @Override
    public Map<String,String> getParams()
    {
        return parmas;
    }
}
