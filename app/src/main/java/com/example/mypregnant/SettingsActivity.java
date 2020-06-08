package com.example.mypregnant;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

import com.example.mypregnant.Function.ToolBarFunction;

public class SettingsActivity extends AppCompatActivity {
    LinearLayout settingBasic,settingPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        ToolBarFunction.setToolBarInit(this,"設定");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        settingBasic=findViewById(R.id.settingBasic);
        settingPassword=findViewById(R.id.settingBasicPassword);
        settingBasic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(SettingsActivity.this,BasicInfoEdit.class);
                startActivity(intent);
            }
        });
        settingPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                intent.setClass(SettingsActivity.this,BasicPasswordEdit.class);
                startActivity(intent);
            }
        });
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
            Preference p=findPreference("messageNotify");
            p.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Toast.makeText(getActivity(), newValue.toString(), Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }
    }
}