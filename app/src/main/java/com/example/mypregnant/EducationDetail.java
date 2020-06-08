package com.example.mypregnant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.widget.TextView;

import com.example.mypregnant.Function.ToolBarFunction;
import com.example.mypregnant.ObjectClasses.MedicalEducation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class EducationDetail extends AppCompatActivity {

    MedicalEducation thisEducation;
    TextView detailName,detailContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education_detail);
        ToolBarFunction.setToolBarInit(this,"衛教資訊");
        thisEducation=(MedicalEducation) getIntent().getExtras().getSerializable("EducationNode");
        detailName=findViewById(R.id.educationDetailName);
        detailContent=findViewById(R.id.educationDetailContent);
        detailName.setText(thisEducation.getEducationName());

        detailContent.setText(Html.fromHtml(thisEducation.getContent(), new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {

                InputStream is = null;
                try {
                    is = (InputStream) new URL(source).getContent();
                    Drawable d = Drawable.createFromStream(is, "src");
                    d.setBounds(0, 0, d.getIntrinsicWidth(),
                            d.getIntrinsicHeight());
                    is.close();
                    return d;
                } catch (Exception e) {
                    return null;
                }

            }
        },null));

    }
}
