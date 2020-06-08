package com.example.mypregnant;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mypregnant.Function.RepositoryFucntion;


public class RegisterFragment extends Fragment {

    int position;
    public RegisterFragment(int position){
        this.position=position;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_register, container, false);
        String [][] totalText={{"帳　　號","名　　稱"},{"密　　碼","確認密碼"},{"生　　日","最後一次\n月經初日"},{"身　　高","體　　重"},{"診　　室","建  卡  號"},{"診室醫生","診室護士"}};
        TextView firstTitle=view.findViewById(R.id.registerFirstTitle);
        EditText firstText=view.findViewById(R.id.registerFirstEditText);
        TextView secondTitle=view.findViewById(R.id.registerSecondTitle);
        EditText secondText=view.findViewById(R.id.registerSecondEditText);
        firstTitle.setText(totalText[position][0]);
        secondTitle.setText(totalText[position][1]);
        if(position==1){
            firstText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            secondText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        }
        else if(position==2){
            RepositoryFucntion.setDateText(firstText,getActivity(),false);
            RepositoryFucntion.setDateText(secondText,getActivity(),false);
            LinearLayout textLayout=view.findViewById(R.id.registerTextLayout);
            textLayout.setFocusable(true);
            textLayout.setFocusableInTouchMode(true);
            textLayout.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        }
        else if(position==3){
            firstText.setInputType(InputType.TYPE_CLASS_NUMBER);
            secondText.setInputType(InputType.TYPE_CLASS_NUMBER);
        }
        return view;
    }

}
